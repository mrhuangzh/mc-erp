package com.mc.erp.admin.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: mrhuangzh
 * @date: 2024/7/6 19:49
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "id，遵循雪花算法，为19位数字")
    private Long id;

    @Schema(title = "账号")
    private String account;

    @Schema(title = "姓名")
    private String username;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "电话")
    private String phone;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "状态：0-已注册；1-已激活；2-已失效，3-已注销，4-已禁用")
    private Integer status;

    @Schema(title = "性别：0-未知；1-男；2-女")
    private Integer gender;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "出生日期")
    private LocalDate birthDate;

    @Schema(title = "地址")
    private String address;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "最后登录时间")
    private LocalDateTime lastLoginAt;

    @Schema(title = "创建时间")
    private LocalDateTime createAt;

    @Schema(title = "创建者")
    private Long createBy;

    @Schema(title = "更新时间")
    private LocalDateTime updateAt;

    @Schema(title = "更新者")
    private Long updateBy;

    @Schema(title = "删除标志：0-未删除；1-已删除")
    private Integer deleteFlag;

    @Schema(title = "状态")
    private List<Integer> statusList;
}
