package com.ytu.store.service;

import com.ytu.store.vo.CartVO;

import java.util.List;

public interface ICartService {

    void addToCart(Integer uid, Integer pid, Integer num, String username);

    void deleteByUid(Integer uid);

    void deleteByCid(Integer cid);

    /**
     * 查询某用户的购物车数据列表
     * @param uid 用户的id
     * @return 该用户的购物车数据列表
     */
    List<CartVO> getByUid(Integer uid);

    Integer addNum(Integer cid, Integer uid, String username);

    Integer subNum(Integer cid, Integer uid, String username);
}
