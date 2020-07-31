package com.ytu.store.mapper;

import com.ytu.store.entity.Cart;
import com.ytu.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    Integer save(Cart cart);

    /**
     * 通过uid删除所有购物车信息
     * @param uid 用户ID
     * @return 执行成功返回的行数(用户有几条就删几条)
     */
    Integer deleteByUid(Integer uid);

    Integer deleteByCid(Integer cid);

    Integer updateNum(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime
    );

    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid
    );

    List<CartVO> findByUid(Integer uid);

    Cart findByCid(Integer cid);
}
