package com.mc.erp.core.filter;

import com.mc.erp.common.enums.LogTraceEnum;
import com.mc.erp.common.enums.RequestHeaderEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 使用过滤器，防止有些异常情况，切面无法进入
 *
 * @author: mrhuangzh
 * @date: 2024/6/9 13:50
 **/

@Component
@Order(1)
@Slf4j
public class MdcRequestIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute());
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
                log.info("*** ---- ThreadId: {} |----| requestId为空，自动生成 {}",
                        Thread.currentThread().getId(), requestId);
            }
            MDC.put(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute(), requestId);
            MDC.put(LogTraceEnum.TRACE_ID.getAttribute(), requestId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute());
        }
    }
}
