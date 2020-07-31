package com.ytu.store.service;


import com.ytu.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {

    @Autowired
    IDistrictService districtService;

    @Test
    public void getByParent() {
        String parent = "86";
        List<District> list = districtService.getByParent(parent);
        System.err.println("BEGIN:");
        for (District item : list) {
            System.err.println(item);
        }
        System.err.println("END.");
    }

    @Test
    public void getByCode() {
        String code = "110000";
        District result = districtService.getByCode(code);
        System.err.println(result);
    }
}
