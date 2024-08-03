package com.mc.erp.business.config;

import com.mc.erp.business.domain.dto.ValidateTokenResultDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * token缓存
 *
 * @author: mrhuangzh
 * @date: 2024/8/3 0:08
 **/
@Component
public class TokenCache implements DisposableBean {

    private final ConcurrentHashMap<String, ValidateTokenResultDto> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int MAX_ENTRIES = 10;
    private static final int INITIAL_DELAY = 0;
    private static final int EXEC_PERIOD = 10;

    /**
     * 初始化定时清理任务
     */
    @PostConstruct
    public void init() {
        // 定时任务，每分钟执行一次，清理过期的缓存
        scheduler.scheduleAtFixedRate(this::cleanUpExpired, INITIAL_DELAY, EXEC_PERIOD, TimeUnit.MINUTES);
    }

    /**
     * 清理
     */
    private void cleanUpExpired() {
        cache.entrySet()
                .removeIf(entry ->
                        entry.getValue().getExpiredTime() <= System.currentTimeMillis());
    }

    /**
     * 清理
     */
    private void cleanUpEarlier() {
        cache.entrySet().stream().min((e1, e2) ->
                        Long.compare(e1.getValue().getExpiredTime(), e2.getValue().getExpiredTime()))
                .ifPresent(entry -> cache.remove(entry.getKey()));
    }

    public void put(String key, ValidateTokenResultDto dto) {
        if (cache.size() >= MAX_ENTRIES) {
            // 移除过期
            cleanUpExpired();
            // 移除时间最早的条目
            if (cache.size() >= MAX_ENTRIES) {
                cleanUpEarlier();
            }
        }
        cache.put(key, dto);
    }


    public ValidateTokenResultDto get(String key) {
        ValidateTokenResultDto dto = cache.get(key);
        if (dto == null || dto.getExpiredTime() <= System.currentTimeMillis()) {
            cache.remove(key);
            return null;
        }
        return dto;
    }

    @Override
    public void destroy() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
