package com.ams.springboot.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer studentid;

    @ApiModelProperty("社员名称")
    private String amname;

    @ApiModelProperty("社员电话")
    private String phone;

    @ApiModelProperty("社员政治面貌")
    private String status;

    @ApiModelProperty("社员加入社团时间")
    private LocalDateTime intotime;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty("社员是否删除")
    private Boolean isamdelete;

    @ApiModelProperty("社团ID")
    private Integer clubid;

    @ApiModelProperty("角色ID")
    private Integer roleid;
}
