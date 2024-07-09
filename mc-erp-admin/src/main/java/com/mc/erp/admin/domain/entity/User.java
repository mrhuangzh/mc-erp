package com.mc.erp.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author mrhuangzh
 * @date 2024/07/07 12:21
 */
@Getter
@Setter
@TableName("t_user")
@Schema(title = "User对象")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "id，遵循雪花算法，为19位数字")
    @TableId("id")
    private Long id;

    @Schema(title = "账号")
    @TableField("account")
    private String account;

    @Schema(title = "姓名")
    @TableField("username")
    private String username;

    @Schema(title = "密码")
    @TableField("password")
    private String password;

    @Schema(title = "电话")
    @TableField("phone")
    private String phone;

    @Schema(title = "邮箱")
    @TableField("email")
    private String email;

    @Schema(title = "状态：0-已注册；1-已激活；2-已失效，3-已注销，4-已禁用")
    @TableField("status")
    private Integer status;

    @Schema(title = "性别：0-未知；1-男；2-女")
    @TableField("gender")
    private Integer gender;

    @Schema(title = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(title = "出生日期")
    @TableField("birth_date")
    private LocalDate birthDate;

    @Schema(title = "地址")
    @TableField("address")
    private String address;

    @Schema(title = "备注")
    @TableField("remark")
    private String remark;

    @Schema(title = "最后登录时间")
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    @Schema(title = "创建时间")
    @TableField("create_at")
    private LocalDateTime createAt;

    @Schema(title = "创建者")
    @TableField("create_by")
    private Long createBy;

    @Schema(title = "更新时间")
    @TableField("update_at")
    private LocalDateTime updateAt;

    @Schema(title = "更新者")
    @TableField("update_by")
    private Long updateBy;

    @Schema(title = "删除标志：0-未删除；1-已删除")
    @TableField("delete_flag")
    private Integer deleteFlag;
}
