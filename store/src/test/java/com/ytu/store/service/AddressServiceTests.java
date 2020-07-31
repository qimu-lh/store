package com.ytu.store.service;

import com.ytu.store.entity.Address;
import com.ytu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {

    @Autowired
    private IAddressService service;

    @Test
    public void addnew() {
        try {
            Address address = new Address();
            address.setName("Kitty");
            Integer uid = 2;
            String username = "数据管理员";
            service.addnew(address, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByAid() {
        try {
            Integer aid=4;
            Address address = service.getByAid(aid);
            System.err.println(address);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}

