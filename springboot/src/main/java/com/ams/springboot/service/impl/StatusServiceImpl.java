package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Status;
import com.ams.springboot.mapper.StatusMapper;
import com.ams.springboot.service.IStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status> implements IStatusService {
}
