package com.mc.erp.admin.config;

import com.mc.erp.admin.handler.LoginFailHandler;
import com.mc.erp.admin.handler.LoginSuccessHandler;
import com.mc.erp.admin.filter.jwt.CustomJwtAuthenticationFilter;
import com.mc.erp.admin.service.JwtService;
import com.mc.erp.admin.filter.usernameLogin.UsernameAuthenticationFilter;
import com.mc.erp.admin.handler.exception.CustomAuthenticationExceptionHandler;
import com.mc.erp.admin.handler.exception.CustomAuthorizationExceptionHandler;
import com.mc.erp.admin.handler.exception.CustomSecurityExceptionHandler;
import com.mc.erp.admin.filter.usernameLogin.UsernameAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * security 配置
 *
 * @author: mrhuangzh
 * @date: 2024/7/4 15:14
 **/
@Configuration
public class CustomWebSecurityConfig {
    @Resource
    private ApplicationContext applicationContext;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");// 允许谁跨域
//        configuration.addAllowedOrigin("*");// 允许所有人跨域
        configuration.setAllowCredentials(true);// 传cookie
        configuration.addAllowedMethod("*");// 允许哪些方法跨域 post get
        configuration.addAllowedHeader("*");// 允许哪些头信息
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);// 拦截一切请求
        return new CorsFilter(source);
    }


    /**
     * 禁用一些默认filter，在应用启动时，将不再初始化被禁用的组件到 filter chain 中
     *
     * @param httpSecurity
     * @throws Exception
     */
    private void commonFilter(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);// 表单登录，默认自动生成
        httpSecurity.logout(AbstractHttpConfigurer::disable);// 登出
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);// 基础配置
        httpSecurity.sessionManagement(AbstractHttpConfigurer::disable);// 会话管理
        httpSecurity.csrf(AbstractHttpConfigurer::disable);// CSRF 保护，默认激活
        httpSecurity.requestCache(AbstractHttpConfigurer::disable);// 重定向，认证前访问A，将被转到认证页面，认证后重定向回A
        httpSecurity.anonymous(AbstractHttpConfigurer::disable);// 匿名用户默认不为 null，且拥有权限 ROLE_ANONYMOUS
        httpSecurity.exceptionHandling(exception ->
                exception.authenticationEntryPoint(new CustomAuthenticationExceptionHandler())
                        .accessDeniedHandler(new CustomAuthorizationExceptionHandler()));
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.addFilterBefore(new CustomSecurityExceptionHandler(), SecurityContextHolderFilter.class);
    }

    /**
     * 通过指定 controller 接口的方式登录
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain usernameLoginControllerFilter(HttpSecurity httpSecurity) throws Exception {
        commonFilter(httpSecurity);
        httpSecurity.securityMatcher("/auth/account/login").authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    /**
     * 通过 filter chain 用户名密码登录
     * 这种方式登录的弊端之一就是，必须操控 HttpServletResponse.getWriter().print()完成接口响应
     * 另外，由于 filter chain 的异常不走 GlobalExceptionHandler ，因此 [请求方式不被支持] 这种异常无法被准确捕获，只能统一抛出 AuthenticationException 认证异常
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain usernameLoginChainFilter(HttpSecurity httpSecurity) throws Exception {
        commonFilter(httpSecurity);
        // securityMatcher 限定当前 filter 的作用域
        httpSecurity.securityMatcher("/auth/login/username")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        UsernameAuthenticationFilter smsLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/auth/login/username", HttpMethod.POST.name()),
                new ProviderManager(List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                applicationContext.getBean(LoginSuccessHandler.class),
                applicationContext.getBean(LoginFailHandler.class)
        );
        httpSecurity.addFilterBefore(smsLoginFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * 默认放行接口
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain openFilterChain(HttpSecurity httpSecurity) throws Exception {
        commonFilter(httpSecurity);
        // securityMatcher 限定当前 filter 的作用域
        httpSecurity.securityMatcher(
                        "/public/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    /**
     * 通过 jwt 进行 api 鉴权
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain JwtApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        commonFilter(httpSecurity);
        // securityMatcher 限定当前 filter 的作用域
        httpSecurity.securityMatcher("/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        httpSecurity.addFilterBefore(new CustomJwtAuthenticationFilter(applicationContext.getBean(JwtService.class)),
                UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
