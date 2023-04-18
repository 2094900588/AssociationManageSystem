package com.ams.springboot;

import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Club;
import com.ams.springboot.service.IClubService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Resource
    private IClubService clubService;
    @Test
    void contextLoads() {
        List<Club> clubList =  clubService.getAllClubAndSum();
        System.out.println(clubList);
    }

}
