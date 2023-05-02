package com.ams.springboot.controller;


import cn.hutool.json.JSONObject;
import com.ams.springboot.common.Result;
import com.ams.springboot.service.IAmService;
import com.ams.springboot.service.IClubService;
import com.ams.springboot.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sys")
public class HomeController {

    @Resource
    IClubService clubService;

    @Resource
    IAmService amService;

    @Resource
    IUserService userService;

    @GetMapping("getinfo")
    public Result findAll() {
        JSONObject resp = new JSONObject();
        resp.set("count_club",clubService.count());
        resp.set("count_am",amService.count());
        resp.set("count_user",userService.count());

        return Result.success(resp);
    }
}
