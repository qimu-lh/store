package com.ytu.jierui.store.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie getCookieByCookieName(Cookie[] cookies,String cookieName){
        // 没有cookie信息，则返回null
        if (null == cookies) {
            return null;
        }
        Cookie cookie=null;
        // 获取cookie里面的一些用户信息
        for (Cookie item : cookies) {
            if (cookieName.equals(item.getName())) {
                cookie=item;
            }
        }
        return cookie;
    }
}
