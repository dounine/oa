/*
Navicat MySQL Data Transfer

Source Server         : LocalhostMySQL
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : yasion

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2014-10-26 18:06:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_acount`
-- ----------------------------
DROP TABLE IF EXISTS `t_acount`;
CREATE TABLE `t_acount` (
  `id` varchar(32) NOT NULL,
  `acount` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_acount
-- ----------------------------

-- ----------------------------
-- Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------

-- ----------------------------
-- Table structure for `t_idcard`
-- ----------------------------
DROP TABLE IF EXISTS `t_idcard`;
CREATE TABLE `t_idcard` (
  `id` varchar(32) NOT NULL,
  `serial` varchar(32) DEFAULT NULL,
  `info` varchar(1024) DEFAULT NULL,
  `userId` varchar(32) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_idcard
-- ----------------------------

-- ----------------------------
-- Table structure for `t_orders`
-- ----------------------------
DROP TABLE IF EXISTS `t_orders`;
CREATE TABLE `t_orders` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `userId` varchar(32) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orders
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `groupId` varchar(32) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
