-- 设置字符集
SET NAMES utf8mb4;

-- 关闭外键检查
SET FOREIGN_KEY_CHECKS = 0;

-- 创建数据库，如果不存在则新建，若已存在则更新其字符集和排序规则
CREATE DATABASE IF NOT EXISTS `mc-erp-admin` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER DATABASE `mc-erp-admin` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 检查用户是否存在，如果不存在则创建用户并赋权，如果存在则更新密码并赋权
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY '@Admin123';
ALTER USER 'admin'@'%' IDENTIFIED BY '@Admin123';

-- 赋予所有权限给用户
GRANT ALL PRIVILEGES ON `mc-erp-admin`.* TO 'admin'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- 切换到 mc-erp-admin 数据库
USE `mc-erp-admin`;


-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`  (
                           `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                           `parentId` bigint NULL DEFAULT NULL COMMENT '父id',
                           `sort_order` int NULL DEFAULT 0 COMMENT '排序',
                           `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-启用；1-禁用',
                           `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                           `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `create_by` bigint NOT NULL COMMENT '创建者',
                           `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                           `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
                           `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                           `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                           `desc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '简介',
                           `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-启用；1-禁用',
                           `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                           `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `create_by` bigint NOT NULL COMMENT '创建者',
                           `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                           `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                           `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                           `account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                           `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
                           `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                           `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
                           `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                           `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-已注册；1-已激活；2-已失效，3-已注销，4-已禁用',
                           `gender` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别：0-未知；1-男；2-女',
                           `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
                           `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
                           `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
                           `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                           `last_login_at` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                           `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `create_by` bigint NOT NULL COMMENT '创建者',
                           `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                           `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_user_dept`;
CREATE TABLE `t_user_dept`  (
                                `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                `user_id` bigint NOT NULL COMMENT '用户id',
                                `dept_id` bigint NOT NULL COMMENT '部门id',
                                `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `create_by` bigint NOT NULL COMMENT '创建者',
                                `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户部门关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
                                `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                `user_id` bigint NOT NULL COMMENT '用户id',
                                `role_id` bigint NOT NULL COMMENT '部门id',
                                `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `create_by` bigint NOT NULL COMMENT '创建者',
                                `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关系' ROW_FORMAT = DYNAMIC;

-- 打开外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- 插入数据
INSERT INTO `mc-erp-admin`.`t_user` (`id`, `account`, `username`, `password`, `phone`, `email`, `status`, `gender`, `avatar`, `birth_date`, `address`, `remark`, `last_login_at`, `create_at`, `create_by`, `update_at`, `update_by`, `delete_flag`) VALUES (1810626502187454465, 'admin', '超级管理员', '$2a$10$f9u624.TQZziwjfbOK.fl.rXYxB1NWDLsU0lyTRFKhGaLE5WwoVoO', NULL, NULL, 0, 0, NULL, NULL, NULL, 'super@Admin123', NULL, '2024-07-09 18:50:56', 1810626502187454465, '2024-07-09 18:52:44', NULL, 0);

