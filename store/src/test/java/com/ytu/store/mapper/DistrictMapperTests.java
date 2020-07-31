package com.ytu.store.mapper;

import com.ytu.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {

    @Autowired
    DistrictMapper mapper;

    @Test
    public void findByParent() {
        String parent = "86";
        List<District> list = mapper.findByParent(parent);
        System.err.println("BEGIN:");
        for (District item : list) {
            System.err.println(item);
        }
        System.err.println("END.");
    }

/*    @Test
    public void findByCode() {
        String code = "130000";
        District data = mapper.findByCode(code);
        System.err.println(data);
    }*/
}
