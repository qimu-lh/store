package com.ytu.store.controller;

import com.ytu.store.entity.District;
import com.ytu.store.service.IDistrictService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {

    @Autowired
    private IDistrictService districtService;

    @GetMapping("/")
    public JsonResult<List<District>> getByParent(String parent) {
        // 调用业务层对象执行查询
        List<District> data = districtService.getByParent(parent);
        // 响应成功，及查询结果
        return new JsonResult<List<District>>(SUCCESS,data);
    }
}