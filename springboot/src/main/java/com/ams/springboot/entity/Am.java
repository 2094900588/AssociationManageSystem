package com.ams.springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty("社员名称")
    private String amname;

    @ApiModelProperty("社员电话")
    private String phone;

    @ApiModelProperty("社员政治面貌")
    private String status;

    @ApiModelProperty("社员加入社团时间")
    private String intotime;

    @ApiModelProperty("社员是否删除")
    private String isamdelete;

    @ApiModelProperty("社团ID")
    private String clubid;

    @ApiModelProperty("角色ID")
    private String roleid;
}
