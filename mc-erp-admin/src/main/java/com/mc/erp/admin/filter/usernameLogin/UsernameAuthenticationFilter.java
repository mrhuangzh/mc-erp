package com.mc.erp.admin.filter.usernameLogin;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.admin.domain.param.UsernameLoginParam;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:05
 **/
public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UsernameAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
                                        AuthenticationManager authenticationManager,
                                        AuthenticationSuccessHandler authenticationSuccessHandler,
                                        AuthenticationFailureHandler authenticationFailureHandler) {
        super(pathRequestMatcher);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String requestJsonData = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        UsernameLoginParam loginParam = JSON.parseObject(requestJsonData, UsernameLoginParam.class);
        UsernameAuthentication authentication = new UsernameAuthentication(null);
        authentication.setAccount(loginParam.getAccount());
        authentication.setPassword(loginParam.getPassword());
        authentication.setAuthenticated(false); // 提取参数阶段，authenticated一定是false
        return this.getAuthenticationManager().authenticate(authentication);
    }
}
