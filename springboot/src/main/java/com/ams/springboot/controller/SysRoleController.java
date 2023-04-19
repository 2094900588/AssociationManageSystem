package com.ams.springboot.controller;


import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Option;
import com.ams.springboot.entity.SysRole;
import com.ams.springboot.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sysrole")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    //增加修改同一个方法
    @PostMapping
    public Result save(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.saveOrUpdate(sysRole));
    }

    //根据id删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(sysRoleService.removeById(id));
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(sysRoleService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(sysRoleService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        //让新添加的排在页面前面，进行一个排序工作
        queryWrapper.orderByDesc("id");
        return Result.success(sysRoleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
}
