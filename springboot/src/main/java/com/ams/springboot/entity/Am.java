package com.ams.springboot.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_am")
public class Am {

    @ApiModelProperty("社员编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("社员学号")
    private String studentid;

    @ApiModelProperty("社员性别")
    private Integer sex;

    @ApiModelProperty("社员班号")
    private String classid;

    @ApiModelProperty("社员名称")
    private String amname;

    @ApiModelProperty("社员电话")
    private String phone;

    @ApiModelProperty("社员政治面貌")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @ApiModelProperty("社员加入社团时间")
    private Date intotime;

    @ApiModelProperty("社员是否删除")
    private Integer isamdelete;

    @ApiModelProperty("社团ID")
    private Integer clubid;

    @ApiModelProperty("角色ID")
    private Integer roleid;
}
