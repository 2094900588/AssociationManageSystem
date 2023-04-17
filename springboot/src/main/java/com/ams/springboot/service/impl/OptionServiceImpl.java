package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Option;
import com.ams.springboot.mapper.OptionMapper;
import com.ams.springboot.service.IOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl  extends ServiceImpl<OptionMapper, Option> implements IOptionService {
}
