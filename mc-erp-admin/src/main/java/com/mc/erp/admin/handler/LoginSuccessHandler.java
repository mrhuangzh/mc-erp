package com.mc.erp.admin.handler;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.admin.service.AuthService;
import com.mc.erp.admin.service.JwtService;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.common.response.CommonResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:20
 **/
@Component
public class LoginSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    @Value("${login.jwt.expire-time}")
    private Long expireTime;

    @Resource
    private JwtService jwtService;

    @Resource
    private AuthService authService;

    public LoginSuccessHandler() {
        this.setRedirectStrategy(new RedirectStrategy() {
            @Override
            public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) {
                // 更改重定向策略，前后端分离项目，后端使用RestFul风格，无需做重定向
                // Do nothing, no redirects in REST
            }
        });
    }

    /**
     * 登录成功，生成 token 等
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserLoginVo currentUser = authService.getUserLoginVo(authentication);
        CommonResponse<UserLoginVo> successResponse = CommonResponse.success(currentUser);
        // 虽然APPLICATION_JSON_UTF8_VALUE过时了，但也要用。因为Postman工具不声明utf-8编码就会出现乱码
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(successResponse));
        writer.flush();
        writer.close();
    }

}
