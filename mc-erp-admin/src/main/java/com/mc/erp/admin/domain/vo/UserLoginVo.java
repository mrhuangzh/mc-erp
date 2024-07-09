package com.mc.erp.admin.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:09
 **/
@Setter
@Getter
@Schema(title = "登录信息")
public class UserLoginVo {
    @Schema(title = "id")
    private Long id;
    @Schema(title = "账号")
    private String account;
    @Schema(title = "用户名")
    private String username;
    @Schema(title = "密码")
    private String password;
    @Schema(title = "会话id")
    private String sessionId;
    @Schema(title = "过期时间")
    private Long expiredTime;
    @Schema(title = "token")
    private String token;
    @Schema(title = "用于刷新 token 的 token")
    private String refreshToken;
}
