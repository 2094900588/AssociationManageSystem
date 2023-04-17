package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Role;
import com.ams.springboot.mapper.RoleMapper;
import com.ams.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
