package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Boss;
import com.ytu.jierui.store.service.IBossService;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("bosses")
public class BossController extends BaseController{

    @Autowired
    private IBossService iBossService;

    @RequestMapping("login")
    public JsonResult<Boss> login(String bossname, String password, HttpSession session) {
        Boss data = iBossService.login(bossname, password);
        session.setAttribute("bid", data.getBid());
        session.setAttribute("bossname", data.getBossname());
        return new JsonResult<Boss>(SUCCESS , data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword, HttpSession session) {
        Integer bid = getBidFromSession(session);
        String bossname = getBossnameFromSession(session);
        iBossService.changePassword(bid, bossname, oldPassword, newPassword);
        return new JsonResult<>(SUCCESS);
    }
}
