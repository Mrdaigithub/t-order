-- Adminer 4.7.1 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `t_order`;
CREATE DATABASE `t_order` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `t_order`;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                       `username` varchar(255) NOT NULL COMMENT '管理员用户名',
                       `password` varchar(255) NOT NULL COMMENT '管理员密码',
                       `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `admin_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

INSERT INTO `admin` (`id`, `username`, `password`, `gmt_create`, `gmt_modified`) VALUES
(2,	'root',	'$2a$10$6dqAS73smpYqNcRw4UY4xOHKv4S3Q.bX2bsKtGysYYfwsZcgqDs2.',	'2019-03-21 03:45:05',	'2019-03-21 03:50:38');

-- 2019-03-21 06:16:55