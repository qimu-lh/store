package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Authority;
import com.ytu.jierui.store.entity.Boss;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BossMapperTests {

    @Autowired
    private BossMapper bossMapper;

    @Test
    public void selectAuthority(){
        Authority authority = bossMapper.findAuthorityByBid(1);
        System.out.println(authority);
    }
}
