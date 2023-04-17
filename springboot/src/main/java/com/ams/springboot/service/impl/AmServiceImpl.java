package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Am;
import com.ams.springboot.mapper.AmMapper;
import com.ams.springboot.service.IAmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AmServiceImpl extends ServiceImpl<AmMapper, Am> implements IAmService {

}
