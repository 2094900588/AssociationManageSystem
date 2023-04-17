package com.ams.springboot.service;

import com.ams.springboot.controller.dto.UserDTO;
import com.ams.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dengc
 * @since 2023-03-28
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    Boolean register(User user);
}
