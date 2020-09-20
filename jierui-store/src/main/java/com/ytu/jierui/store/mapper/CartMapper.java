package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Cart;
import com.ytu.jierui.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    Integer save(Cart cart);

    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid
    );

    Integer updateNum(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime
    );

    List<CartVO> findByUid(Integer uid);

    Cart findByCid(Integer cid);

    Integer deleteByCid(Integer cid);

    Integer deleteByUid(Integer uid);
}
