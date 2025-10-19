/*
 Navicat Premium Dump SQL

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : md2doc

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 07/10/2025 17:33:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict`  (
  `dict_id` bigint NOT NULL COMMENT '字典ID',
  `dict_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典Key',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_dictKey_uk`(`dict_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dict
-- ----------------------------
INSERT INTO `tb_dict` VALUES (1974800914256699392, '通用状态', 'common_status', 1974016655841509377, '2025-10-05 19:36:43', '2025-10-05 19:36:43', 1, 1);
INSERT INTO `tb_dict` VALUES (1974826537440174080, '菜单显示状态', 'system_menu_hidden', 1974016655841509377, '2025-10-05 21:18:32', '2025-10-05 21:18:32', 1, 1);
INSERT INTO `tb_dict` VALUES (1975475788591636480, 'Java类型', 'system_java_type', 1974016655841509377, '2025-10-07 16:18:25', '2025-10-07 16:18:25', 1, 1);

-- ----------------------------
-- Table structure for tb_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_data`;
CREATE TABLE `tb_dict_data`  (
  `dict_data_id` bigint NOT NULL COMMENT '字典数据ID',
  `dict_id` bigint NOT NULL COMMENT '字典ID',
  `dict_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典值',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`dict_data_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dict_data
-- ----------------------------
INSERT INTO `tb_dict_data` VALUES (1974809021305208832, 1974800914256699392, '正常', '1', 1974016655841509377, '2025-10-05 20:08:56', '2025-10-05 20:08:56', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1974812232862842880, 1974800914256699392, '禁用', '2', 1974016655841509377, '2025-10-05 20:21:41', '2025-10-05 20:21:41', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1974827254838091776, 1974826537440174080, '显示', '1', 1974016655841509377, '2025-10-05 21:21:23', '2025-10-05 21:21:23', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1974827293534740480, 1974826537440174080, '隐藏', '2', 1974016655841509377, '2025-10-05 21:21:32', '2025-10-05 21:21:32', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475832493416448, 1975475788591636480, 'Long', 'Long', 1974016655841509377, '2025-10-07 16:18:36', '2025-10-07 16:18:36', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475850780581888, 1975475788591636480, 'String', 'String', 1974016655841509377, '2025-10-07 16:18:40', '2025-10-07 16:18:40', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475872964255744, 1975475788591636480, 'Integer', 'Integer', 1974016655841509377, '2025-10-07 16:18:45', '2025-10-07 16:18:45', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475888554483712, 1975475788591636480, 'Double', 'Double', 1974016655841509377, '2025-10-07 16:18:49', '2025-10-07 16:18:49', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475907152027648, 1975475788591636480, 'BigDecimal', 'BigDecimal', 1974016655841509377, '2025-10-07 16:18:53', '2025-10-07 16:18:53', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475923316875264, 1975475788591636480, 'Date', 'Date', 1974016655841509377, '2025-10-07 16:18:57', '2025-10-07 16:18:57', 1, 1);
INSERT INTO `tb_dict_data` VALUES (1975475937950801920, 1975475788591636480, 'Boolean', 'Boolean', 1974016655841509377, '2025-10-07 16:19:01', '2025-10-07 16:19:01', 1, 1);

-- ----------------------------
-- Table structure for tb_gen_table
-- ----------------------------
DROP TABLE IF EXISTS `tb_gen_table`;
CREATE TABLE `tb_gen_table`  (
  `gen_table_id` bigint NOT NULL COMMENT '表ID',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表名称',
  `table_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表注释',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类名',
  `package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '包名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `module_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '功能名',
  `business_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务名',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`gen_table_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for tb_gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `tb_gen_table_column`;
CREATE TABLE `tb_gen_table_column`  (
  `gen_table_column_id` bigint NOT NULL COMMENT '表字段ID',
  `gen_table_id` bigint NOT NULL COMMENT '表ID',
  `column_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段名称',
  `column_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段注释',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Java类型',
  `java_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Java字段名',
  `is_pk` tinyint NULL DEFAULT NULL COMMENT '是否主键',
  `is_require` tinyint NULL DEFAULT NULL COMMENT '是否必须',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `is_increment` tinyint NULL DEFAULT NULL COMMENT '是否自增',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`gen_table_column_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成字段表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `menu_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` tinyint NULL DEFAULT NULL COMMENT '菜单类型',
  `order_num` int NULL DEFAULT NULL COMMENT '顺序',
  `perms` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `hidden` tinyint NULL DEFAULT NULL COMMENT '是否隐藏',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1970482232512675841, 0, '系统管理', 'system', NULL, 1, 1, NULL, 'system', 1, 1974016655841509377, '2025-10-04 13:04:29', '2025-10-04 13:04:32', 1, 1);
INSERT INTO `tb_menu` VALUES (1970482232512695842, 1970482232512675841, '用户管理', 'user', 'system/user/index', 2, 1, NULL, 'user', 1, 1974016655841509377, '2025-10-04 13:04:29', '2025-10-04 13:04:32', 1, 1);
INSERT INTO `tb_menu` VALUES (1970482232512695843, 1970482232512675841, '菜单管理', 'menu', 'system/menu/index', 2, 3, NULL, 'tree-table', 1, 1974016655841509377, '2025-10-04 14:55:44', '2025-10-04 14:55:50', 1, 1);
INSERT INTO `tb_menu` VALUES (1974446802340073472, 1970482232512675841, '角色管理', 'role', 'system/role/index', 2, 2, NULL, 'peoples', 1, 1974016655841509377, '2025-10-04 20:09:35', '2025-10-04 20:09:35', 1, 1);
INSERT INTO `tb_menu` VALUES (1974798888420397056, 1970482232512675841, '字典管理', 'dict', 'system/dict/index', 2, 4, NULL, 'dict', 1, 1974016655841509377, '2025-10-05 19:28:37', '2025-10-05 19:28:37', 1, 1);
INSERT INTO `tb_menu` VALUES (1974813105718456320, 0, '开发工具', 'tool', NULL, 1, 2, NULL, 'tool', 1, 1974016655841509377, '2025-10-05 20:25:09', '2025-10-05 20:25:09', 1, 1);
INSERT INTO `tb_menu` VALUES (1974813383091974144, 1974813105718456320, '代码生成', 'generate', 'tool/generate/index', 2, 1, NULL, 'code', 1, 1974016655841509377, '2025-10-05 20:26:15', '2025-10-05 20:26:15', 1, 1);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色Key',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1970482232512675840, '超级管理员', 'super_admin', 1974016655841509377, '2025-10-03 22:05:15', '2025-10-03 22:05:18', 1, 1);

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1970482232512675841);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1970482232512695842);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1970482232512695843);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1974446802340073472);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1974798888420397056);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1974813105718456320);
INSERT INTO `tb_role_menu` VALUES (1970482232512675840, 1974813383091974144);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `ver` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1974016655841509377, '123456', '$2a$10$ixVpCBU00FIfut/ZiUq/yuuuA837rvPibpU2xYxxHqnLxRZgKSrkW', 'https://q1.qlogo.cn/g?b=qq&nk=2770063826&s=640', '用户9zig9', '2025-10-03 15:40:21', '2025-10-03 15:40:21', 1, 1);

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1974016655841509377, 1970482232512675840);

SET FOREIGN_KEY_CHECKS = 1;
