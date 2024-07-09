package com.mc.erp.admin.service.impl;

import com.mc.erp.admin.domain.entity.User;
import com.mc.erp.admin.mapper.UserMapper;
import com.mc.erp.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author mrhuangzh
 * @date 2024/07/04 10:57
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public User getByAccount(String account) {
        return userMapper.getByAccount(account);
    }
}
