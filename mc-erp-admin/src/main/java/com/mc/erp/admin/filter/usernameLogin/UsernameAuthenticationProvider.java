package com.mc.erp.admin.filter.usernameLogin;

import com.mc.erp.admin.domain.entity.User;
import com.mc.erp.admin.service.UserService;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:13
 **/
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(Object::toString).orElse(null);
        String password = Optional.ofNullable(authentication)
                .map(Authentication::getCredentials)
                .map(Object::toString).orElse(null);
        User user = userService.getByAccount(account);
        if (user == null
                || !passwordEncoder.matches(password, user.getPassword())) {
            // 密码错误，直接抛异常。
            // 根据SpringSecurity框架的代码逻辑，认证失败时，应该抛这个异常：org.springframework.security.core.AuthenticationException
            // BadCredentialsException 就是这个异常的子类
//            throw new BadCredentialsException("用户名或密码不正确");// 使用filter chain
            throw new BusinessException(CommonErrorCodeEnum.BAD_CREDENTIALS);
        }
        UsernameAuthentication usernameAuthentication = new UsernameAuthentication(null);
        UserLoginVo userLoginInfo = new UserLoginVo();
        BeanUtils.copyProperties(user, userLoginInfo);
        usernameAuthentication.setCurrentUser(userLoginInfo);
        usernameAuthentication.setAuthenticated(true); // 认证通过，这里一定要设成true
        return usernameAuthentication;
    }

}
