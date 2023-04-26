package com.ams.springboot.service;

import cn.hutool.json.JSONArray;
import com.ams.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ICourseService extends IService<Course> {
    public JSONArray getall();
}
