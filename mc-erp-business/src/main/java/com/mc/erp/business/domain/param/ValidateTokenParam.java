package com.mc.erp.business.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: mrhuangzh
 * @date: 2024/8/2 10:55
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenParam {
    private String token;
}
