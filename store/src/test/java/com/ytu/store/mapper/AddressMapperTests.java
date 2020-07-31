package com.ytu.store.mapper;

import com.ytu.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

    @Autowired
    private AddressMapper mapper;

    @Test
    public void save() {
        Address address = new Address();
        address.setUid(1);
        address.setName("Lucy");
        address.setProvinceCode("123456");
        Integer rows = mapper.save(address);
        System.err.println("rows=" + rows);
    }

    @Test
    public void countByUid() {
        Integer uid = 1;
        Integer count = mapper.countByUid(uid);
        System.err.println("count=" + count);
    }

    @Test
    public void findByUid() {
        Integer uid = 3;
        List<Address> list = mapper.findByUid(uid);
        System.err.println("count=" + list.size());
        for (Address item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findByIsDefaultUid(){
        Integer uid=3;
        Address address=mapper.findByIsDefaultUid(uid);
        System.err.println(address);
    }
}
