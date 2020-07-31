package com.ytu.store.mapper;

import com.ytu.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    /**
     * 插入收货地址
     * @param address
     * @return
     */
    Integer save(Address address);

    Integer deleteByAid(Integer aid);

    Integer updateDefault(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime
    );

    Integer updateNonDefault(Integer uid);

    /**
     * 根据uid查询用户收货地址数量
     * @param uid
     * @return
     */
    Integer countByUid(Integer uid);

    /**
     * 根据uid查询出用户所有的收货地址列表
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    Address findByAid(Integer aid);

    Address findLastModified(Integer uid);

    /**
     * 根据用户id和is_default查询用户数据
     * @return
     */
    Address findByIsDefaultUid(Integer uid);
}
