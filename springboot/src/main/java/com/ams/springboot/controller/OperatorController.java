package com.ams.springboot.controller;


import com.ams.springboot.common.Constants;
import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Operator;
import com.ams.springboot.entity.User;
import com.ams.springboot.service.IAmService;
import com.ams.springboot.service.IOperatorService;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    @Resource
    private IOperatorService operatorService;
    @Resource
    private IAmService amService;

    //增加修改同一个方法
    @PostMapping("/{id}")
    public Result revoke(@PathVariable Integer id) {
        User user = TokenUtils.getCurrentUser();
        if (isPower(user)){
            Operator operator = operatorService.getById(id);
            amService.getById(operator.getObjectid()).setIsamdelete(0);
            //点击撤销时置为1
            operator.setCloperate(1);
            return Result.success(operatorService.saveOrUpdate(operator));
        }
        return Result.error(Constants.CODE_401,"当前用户权限不足！");
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(operatorService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(operatorService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Operator> queryWrapper=new QueryWrapper<>();
        //让新添加的排在页面前面，进行一个排序工作
        queryWrapper.orderByDesc("id");
        //获取当前用户信息
//            User currentUser = TokenUtils.getCurrentUser();
//            System.out.println("获取当前用户信息==================" + currentUser.getNickname());
        return Result.success(operatorService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //进行权限验证
    public  boolean isPower(User user){
        if (user.getSysroleid()==0){
            return true;
        }else {
            return false;
        }
    }
}
