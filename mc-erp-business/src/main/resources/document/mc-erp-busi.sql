
-- 设置字符集
SET NAMES utf8mb4;

-- 关闭外键检查
SET FOREIGN_KEY_CHECKS = 0;

-- 创建数据库，如果不存在则新建，若已存在则更新其字符集和排序规则
CREATE DATABASE IF NOT EXISTS `mc-erp-busi` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER DATABASE `mc-erp-busi` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 检查用户是否存在，如果不存在则创建用户并赋权，如果存在则更新密码并赋权
CREATE USER IF NOT EXISTS 'busi'@'%' IDENTIFIED BY '@Busi123';
ALTER USER 'busi'@'%' IDENTIFIED BY '@Busi123';

-- 赋予所有权限给用户
GRANT ALL PRIVILEGES ON `mc-erp-busi`.* TO 'busi'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- 切换到 mc-erp-busi 数据库
USE `mc-erp-busi`;


-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`  (
                            `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                            `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                            `desc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '简介',
                            `sort_order` int NULL DEFAULT 0 COMMENT '排序',
                            `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-启用；1-禁用',
                            `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                            `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `create_by` bigint NOT NULL COMMENT '创建者',
                            `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班级信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_class_attnd
-- ----------------------------
DROP TABLE IF EXISTS `t_class_attnd`;
CREATE TABLE `t_class_attnd`  (
                                  `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                  `class_id` bigint NOT NULL COMMENT '班级id',
                                  `course_id` bigint NOT NULL COMMENT '课程id',
                                  `teacher_id` bigint NOT NULL COMMENT '教师id',
                                  `start_at` datetime NOT NULL COMMENT '开始时间',
                                  `sotp_at` datetime NOT NULL COMMENT '结束时间',
                                  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_by` bigint NOT NULL COMMENT '创建者',
                                  `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班级授课记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
                             `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                             `desc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '简介',
                             `sort_order` int NULL DEFAULT 0 COMMENT '排序',
                             `lesson_hour` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '课时',
                             `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-启用；1-禁用',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                             `version` int NOT NULL DEFAULT 0 COMMENT '版本',
                             `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_by` bigint NOT NULL COMMENT '创建者',
                             `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
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
                             `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '类型：0-试课；1-周卡；2-月卡；3-季卡；4-年卡；5-课时卡',
                             `source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '来源：0-门店自主；1-营销推荐；2-亲朋推荐',
                             `expires_at` datetime NULL DEFAULT NULL COMMENT '到期时间',
                             `lesson_hour` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '课时',
                             `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_by` bigint NOT NULL COMMENT '创建者',
                             `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志：0-未删除；1-已删除',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member_attnd
-- ----------------------------
DROP TABLE IF EXISTS `t_member_attnd`;
CREATE TABLE `t_member_attnd`  (
                                   `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                   `class_id` bigint NOT NULL COMMENT '班级id',
                                   `course_id` bigint NOT NULL COMMENT '课程id',
                                   `teacher_id` bigint NOT NULL COMMENT '教师id',
                                   `member_id` bigint NOT NULL COMMENT '会员id',
                                   `start_at` datetime NOT NULL COMMENT '开始时间',
                                   `sotp_at` datetime NOT NULL COMMENT '结束时间',
                                   `lesson_hour` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '课时',
                                   `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `create_by` bigint NOT NULL COMMENT '创建者',
                                   `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员出勤信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member_class
-- ----------------------------
DROP TABLE IF EXISTS `t_member_class`;
CREATE TABLE `t_member_class`  (
                                   `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                   `member_id` bigint NOT NULL COMMENT '会员id',
                                   `course` bigint NOT NULL COMMENT '课程id',
                                   `class_id` bigint NOT NULL COMMENT '班级id',
                                   `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `create_by` bigint NOT NULL COMMENT '创建者',
                                   `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员班级关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_pay_record
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_record`;
CREATE TABLE `t_pay_record`  (
                                 `id` bigint NOT NULL COMMENT 'id，遵循雪花算法，为19位数字',
                                 `amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
                                 `pay_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '类型：0-现金；1-支付宝；2-微信；3-三方',
                                 `member_source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '来源：0-新客；1-续费',
                                 `member_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '类型：0-试课；1-周卡；2-月卡；3-季卡；4-年卡；5-课时卡',
                                 `expires_at` datetime NULL DEFAULT NULL COMMENT '到期时间',
                                 `lesson_hour` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '课时',
                                 `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `create_by` bigint NOT NULL COMMENT '创建者',
                                 `update_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付记录' ROW_FORMAT = DYNAMIC;


-- 打开外键检查
SET FOREIGN_KEY_CHECKS = 1;

