package com.mc.erp.business.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.mc.erp.business.config.TokenCache;
import com.mc.erp.business.domain.dto.ValidateTokenResultDto;
import com.mc.erp.business.domain.param.ValidateTokenParam;
import com.mc.erp.common.enums.CommonErrorCodeEnum;
import com.mc.erp.common.enums.RequestHeaderEnum;
import com.mc.erp.common.enums.ResponseCodeEnum;
import com.mc.erp.common.response.CommonResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录信息认证
 *
 * @author: mrhuangzh
 * @date: 2024/8/2 11:28
 **/
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String VALIDATE_TOKEN_URL = "public/validate/token";


    @Value("${mc.erp.admin.url}")
    private String adminUrl;

    @Resource
    private TokenCache tokenCache;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String jwtToken = request.getHeader(AUTHORIZATION);
        if (StrUtil.isEmpty(jwtToken)) {
            extracted(response, CommonErrorCodeEnum.UNAUTHORIZED.getCode(), CommonErrorCodeEnum.UNAUTHORIZED.getMessage(),
                    JSON.toJSONString(request.getRequestURI()));
            return false;
        }
        if (jwtToken.startsWith(RequestHeaderEnum.BEARER_KEY.getAttribute())) {
            jwtToken = jwtToken.substring(RequestHeaderEnum.BEARER_KEY.getAttribute().length());
        }
        ValidateTokenResultDto tokenCache = this.tokenCache.get(jwtToken);
        if (tokenCache != null) {
            return true;
        }
        // 校验
        try {
            String result = HttpRequest.post(adminUrl + VALIDATE_TOKEN_URL).timeout(1000)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute(), request.getHeader(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute()))
                    .body(JSON.toJSONString(new ValidateTokenParam(jwtToken))).execute().body();
            CommonResponse<ValidateTokenResultDto> resultResponse = JSON.parseObject(result, CommonResponse.class);
            if (!ResponseCodeEnum.SUCCESS.getCode().equals(resultResponse.getCode())) {
                extracted(response, resultResponse.getCode(), resultResponse.getMessage(),
                        resultResponse.getData(), resultResponse.getRid(),
                        JSON.toJSONString(request.getRequestURI()));
                return false;
            } else {
                ValidateTokenResultDto dto = JSON.parseObject(JSON.toJSONString(resultResponse.getData()),
                        ValidateTokenResultDto.class);
                if (!dto.getValid()) {
                    extracted(response, CommonErrorCodeEnum.JWT_INVALID.getCode(),
                            CommonErrorCodeEnum.JWT_INVALID.getMessage(),
                            resultResponse.getData(), resultResponse.getRid(),
                            JSON.toJSONString(request.getRequestURI()));
                    return false;
                }
                if (dto.getExpiredTime() < System.currentTimeMillis()) {
                    extracted(response, CommonErrorCodeEnum.JWT_EXPIRED.getCode(),
                            CommonErrorCodeEnum.JWT_EXPIRED.getMessage(),
                            resultResponse.getData(), resultResponse.getRid(),
                            JSON.toJSONString(request.getRequestURI()));
                    return false;
                }
                this.tokenCache.put(jwtToken, dto);
            }
            return true;
        } catch (Exception e) {
            log.error("认证信息校验失败", e);
            extracted(response, CommonErrorCodeEnum.JWT_INVALID.getCode(), CommonErrorCodeEnum.JWT_INVALID.getMessage(),
                    JSON.toJSONString(request.getRequestURI()));
            return false;
        }
    }

    private static void extracted(HttpServletResponse response, Integer errorCode, String message, String uri) throws IOException {
        extracted(response, errorCode, message, null, null, uri);
    }

    private static void extracted(HttpServletResponse response, Integer errorCode, String message, Object Data, String rid, String uri) throws IOException {
        log.error("认证信息校验失败! msg: {}, uri: {}", message, uri);
        CommonResponse<Object> commonResponse = CommonResponse.restResponse(errorCode, message, Data, rid);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(commonResponse));
        writer.flush();
        writer.close();
    }

}
