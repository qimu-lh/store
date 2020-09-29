package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.service.ICartService;
import com.ytu.jierui.store.util.JsonResult;
import com.ytu.jierui.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("carts")
public class CartController extends BaseController{

    @Autowired
    private ICartService cartService;

    @ResponseBody
    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session) {
        // 从session中获取uid和username
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        // 调用业务层对象执行增加
        cartService.addToCart(uid,pid,num,username);
        // 响应成功
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("/")
    public String getByUid(HttpSession session, Model model) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        //User user = (User)session.getAttribute("userInfo");
        //Integer uid=user.getUid();
        // 执行查询，获取结果
        List<CartVO> data = cartService.getByUid(uid);
        model.addAttribute("carts",data);
        // 响应成功，及查询结果
        return "cart";
    }

    @ResponseBody
    @RequestMapping("{cid}/add_num")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从session中获取uid, username
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        // 调用业务对象执行增加，并获取返回结果
        Integer data = cartService.addNum(cid, uid, username);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS,data);
    }

    @ResponseBody
    @RequestMapping("{cid}/sub_num")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从session中获取uid, username
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        // 调用业务对象执行增加，并获取返回结果
        Integer data = cartService.subNum(cid, uid, username);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS,data);
    }

    @ResponseBody
    @RequestMapping("{cid}/delete_cart")
    public JsonResult<Void> deleteCart(@PathVariable("cid") Integer cid) {
        cartService.deleteByCid(cid);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("deleteAllCarts")
    public JsonResult<Void> deleteAllCarts(HttpSession session){
        Integer uid=getUidFromSession(session);
        cartService.deleteByUid(uid);
        return new JsonResult<>(SUCCESS);
    }
}
