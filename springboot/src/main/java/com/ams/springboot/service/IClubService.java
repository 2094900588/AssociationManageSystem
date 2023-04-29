package com.ams.springboot.service;

import com.ams.springboot.entity.Club;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IClubService extends IService<Club> {

    //查询社团全部人数
    public IPage<Club> getAllClubAndSum(Page<Club> page, Wrapper<Club> wrapper);

    public IPage<Club> getAllClub(Page<Club> page, Wrapper<Club> wrapper);

}
