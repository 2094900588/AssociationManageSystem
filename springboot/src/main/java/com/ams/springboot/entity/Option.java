package com.ams.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
