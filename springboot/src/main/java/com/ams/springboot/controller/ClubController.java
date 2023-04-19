package com.ams.springboot.controller;

import com.ams.springboot.common.Constants;
import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Am;
import com.ams.springboot.entity.Club;
import com.ams.springboot.entity.User;
import com.ams.springboot.service.IAmService;
import com.ams.springboot.service.IClubService;
import com.ams.springboot.service.IUserService;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/club")
public class ClubController {
    @Resource
    private IClubService clubService;
    @Resource
    private IUserService userService;
    @Resource
    private IAmService amService;

    //增加修改同一个方法
    @PostMapping
    public Result save(@RequestBody Club club) {
        User user = TokenUtils.getCurrentUser();
        if (isPower(user)){
            if (club.getId().equals("")){
                //如果社团id为空说明是新增，直接执行既可
            return Result.success(clubService.saveOrUpdate(club));
            }else {
                if (user.getClubid()==club.getId()){
                    return Result.success(clubService.saveOrUpdate(club));
                }else {
                    return  Result.error(Constants.CODE_401,"社团信息只能由本社团会长和社团联合会管理人员更改!");
                }
            }
        }
        else{
            return  Result.error(Constants.CODE_401,"当前用户权限不足!");
        }
    }

    //根据id删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Am> amQueryWrapper=new QueryWrapper<>();
        User user = TokenUtils.getCurrentUser();
        Club club = clubService.getById(id);
        if (isPower(user)){
            if (user.getSysroleid() == 3){
                return  Result.error(Constants.CODE_401,"当前用户权限不足!");
            }else {
                userQueryWrapper.eq("clubid",club.getId());
                amQueryWrapper.eq("clubid",club.getId());
                if(userService.list(userQueryWrapper).size()==0 && amService.list(amQueryWrapper).size()==0){
                    return Result.success(clubService.removeById(id));
                }else {
                    return Result.error(Constants.CODE_600,"当前社团还存在社员或者用户，不能删除!");
                }
            }
        }
        else{
            return  Result.error(Constants.CODE_401,"当前用户权限不足!");
        }
    }

    //查询整个列表
    @GetMapping
    public Result findAll() {
        return Result.success(clubService.list());
    }

    //根据id查找单独一条记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(clubService.getById(id));
    }

    //模糊查询的时候进行添加条件既可
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String clubname) {
        QueryWrapper<Club> queryWrapper=new QueryWrapper<>();
        //让新添加的排在页面前面，进行一个排序工作
        if(!"".equals(clubname)) {
            queryWrapper.like("clubname", clubname);
        }
        queryWrapper.orderByDesc("id");
        //获取当前用户信息
//            User currentUser = TokenUtils.getCurrentUser();
//            System.out.println("获取当前用户信息==================" + currentUser.getNickname());
        return Result.success(clubService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //社团排名api接口
    @GetMapping("/page/ranking")
    public Result findPageranking(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize
                           ) {
        QueryWrapper<Club> queryWrapper=new QueryWrapper<>();
        //对社团积分进行排名，实现社团排名的数据
        queryWrapper.orderByDesc("integral");
        return Result.success(clubService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //社团总人数api接口
    @GetMapping("/page/sum")
    public Result findPageSum()
    {
        return Result.success(clubService.getAllClubAndSum());
    }


    //进行权限验证
    public  boolean isPower(User user){
        if (user.getSysroleid()==0 || user.getSysroleid()==1 || user.getSysroleid()==2 || user.getSysroleid()==3){
            return true;
        }else {
            return false;
        }
    }

}
