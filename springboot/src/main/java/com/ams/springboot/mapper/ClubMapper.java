package com.ams.springboot.mapper;

import com.ams.springboot.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface ClubMapper extends BaseMapper<Club> {

    //查询社团全部人数
    public List<Club> getAllClubAndSum();
}
