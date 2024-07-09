package com.mc.erp.admin.service;

import com.mc.erp.admin.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author mrhuangzh
 * @date 2024/07/04 10:57
 */
public interface UserService extends IService<User> {

    User getById(Long id);

    User getByAccount(String account);
}
