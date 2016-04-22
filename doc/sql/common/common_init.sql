/*
Navicat MySQL Data Transfer

Source Server         : LocalhostMySQL
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : yaheen

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2015-01-12 15:04:07
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
INSERT INTO `tb_permission` VALUES ('402881e448e5ae5f0148e5ae5f390005', '测试6', 'test6', '', 'Y', '/permission/[A-Za-z0-9]*.do,/role/[A-Za-z0-9]*.do,/user/[A-Za-z0-9]*.do,/unit/[A-Za-z0-9]*.do,/rolePermission/[A-Za-z0-9]*.do,/userRole/[A-Za-z0-9]*.do,/logger/[A-Za-z0-9]*.do, /logger/[A-Za-z0-9]*.do', '/user/info.do,/user/infoUpdate.do,/unit/ajaxLoadUnit.do,/unit/searchUnitById.do,V:/user/list.do', '402881e448e5ae5f0148e5ae648d0063', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-28 23:45:04', '2014-11-20 19:15:14', null, 'SUPERADMIN', null, null);
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
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948898ce40009', '402881e448e5ae5f0148e5ae5f77000d', '402881e448e5ae5f0148e5ae5f380004', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948898ce4000a', '402881e448e5ae5f0148e5ae5f77000d', '402881e448e5ae5f0148e5ae5f350002', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948924d89002c', '402881e448e5ae5f0148e5ae5f75000c', '402881e448e5ae5f0148e5ae5f380004', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948924d89002d', '402881e448e5ae5f0148e5ae5f75000c', '402881e448e5ae5f0148e5ae5f350002', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948a38b060050', '402881e448e5ae5f0148e5ae5f74000b', '402881e448e5ae5f0148e5ae5f350002', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('402881e449487cd8014948a38b060051', '402881e448e5ae5f0148e5ae5f74000b', '402881e448e5ae5f0148e5ae5f370003', null, null, null, null, null, null, null, null);
INSERT INTO `tb_role_permission` VALUES ('40289f1149cc9e7f0149ccea6f9d000d', '402881e448e5ae5f0148e5ae5f6c0008', '402881e448e5ae5f0148e5ae5f390005', null, null, null, null, null, null, null, null);

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
INSERT INTO `tb_super_user` VALUES ('402881e448e5ae5f0148e5ae5f7a000e', 'admin', '5F4DCC3B5AA765D61D8327DEB882CF99', '超级管理员', 'dasdddddd', '大专', '123123123', '12345678dd7', 'asdasd', 'M', 'N', null, null, null, null, 'SUPERADMIN', null, '2015-01-09 19:07:22', null, 'SYSTEM', null, 'SYS');

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
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b805e70002', 'ddddd', '', 'ddddd', '', '', '', null, null, '402881e448e5ae5f0148e5ae64800062', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:00', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b824530003', 'qeeqwwe', '', 'qeqwe', 'eee', '', 'qweqweq', null, null, '402881e448e5ae5f0148e5ae64800062', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:07', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b83f450004', 'qweqw', '', 'eqwe', 'eee', '', '', null, null, '402881e448e5ae5f0148e5ae64800062', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:14', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b85e110005', 'qweq', '', 'weqweq', 'weeee', '', '', null, null, '4028818e49e6ae4a0149e6b83f450004', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:22', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b882520006', 'qweqwe', '', 'qweqw', 'eqee', '', '', null, null, '4028818e49e6ae4a0149e6b805e70002', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:32', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b8a8250007', 'eeeeee', '', 'eeeeeeeeeeeeee', 'eeeeeeeee', '', '', null, null, '4028818e49e6ae4a0149e6b824530003', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:41', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b8e2d60008', 'eqwe', '', 'e', 'ee', '', 'ee', null, null, '4028818e49e6ae4a0149e6b85e110005', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:31:56', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b912440009', 'qweq', '', 'eqe', 'eee', '', '', null, null, '4028818e49e6ae4a0149e6b8e2d60008', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:32:08', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b94cd2000a', 'qweqw', '', 'eqweqe', 'qwe', '', 'qweqe', null, null, '402881e449d6fcb20149d6fe55c20002', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:32:23', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b97f9d000b', 'qweq', '', 'eee', '', '', '', null, null, '4028818e49e6ae4a0149e6b83f450004', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:32:36', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('4028818e49e6ae4a0149e6b9bc34000c', 'asd', '', 'dd', '', '', '', null, null, '402881e449d6fcb20149d6fe55c20002', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 19:32:52', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f7b000f', '广州航海高等专科学校', '办学性质：公办', '11106', '', '', '', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-09-27 20:44:20', null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f7d0010', '广东交通职业技术学院', '办学性质：公办', '10861', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f7e0011', '广东水利电力职业技术学院', '办学性质：公办', '10862', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f7f0012', '广东财经职业学院（已并入广东外语外贸大学）', '', '12005', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f800013', '广东司法警官职业学院', '办学性质：公办', '12960', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f810014', '广东行政职业学院', '办学性质：公办', '12577', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f820015', '广东体育职业技术学院', '办学性质：公办', '12578', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f840016', '广东文艺职业学院', '办学性质：公办', '13707', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f850017', '广东食品药品职业学院', '办学性质：公办', '12573', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f860018', '广东女子职业技术学院', '办学性质：公办', '12742', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f860019', '广东松山职业技术学院', '办学性质：公办', '12060', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f88001a', '广东轻工职业技术学院', '办学性质：公办', '10833', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f89001b', '广东农工商职业技术学院', '办学性质：公办', '12322', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f8a001c', '广东邮电职业技术学院', '办学性质：公办', '12953', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f8e001d', '广东工程职业技术学院', '办学性质：公办', '13930', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f90001e', '广东科贸职业学院', '办学性质：公办', '14063', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f92001f', '广东环境保护工程职业学院', '办学性质：公办', '14311', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f930020', '广东青年职业学院', '办学性质：公办', '14361', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f950021', '广东舞蹈戏剧职业学院（原广东舞蹈学校）', '办学性质：公办', '14407', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f960022', '广州体育职业技术学院', '办学性质：公办', '13708', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f970023', '广州工程技术职业学院', '办学性质：公办', '13709', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f980024', '广东省外语艺术职业学院', '办学性质：公办', '12962', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f990025', '广州铁路职业技术学院', '办学性质：公办', '13943', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f9a0026', '广州城市职业学院', '办学性质：公办', '13929', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f9c0027', '广州科技贸易职业学院', '办学性质：公办', '14065', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f9e0028', '深圳职业技术学院', '办学性质：公办', '11113', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5f9f0029', '深圳信息职业技术学院', '办学性质：公办', '12957', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa0002a', '珠海城市职业技术学院', '办学性质：公办', '13713', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa1002b', '汕头职业技术学院', '办学性质：公办', '12954', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa2002c', '河源职业技术学院', '办学性质：公办', '12772', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa3002d', '惠州卫生职业技术学院', '办学性质：公办', '14408', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa4002e', '汕尾职业技术学院', '办学性质：公办', '12765', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa5002f', '广东机电职业技术学院', '办学性质：公办', '12743', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fa60030', '中山火炬职业技术学院', '办学性质：公办', '13710', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb20034', '中山职业技术学院', '办学性质：公办', '14066', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb30035', '江门职业技术学院', '办学性质：公办', '13711', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb40036', '佛山职业技术学院', '办学性质：公办', '12327', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb50037', '阳江职业技术学院', '办学性质：公办', '12771', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb60038', '茂名职业技术学院', '办学性质：公办', '13712', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb70039', '肇庆医学高等专科学校', '办学性质：公办', '13810', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb8003a', '清远职业技术学院', '办学性质：公办', '12958', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fb9003b', '揭阳职业技术学院', '办学性质：公办', '12956', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fba003c', '罗定职业技术学院', '办学性质：公办', '12770', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fd1003d', '广东工贸职业技术学院', '办学性质：公办', '12959', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fdc003e', '顺德职业技术学院', '办学性质：公办', '10831', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fe0003f', '东莞职业技术学院', '办学性质：公办', '14263', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae5fe70040', '民办南华工商学院', '办学性质：民办', '11114', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60140041', '私立华联学院', '办学性质：民办', '11121', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60340042', '潮汕职业技术学院', '办学性质：民办', '10965', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae603f0043', '广东新安职业技术学院', '办学性质：民办', '12325', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60610044', '广东岭南职业技术学院', '办学性质：民办', '12749', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60820045', '广东亚视演艺职业学院', '办学性质：民办', '12961', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60a30046', '南海东软信息技术职业学院', '办学性质：民办', '12574', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60d10047', '广州康大职业技术学院', '办学性质：民办', '12575', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60f10048', '广东职业技术学院（原广东纺织职业技术学院）', '办学性质：公办', '12736', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae60fc0049', '珠海艺术职业学院', '办学性质：民办', '12576', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae611e004a', '广州工商职业技术学院', '办学性质：民办', '13714', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae613f004b', '广州涉外经济职业技术学院', '办学性质：民办', '13715', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae6178004c', '广州南洋理工职业学院', '办学性质：民办', '13716', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae6198004d', '广州科技职业技术学院', '办学性质：民办', '13717', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae61a3004e', '惠州经济职业技术学院', '办学性质：民办', '13718', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae61c4004f', '肇庆科技职业技术学院', '办学性质：民办', '13720', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae61ea0050', '肇庆工商职业技术学院', '办学性质：民办', '13721', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae62070051', '广州华南商贸职业学院', '办学性质：民办', '13927', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae62440052', '广州华立科技职业学院', '办学性质：民办', '13928', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae625f0053', '广东建设职业技术学院', '办学性质：公办', '12741', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae626c0054', '广州现代信息工程职业技术学院', '办学性质：民办', '13912', '', '', '', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-22 21:56:58', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae628c0055', '广州珠江职业技术学院', '办学性质：民办', '14123', '', '', '8888', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae62ad0056', '广州松田职业学院', '办学性质：民办', '14125', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae62d10057', '广东文理职业学院', '办学性质：民办', '14126', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae63070058', '广州城建职业学院', '办学性质：民办', '14136', '', '', '', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-22 22:04:30', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae63280059', '广东南方职业学院（原广东江门艺华旅游职业学院）', '办学性质：民办', '14265', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae6333005a', '广州华商职业学院', '办学性质：民办', '14266', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae6354005b', '广州华夏职业学院', '办学性质：民办', '14268', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae63a3005c', '广东创新科技职业学院', '办学性质：民办', '14363', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae63db005d', '广州东华职业学院', '办学性质：民办', '14362', '', '', '', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-22 22:05:23', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae63fb005e', '广东理工职业学院', '办学性质：公办', '13919', '', '', '', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae6406005f', '广东信息工程职业学院', '办学性质：民办', '12345', '123123', '12313', '3123123', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-12-14 02:51:47', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae64600061', '广东科学技术职业学院', '办学性质：公办', '12572', '', '', '', null, null, null, null, '402881e448b2dc400148b2dcfa370003', null, '2014-09-29 11:42:47', null, '77', null, null);
INSERT INTO `tb_unit` VALUES ('402881e448e5ae5f0148e5ae64800062', '广州番禺职业技术学院', '办学性质：公办', '12046', '123123', '123123', '12333', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-12-03 16:48:09', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e449d6fcb20149d6fe55c20002', '123', 'asdd', '123', '123', '123', '123', null, null, '402881e448e5ae5f0148e5ae64800062', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-11-22 18:13:52', '2014-11-23 12:16:16', 'SUPERADMIN', 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e449d781060149d795cf280003', '123', '333', '13', '123', '123', '123', null, null, '402881e448e5ae5f0148e5ae63db005d', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-11-22 20:59:19', '2014-11-22 21:17:38', 'SUPERADMIN', 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e449d781060149d79ada410006', 'qwe', '', 'qe', '', '', 'e', null, null, '402881e448e5ae5f0148e5ae63db005d', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-22 21:04:50', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e449d781060149d7a76ae60007', '1231', '', '33333s', '', '', '', null, null, '402881e448e5ae5f0148e5ae6333005a', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-22 21:18:33', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_unit` VALUES ('402881e449d781060149d7ae9bcd0008', 'sss', '', 'ssss', '', '', '', null, null, '402881e448e5ae5f0148e5ae62ad0056', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-11-22 21:26:24', '2014-11-22 21:30:08', 'SUPERADMIN', 'SUPERADMIN', null, null);
INSERT INTO `tb_unit` VALUES ('402881e449da96300149dadda4e80005', '12312', '<script>alert(0);</script>', 'assdassdasd', '<script>alert(0);</script>', 'd', 'asdad', null, null, '402881e449d6fcb20149d6fe55c20002', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-11-23 12:16:39', '2014-11-25 17:00:07', 'SUPERADMIN', 'SUPERADMIN', null, null);

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
INSERT INTO `tb_user` VALUES ('402832d34a433845014a4338cb8d0002', 'aaaa', 'E10ADC3949BA59ABBE56E057F20F883E', 'aaaa', null, null, null, null, null, null, null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-12-13 18:36:43', '2014-12-13 18:37:12', null, 'SUPERADMIN', null, 'D');
INSERT INTO `tb_user` VALUES ('402832d34a433845014a4338f9270004', 'bbbv', 'E10ADC3949BA59ABBE56E057F20F883E', 'bbbv', null, null, null, null, null, null, null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-12-13 18:36:54', '2014-12-15 15:18:47', null, 'SUPERADMIN', null, 'D');
INSERT INTO `tb_user` VALUES ('402832d34a433845014a43391cbe0006', 'ccccc', 'E10ADC3949BA59ABBE56E057F20F883E', 'ccccc', null, null, null, null, null, null, null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-12-13 18:37:04', '2014-12-13 18:37:12', null, 'SUPERADMIN', null, 'D');
INSERT INTO `tb_user` VALUES ('4028818e49e5a4b00149e5da7d540002', '123', '202CB962AC59075B964B07152D234B70', '123', '', '', '', '123', '', 'M', 'N', null, null, '402881e449d781060149d79ada410006', '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 15:29:01', null, 'SUPERADMIN', null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae648d0063', 'admin2', '5F4DCC3B5AA765D61D8327DEB882CF99', '超级管理员', '', '茜', '132423424', '234243234', '32432@3.com', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae64ad0064', 'test', '098F6BCD4621D373CADE4E832627B4F6', 'test2', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae64ce0065', '胡耀民', 'D41D8CD98F00B204E9800998ECF8427E', '胡耀民', '123123', '', '1111111111', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae64800062', null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-25 15:29:34', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae65110066', '钟伟成', 'D41D8CD98F00B204E9800998ECF8427E', '钟伟成', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae651c0067', '陈赛', 'D41D8CD98F00B204E9800998ECF8427E', '陈赛', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae653d0068', '余明辉', 'D41D8CD98F00B204E9800998ECF8427E', '余明辉', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae655e0069', '李洛', 'D41D8CD98F00B204E9800998ECF8427E', '李洛', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae6581006a', '蒲少琴', 'D41D8CD98F00B204E9800998ECF8427E', '蒲少琴', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae65a2006b', '朱义勇', 'D41D8CD98F00B204E9800998ECF8427E', '朱义勇', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae65db006c', '李向阳', 'D41D8CD98F00B204E9800998ECF8427E', '李向阳', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae65fb006d', 'user2', '24C9E15E52AFC47C225B757E7BEE1F9D', '李二', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae6606006e', '王伟', 'D41D8CD98F00B204E9800998ECF8427E', '王伟', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae6627006f', '许凤萍', 'D41D8CD98F00B204E9800998ECF8427E', '许凤萍', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae66490070', '李惠华', 'D41D8CD98F00B204E9800998ECF8427E', '李惠华', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae666a0071', '苏炳汉', 'D41D8CD98F00B204E9800998ECF8427E', '苏炳汉', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae66980072', '刘载兴', 'D41D8CD98F00B204E9800998ECF8427E', '刘载兴', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae66da0073', 'user1', '24C9E15E52AFC47C225B757E7BEE1F9D', '张一', '', '', '', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae60f10048', null, '402881e448e5ae5f0148e5ae66da0073', null, '2014-11-20 19:27:56', null, '402881e448e5ae5f0148e5ae63280059', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67340074', '张平安', 'D41D8CD98F00B204E9800998ECF8427E', '张平安', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67490075', '黄福良', 'E10ADC3949BA59ABBE56E057F20F883E', '黄福良', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67790076', '欧钧陶', 'D41D8CD98F00B204E9800998ECF8427E', '欧钧陶', '', '', '', '', '', 'M', 'N', null, '', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67ae0077', 'xszyou', '5F4DCC3B5AA765D61D8327DEB882CF99', '斌', '', '', '', '', '<script>alert(0);</script>', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae64800062', null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-24 23:17:24', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67b80078', 'user3', '24C9E15E52AFC47C225B757E7BEE1F9D', '王三', null, null, null, null, null, null, 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae67d90079', 'user4', 'E10ADC3949BA59ABBE56E057F20F883E', '啥四', '', '', '', '', '', 'M', 'N', null, null, null, null, '402881e448b2dc400148b2dcfa370003', null, '2014-09-29 12:23:59', null, '77', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae6812007a', 'test4', '5F4DCC3B5AA765D61D8327DEB882CF99', 'test4', '', 'y', '', '', '', 'M', 'N', null, null, null, null, '402881e448b2dc400148b2dcfa370003', null, '2014-09-30 00:25:36', null, '77', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae6832007b', 'test5', '5F4DCC3B5AA765D61D8327DEB882CF99', 'test5', '', '', '', '', '', 'M', 'N', null, null, null, null, '402881e448bc5ab10148bc5bed330002', null, '2014-09-29 00:16:33', null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae683d007c', 'test6', '5F4DCC3B5AA765D61D8327DEB882CF99', 'test6', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae685e007d', 'test0', '5F4DCC3B5AA765D61D8327DEB882CF99', 'test0', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae687f007e', 'test03', '5F4DCC3B5AA765D61D8327DEB882CF99', 'test03', '', '', '', '', '', 'M', 'N', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae68a1007f', 'test55', '827CCB0EEA8A706C4C34A16891F84E7B', 'test55', '123', '大专', '1231233', 'eee', '123123', 'M', 'Y', null, null, '402881e448e5ae5f0148e5ae64800062', '402881e448e5ae5f0148e5ae5f7a000e', '402881e448e5ae5f0148e5ae5f7a000e', '2014-09-29 00:14:21', '2014-12-01 21:31:44', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae68da0080', 'hym', 'CB201CFA6F4DB71EE0ECABAE56C02B7D', '胡耀民', '', '', '', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae64800062', null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-12-01 21:32:06', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae68fa0081', '邓文辉', '5F4DCC3B5AA765D61D8327DEB882CF99', '邓文辉', '', '', '', '12345', '', 'M', 'N', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-12-02 16:28:35', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae69050082', 'gsh', 'AE9FA6D4A2DE36B4477D0381B9F0B795', '郭盛辉', '', '', '', '', '', 'M', 'N', null, null, null, null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-24 23:17:36', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e448e5ae5f0148e5ae69260083', 'test3', '8AD8757BAA8564DC136C1E07507F4A98', 'test3', '', '大专', '', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae60f10048', null, '402881e448e5ae5f0148e5ae5f7a000e', null, '2014-11-20 19:07:26', null, 'SUPERADMIN', null, null);
INSERT INTO `tb_user` VALUES ('402881e44a57edeb014a57ef5b3e0002', 'test99', 'E10ADC3949BA59ABBE56E057F20F883E', '测试99', '', '', '', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae64800062', null, '402881e44a57edeb014a57ef5b3e0002', null, '2014-12-17 19:09:04', null, '402881e448e5ae5f0148e5ae64800062', null, null);
INSERT INTO `tb_user` VALUES ('40289f1149cc9e7f0149ccf066820012', '222', '202CB962AC59075B964B07152D234B70', '333', '', '', '', '', '', 'M', 'N', null, null, '402881e448e5ae5f0148e5ae64600061', '402881e448e5ae5f0148e5ae69260083', '402881e448e5ae5f0148e5ae5f7a000e', '2014-11-20 19:22:27', '2014-11-24 23:13:34', '402881e448e5ae5f0148e5ae60f10048', 'SUPERADMIN', null, null);

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
INSERT INTO `tb_user_role` VALUES ('4028818e49f557360149f558a0160002', '402881e448e5ae5f0148e5ae648d0063', '402881e448e5ae5f0148e5ae5f6a0007', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e448ee91200148ee9e9d3d0003', '402881e448e5ae5f0148e5ae68a1007f', '402881e448e5ae5f0148e5ae5f74000b', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e448f33e480148f3482c4c0005', '402881e448e5ae5f0148e5ae69260083', '402881e448e5ae5f0148e5ae5f6a0007', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e449487cd80149488a0ed5000c', '402881e448e5ae5f0148e5ae69050082', '402881e448e5ae5f0148e5ae5f74000b', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e449487cd80149488a0ed5000d', '402881e448e5ae5f0148e5ae69050082', '402881e448e5ae5f0148e5ae5f720009', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e449487cd8014948910882002b', '402881e448e5ae5f0148e5ae687f007e', '402881e448e5ae5f0148e5ae5f73000a', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e449487cd8014948a050ec004c', '402881e448e5ae5f0148e5ae68fa0081', '402881e448e5ae5f0148e5ae5f77000d', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('402881e449487cd8014948a050ec004d', '402881e448e5ae5f0148e5ae68fa0081', '402881e448e5ae5f0148e5ae5f6c0008', null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_role` VALUES ('40289f1149cc9e7f0149cceafb5d000e', '402881e448e5ae5f0148e5ae66da0073', '402881e448e5ae5f0148e5ae5f6c0008', null, null, null, null, null, null, null, null);

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
  UNIQUE KEY `webfile_id_unique_index` (`id`),
  KEY `webfile_md5_index` (`md5`),
  KEY `webfile_file_name_index` (`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_web_file
-- ----------------------------
