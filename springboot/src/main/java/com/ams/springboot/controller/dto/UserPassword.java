package com.ams.springboot.controller.dto;


import lombok.Data;

//接受前端发起的修改密码的请求
@Data
public class UserPassword {
    private Integer Id;
    private String oldpassword;
    private String secondpassword;
    private String newpassword;
}
