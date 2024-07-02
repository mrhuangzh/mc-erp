package com.mc.erp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: mrhuangzh
 * @date: 2024/7/1 18:55
 **/
public interface ErrorCodeEnumInter {
    Integer getCode();

    String getMessage();
}
