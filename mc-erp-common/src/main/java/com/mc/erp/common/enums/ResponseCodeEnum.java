package com.mc.erp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/6/7 0:20
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    SUCCESS(20000, "请求成功"),

    REQUEST_METHOD_NOT_SUPPORT(40003, "请求方式不被支持"),

    FAILED(50000, "操作失败"),
    ERROR(50001, "请求发生错误"),
    PARAM_ERROR(50002, "请求参数错误"),
    DB_ERROR(50003, "数据库操作异常"),
    NO_RESOURCE(50004, "未找到资源"),

    SYSTEM_EXCEPTION(99999, "系统异常"),

    ;

    private final Integer code;

    private final String message;
}
