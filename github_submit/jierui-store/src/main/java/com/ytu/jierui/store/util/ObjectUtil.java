package com.ytu.jierui.store.util;

import com.ytu.jierui.store.entity.Business;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ObjectUtil {

    public static Business toBusiness(String business){
        Business business1=new Business();
        String businesses[] = business.split("&");
        try {
            business1.setBusinessName(URLDecoder.decode(businesses[0].split("=")[1], "UTF-8"));
            business1.setIdCard(URLDecoder.decode(businesses[1].split("=")[1], "UTF-8"));
            business1.setProvince(URLDecoder.decode(businesses[2].split("=")[1], "UTF-8"));
            business1.setCity(URLDecoder.decode(businesses[3].split("=")[1],"UTF-8"));
            business1.setDistrict(URLDecoder.decode(businesses[4].split("=")[1],"UTF-8"));
            business1.setAddress(URLDecoder.decode(businesses[5].split("=")[1],"UTF-8"));
            business1.setPhone(URLDecoder.decode(businesses[6].split("=")[1],"UTF-8"));
            business1.setEmail(URLDecoder.decode(businesses[7].split("=")[1],"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return business1;
    }
}
