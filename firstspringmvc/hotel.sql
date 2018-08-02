/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : hotel

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-02 16:19:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mgt_user
-- ----------------------------
DROP TABLE IF EXISTS `t_mgt_user`;
CREATE TABLE `t_mgt_user` (
  `user_id` varchar(100) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `en_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `home_town` varchar(255) DEFAULT NULL,
  `chanese_id` varchar(255) DEFAULT NULL,
  `dept_id` varchar(255) DEFAULT NULL,
  `job_id` varchar(255) DEFAULT NULL,
  `user_level` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `mender` varchar(255) DEFAULT NULL,
  `mend_time` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sex_name` varchar(255) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mgt_user
-- ----------------------------
INSERT INTO `t_mgt_user` VALUES ('1', 'chenxiang', '', 'abc123', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
