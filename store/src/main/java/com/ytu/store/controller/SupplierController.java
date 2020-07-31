package com.ytu.store.controller;

import com.ytu.store.entity.Supplier;
import com.ytu.store.service.ISupplierService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("suppliers")
public class SupplierController extends BaseController{

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("login")
    public JsonResult<Supplier> login(String name, String password, HttpSession session) {
        Supplier data = supplierService.login(name, password);
        session.setAttribute("gid", data.getGid());
        session.setAttribute("supplierName", data.getSupplierName());
        return new JsonResult<Supplier>(SUCCESS , data);
    }
}
