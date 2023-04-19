package com.ams.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2  //开启Swagger
public class SwaggerConfig {

    //配置了Swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("DC")
                //是否启用Swagger
                //.enable(true)
                .select()
                //RequestHandlerSelectors，配置要扫描的接口方式
                //basePackage指定要扫描的包
                //any()扫描全部
                //none()不扫描
                //withClassAnnotation()：扫描类上的注解，参与是一个注解的反射对象
                //withMethodAnnotation：扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.ams.springboot.controller"))
                //过滤什么路径
                //.paths(PathSelectors.ant("/api/**"))
                .build();
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
       Contact contact = new Contact("DC大锅", "https://www.baidu.com", "2242551679@qq.com");
        return   new ApiInfo(
                "dengc的SwaggerAPI",
                "用于后端接口测试，降低集成的风险",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
