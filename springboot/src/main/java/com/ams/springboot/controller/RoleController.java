package com.ams.springboot.controller;


import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Am;
import com.ams.springboot.entity.Role;
import com.ams.springboot.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    //增加修改同一个方法
    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    //根据id删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String rolename) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        //让新添加的排在页面前面，进行一个排序工作
        if(!"".equals(rolename)) {
            queryWrapper.like("rolename", rolename);
        }
        queryWrapper.orderByDesc("id");
        //获取当前用户信息
//            User currentUser = TokenUtils.getCurrentUser();
//            System.out.println("获取当前用户信息==================" + currentUser.getNickname());
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
}
