package com.ams.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfigProperties {
    //地域节点
    @Value("${aliyunoss.endpoint}")
    private String endpoint;
    //个人域名

    private String domain;
    //bucketName
    @Value("${aliyunoss.bucketName}")
    private String bucketName;
    @Value("${aliyunoss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyunoss.accessKeySecret}")
    private String accessKeySecret;

}
