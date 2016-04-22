/*
Navicat MySQL Data Transfer

Source Server         : LocalhostMySQL
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : ajoa

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2015-03-26 14:16:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `id` varchar(32) NOT NULL,
  `title` varchar(128) DEFAULT NULL,
  `logger_type` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `ip_address` varchar(128) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `log_time` varchar(64) DEFAULT NULL,
  `content` varchar(4096) DEFAULT NULL,
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
-- Records of tb_log
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `code` varchar(60) DEFAULT NULL,
  `descr` varchar(255) DEFAULT NULL,
  `disable` varchar(1) DEFAULT NULL,
  `black_urls` text,
  `white_urls` text,
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
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f1f0000', 'test2', 'test2', '', 'N', '/[A-Za-z0-9]*/edit.do,V:/user/list.do', '', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 15:49:12', '2014-09-29 22:43:53', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f210001', 'test3', 'test3', '', 'Y', 'V:/role/list.do', '', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae648d0063', '2014-09-28 15:49:41', '2014-09-28 23:29:05', null, null, null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f350002', 'test4', 'test4', '', 'N', 'V:/test.do', '', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 15:50:09', '2014-09-28 22:59:52', null, null, null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f370003', 'test5', 'test5', '', 'N', '/[A-Za-z0-9]*/edit.do,/[0-9]/edit.do,/user/list.do,  /user/list.do', '', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448b2dc400148b2dcfa370003', '2014-09-28 17:31:37', '2014-09-29 11:34:47', null, '77', null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f380004', '测试5', 'code5', '', 'N', '/[A-Za-z0-9]*/edit.do,/[0-9]/edit.do,/user/list.do,  /user/list.do', 'V:/user/list.do', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448bc5ab10148bc5bed330002', '2014-09-28 21:05:24', '2014-09-29 00:16:10', null, '85', null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f390005', '测试6', 'test6', '', 'Y', '/permission/[A-Za-z0-9]*.do,/role/[A-Za-z0-9]*.do,/user/[A-Za-z0-9]*.do,/unit/[A-Za-z0-9]*.do,/rolePermission/[A-Za-z0-9]*.do,/userRole/[A-Za-z0-9]*.do,/logger/[A-Za-z0-9]*.do, /logger/[A-Za-z0-9]*.do', '/user/info.do,/user/infoUpdate.do,/unit/ajaxLoadUnit.do,/unit/searchUnitById.do,V:/user/list.do', '402881e448e5ae5f0148e5ae648d0063', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 23:45:04', '2015-02-28 16:14:12', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f3a0006', 'test1', 'test1', '', 'N', '/[A-Za-z0-9]*/edit.do,/[0-9]/edit.do,/user/list.do', 'V:/rolePermission/findPermissionsId.do,V:/user/list.do', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 15:48:18', '2014-09-29 11:45:17', null, null, null, null);

-- ----------------------------
-- Table structure for `tb_personal_experience`
-- ----------------------------
DROP TABLE IF EXISTS `tb_personal_experience`;
CREATE TABLE `tb_personal_experience` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户关联Id',
  `start_time` varchar(64) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(64) DEFAULT NULL COMMENT '结束时间',
  `descr` varchar(128) DEFAULT NULL COMMENT '具体描述',
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
-- Records of tb_personal_experience
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_personal_profile`
-- ----------------------------
DROP TABLE IF EXISTS `tb_personal_profile`;
CREATE TABLE `tb_personal_profile` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `real_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `english_name` varchar(64) DEFAULT NULL COMMENT '英文名',
  `birthday` varchar(64) DEFAULT NULL COMMENT '出生日期',
  `blood_type` varchar(4) DEFAULT NULL COMMENT '血型',
  `residence` varchar(80) DEFAULT NULL COMMENT '现居住地',
  `hometown` varchar(80) DEFAULT NULL COMMENT '故乡',
  `detailed_address` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `qq` varchar(15) DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(15) DEFAULT NULL COMMENT '微信',
  `individual_resume` varchar(256) DEFAULT NULL COMMENT '个人简介',
  `photo` varchar(4096) DEFAULT NULL COMMENT '头像路径',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户关联Id',
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
-- Records of tb_personal_profile
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire`;
CREATE TABLE `tb_questionnaire` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(128) DEFAULT NULL COMMENT '问卷名称',
  `code` varchar(64) DEFAULT NULL COMMENT '问卷编码',
  `descr` varchar(512) DEFAULT NULL COMMENT '问卷的描述',
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
-- Records of tb_questionnaire
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire_answer`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire_answer`;
CREATE TABLE `tb_questionnaire_answer` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `questionnaire_instance_id` varchar(32) DEFAULT NULL COMMENT '问卷实例Id',
  `questionnaire_question_id` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `questionnaire_option_id` varchar(32) DEFAULT NULL COMMENT '选项Id',
  `content` varchar(1024) DEFAULT NULL COMMENT '问答题的回答',
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
-- Records of tb_questionnaire_answer
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire_instance`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire_instance`;
CREATE TABLE `tb_questionnaire_instance` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `questionnaire_id` varchar(32) DEFAULT NULL COMMENT '问卷Id',
  `fill_time` varchar(64) DEFAULT NULL COMMENT '填写时间',
  `applicant` varchar(32) DEFAULT NULL COMMENT '填写人',
  `applicant_mobile` varchar(32) DEFAULT NULL COMMENT '填写人手机',
  `ip_address` varchar(64) DEFAULT NULL,
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
-- Records of tb_questionnaire_instance
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire_option`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire_option`;
CREATE TABLE `tb_questionnaire_option` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(64) DEFAULT NULL COMMENT '选项名称',
  `code` varchar(64) DEFAULT NULL COMMENT '选项编码',
  `content` varchar(128) DEFAULT NULL COMMENT '选项的具体内容',
  `questionnaire_question_id` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `descr` varchar(512) DEFAULT NULL COMMENT '选项描述',
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
-- Records of tb_questionnaire_option
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire_question`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire_question`;
CREATE TABLE `tb_questionnaire_question` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(64) DEFAULT NULL COMMENT '问题名称',
  `code` varchar(64) DEFAULT NULL COMMENT '问题编码',
  `question` varchar(2048) DEFAULT NULL COMMENT '问题内容,具体的问题',
  `type` varchar(32) DEFAULT NULL COMMENT '问题类型,单选、多选、问答',
  `descr` varchar(512) DEFAULT NULL COMMENT '描述问题的细节',
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
-- Records of tb_questionnaire_question
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_questionnaire_question_relation`
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire_question_relation`;
CREATE TABLE `tb_questionnaire_question_relation` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `questionnaire_id` varchar(32) DEFAULT NULL COMMENT '问卷Id',
  `questionnaire_question_id` varchar(32) DEFAULT NULL COMMENT '问题Id',
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
-- Records of tb_questionnaire_question_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `code` varchar(60) DEFAULT NULL,
  `descr` varchar(255) DEFAULT NULL,
  `disable` varchar(1) DEFAULT NULL,
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
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f6a0007', '管理员', 'ADMIN', '', 'N', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-09-28 15:50:23', null, null, null, null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f6c0008', '测试1', 'code1', '', 'N', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-09-28 15:50:34', null, null, null, null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f720009', '测试2', 'code2', '', 'N', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448b2dc400148b2dcfa370003', '2014-09-28 15:50:44', '2014-09-29 12:41:27', null, '85', null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f73000a', '测试4', 'code4', '', 'N', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 17:31:10', '2014-10-09 13:03:56', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f74000b', '测试3', 'code3', '', 'Y', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448bc5ab10148bc5bed330002', '2014-09-28 17:31:25', '2014-09-29 11:27:09', null, '9', null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f75000c', '测试5', 'code5', '', 'N', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-09-28 21:05:00', null, null, null, null, null);
INSERT INTO `tb_role` VALUES ('402881e448e5ae5f0148e5ae5f77000d', 'test7', 'test7', 'test7', 'N', '402881e448bc5ab10148bc5bed330002', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-29 11:28:02', '2014-10-26 02:18:25', '9', 'SUPERADMIN', null, null);

-- ----------------------------
-- Table structure for `tb_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `permission_id` varchar(32) DEFAULT NULL,
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
-- Records of tb_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_super_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_super_user`;
CREATE TABLE `tb_super_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职称',
  `education` varchar(50) DEFAULT NULL COMMENT '学历',
  `mobile` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `disable` varchar(1) DEFAULT NULL COMMENT '禁用否',
  `regi_time` varchar(50) DEFAULT NULL COMMENT '注册时间',
  `disable_datetime` varchar(50) DEFAULT NULL COMMENT '用户过期时间',
  `unit_id` varchar(32) DEFAULT NULL,
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
-- Records of tb_super_user
-- ----------------------------
INSERT INTO `tb_super_user` VALUES ('402881e448e5ae5f0148e5ae5f7a000e', 'admin', '5F4DCC3B5AA765D61D8327DEB882CF99', '超级管理员', '博士', '大专', '123123123', '12345678dd7', '88888888@qq.com', 'M', 'N', null, null, null, null, 'SUPERADMIN', null, '2015-03-23 15:58:14', null, 'SYSTEM', null, 'SYS');

-- ----------------------------
-- Table structure for `tb_ueditor_file`
-- ----------------------------
DROP TABLE IF EXISTS `tb_ueditor_file`;
CREATE TABLE `tb_ueditor_file` (
  `id` varchar(32) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `original_name` varchar(255) DEFAULT NULL COMMENT '源文件名',
  `upload_time` varchar(30) DEFAULT NULL COMMENT '上传时间',
  `file_type` varchar(30) DEFAULT NULL COMMENT '文件类型',
  `file_id` varchar(32) DEFAULT NULL,
  `file_path` varchar(512) DEFAULT NULL COMMENT '文件存放放位置',
  `size` decimal(15,0) DEFAULT NULL COMMENT '文件大小',
  `ueditor_type` varchar(32) DEFAULT NULL COMMENT 'ueditor类型,image/file/scrawl等',
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
-- Records of tb_ueditor_file
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_unit`
-- ----------------------------
DROP TABLE IF EXISTS `tb_unit`;
CREATE TABLE `tb_unit` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `descr` varchar(1023) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `postcode` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `startTime` varchar(50) DEFAULT NULL,
  `endTime` varchar(50) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_id_unique_index` (`id`) USING BTREE,
  KEY `unit_parent_id_index` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_unit
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职称',
  `education` varchar(50) DEFAULT NULL COMMENT '学历',
  `mobile` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `disable` varchar(1) DEFAULT NULL COMMENT '禁用否',
  `regi_time` varchar(50) DEFAULT NULL COMMENT '注册时间',
  `disable_datetime` varchar(50) DEFAULT NULL COMMENT '用户过期时间',
  `unit_id` varchar(32) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_unique_index` (`id`) USING BTREE,
  KEY `user_unit_id_index` (`unit_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
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
-- Records of tb_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user_unit_relating`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_unit_relating`;
CREATE TABLE `tb_user_unit_relating` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `unit_id` varchar(32) DEFAULT NULL,
  `disable` varchar(1) DEFAULT NULL,
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
-- Records of tb_user_unit_relating
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_web_file`
-- ----------------------------
DROP TABLE IF EXISTS `tb_web_file`;
CREATE TABLE `tb_web_file` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `file_name` varchar(64) DEFAULT NULL,
  `original_file_name` varchar(255) DEFAULT NULL,
  `file_type` varchar(64) DEFAULT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `size` decimal(15,0) DEFAULT NULL,
  `file_path` varchar(4096) DEFAULT NULL,
  `upload_url` varchar(512) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `modified_user_id` varchar(32) DEFAULT NULL,
  `create_date` varchar(64) DEFAULT NULL,
  `modified_date` varchar(64) DEFAULT NULL,
  `create_unit_id` varchar(32) DEFAULT NULL,
  `modified_unit_id` varchar(32) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `flag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `webfile_id_unique_index` (`id`) USING BTREE,
  KEY `webfile_md5_index` (`md5`) USING BTREE,
  KEY `webfile_file_name_index` (`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_web_file
-- ----------------------------

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
INSERT INTO `t_user` VALUES ('402881e44b45bfb2014b45c0ae160001', 'helloworld', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_user` VALUES ('402881e44b45c1db014b45c239e80001', 'helloworld', null, null, null, null, null, null, null, null, null);
