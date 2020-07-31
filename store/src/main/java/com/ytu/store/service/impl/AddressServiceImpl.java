package com.ytu.store.service.impl;

import com.ytu.store.entity.Address;
import com.ytu.store.entity.District;
import com.ytu.store.mapper.AddressMapper;
import com.ytu.store.service.IAddressService;
import com.ytu.store.service.IDistrictService;
import com.ytu.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Override
    public void addnew(Address address, Integer uid, String username) {
        // 根据参数uid获取该用户的收货地址数据的数量
        Integer count = addressMapper.countByUid(uid);
        // 判断数量是否大于或等于限制值
        if (count >= ADDRESS_MAX_COUNT) {
            // 是：抛出AddressSizeLimitException
            throw new AddressSizeLimitException("增加收货地址失败！当前收货地址数量已经达到上限！最多允许创建"
                    + ADDRESS_MAX_COUNT + "条，已经创建" + count + "条！");
        }
        // 判断收货地址数据的数量是否为0
        // 是：即将插入的收货地址是默认的，isDefault=1
        // 否：即将插入的收货地址不是默认的，isDefault=0
        Integer isDefault = count == 0 ? 1 : 0;
        // 补全数据：将isDefault封装到参数address中
        address.setIsDefault(isDefault);
        // 补全数据：将参数uid封装到参数address中
        address.setUid(uid);
        // 补全数据：省、市、区的名称
        District province = districtService.getByCode(address.getProvinceCode());
        if (province != null) {
            address.setProvinceName(province.getName());
        }
        District city = districtService.getByCode(address.getCityCode());
        if (city != null) {
            address.setCityName(city.getName());
        }
        District area = districtService.getByCode(address.getAreaCode());
        if (area != null) {
            address.setAreaName(area.getName());
        }
        // 补全数据：将参数username封装为参数address中的createdUser和modifiedUser属性值
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        // 补全数据：创建当前时间对象，封装为参数address中的createdTime和modifiedTime属性值
        Date now = new Date();
        address.setCreatedTime(now);
        address.setModifiedTime(now);

        // 执行插入数据，并获取返回值
        Integer rows = addressMapper.save(address);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException("插入收货地址数据时出现未知错误");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        return addressMapper.findByUid(uid);
    }

    @Override
    @Transactional
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据参数aid查询收货地址数据
        Address result = addressMapper.findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出AddressNotFoundException
            throw new AddressNotFoundException("收货地址数据不存在，可能已经被删除");
        }

        // 判断查询结果中的uid和参数uid是否不一致，使用equals()而不要使用!=
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 将当前用户的所有收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时出现未知错误[1]");
        }

        // 将指定的收货地址设置为默认
        rows = addressMapper.updateDefault(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时出现未知错误[2]");
        }
    }

    @Override
    @Transactional
    public void delete(Integer aid, Integer uid, String username) {
        // 根据aid查询收货地址数据
        Address result = addressMapper.findByAid(aid);
        // 判断结果是否为null
        if (result == null) {
            // 是：抛出AddressNotFoundException
            throw new AddressNotFoundException(
                    "删除收货地址失败！尝试操作的数据不存在！");
        }

        // 判断结果中的uid与参数uid是否不一致
        if (result.getUid()!=uid) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException(
                    "删除收货地址失败！不允许访问他人的数据！");
        }

        // 执行删除
        addressMapper.deleteByAid(aid);

        // 判断此前的查询结果中的isDefault是否为0
        if (result.getIsDefault() == 0) {
            // return;
            return;
        }

        // 统计当前用户的收货地址数量：countByUid()
        Integer count = addressMapper.countByUid(uid);
        // 判断剩余收货地址数量是否为0
        if (count == 0) {
            // return;
            return;
        }

        // 查询当前用户最近修改的收货地址
        Address lastModifiedAddress = addressMapper.findLastModified(uid);
        // 将最近修改的收货地址设置为默认
        addressMapper.updateDefault(lastModifiedAddress.getAid(), username, new Date());
    }

    @Override
    public Address getAddress(Integer uid) {
        return addressMapper.findByIsDefaultUid(uid);
    }

    /**
     * 根据收货地址的数据id查询详情
     * @param aid 收货地址的数据id
     * @return 匹配的收货地址的详情，如果没有匹配的数据，则返回null
     */
    public Address getByAid(Integer aid) {
        return addressMapper.findByAid(aid);
    }

}
