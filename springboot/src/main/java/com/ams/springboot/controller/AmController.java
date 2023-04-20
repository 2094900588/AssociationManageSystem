package com.ams.springboot.controller;

import com.ams.springboot.common.Result;
import com.ams.springboot.entity.*;
import com.ams.springboot.service.*;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/am")
public class AmController {

    @Resource
    private IAmService amService;
    @Resource
    private IClubService clubService;
    @Resource
    private IRoleService roleService;
    @Resource
    private ISysRoleService sysRoleService;
    @Resource
    private IOperatorService operatorService;

    //增加修改同一个方法
    @PostMapping
    public Result save(@RequestBody Am am) {
        return Result.success(amService.saveOrUpdate(am));
    }

    //根据id删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        User user = TokenUtils.getCurrentUser();
        Operator operator = new Operator();
        SysRole sysRole = sysRoleService.getById(user.getId());
        Club club = clubService.getById(user.getClubid());
        //填入需要填写的数据
        operator.setUserid(user.getId());
        operator.setUsername(user.getName());
        operator.setClubid(user.getClubid());
        operator.setClubname(club.getClubname());
        operator.setRoleid(user.getSysroleid());
        operator.setRolename(sysRole.getSysrolename());
        operator.setOperatename("删除社员");
        operator.setOperatetime(LocalDateTime.now());
        //默认填写0，可以进行撤销操作
        operator.setCloperate(0);
        operator.setObjectid(id);
        //数据库中插入数据
        operatorService.save(operator);
        Am am =  amService.getById(id);
        am.setIsamdelete(1);
        return Result.success(amService.saveOrUpdate(am));
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(amService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(amService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String studentid,
                           @RequestParam(defaultValue = "") String amname,
                           @RequestParam (defaultValue = "") String status) {
        User user = TokenUtils.getCurrentUser();
        QueryWrapper<Am> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("isamdelete",false);
        if (!isPower(user)){
            queryWrapper.eq("clubid",user.getClubid());
        }
        if(!"".equals(studentid)) {
            queryWrapper.like("studentid", studentid);
        }
        if(!"".equals(amname)) {
            queryWrapper.like("amname", amname);
        }
        if(!"".equals(status)) {
            queryWrapper.like("status", status);
        }
        //让新添加的排在页面前面，进行一个排序工作
        queryWrapper.orderByDesc("id");
        //获取当前用户信息
//            User currentUser = TokenUtils.getCurrentUser();
//            System.out.println("获取当前用户信息==================" + currentUser.getNickname());
        return Result.success(amService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //进行权限验证
    public  boolean isPower(User user){
        if (user.getSysroleid()==0 || user.getSysroleid()==1 || user.getSysroleid()==2){
            return true;
        }else {
            return false;
        }
    }
}
