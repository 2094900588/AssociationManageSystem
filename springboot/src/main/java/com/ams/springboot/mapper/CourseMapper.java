package com.ams.springboot.mapper;

import cn.hutool.json.JSONArray;
import com.ams.springboot.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface CourseMapper extends BaseMapper<Course> {
    public JSONArray getall();
}
