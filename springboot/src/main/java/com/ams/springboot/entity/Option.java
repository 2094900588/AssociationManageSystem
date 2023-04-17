package com.ams.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_option")
public class Option {
    @ApiModelProperty("活动编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("活动名称")
    private String optionname;

    @ApiModelProperty("活动描述")
    private String optiondesc;

    @ApiModelProperty("活动目的")
    private String optionaim;

    @ApiModelProperty("活动参与人数")
    private Integer optionnum;

    @ApiModelProperty("活动成绩表")
    private String optionfile;

    @ApiModelProperty("活动评分")
    private String optiongrade;

    @ApiModelProperty("活动是否评分")
    private String isgrade;

    @ApiModelProperty("举办活动社团ID")
    private Integer clubid;

    @ApiModelProperty("举办活动角色ID")
    private Integer roleid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("活动时间")
    private LocalDateTime optiondate;
}
