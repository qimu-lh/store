package com.ytu.store.controller;

import com.ytu.store.entity.Operator;
import com.ytu.store.service.IOperatorService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("operators")
public class OperatorController extends BaseController {

    @Autowired
    private IOperatorService operatorService;

    @RequestMapping("login")
    public JsonResult<Operator> login(String name, String password, HttpSession session) {
        Operator data = operatorService.login(name, password);
        session.setAttribute("yid", data.getYid());
        session.setAttribute("operatorName", data.getOperatorName());
        return new JsonResult<Operator>(SUCCESS , data);
    }
}
