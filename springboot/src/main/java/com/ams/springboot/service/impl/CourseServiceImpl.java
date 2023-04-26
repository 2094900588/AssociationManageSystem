package com.ams.springboot.service.impl;


import cn.hutool.json.JSONArray;
import com.ams.springboot.entity.Course;
import com.ams.springboot.mapper.CourseMapper;
import com.ams.springboot.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public JSONArray getall() {
        return courseMapper.getall();
    }
}
