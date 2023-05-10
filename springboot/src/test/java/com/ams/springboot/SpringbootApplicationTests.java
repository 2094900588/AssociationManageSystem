package com.ams.springboot;

import com.ams.springboot.entity.Club;
import com.ams.springboot.mapper.ClubMapper;
import com.ams.springboot.mapper.CourseMapper;
import com.ams.springboot.service.IClubService;
import com.ams.springboot.utils.AliOssUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

import java.nio.file.Files;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ClubMapper clubMapper;
    @Test
    void contextLoads() throws IOException {
//        Page<Club> page = new Page<>(1,1);
//        IPage<Club> clubIPage = clubMapper.getAllClub(page,new QueryWrapper<>());
//        System.out.println(clubIPage.getRecords());
//        System.out.println(clubMapper.getAllClub(new QueryWrapper<>()));
    }

}
