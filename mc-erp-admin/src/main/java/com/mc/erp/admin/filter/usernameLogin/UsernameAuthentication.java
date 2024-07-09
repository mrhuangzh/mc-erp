package com.mc.erp.admin.filter.usernameLogin;

import com.mc.erp.admin.domain.vo.UserLoginVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:08
 **/
@Setter
@Getter
public class UsernameAuthentication extends AbstractAuthenticationToken {

    private String account;
    private String password;
    private UserLoginVo currentUser;

    public UsernameAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : password;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated() ? currentUser : account;
    }
}
