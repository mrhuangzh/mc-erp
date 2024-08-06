package com.mc.erp.admin.controller;

import com.mc.erp.admin.domain.param.UsernameLoginParam;
import com.mc.erp.admin.service.AuthService;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证
 *
 * @author: mrhuangzh
 * @date: 2024/7/5 14:28
 **/
@RestController
@RequestMapping("/auth")
@Tag(name = "认证", description = "认证相关接口")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/account/login")
    @Operation(summary = "账号密码登录", description = "账号密码登录")
    public CommonResponse<UserLoginVo> usernameLogin(@RequestBody UsernameLoginParam param) {
        UserLoginVo currentUser = authService.usernameLogin(param);
        return CommonResponse.success(currentUser);
    }

}
