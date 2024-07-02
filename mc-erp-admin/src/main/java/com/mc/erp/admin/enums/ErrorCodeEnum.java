package com.mc.erp.admin.enums;

import com.mc.erp.common.enums.ErrorCodeEnumInter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/7/1 18:49
 **/
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements ErrorCodeEnumInter {
    PARAM_NULL_ERROR(51001, "参数不可为空"),
    ;

    private final Integer code;

    private final String message;
}
