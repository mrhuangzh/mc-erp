package com.mc.erp.admin.filter.jwt;

import com.mc.erp.admin.service.JwtService;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.enums.RequestHeaderEnum;
import com.mc.erp.common.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:42
 **/
@Slf4j
@AllArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {


    @Resource
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwtToken)) {
            throw new BusinessException(CommonErrorCodeEnum.UNAUTHORIZED);
        }
        if (jwtToken.startsWith(RequestHeaderEnum.BEARER_KEY.getAttribute())) {
            jwtToken = jwtToken.substring(RequestHeaderEnum.BEARER_KEY.getAttribute().length());
        }
        try {
            UserLoginVo userLoginInfo = jwtService.verifyJwt(jwtToken, UserLoginVo.class);

            CustomJwtAuthentication authentication = new CustomJwtAuthentication(null);
            authentication.setJwtToken(jwtToken);
            authentication.setAuthenticated(true); // 设置true，认证通过。
            authentication.setCurrentUser(userLoginInfo);
            // 认证通过后，一定要设置到SecurityContextHolder里面去。
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            // 转换异常，指定code，让前端知道时token过期，去调刷新token接口
            throw new BusinessException(CommonErrorCodeEnum.JWT_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodeEnum.JWT_INVALID);
        }
        // 放行
        filterChain.doFilter(request, response);

    }
}
