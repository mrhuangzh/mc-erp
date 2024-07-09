package com.mc.erp.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import com.mc.erp.admin.service.UserService;
import com.mc.erp.admin.domain.entity.User;
import com.mc.erp.common.response.CommonResponse;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author mrhuangzh
 * @date 2024/07/04 10:57
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息 控制器", description = "用户信息 控制器")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 通过 id 查询 UserService
     *
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "通过 id 查询 UserService", description = "通过 id 查询 UserService")
    public CommonResponse<User> info(@RequestParam("id") Long id) {
        User User = userService.getById(id);
        return CommonResponse.success(User);
    }

}
