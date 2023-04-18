package com.ams.springboot.service;

import com.ams.springboot.entity.Club;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IClubService extends IService<Club> {

    //查询社团全部人数
    public List<Club> getAllClubAndSum();

}
