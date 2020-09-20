package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Product;
import com.ytu.jierui.store.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    @GetMapping("hot")
    public String getHotList(Model model) {
        // 调用业务层对象查询数据
        List<Product> data = productService.getHotList();
        model.addAttribute("data", data);
        // 响应成功，和数据
        return "index";
    }

    @GetMapping("{pid}")
    public String getByPid(@PathVariable("pid") Integer pid,Model model){
        // 执行查询
        Product product = productService.findByPid(pid);
        model.addAttribute("product", product);
        // 响应成功，及查询结果
        return "shop-single";
    }
}
