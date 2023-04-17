package com.ams.springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_club")
public class Club {
    @ApiModelProperty("社团编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("社团名称")
    private String clubname;

    @ApiModelProperty("社团描述")
    private String clubdesc;

    @ApiModelProperty("社团创始人")
    private String clubfounder;

    @ApiModelProperty("社团创建时间")
    private LocalDateTime clubtime;

    @ApiModelProperty("社团照片")
    private String clubphoto;

    @ApiModelProperty("社团积分")
    private Integer integral;
}
