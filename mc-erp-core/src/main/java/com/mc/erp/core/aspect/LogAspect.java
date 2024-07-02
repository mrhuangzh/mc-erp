package com.mc.erp.core.aspect;

import com.alibaba.fastjson2.JSON;
import com.mc.erp.common.exception.BusinessException;
import com.mc.erp.common.response.CommonResponse;
import com.mc.erp.common.enums.RequestHeaderEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 接口请求日志
 *
 * @author: mrhuangzh
 * @date: 2024/6/7 0:16
 **/

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)"
            + "|| @within(org.springframework.stereotype.Controller)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HashMap<Object, Object> headerMap = new HashMap<>();
        for (Enumeration<String> enu = request.getHeaderNames(); enu.hasMoreElements(); ) {
            String name = enu.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        log.info("*** ---- ThreadId: {} |----| request url: {} |----| request method: {} |----| " +
                        "请求方法所在路径 : {}.{} |----| " +
                        "headers : {} |----| 请求参数: {} |----| " +
                        "请求体: {}",
                Thread.currentThread().getId(), request.getRequestURL().toString(), request.getMethod()
                , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                JSON.toJSONString(headerMap), JSON.toJSONString(request.getParameterMap()),
                JSON.toJSONString(Arrays.stream(joinPoint.getArgs())
                        .filter(arg -> !(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse))
                        .collect(Collectors.toList())));
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            final Object result = joinPoint.proceed();
            stopWatch.stop();
            if (result instanceof CommonResponse<?> response) {
                response.setRid(MDC.get(RequestHeaderEnum.REQUEST_ID_KEY.getAttribute()));
            }
            log.info("*** ---- ThreadId: {} |----| execute milliseconds : {}ms |----| response: {}"
                    , Thread.currentThread().getId(), stopWatch.getTotalTimeMillis(), JSON.toJSONString(result));
            return result;
        } catch (Throwable throwable) {
            log.error("*** ---- ThreadId: {} |----| execute milliseconds : {}ms |----| error: {}",
                    Thread.currentThread().getId(), stopWatch.getTotalTimeMillis(), throwable.getMessage(), throwable);
            // 重新抛出异常，让异常继续向上传播
            throw throwable;
        }
    }
}
