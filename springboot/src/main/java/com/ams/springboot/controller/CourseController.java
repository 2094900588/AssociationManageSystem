package com.ams.springboot.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ams.springboot.common.Constants;
import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Course;
import com.ams.springboot.entity.User;
import com.ams.springboot.service.ICourseService;
import com.ams.springboot.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @PostMapping
    public Result save(@RequestBody JSONObject object) {
//        Integer id =object.getInt("id");
        List<Course> list=new ArrayList<>();
//        if(id !=null){
//            System.out.println("更新");
//        } else {
//            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//            if(object.getInt("amid")!=null)queryWrapper.eq("amid",object.getInt("amid"));
//            if(!"".equals(object.getStr("classid")))queryWrapper.eq("classid",object.getInt("classid"));
//
//            if(courseService.count(queryWrapper)!=0){
//                return Result.error(Constants.CODE_400,"数据库存在此数据，请使用修改功能");
//            }
        if("".equals(object.getStr("studentid")) && "".equals(object.getStr("classid")))
            return Result.error(Constants.CODE_400,"学号或班级不能为空");


        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(!"".equals(object.getStr("studentid")))queryWrapper.eq("studentid",object.getStr("studentid"));
        if(!"".equals(object.getStr("classid")))queryWrapper.eq("classid",object.getStr("classid"));
        courseService.remove(queryWrapper);

        Course course= new Course(null,object.getStr("studentid"), object.getStr("classid"),"无",
                null,null,null,null,null,null);
        JSONArray array = object.getJSONArray("course");
        for(int i=0;i<array.size();i++){
            JSONArray array1 = array.getJSONArray(i);
            for(int j=0;j<array1.size();j++){
                if(array1.getJSONObject(j).getBool("iscourse")){
                    course.setCoursedate(i);
                    course.setWeek(j);
                    course.setBegin(array1.getJSONObject(j).getInt("begin"));
                    course.setEnd(array1.getJSONObject(j).getInt("end"));
                    course.setIsoeweek(array1.getJSONObject(j).getInt("isoeweek"));
                    list.add((Course) SerializationUtils.clone(course));
                }
            }
        }
        courseService.saveBatch(list);

        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(courseService.getall());
    }

    @GetMapping("one")
    public Result getone(@RequestParam(defaultValue = "") String studentid,
                         @RequestParam(defaultValue = "") String classid) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(!"".equals(studentid))queryWrapper.eq("studentid",studentid);
        if(!"".equals(classid))queryWrapper.eq("classid",classid);

        return Result.success(courseService.list(queryWrapper));
    }

    @DeleteMapping
    public Result delete(@RequestParam(defaultValue = "") String studentid,
                         @RequestParam(defaultValue = "") String classid) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(!"".equals(studentid))queryWrapper.eq("studentid",studentid);
        if(!"".equals(classid))queryWrapper.eq("classid",classid);
        return Result.success(courseService.remove(queryWrapper));
    }

    @GetMapping("list")
    public Result show(){
        return Result.success(courseService.list());
    }

}
