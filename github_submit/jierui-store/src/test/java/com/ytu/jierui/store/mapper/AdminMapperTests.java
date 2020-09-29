package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMapperTests {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void findByCondition(){
        Admin byConditions = adminMapper.findByConditions(1, "");
    }
}
