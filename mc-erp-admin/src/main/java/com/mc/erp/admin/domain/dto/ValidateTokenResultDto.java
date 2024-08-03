package com.mc.erp.admin.domain.dto;

import lombok.Data;

/**
 * @author: mrhuangzh
 * @date: 2024/8/2 10:53
 **/
@Data
public class ValidateTokenResultDto {
    private Boolean valid;
    private Long expiredTime;
}
