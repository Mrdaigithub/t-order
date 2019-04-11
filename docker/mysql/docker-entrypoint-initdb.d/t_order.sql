-- Adminer 4.7.1 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `t_order`;
CREATE DATABASE `t_order` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `t_order`;

DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '配置名称',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '配置详情',
  `value` varchar(255) NOT NULL COMMENT '配置项值',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_value_uindex` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';


DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '消息标题',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '消息详情',
  `broadcaster_id` bigint(20) unsigned NOT NULL COMMENT '发布者id',
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态,默认启用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';


DROP TABLE IF EXISTS `message_user_relation`;
CREATE TABLE `message_user_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) unsigned NOT NULL COMMENT '消息id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `read` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '消息是否已读,默认未读',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息用户关系表';


DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '订单标题',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '订单详情',
  `score` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '此订单所含的积分',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态\n0: 待提交\n1: 待审核\n2: 审核通过\n3: 审核未通过',
  `broadcaster_id` bigint(20) unsigned NOT NULL COMMENT '发布者id',
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态,默认启用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


DROP TABLE IF EXISTS `order_user_relation`;
CREATE TABLE `order_user_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_user_relation_order_id_uindex` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单用户id';


DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '权限名称',
  `value` varchar(255) NOT NULL COMMENT '权限值',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

INSERT INTO `permission` (`id`, `name`, `value`, `gmt_create`, `gmt_modified`) VALUES
(1,	'管理员列表',	'admin:read',	'2019-03-27 07:42:13',	'2019-03-29 06:10:52'),
(2,	'添加管理员',	'admin:create',	'2019-03-27 07:42:35',	'2019-03-27 07:42:35'),
(3,	'编辑管理员',	'admin:update',	'2019-03-27 07:43:13',	'2019-03-27 07:43:13'),
(4,	'删除管理员',	'admin:delete',	'2019-03-27 07:43:44',	'2019-03-27 07:43:44'),
(5,	'用户列表',	'user:read',	'2019-03-27 08:28:52',	'2019-03-27 08:28:52'),
(6,	'添加用户',	'user:create',	'2019-03-27 08:29:13',	'2019-03-27 08:29:13'),
(7,	'编辑用户',	'user:update',	'2019-03-27 08:29:29',	'2019-03-27 08:29:29'),
(8,	'删除用户',	'user:delete',	'2019-03-27 08:29:46',	'2019-03-27 08:29:46');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色组名称',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '角色组详情',
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否启用,默认启用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `role` (`id`, `name`, `description`, `enabled`, `gmt_create`, `gmt_modified`) VALUES
(1,	'root',	'根管理员',	1,	'2019-03-27 07:00:03',	'2019-03-27 07:00:23'),
(2,	'admin',	'普通管理员',	1,	'2019-03-27 07:01:05',	'2019-03-27 07:01:05'),
(3,	'user',	'普通用户',	1,	'2019-03-27 07:01:27',	'2019-03-27 07:01:27');

DROP TABLE IF EXISTS `role_permission_relation`;
CREATE TABLE `role_permission_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

INSERT INTO `role_permission_relation` (`id`, `role_id`, `permission_id`, `gmt_create`, `gmt_modified`) VALUES
(1,	1,	1,	'2019-03-27 07:46:34',	'2019-03-27 07:46:34'),
(2,	1,	2,	'2019-03-27 07:46:39',	'2019-03-27 07:46:39'),
(3,	1,	3,	'2019-03-27 07:46:54',	'2019-03-27 07:46:54'),
(4,	1,	4,	'2019-03-27 07:46:58',	'2019-03-27 07:46:58'),
(5,	1,	5,	'2019-03-27 08:30:17',	'2019-03-27 08:30:17'),
(6,	1,	6,	'2019-03-27 08:30:22',	'2019-03-27 08:30:22'),
(7,	1,	7,	'2019-03-27 08:30:27',	'2019-03-27 08:30:27'),
(8,	1,	8,	'2019-03-27 08:30:32',	'2019-03-27 08:30:32'),
(10,	2,	5,	'2019-03-27 08:30:59',	'2019-03-27 08:30:59'),
(11,	2,	6,	'2019-03-27 08:31:06',	'2019-03-27 08:31:06'),
(12,	2,	7,	'2019-03-27 08:31:12',	'2019-03-27 08:31:12'),
(13,	2,	8,	'2019-03-27 08:31:16',	'2019-03-27 08:31:16');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `bank_card` varchar(255) DEFAULT NULL COMMENT '银行卡号',
  `score` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `pid` bigint(20) unsigned DEFAULT NULL COMMENT '引荐人id',
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否启用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_username_uindex` (`username`),
  UNIQUE KEY `user_back_card_uindex` (`bank_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `user` (`id`, `username`, `password`, `bank_card`, `score`, `pid`, `enabled`, `gmt_create`, `gmt_modified`) VALUES
(1,	'root',	'$2a$10$6dqAS73smpYqNcRw4UY4xOHKv4S3Q.bX2bsKtGysYYfwsZcgqDs2.',	NULL,	0,	NULL,	1,	'2019-03-27 02:33:54',	'2019-03-27 07:49:53'),
(3,	'admin',	'$2a$10$P9/gDyR53RSYJnk5ZSOXB..1xIRYIZ8QpXTg7jU4w8DXjD8bmN81.',	'6226304550770318',	0,	NULL,	1,	'2019-04-11 07:51:31',	'2019-04-11 07:51:31');

DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

INSERT INTO `user_role_relation` (`id`, `user_id`, `role_id`, `gmt_create`, `gmt_modified`) VALUES
(1,	1,	1,	'2019-03-27 08:25:39',	'2019-03-27 08:25:39');

-- 2019-04-11 09:00:13
