package com.mc.erp.admin.handler.exception;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未认证
 *
 * @author: mrhuangzh
 * @date: 2024/7/4 15:17
 **/
public class CustomAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResponse.failed(CommonErrorCodeEnum.UNAUTHORIZED)));
        writer.flush();
        writer.close();
    }
}
