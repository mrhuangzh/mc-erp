package com.mc.erp.admin.controller;

import com.mc.erp.admin.domain.dto.ValidateTokenResultDto;
import com.mc.erp.admin.domain.param.ValidateTokenParam;
import com.mc.erp.admin.domain.vo.UserLoginVo;
import com.mc.erp.admin.service.JwtService;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.exception.BusinessException;
import com.mc.erp.common.response.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无需认证即可访问的接口
 *
 * @author: mrhuangzh
 * @date: 2024/8/2 11:22
 **/
@RestController
@RequestMapping("/public")
@Tag(name = "公开接口", description = "无需认证即可访问的接口")
public class PublicController {

    @Resource
    JwtService jwtService;

    @PostMapping("/validate/token")
    @Operation(summary = "账号密码登录", description = "账号密码登录")
    public CommonResponse<ValidateTokenResultDto> validateToken(@RequestBody ValidateTokenParam param) {
        ValidateTokenResultDto dto = new ValidateTokenResultDto();
        try {
            UserLoginVo userLoginVo = jwtService.verifyJwt(param.getToken(), UserLoginVo.class);
            dto.setValid(true);
            dto.setExpiredTime(userLoginVo.getExpiredTime());
        } catch (ExpiredJwtException e) {
            throw new BusinessException(CommonErrorCodeEnum.JWT_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodeEnum.JWT_INVALID);
        }
        return CommonResponse.success(dto);
    }
}
