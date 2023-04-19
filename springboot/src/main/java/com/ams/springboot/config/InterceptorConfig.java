package com.ams.springboot.config;

import com.ams.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return  new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") //拦截所有请求，通过判断token是否合法决定是否需要登录
                .excludePathPatterns("/api/user/login","/api/user/register","/**/export","/**/import","/api/file/**","/swagger-ui.html",
                        "/swagger-resources/**","/webjars/**","/v2/**"//,"/**"
                );
    }
}
