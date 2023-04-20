package com.ams.springboot.controller.dto;


import lombok.Data;

//返回给前端的登录信息
@Data
public class UserDTI {
    private String username;
    private String nickname;
    private String avatarUrl;
    private String token;
}
