package com.mc.erp.business.config;

import com.mc.erp.business.interceptor.AuthenticationInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author: mrhuangzh
 * @date: 2024/8/2 14:20
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).excludePathPatterns(unPatternUrls()).addPathPatterns("/**");

    }

    private List<String> unPatternUrls() {
        // 自定义不拦截的url
        return Arrays.asList(
                //swagger及/error不拦截
                "/swagger-ui.html",
                "/v2/**",
                "/v3/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/doc.html",
                "/swagger-resources/**",
                "/error"
        );
    }
}
