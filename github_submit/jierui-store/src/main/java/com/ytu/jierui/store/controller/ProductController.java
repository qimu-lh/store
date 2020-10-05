package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Product;
import com.ytu.jierui.store.service.IProductService;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    @GetMapping("hot")
    public String getHotList(Model model) {
        Integer currentPage=0;
        Integer pageNum=4;
        // 调用业务层对象查询数据
        List<Product> data = productService.getHotList(currentPage,pageNum);
        model.addAttribute("data", data);
        // 响应成功，和数据
        return "index";
    }

    @ResponseBody
    @GetMapping("{currentPage}/pageHot/{pageNum}")
    public JsonResult<List<Product>> getHotPageList(@PathVariable("currentPage") Integer currentPage,
                                                 @PathVariable("pageNum") Integer pageNum) {
        // 调用业务层对象查询数据
        List<Product> data = productService.getHotList(currentPage,pageNum);
        // 响应成功，和数据
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{pid}")
    public String getByPid(@PathVariable("pid") Integer pid,Model model){
        // 执行查询
        Product product = productService.findByPid(pid);
        model.addAttribute("product", product);
        // 响应成功，及查询结果
        return "shop-single";
    }

    @ResponseBody
    @GetMapping("{str}/search")
    public JsonResult<List<Product>> getByStr(@PathVariable("str") String str){
        List<Product> data=productService.getByStr(str);
        return new JsonResult<>(SUCCESS,data);
    }
}
