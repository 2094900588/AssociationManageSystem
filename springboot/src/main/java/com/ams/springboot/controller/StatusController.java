package com.ams.springboot.controller;


import com.ams.springboot.common.Result;
import com.ams.springboot.service.IStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Resource
    private IStatusService statusService;

    @GetMapping
    public Result findAll() {
        return Result.success(statusService.list());
    }
}
