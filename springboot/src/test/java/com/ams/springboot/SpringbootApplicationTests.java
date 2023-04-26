package com.ams.springboot;

import com.ams.springboot.mapper.CourseMapper;
import com.ams.springboot.service.IClubService;
import com.ams.springboot.utils.AliOssUtil;
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
    @Test
    void contextLoads() throws IOException {
        System.out.println(courseMapper.getall());
    }

}
