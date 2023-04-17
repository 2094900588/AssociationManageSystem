package com.ams.springboot.service.impl;

import com.ams.springboot.entity.Club;
import com.ams.springboot.mapper.ClubMapper;
import com.ams.springboot.service.IClubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
}
