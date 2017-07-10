/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : phystrix

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-10 15:48:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hystrix_config`
-- ----------------------------
DROP TABLE IF EXISTS `hystrix_config`;
CREATE TABLE `hystrix_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `app_id` varchar(64) DEFAULT NULL COMMENT 'app唯一标识',
  `command_key` varchar(255) DEFAULT NULL COMMENT 'conmmandKey字段',
  `command_group` varchar(255) DEFAULT NULL COMMENT 'conmmandGroup字段',
  `fallback` varchar(255) DEFAULT NULL COMMENT 'fallback字段',
  `isolationStgy` varchar(255) DEFAULT NULL COMMENT 'fallback字段',
  `maxRequest` int(11) unsigned DEFAULT NULL COMMENT 'maxRequest字段',
  `timeout` int(11) unsigned DEFAULT NULL COMMENT 'tiameout字段',
  `threadPoolSize` smallint(4) unsigned DEFAULT NULL COMMENT 'threadPoolSize字段',
  `requestThreshold` int(11) unsigned DEFAULT NULL COMMENT 'requestThreshold字段',
  `errorThreshold` tinyint(2) unsigned DEFAULT NULL COMMENT 'errorThreshold字段',
  `circuitBreakTime` int(11) unsigned DEFAULT NULL COMMENT 'circuitBreakTime字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `appId_commandKey_unique` (`app_id`,`command_key`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of hystrix_config
-- ----------------------------
INSERT INTO `hystrix_config` VALUES ('59', 'aaa', 'getAddress', 'address', 'fail', 'SEMAPHORE', '10', '2000', '10', '20', '30', '10000', '2017-07-10 11:25:32', '2017-07-10 11:25:32');
INSERT INTO `hystrix_config` VALUES ('60', 'aaa', 'getContact', 'contact', 'contactFallBack', 'THREAD', '500', '1000', '30', '20', '30', '10000', '2017-07-10 11:25:32', '2017-07-10 11:25:32');
INSERT INTO `hystrix_config` VALUES ('63', 'phystrix', 'getAddress', 'address', 'fail', 'SEMAPHORE', '10', '2000', '10', '20', '30', '10000', '2017-07-10 11:58:00', '2017-07-10 11:58:00');
INSERT INTO `hystrix_config` VALUES ('64', 'phystrix', 'getContact', 'contact', 'contactFallBack', 'THREAD', '500', '1000', '30', '20', '30', '10000', '2017-07-10 11:58:00', '2017-07-10 11:58:00');

-- ----------------------------
-- Table structure for `hystrix_switch`
-- ----------------------------
DROP TABLE IF EXISTS `hystrix_switch`;
CREATE TABLE `hystrix_switch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `app_id` varchar(64) DEFAULT NULL COMMENT 'app唯一标识',
  `switch_status` varchar(3) DEFAULT NULL COMMENT '开关状态，1:打开，0:关闭',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `appaId_unique` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of hystrix_switch
-- ----------------------------
INSERT INTO `hystrix_switch` VALUES ('43', 'aaa', '1', '2017-07-10 11:23:43', '2017-07-10 11:23:43');
INSERT INTO `hystrix_switch` VALUES ('44', 'phystrix', '1', '2017-07-10 11:48:52', '2017-07-10 11:48:52');
