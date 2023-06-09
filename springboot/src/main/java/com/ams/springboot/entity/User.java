package com.ams.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author dengc
 * @since 2023-03-28
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
//      @TableField(select = false)
      private String password;

      @ApiModelProperty("昵称")
      private String nickname;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("电话")
      private String phone;

      @ApiModelProperty("地址")
      private String address;

      @ApiModelProperty("头像")
      private String userphoto;

      @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
      @ApiModelProperty("创建时间")
      private Date createTime;

      @ApiModelProperty("账号使用者")
      private String name;

      @ApiModelProperty("账号使用者学号")
      private String studentid;

      @ApiModelProperty("社团ID")
      private Integer clubid;

      @ApiModelProperty("角色ID")
      private Integer roleid;

      @ApiModelProperty("系统角色ID")
      private Integer sysroleid;
}
