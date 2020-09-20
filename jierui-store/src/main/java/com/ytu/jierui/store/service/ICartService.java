package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Cart;
import com.ytu.jierui.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartService {

    void addToCart(Integer uid, Integer pid, Integer num, String username);

    /**
     * 查询某用户的购物车数据列表
     * @param uid 用户的id
     * @return 该用户的购物车数据列表
     */
    List<CartVO> getByUid(Integer uid);

    Integer addNum(Integer cid, Integer uid, String username);

    Integer subNum(Integer cid, Integer uid, String username);

    void deleteByCid(Integer cid);

    void deleteByUid(Integer uid);
}
