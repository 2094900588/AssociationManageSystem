package com.ams.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("社员学号")
    private String studentid;

    @ApiModelProperty("班级编号")
    private String classid;

    @ApiModelProperty("课程名称")
    private String coursename;

    @ApiModelProperty("上课时间")
    private Integer coursedate;

    @ApiModelProperty("星期几")
    private Integer week;

    @ApiModelProperty("单双周")
    private Integer isoeweek;

    @ApiModelProperty("上课地点")
    private String courselocal;
}
