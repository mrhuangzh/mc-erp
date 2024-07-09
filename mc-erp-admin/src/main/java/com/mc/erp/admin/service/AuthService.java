package com.mc.erp.admin.service;

import com.mc.erp.admin.domain.param.UsernameLoginParam;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import org.springframework.security.core.Authentication;

/**
 * 认证
 *
 * @author: mrhuangzh
 * @date: 2024/7/5 18:03
 **/
public interface AuthService {
    UserLoginVo usernameLogin(UsernameLoginParam param);

    UserLoginVo getUserLoginVo(Authentication authentication);
}
