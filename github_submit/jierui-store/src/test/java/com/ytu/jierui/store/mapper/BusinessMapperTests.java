package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Business;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessMapperTests {

    @Autowired
    private BusinessMapper businessMapper;

    @Test
    public void save(){
        Business business=new Business();
        business.setBusinessName("栖木");
        business.setApplyTime(new Date());
        Integer rows = businessMapper.save(business);
        System.out.println("rows:"+rows);
    }

    @Test
    public void select(){
        System.out.println(businessMapper.selectByCardId("1"));
    }
}
