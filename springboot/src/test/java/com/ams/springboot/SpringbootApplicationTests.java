package com.ams.springboot;

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
    private IClubService clubService;
    @Test
    void contextLoads() throws IOException {

        File file = new File("C:\\Users\\Administrator\\Desktop\\index.html");
        InputStream inputStream = Files.newInputStream(file.toPath());
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),inputStream);
        System.out.println("aaaaaaa");
        System.out.println(file.getName());
        System.out.println(multipartFile.getOriginalFilename());
//        System.out.println(AliOssUtil.upload("photo/",multipartFile));
    }

}
