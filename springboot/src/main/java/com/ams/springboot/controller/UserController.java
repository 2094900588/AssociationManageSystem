package com.ams.springboot.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.ams.springboot.controller.dto.UserDTI;
import com.ams.springboot.controller.dto.UserPassword;
import com.ams.springboot.entity.User;
import com.ams.springboot.utils.MD5Utils;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ams.springboot.common.Constants;
import com.ams.springboot.common.Result;
import com.ams.springboot.controller.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.ams.springboot.service.IUserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dengc
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

        @Resource
        private IUserService userService;

        @PostMapping("/login")
        public Result login(@RequestBody UserDTO userDTO) {
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();
            if (StrUtil.isBlank(username)||StrUtil.isBlank(password)) {
                return Result.error(Constants.CODE_400,"参数错误");
            }
            UserDTO dto = userService.login(userDTO);
            UserDTI dti = new UserDTI();
            dti.setUsername(dto.getUsername());
            dti.setNickname(dto.getNickname());
            dti.setAvatarUrl(dto.getAvatarUrl());
            dti.setToken(dto.getToken());
            return Result.success(dti);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())) {
            return Result.error(Constants.CODE_400,"参数错误");
        }else {
            //对注册密码进行加密
            user.setPassword(MD5Utils.code(user.getPassword()));
            return Result.success(userService.register(user));
        }
    }

        @PostMapping
        public Result save(@RequestBody User user) {
            User user1 = TokenUtils.getCurrentUser();
            if (isPower(user1)){
                //对密码进行加密，不论修改还是新增都执行一次
                user.setPassword(MD5Utils.code(user.getPassword()));
                return Result.success(userService.saveOrUpdate(user));
            }else {
                return Result.error(Constants.CODE_401,"当前用户权限不足");
            }
        }

        //修改个人信息接口
        @PostMapping("/personupdate")
        public Result personsave(@RequestBody User user) {
                return Result.success(userService.saveOrUpdate(user));
        }

        //修改密码接口
        @PostMapping("/updatepassword")
        public Result updatepassword(@RequestBody UserPassword userPassword){
            //查出当前用户
            User user = userService.getById(userPassword.getId());
            //验证旧密码是否输入正确
            if (MD5Utils.code(userPassword.getOldpassword())==user.getPassword()){
                if (userPassword.getNewpassword()==userPassword.getSecondpassword()){
                    //对新密码进行加密
                    user.setPassword(MD5Utils.code(userPassword.getNewpassword()));
                    //进行修改密码
                    return Result.success(userService.saveOrUpdate(user));
                }else {
                    return Result.error(Constants.CODE_600,"两次密码输入不一致！");
                }
            }else {
                return Result.error(Constants.CODE_600,"旧密码输入错误！");
            }
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            User user = TokenUtils.getCurrentUser();
            if (isPower(user)){
                return Result.success(userService.removeById(id));
            }else {
                return Result.error(Constants.CODE_401,"当前用户权限不足");
            }
        }

        @GetMapping
        public Result findAll() {
            List<User> users = userService.list();
            for (User user : users) {
                user.setPassword("");
            }
            return Result.success(users);
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(userService.getById(id));
        }

        @GetMapping("/username")
        public Result findOne() {
            User user = TokenUtils.getCurrentUser();
            return Result.success(userService.getById(user.getId()));
    }

        //批量删除
        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids){
            User user = TokenUtils.getCurrentUser();
            if (isPower(user)){
                return Result.success(userService.removeByIds(ids));
            }else {
                return Result.error(Constants.CODE_401,"当前用户权限不足");
            }
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String address) {
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            if(!"".equals(username)) {
                queryWrapper.like("username", username);
            }
            if(!"".equals(email)) {
                queryWrapper.like("email", email);
            }
            if(!"".equals(address)) {
                queryWrapper.like("address", address);
            }
            queryWrapper.orderByDesc("id");
            return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }

        //导出用户信息
        @GetMapping("/export")
        public Result export(HttpServletResponse response) throws IOException {
            User user = TokenUtils.getCurrentUser();
            if (isPower(user)){
                //从数据库查询出所有数据
                List<User> list =userService.list();
                //自定义列表名
                ExcelWriter writer = ExcelUtil.getWriter(true);
//            writer.addHeaderAlias("username","用户名");
//            writer.addHeaderAlias("password","密码");
//            writer.addHeaderAlias("nickname","昵称");
//            writer.addHeaderAlias("email","邮箱");
//            writer.addHeaderAlias("phone","电话");
//            writer.addHeaderAlias("address","地址");
//            writer.addHeaderAlias("createTime","创建时间");
//            writer.addHeaderAlias("avatarUrl","头像");
                //一次性写出list对象到excel。使用默认样式，强制输出标题
                writer.write(list,true);
                //设置浏览器响应的格式
                response.setContentType("application/vnd.openxmlfonmats-officedocument.spreadsheetml.sheet;charset=utf-8");
                String fileName = URLEncoder.encode("用户信息.","UTF-8");
                response.setHeader( "Content-Disposition","attachment;filename=" + fileName +".xlsx");

                ServletOutputStream out = response.getOutputStream();
                writer.flush(out,true);
                out.close();
                writer.close();
                return Result.success(new Result(Constants.CODE_200,"成功",null));
            }else {
                return Result.error(Constants.CODE_401,"当前用户权限不足");
            }
        }

        //导入用户信息
        @PostMapping("/import")
        public  Result imp(MultipartFile file) throws IOException {
            User user = TokenUtils.getCurrentUser();
            if (isPower(user)){
                InputStream inputStream = file.getInputStream();
                ExcelReader reader = ExcelUtil.getReader(inputStream);
                //写入时模板需要使用英文表头
                List<User> list = reader.readAll(User.class);
                userService.saveBatch(list);
                return Result.success(true);
            }else
            {
                return Result.error(Constants.CODE_401,"当前用户权限不足");
            }
        }

    //进行权限验证
    public  boolean isPower(User user){
        if (user.getSysroleid()==0 || user.getSysroleid()==1){
            return true;
        }else {
            return false;
        }
    }
}
