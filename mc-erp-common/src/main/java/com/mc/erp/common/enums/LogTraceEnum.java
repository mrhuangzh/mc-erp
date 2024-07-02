package com.mc.erp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/6/7 0:20
 **/
@Getter
@AllArgsConstructor
public enum LogTraceEnum {
    TRACE_ID("traceId");
    public final String attribute;
}
