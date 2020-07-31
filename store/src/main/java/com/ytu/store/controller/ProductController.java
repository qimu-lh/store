package com.ytu.store.controller;

import com.ytu.store.entity.Product;
import com.ytu.store.service.IProductService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    @GetMapping("hot")
    public JsonResult<List<Product>> getHotList() {
        // 调用业务层对象查询数据
        List<Product> data = productService.getHotList();
        // 响应成功，和数据
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("/")
    public JsonResult<List<Product>> getProducts() {
        // 调用业务层对象查询数据
        List<Product> data = productService.getProductsList();
        // 响应成功，和数据
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{pid}/details")
    public JsonResult<Product> getByPid(
            @PathVariable("pid") Integer pid) {
        // 执行查询
        Product data = productService.findByPid(pid);
        // 响应成功，及查询结果
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{str}/search")
    public JsonResult<List<Product>> getByStr(@PathVariable("str") String str){
        return new JsonResult<>(SUCCESS,productService.getByStr(str));
    }

    @RequestMapping("add")
    public JsonResult<Void> add(Product product){
        productService.addProduct(product);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{pid}/delete_by_pid")
    public JsonResult<Void> add(@PathVariable("pid") Integer pid){
        productService.deleteProductByPid(pid);
        return new JsonResult<>(SUCCESS);
    }

}
