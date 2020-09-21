package com.ytu.jierui.store.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class User extends BaseEntity{

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String phone;
    private String belonger;
    private Integer status;
    private Integer isFreeze;
    private Integer isDelete;
}
