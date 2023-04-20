package com.ams.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.ams.springboot.common.Constants;
import com.ams.springboot.controller.dto.UserDTO;
import com.ams.springboot.entity.User;
import com.ams.springboot.exception.ServiceException;
import com.ams.springboot.service.IUserService;
import com.ams.springboot.utils.MD5Utils;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ams.springboot.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dengc
 * @since 2023-03-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private static final Log LOG = Log.get();
    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password", MD5Utils.code(userDTO.getPassword()));
        User one = getOne(queryWrapper);
        //密码错误时使用这个抛出异常
        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    //实现注册
    @Override
    public Boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User one = getOne(queryWrapper);
        //为空说明数据库中不存在相同用户名的账号可以进行注册
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(user,one,true);
            save(one);
            System.out.println(one.getPassword());
            return true;
        }else {
            throw new  ServiceException(Constants.CODE_500,"用户名已存在");
        }
    }
}
