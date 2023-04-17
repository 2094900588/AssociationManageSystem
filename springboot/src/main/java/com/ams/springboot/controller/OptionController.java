package com.ams.springboot.controller;


import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Am;
import com.ams.springboot.entity.Option;
import com.ams.springboot.service.IOptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/option")
public class OptionController {
    @Resource
    private IOptionService optionService;

    //增加修改同一个方法
    @PostMapping
    public Result save(@RequestBody Option option) {
        return Result.success(optionService.saveOrUpdate(option));
    }

    //根据id删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(optionService.removeById(id));
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(optionService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(optionService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Option> queryWrapper=new QueryWrapper<>();
        //让新添加的排在页面前面，进行一个排序工作
        queryWrapper.orderByDesc("id");
        //获取当前用户信息
//            User currentUser = TokenUtils.getCurrentUser();
//            System.out.println("获取当前用户信息==================" + currentUser.getNickname());
        return Result.success(optionService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
}
