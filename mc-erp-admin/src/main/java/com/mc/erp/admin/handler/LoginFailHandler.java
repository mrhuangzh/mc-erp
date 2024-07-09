package com.mc.erp.admin.handler;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.admin.enums.ErrorCodeEnum;
import com.mc.erp.common.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:30
 **/
@Slf4j
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("登录失败", exception);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResponse.failed(ErrorCodeEnum.BAD_CREDENTIALS)));
        writer.flush();
        writer.close();
    }
}
