package com.ytu.store.service;

import com.ytu.store.entity.Address;

import java.util.List;

public interface IAddressService {

    /**
     * 收货地址数量上限
     */
    int ADDRESS_MAX_COUNT = 4;

    void addnew(Address address, Integer uid, String username);

    /**
     * 查询某用户的收货地址列表
     * @param uid 用户id
     * @return 该用户的收货地址列表
     */
    List<Address> getByUid(Integer uid);

    void setDefault(Integer aid, Integer uid, String username);

    void delete(Integer aid, Integer uid, String username);

    /**
     * 查询该用户设为默认的收货地址
     * @param uid
     * @return
     */
    Address getAddress(Integer uid);

    /**
     * 根据收货地址的数据id查询详情
     * @param aid 收货地址的数据id
     * @return 匹配的收货地址的详情，如果没有匹配的数据，则返回null
     */
    Address getByAid(Integer aid);
}
