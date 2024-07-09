package com.mc.erp.admin.handler.exception;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.admin.enums.ErrorCodeEnum;
import com.mc.erp.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * SpringSecurity框架捕捉到 AccessDeniedException 时<br/>
 * 或 认证成功但无权访问时
 *
 * @author: mrhuangzh
 * @date: 2024/7/4 15:40
 **/
public class CustomAuthorizationExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResponse.failed(ErrorCodeEnum.FORBIDDEN)));
        writer.flush();
        writer.close();
    }
}
