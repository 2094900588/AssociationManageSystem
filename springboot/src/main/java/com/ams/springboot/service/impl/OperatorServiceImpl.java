package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Operator;
import com.ams.springboot.mapper.OperatorMapper;
import com.ams.springboot.service.IOperatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper,Operator> implements IOperatorService{
}
