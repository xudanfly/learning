/*
创建数据库/用户
CREATE DATABASE `taxbankplatform` /-这里有注释符-*!40100 COLLATE 'utf8_general_ci' *-这里有注释符-/;
FLUSH PRIVILEGES;
CREATE USER 'taxbankplatform'@'localhost' IDENTIFIED BY 'mysql';
GRANT USAGE ON *.* TO 'taxbankplatform'@'localhost';
GRANT SELECT, EXECUTE, SHOW VIEW, ALTER, ALTER ROUTINE, CREATE, CREATE ROUTINE, CREATE TEMPORARY TABLES, CREATE VIEW, DELETE, DROP, EVENT, INDEX, INSERT, REFERENCES, TRIGGER, UPDATE, LOCK TABLES  ON `taxbankplatform`.* TO 'taxbankplatform'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'taxbankplatform'@'localhost';
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_login_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_info`;
CREATE TABLE `tb_login_info` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `u_account_name` varchar(255) DEFAULT NULL,
  `l_ip` varchar(255) DEFAULT NULL,
  `l_login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for tb_log_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_log_info`;
CREATE TABLE `tb_log_info` (
  `l_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(20) NOT NULL,
  `l_account_name` varchar(100) DEFAULT NULL,
  `l_operation` varchar(255) DEFAULT NULL COMMENT '用户所做的操作',
  `l_content` varchar(1000) DEFAULT NULL COMMENT '日志内容',
  `l_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `s_parent_id` int(11) DEFAULT NULL COMMENT '资源父id',
  `s_name` varchar(100) NOT NULL COMMENT '资源名称',
  `s_source_key` varchar(100) NOT NULL COMMENT '资源唯一标识',
  `s_type` int(11) NOT NULL COMMENT '资源类型,0:目录;1:菜单;2:按钮',
  `s_source_url` varchar(500) DEFAULT NULL COMMENT '资源url',
  `s_level` int(11) DEFAULT NULL COMMENT '层级',
  `s_icon` varchar(100) DEFAULT '' COMMENT '图标',
  `s_is_hide` int(11) DEFAULT '0' COMMENT '是否隐藏',
  `s_description` varchar(100) DEFAULT NULL COMMENT '描述',
  `s_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `s_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES ('1', null, '控制台', 'desktop', '0', '/welcome.html', '1', 'fa fa-tachometer', '0', '控制台', '2016-01-12 17:08:55', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('2', null, '系统基础管理', 'system', '0', '', '1', 'fa fa-list', '0', '系统基础管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('3', '2', '用户管理', 'system:user', '0', '/user/listUI.html', '2', '', '0', '用户管理', '2016-01-08 12:37:10', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('4', '2', '角色管理', 'system:role', '0', '/role/listUI.html', '2', '', '0', '角色管理', '2016-01-11 22:51:07', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('5', '2', '资源管理', 'system:resource', '0', '/resource/listUI.html', '2', '', '0', '资源管理', '2016-01-11 22:51:55', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('6', null, '系统监控管理', 'monitor', '0', '', '1', 'fa fa-pencil-square-o', '0', '系统监控管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('7', '6', 'Sirona监控', 'monitor:sirona', '0', '/sirona', '2', '', '0', 'Sirona监控', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('8', '6', 'Druid监控', 'monitor:druid', '0', '/druid', '2', '', '0', 'Druid监控', '2016-01-11 22:45:27', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('9', null, '日志信息管理', 'logininfo', '0', '', '1', 'fa fa-tag', '0', '日志信息管理', '2016-01-11 22:46:39', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('10', '9', '用户登录信息', 'logininfo:userLogin', '0', '/loginInfo/listUI.html', '2', '', '0', '用户登录信息', '2016-01-11 22:47:41', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('11', '3', '添加', 'user:add', '1', '/user/add.html', '3', '', '0', '添加用户', '2016-01-22 00:18:40', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('12', '3', '编辑', 'user:edit', '1', '/user/edit.html', '3', '', '0', '编辑用户', '2016-01-22 00:18:40', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('13', '3', '删除', 'user:deleteBatch', '1', '/user/deleteBatch.html', null, null, '0', '删除用户', '2016-02-05 15:26:32', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('14', '3', '重置密码', 'user:resetPassword', '1', '/user/resetPassword.html', null, null, '0', '重置密码', '2016-02-27 23:55:13', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('15', '4', '添加', 'role:add', '1', '/role/add.html', null, null, '0', '添加', '2016-02-27 23:56:52', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('16', '4', '编辑', 'role:edit', '1', '/role/edit.html', null, null, '0', '编辑', '2016-02-27 23:57:35', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('17', '4', '删除', 'role:deleteBatch', '1', '/role/deleteBatch.html ', null, null, '0', '删除', '2016-02-27 23:58:02', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('18', '4', '分配权限', 'role:permission', '1', '/role/permission.html', null, null, '0', '分配权限', '2016-02-27 23:59:20', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('19', '5', '添加', 'resource:add', '1', '/resource/add.html', null, null, '0', '添加', '2016-02-28 00:01:15', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('20', '5', '编辑', 'resource:edit', '1', '/resource/edit.html', null, null, '0', '编辑', '2016-02-28 00:02:01', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('21', '5', '删除', 'resource:deleteBatch', '1', '/resource/deleteBatch.html', null, null, '0', '删除', '2016-02-28 00:03:03', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('22', '9', '用户操作信息', 'loginfo:userOperation', '0', '/logInfo/listUI.html', null, '', '0', '用户操作信息', '2016-03-08 22:00:36', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('23', '2', '区域纳税人角色管理', 'system:region', 0, '/region/listUI.html', NULL, NULL, 0, '系统上线的省市地区纳税人对应的角色', '2016-06-20 08:50:54', null);
INSERT INTO `tb_resource` VALUES ('24', '23', '添加', 'region:add', 1, '/region/add.html', NULL, NULL, 0, '添加', '2016-06-20 08:53:07', null);
INSERT INTO `tb_resource` VALUES ('25', '23', '编辑', 'region:edit', 1, '/region/edit.html', NULL, NULL, 0, '编辑', '2016-06-20 08:54:42', null);
INSERT INTO `tb_resource` VALUES ('26', '23', '删除', 'region:deleteBatch', 1, '/region/deleteBatch.html', NULL, NULL, 0, '删除', '2016-06-20 08:55:54', null);

-- ----------------------------
-- Table structure for tb_resources_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_resources_role`;
CREATE TABLE `tb_resources_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL COMMENT '资源id',
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  `t_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_r_resource_role` (`s_id`),
  KEY `FK_r_role_resource` (`r_id`),
  CONSTRAINT `FK_r_resource_role` FOREIGN KEY (`s_id`) REFERENCES `tb_resource` (`s_id`),
  CONSTRAINT `FK_r_role_resource` FOREIGN KEY (`r_id`) REFERENCES `tb_role` (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8 COMMENT='角色权限映射表';

-- ----------------------------
-- Records of tb_resources_role
-- ----------------------------
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (1, 1, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (2, 2, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (3, 3, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (4, 4, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (5, 5, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (6, 6, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (7, 7, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (8, 8, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (9, 9, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (10, 10, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (11, 11, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (12, 12, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (13, 13, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (14, 14, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (15, 15, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (16, 16, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (17, 17, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (18, 18, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (19, 19, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (20, 20, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (21, 21, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (22, 22, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (23, 23, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (24, 24, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (25, 25, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (26, 26, 1, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (27, 1, 2, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (28, 1, 3, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (29, 1, 4, '2016-06-30 14:21:20');
INSERT INTO `tb_resources_role` (`id`, `s_id`, `r_id`, `t_create_time`) VALUES (30, 1, 5, '2016-06-30 14:21:20');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `r_name` varchar(50) NOT NULL COMMENT '角色名称',
  `r_key` varchar(50) NOT NULL COMMENT '角色key',
  `r_status` int(11) DEFAULT '0' COMMENT '角色状态,0：正常；1：删除',
  `r_description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `u_id` int(11) DEFAULT '1' COMMENT '创建人id',
  `r_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `r_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`r_id`),
  KEY `FK_b_role_user` (`u_id`),
  CONSTRAINT `FK_b_role_user` FOREIGN KEY (`u_id`) REFERENCES `tb_user` (`u_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', '超级管理员', 'administrator', '0', '超级管理员', '1',  '2016-01-05 12:07:42', '2016-02-27 22:30:15');
INSERT INTO `tb_role` VALUES ('2', '管理员', 'admin', '0', '管理员', '0',  '2016-01-05 12:07:42', '2016-02-27 22:30:22');
INSERT INTO `tb_role` VALUES ('3', '普通用户', 'customer', '0', '普通用户', '1',  '2016-02-28 17:09:40', '2016-03-08 22:55:36');
INSERT INTO `tb_role` VALUES ('4', '深圳纳税人', 'nsr4sz', '0', '深圳纳税人', '1',  '2016-02-28 17:09:40', '2016-03-08 22:55:36');
INSERT INTO `tb_role` VALUES ('5', '广州纳税人', 'nsr4gz', '0', '广州纳税人', '1',  '2016-02-28 17:09:40', '2016-03-08 22:55:36');

-- ----------------------------
-- Table structure for tb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user`;
CREATE TABLE `tb_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  `u_id` int(11) DEFAULT NULL COMMENT '用户id',
  `t_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_r_role_user` (`r_id`),
  KEY `FK_r_user_role` (`u_id`),
  CONSTRAINT `FK_r_role_user` FOREIGN KEY (`r_id`) REFERENCES `tb_role` (`r_id`),
  CONSTRAINT `FK_r_user_role` FOREIGN KEY (`u_id`) REFERENCES `tb_user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户角色映射表';

-- ----------------------------
-- Records of tb_role_user
-- ----------------------------
INSERT INTO `tb_role_user` VALUES ('1', '1', '1', '2016-01-05 12:07:15');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `u_name` varchar(100) NOT NULL COMMENT '真实姓名',
  `u_account_name` varchar(100) NOT NULL COMMENT '账户名称',
  `u_password` varchar(100) NOT NULL COMMENT '用户密码',
  `u_delete_status` int(11) DEFAULT '0' COMMENT '逻辑删除状态',
  `u_locked` int(11) DEFAULT '0' COMMENT '是否锁定',
  `u_description` varchar(200) DEFAULT NULL COMMENT '用户描述',
  `u_credentials_salt` varchar(500) NOT NULL COMMENT '加密盐',
  `u_group_name` varchar(100) COMMENT '用户所属集合名称，用于组织机构数据隔离，取值为u_account_name',
  `u_region_id` int(11) COMMENT '所属区域ID',
  `u_creator_id` int(11) NOT NULL COMMENT '创建者ID',
  `u_creator_name` varchar(100) NOT NULL COMMENT '创建者',
  `u_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `u_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`u_id`),
  KEY `FK_u_group_name` (`u_group_name`),
  CONSTRAINT `FK_u_creator_user` FOREIGN KEY (`u_creator_id`) REFERENCES `tb_user` (`u_id`) ON DELETE CASCADE,
  UNIQUE KEY `u_account_name_unique` (`u_account_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '张孟志', 'zhangmengzhi2005@126.com', 'kSQc4hi2XzePDPzOs+IJ4g==', '0', '0', '超级管理员', '1fc0457c06d4e65c99699df22d7e6563', '',2,1, 'admin', '2016-02-15 17:17:26', '2016-03-20 16:16:46');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `u_id` int(11) NOT NULL COMMENT '用户id',
  `u_sex` int(11) DEFAULT NULL COMMENT '性别',
  `u_birthday` date DEFAULT NULL COMMENT '出生日期',
  `u_telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `u_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `u_address` varchar(200) DEFAULT NULL COMMENT '住址',
  `u_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`u_id`),
  CONSTRAINT `FK_r_user_info` FOREIGN KEY (`u_id`) REFERENCES `tb_user` (`u_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展信息表';

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES ('1', '1', '2016-02-10', '13000000000', '1@gmail.com', '广东省广州市中山二路', '2016-02-18 16:43:28');

-- ----------------------------
-- Table structure for tb_region
-- ----------------------------
DROP TABLE IF EXISTS `tb_region`;
CREATE TABLE `tb_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `region_name` varchar(100) NOT NULL COMMENT '区域名称',
  `delete_status` int(11) DEFAULT '0' COMMENT '逻辑删除状态',
  `locked` int(11) DEFAULT '0' COMMENT '是否锁定',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `creator_id` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `region_name_unique` (`region_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统上线区域表';

-- ----------------------------
-- Records of tb_region
-- ----------------------------
INSERT INTO `tb_region` VALUES ('1', '深圳市', '0', '0', '系统已开通深圳市，为深圳市国地税纳税人服务', '1', '2016-02-15 17:17:26', '2016-03-20 16:16:46');
INSERT INTO `tb_region` VALUES ('2', '广州市', '0', '0', '系统已开通广州市，为广州市国地税纳税人服务', '1', '2016-02-28 17:39:31', '2016-03-29 17:39:37');

-- ----------------------------
-- Table structure for tb_role_region
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_region`;
CREATE TABLE `tb_role_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  `reg_id` int(11) DEFAULT NULL COMMENT '区域id',
  `t_create_time` datetime DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_r_role_region` (`r_id`),
  KEY `FK_r_region_role` (`reg_id`),
  CONSTRAINT `FK_r_role_region` FOREIGN KEY (`r_id`) REFERENCES `tb_role` (`r_id`),
  CONSTRAINT `FK_r_region_role` FOREIGN KEY (`reg_id`) REFERENCES `tb_region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域角色映射表';

-- ----------------------------
-- Records of tb_role_region
-- ----------------------------
INSERT INTO `tb_role_region` VALUES ('1', '4', '1', '2016-01-05 12:07:15');
INSERT INTO `tb_role_region` VALUES ('2', '5', '2', '2016-02-28 17:40:08');

-- --------------------------------------------------------------------------------------------------------------------------
-- 新增模块的资源脚本2016-7-18
INSERT INTO `tb_resource` (`s_id`, `s_parent_id`, `s_name`, `s_source_key`, `s_type`, `s_source_url`, `s_level`, `s_icon`, `s_is_hide`, `s_description`, `s_create_time`, `s_update_time`) VALUES (27, NULL, '银行业务', 'bank', 0, '', 1, 'fa fa-bank', 0, '银行相关业务', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` (`s_id`, `s_parent_id`, `s_name`, `s_source_key`, `s_type`, `s_source_url`, `s_level`, `s_icon`, `s_is_hide`, `s_description`, `s_create_time`, `s_update_time`) VALUES (28, 27, '理财产品', 'bank:financialProduct', 0, '/financialProduct/listUI.html', NULL, NULL, 0, '银行上线的理财产品', '2016-06-20 08:50:54', null);
INSERT INTO `tb_resource` (`s_id`, `s_parent_id`, `s_name`, `s_source_key`, `s_type`, `s_source_url`, `s_level`, `s_icon`, `s_is_hide`, `s_description`, `s_create_time`, `s_update_time`) VALUES (29, 28, '添加', 'financialProduct:add', 1, '/financialProduct/add.html', NULL, NULL, 0, '添加', '2016-06-20 08:53:07', null);
INSERT INTO `tb_resource` (`s_id`, `s_parent_id`, `s_name`, `s_source_key`, `s_type`, `s_source_url`, `s_level`, `s_icon`, `s_is_hide`, `s_description`, `s_create_time`, `s_update_time`) VALUES (30, 28, '编辑', 'financialProduct:edit', 1, '/financialProduct/edit.html', NULL, NULL, 0, '编辑', '2016-06-20 08:54:42', null);
INSERT INTO `tb_resource` (`s_id`, `s_parent_id`, `s_name`, `s_source_key`, `s_type`, `s_source_url`, `s_level`, `s_icon`, `s_is_hide`, `s_description`, `s_create_time`, `s_update_time`) VALUES (31, 28, '删除', 'financialProduct:deleteBatch', 1, '/financialProduct/deleteBatch.html', NULL, NULL, 0, '删除', '2016-06-20 08:55:54', null);

-- 权限-角色，添加的权限需要脚本添加给超级管理员，超级管理员拥有所有权限点，才能授权给其他角色
INSERT INTO `tb_resources_role` VALUES ('31', '27', '1', '2016-07-18 16:41:34');
INSERT INTO `tb_resources_role` VALUES ('32', '28', '1', '2016-07-18 16:41:56');
INSERT INTO `tb_resources_role` VALUES ('33', '29', '1', '2016-07-18 16:42:17');
INSERT INTO `tb_resources_role` VALUES ('34', '30', '1', '2016-07-18 16:42:32');
INSERT INTO `tb_resources_role` VALUES ('35', '31', '1', '2016-07-18 16:43:02');

-- 理财产品表
-- ----------------------------
-- Table structure for `tb_financial_product`
-- ----------------------------
DROP TABLE IF EXISTS `tb_financial_product`;
CREATE TABLE `tb_financial_product` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '理财产品id',
  `f_title` varchar(100) NOT NULL COMMENT '标题',
  `f_subtitle` varchar(100) DEFAULT NULL COMMENT '副标题',
  `f_summary` varchar(300) NOT NULL COMMENT '摘要',
  `f_content` text NOT NULL COMMENT '内容',
  `f_delete_status` int(1) DEFAULT '0' COMMENT '逻辑删除状态',
  `u_group_name` varchar(100) DEFAULT NULL COMMENT '用户所属集合名称，用于组织机构数据隔离',
  `f_creator_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `f_creator_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `f_create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `f_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`f_id`),
  KEY `FK_financial_group_name` (`u_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='理财产品表';

-- 附件信息表
-- ----------------------------
-- Table structure for `tb_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_attachment`;
CREATE TABLE `tb_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '附件表id',
  `name` varchar(100) DEFAULT NULL COMMENT '附件名',
  `type` varchar(100) DEFAULT NULL COMMENT '附件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '附件大小',
  `path` varchar(500) DEFAULT NULL COMMENT '附件路径',
  `url` varchar(500) DEFAULT NULL COMMENT '附件地址',
  `fk_table` varchar(50) DEFAULT NULL COMMENT '关联表',
  `fk_id_name` varchar(50) DEFAULT NULL COMMENT '关联表ID名',
  `fk_id` bigint(20) DEFAULT NULL COMMENT '关联ID',
  `status` varchar(4) DEFAULT NULL COMMENT '有效状态',
  `creator_id` int(11) DEFAULT NULL,
  `creator_name` varchar(100) DEFAULT NULL COMMENT '上传者',
  `createtime` timestamp NULL DEFAULT NULL COMMENT '上传人',
  PRIMARY KEY (`id`),
  KEY `fk_table` (`fk_table`,`fk_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;