package com.ams.springboot.service.impl;

import com.ams.springboot.entity.SysRole;
import com.ams.springboot.mapper.SysRoleMapper;
import com.ams.springboot.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
}
