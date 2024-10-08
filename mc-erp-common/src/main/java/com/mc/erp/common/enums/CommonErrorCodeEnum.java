package com.mc.erp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/8/2 11:33
 **/
@Getter
@AllArgsConstructor
public enum CommonErrorCodeEnum implements ErrorCodeEnumInter {

    UNAUTHORIZED(41001, "未认证"),
    FORBIDDEN(41003, "无权限"),
    BAD_CREDENTIALS(41004, "用户名或密码错误"),
    JWT_INVALID(41060, "认证信息无效"),
    JWT_EXPIRED(41061, "认证信息已过期"),
    PARAM_NULL_ERROR(41061, "参数不可为空"),

    ;

    private final Integer code;
    private final String message;
}
