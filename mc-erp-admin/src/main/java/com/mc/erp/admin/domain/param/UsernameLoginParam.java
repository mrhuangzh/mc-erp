package com.mc.erp.admin.domain.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:07
 **/
@Setter
@Getter
@Schema(title = "账号密码登录参数")
public class UsernameLoginParam {
    @Schema(title = "账号")
    private String account;
    @Schema(title = "密码")
    private String password;
}
