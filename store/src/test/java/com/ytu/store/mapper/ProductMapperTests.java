package com.ytu.store.mapper;

import com.ytu.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    ProductMapper mapper;

    @Test
    public void updateNum(){
        Integer num=40;
        Integer pid=1;
        Integer rows = mapper.updateNum(num, pid);
        System.err.println("rows="+rows);
    }

    @Test
    public void findHotList(){
        List<Product> list = mapper.findHotList();
        System.err.println("count=" + list.size());
        for (Product item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findByStr(){
        String str="i";
        List<Product> pr = mapper.findByStr(str);
        for (Product product:
             pr) {
            System.err.println(product);
        }
    }

    @Test
    public void findByName(){
        String str="iPhone X";
        Product product= mapper.findByName(str);
        System.err.println(product);
    }

    @Test
    public void add(){
        Product product=new Product();
        product.setName("ipad");
        product.setNum(30);
        product.setImage("ceshi");
        product.setInformation("测试");
        product.setPid(3000);
        Integer rows = mapper.saveProduct(product);
        System.err.println("rows="+rows);

    }
}
