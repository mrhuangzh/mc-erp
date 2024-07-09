package com.mc.erp.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.admin.service.JwtService;
import com.mc.erp.common.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: mrhuangzh
 * @date: 2024/7/4 20:22
 **/
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${login.jwt.private-key}")
    private String privateKeyBase64;

    @Value("${login.jwt.public-key}")
    private String publicKeyBase64;
    private PrivateKey privateKey;
    private JwtParser jwtParser;

    /**
     * 获取私钥，用于生成Jwt
     *
     * @return 私钥
     */
    private PrivateKey getPrivateKey() {
        try {
            // 利用JDK自带的工具生成私钥
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(Decoders.BASE64.decode(privateKeyBase64));
            return kf.generatePrivate(ks);
        } catch (Exception e) {
            log.error("获取Jwt私钥失败", e);
            throw new BusinessException("获取Jwt私钥失败");
        }
    }

    /**
     * 获取公钥，用于解析Jwt
     *
     * @return 公钥
     */
    private JwtParser getJwtParser() {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Decoders.BASE64.decode(publicKeyBase64));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pk = keyFactory.generatePublic(keySpec);
            return Jwts.parserBuilder().setSigningKey(pk).build();
        } catch (Exception e) {
            log.error("获取Jwt公钥失败", e);
            throw new BusinessException("获取Jwt公钥失败");
        }
    }

    /**
     * 生成 token
     *
     * @param jwtPayload
     * @param expiredAt
     * @return token
     */
    @Override
    public String createJwt(Object jwtPayload, Long expiredAt) {
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.RS256.getValue());//使用RS256签名算法
        headMap.put("typ", "JWT");
        return Jwts.builder()
                .setHeader(headMap)
                .setClaims(JSON.parseObject(JSON.toJSONString(jwtPayload), HashMap.class))
                .setExpiration(new Date(expiredAt))
                .signWith(privateKey)
                .compact();
    }

    /**
     * 验证 token
     *
     * @param jwt
     * @param jwtPayloadClass
     * @param <T>
     * @return token
     */
    @Override
    public <T> T verifyJwt(String jwt, Class<T> jwtPayloadClass) {

        if (jwt == null || jwt.isEmpty()) {
            return null;
        }
        Jws<Claims> jws = this.jwtParser.parseClaimsJws(jwt); // 会校验签名，校验过期时间
        Claims jwtPayload = jws.getBody();
        if (jwtPayload == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(jwtPayload), jwtPayloadClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        privateKey = getPrivateKey();
        jwtParser = getJwtParser();
    }

}
