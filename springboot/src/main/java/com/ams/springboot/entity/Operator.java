package com.ams.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operate")
public class Operator {
    @ApiModelProperty("操作编号")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("操作名称")
    private String operatename;

    @ApiModelProperty("操作人员ID")
    private String userid;

    @ApiModelProperty("操作人员名称")
    private String username;

    @ApiModelProperty("操作人员角色ID")
    private String roleid;

    @ApiModelProperty("操作人员角色名称")
    private String rolename;

    @ApiModelProperty("操作人员社团ID")
    private String clubid;

    @ApiModelProperty("操作人员社团名称")
    private String clubname;

    @ApiModelProperty("操作时间")
    private String operatetime;

    @ApiModelProperty("撤销删除操作")
    @TableLogic
    private String cloperate;
}
