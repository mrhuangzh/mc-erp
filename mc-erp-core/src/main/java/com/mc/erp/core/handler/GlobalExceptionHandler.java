package com.mc.erp.core.handler;

import com.mc.erp.common.enums.RequestHeaderEnum;
import com.mc.erp.common.enums.ResponseCodeEnum;
import com.mc.erp.common.exception.BusinessException;
import com.mc.erp.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author: mrhuangzh
 * @date: 2024/6/10 6:17
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public CommonResponse<Object> handleException(Throwable e) {
        log.error("请求发生错误，错误信息：{}", e.getMessage());
        return CommonResponse.failed(ResponseCodeEnum.FAILED.getCode(), "服务器出错啦，请稍后再试哟～");
    }

    /**
     * 请求参数发生错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,
            BindException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    public Object methodArgumentNotValidHandler(BindException e) {
        log.error("请求参数发生错误，错误信息：{}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errMsg = new StringBuilder();
        for (FieldError err : fieldErrors) {
            errMsg.insert(0, err.getDefaultMessage() + ";");
        }
        return CommonResponse.failed(ResponseCodeEnum.PARAM_ERROR.getCode(), errMsg.toString());
    }

    /**
     * 请求方式不被支持
     *
     * @param req
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class})
    public CommonResponse<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException req) {
        log.error("请求方式不被支持，错误信息：", req);
        return CommonResponse.failed(ResponseCodeEnum.REQUEST_METHOD_NOT_SUPPORT);
    }

    /**
     * 数据库操作异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public CommonResponse<Object> dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("数据库操作异常，错误信息：{}", e.getMessage());
        return CommonResponse.failed(ResponseCodeEnum.DB_ERROR);
    }

    /**
     * 未找到资源
     *
     * @param e
     * @return
     */
    @ExceptionHandler({NoResourceFoundException.class})
    public CommonResponse<Object> noResourceFoundException(Exception e) {
        log.error("请求发生错误，错误信息：{}", e.getMessage());
        return CommonResponse.failed(ResponseCodeEnum.NO_RESOURCE);
    }

    /**
     * 处理多种异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class,
            MissingServletRequestParameterException.class})
    public CommonResponse<Object> handleMultiException(Exception e) {
        log.error("请求发生错误，错误信息：{}", e.getMessage());
        return CommonResponse.failed(ResponseCodeEnum.PARAM_ERROR);
    }


    @ExceptionHandler(value = Exception.class)
    public CommonResponse<Object> customizeException(HttpServletRequest req, Exception e) {
        log.error("请求发生错误，错误信息：{}", e.getMessage());
        if (e instanceof BusinessException e1) {
            return CommonResponse.restResponse(e1.getCode(), e1.getMessage(), null,
                    req.getHeader(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute()));
        }
        return CommonResponse.failed(ResponseCodeEnum.SYSTEM_EXCEPTION);
    }
}
