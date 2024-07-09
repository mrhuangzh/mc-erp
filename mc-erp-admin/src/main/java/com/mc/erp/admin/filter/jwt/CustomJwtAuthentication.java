package com.mc.erp.admin.filter.jwt;

import com.mc.erp.admin.domain.vo.UserLoginVo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:43
 **/
public class CustomJwtAuthentication extends AbstractAuthenticationToken {


    public CustomJwtAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    private String jwtToken; // 前端传过来
    private UserLoginVo currentUser; // 认证成功后，后台从数据库获取信息


    @Override
    public Object getCredentials() {
        // 根据SpringSecurity的设计，授权成后，Credential（比如，登录密码）信息需要被清空
        return isAuthenticated() ? null : jwtToken;
    }

    @Override
    public Object getPrincipal() {
        // 根据SpringSecurity的设计，授权成功之前，getPrincipal返回的客户端传过来的数据。授权成功后，返回当前登陆用户的信息
        return isAuthenticated() ? currentUser : jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public UserLoginVo getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserLoginVo currentUser) {
        this.currentUser = currentUser;
    }
}
