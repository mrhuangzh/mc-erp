package com.mc.erp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: mrhuangzh
 * @date: 2024/6/9 13:54
 **/

@Getter
@AllArgsConstructor
public enum RequestHeaderEnum {
    REQUEST_ID_KEY("X-Request-Id"),
    BEARER_KEY("Bearer "),

    ;
    public final String attribute;
}
