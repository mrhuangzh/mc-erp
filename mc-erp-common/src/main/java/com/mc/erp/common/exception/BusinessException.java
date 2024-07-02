package com.mc.erp.common.exception;

import com.mc.erp.common.enums.ErrorCodeEnumInter;
import com.mc.erp.common.enums.ResponseCodeEnum;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/6/10 6:25
 **/
@Getter
public class BusinessException extends BaseException {

    public BusinessException(ErrorCodeEnumInter errorCodeEnums) {
        super(errorCodeEnums.getCode(), errorCodeEnums.getMessage());
    }

    public BusinessException(ResponseCodeEnum responseEnum) {
        super(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BusinessException(String message) {
        super(ResponseCodeEnum.FAILED.getCode(), message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
