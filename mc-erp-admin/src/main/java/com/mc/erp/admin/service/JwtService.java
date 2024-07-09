package com.mc.erp.admin.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:21
 **/
public interface JwtService extends InitializingBean {

    String createJwt(Object jwtPayload, Long expiredAt);

    <T> T verifyJwt(String jwt, Class<T> jwtPayloadClass);
}
