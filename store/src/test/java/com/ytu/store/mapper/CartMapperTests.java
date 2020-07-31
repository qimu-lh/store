package com.ytu.store.mapper;

import com.ytu.store.entity.Cart;
import com.ytu.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

    @Autowired
    private CartMapper mapper;

    @Test
    public void save() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(4);
        cart.setNum(1);
        cart.setPrice(4L);
        Integer rows = mapper.save(cart);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateNum() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "admin";
        Date modifiedTime = new Date();
        Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 1;
        Integer pid = 4;
        Cart result = mapper.findByUidAndPid(uid, pid);
        System.err.println(result);
    }

    @Test
    public void findByUid() {
        Integer uid = 3;
        List<CartVO> list = mapper.findByUid(uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findByCid() {
        Integer cid = 1;
        Cart result = mapper.findByCid(cid);
        System.err.println(result);
    }

}