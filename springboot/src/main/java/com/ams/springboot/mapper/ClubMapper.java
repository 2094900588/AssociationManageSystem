package com.ams.springboot.mapper;

import com.ams.springboot.entity.Club;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClubMapper extends BaseMapper<Club> {

    //查询社团全部人数
    public IPage<Club> getAllClubAndSum(Page<Club> page, @Param(Constants.WRAPPER) Wrapper<Club> wrapper);

    //查询社团积分总和
    public IPage<Club> getAllClub(Page<Club> page, @Param(Constants.WRAPPER) Wrapper<Club> wrapper);
}
