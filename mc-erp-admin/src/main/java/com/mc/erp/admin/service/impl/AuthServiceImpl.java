package com.mc.erp.admin.service.impl;

import com.mc.erp.admin.domain.param.UsernameLoginParam;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.admin.filter.usernameLogin.UsernameAuthenticationProvider;
import com.mc.erp.admin.service.AuthService;
import com.mc.erp.admin.service.JwtService;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证
 *
 * @author: mrhuangzh
 * @date: 2024/7/5 18:04
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${login.jwt.expire-time}")
    private Long expireTime;

    @Resource
    private JwtService jwtService;

    @Resource
    UsernameAuthenticationProvider usernameAuthenticationProvider;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    public UserLoginVo usernameLogin(UsernameLoginParam param) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(param.getAccount(), param.getPassword());
        Authentication authentication = usernameAuthenticationProvider.authenticate(authenticationToken);
        return getUserLoginVo(authentication);
    }

    /**
     * 登录成功后返回数据
     *
     * @param authentication
     * @return
     */
    public UserLoginVo getUserLoginVo(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserLoginVo currentUser)) {
            throw new BusinessException(CommonErrorCodeEnum.JWT_INVALID.getCode(),
                    "登陆认证成功后，authentication.getPrincipal()返回的Object对象必须是：UserVo！");
        }

        // 生成token和refreshToken
        Long expiredTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expireTime); // 10分钟后过期
        currentUser.setExpiredTime(expiredTime);
        String token = jwtService.createJwt(currentUser, expiredTime);
        String refreshToken = jwtService.createJwt(currentUser, expiredTime << 1); // 2倍
        currentUser.setToken(token);
        currentUser.setRefreshToken(refreshToken);
        currentUser.setSessionId(UUID.randomUUID().toString());
        return currentUser;
    }
}
