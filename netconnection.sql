/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : netconnection

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2014-10-11 14:29:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for copyscreen
-- ----------------------------
DROP TABLE IF EXISTS `copyscreen`;
CREATE TABLE `copyscreen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mac` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `recordtime` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of copyscreen
-- ----------------------------

-- ----------------------------
-- Table structure for illegal
-- ----------------------------
DROP TABLE IF EXISTS `illegal`;
CREATE TABLE `illegal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(100) DEFAULT NULL,
  `mac` varchar(100) DEFAULT NULL,
  `clientname` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `illegal` int(5) DEFAULT NULL,
  `itime` varchar(255) DEFAULT NULL,
  `flow` int(5) DEFAULT NULL,
  `ftime` varchar(255) DEFAULT NULL,
  `recordtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of illegal
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operationername` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', null, '添加管理员信息', '添加一个姓名叫：admin管理员的信息！', '2014-10-10 12:39:12');
INSERT INTO `log` VALUES ('2', null, '添加策略', '添加了一个名为：默认策略新的策略', '2014-10-10 12:39:12');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Mac` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `readstate` int(11) DEFAULT NULL,
  `recordtime` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `clientname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for onlinetime
-- ----------------------------
DROP TABLE IF EXISTS `onlinetime`;
CREATE TABLE `onlinetime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8 NOT NULL,
  `mac` varchar(255) CHARACTER SET utf8 NOT NULL,
  `begintime` varchar(255) DEFAULT '0000-00-00 00:00:00',
  `endtime` varchar(255) DEFAULT NULL,
  `clientname` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of onlinetime
-- ----------------------------

-- ----------------------------
-- Table structure for patchinstallstate
-- ----------------------------
DROP TABLE IF EXISTS `patchinstallstate`;
CREATE TABLE `patchinstallstate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mac` varchar(45) NOT NULL,
  `patchname` varchar(45) DEFAULT NULL,
  `installstate` varchar(45) DEFAULT NULL,
  `version` varchar(45) DEFAULT NULL,
  `recordtime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patchinstallstate
-- ----------------------------

-- ----------------------------
-- Table structure for patchlist
-- ----------------------------
DROP TABLE IF EXISTS `patchlist`;
CREATE TABLE `patchlist` (
  `patchid` int(11) NOT NULL AUTO_INCREMENT,
  `patchname` varchar(45) DEFAULT NULL,
  `choose` varchar(45) DEFAULT NULL,
  `degree` varchar(45) DEFAULT NULL,
  `os` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patchid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patchlist
-- ----------------------------

-- ----------------------------
-- Table structure for pcinfo
-- ----------------------------
DROP TABLE IF EXISTS `pcinfo`;
CREATE TABLE `pcinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `mac` varchar(255) NOT NULL,
  `clientname` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `loadflow` bigint(20) DEFAULT NULL,
  `upflow` bigint(20) DEFAULT NULL,
  `cpu` varchar(255) DEFAULT NULL,
  `memory` varchar(255) DEFAULT NULL,
  `allmemory` varchar(255) DEFAULT NULL,
  `availablity` varchar(255) DEFAULT NULL,
  `onlinestate` int(11) DEFAULT NULL,
  `recordtime` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `statu` varchar(45) DEFAULT NULL,
  `warnstate` int(11) DEFAULT NULL,
  `pctype` int(11) DEFAULT NULL,
  `wifichk` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pcinfo
-- ----------------------------

-- ----------------------------
-- Table structure for softinstallstate
-- ----------------------------
DROP TABLE IF EXISTS `softinstallstate`;
CREATE TABLE `softinstallstate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mac` varchar(255) NOT NULL,
  `softname` varchar(255) DEFAULT NULL,
  `installstate` int(11) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `recordtime` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `state` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of softinstallstate
-- ----------------------------

-- ----------------------------
-- Table structure for softlist
-- ----------------------------
DROP TABLE IF EXISTS `softlist`;
CREATE TABLE `softlist` (
  `softid` int(11) NOT NULL AUTO_INCREMENT,
  `softname` varchar(45) DEFAULT NULL,
  `threadname` varchar(45) DEFAULT NULL,
  `choose` varchar(45) DEFAULT NULL,
  `statu` int(11) DEFAULT NULL,
  PRIMARY KEY (`softid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of softlist
-- ----------------------------

-- ----------------------------
-- Table structure for tacticslist
-- ----------------------------
DROP TABLE IF EXISTS `tacticslist`;
CREATE TABLE `tacticslist` (
  `tacticsid` int(11) NOT NULL AUTO_INCREMENT,
  `tacticsname` varchar(45) NOT NULL,
  `blacktime` int(11) DEFAULT NULL,
  `whitetime` int(11) DEFAULT NULL,
  `patchtime` int(11) DEFAULT NULL,
  PRIMARY KEY (`tacticsid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tacticslist
-- ----------------------------
INSERT INTO `tacticslist` VALUES ('1', '默认策略', '20', '20', '120');

-- ----------------------------
-- Table structure for tactics_pc
-- ----------------------------
DROP TABLE IF EXISTS `tactics_pc`;
CREATE TABLE `tactics_pc` (
  `tacticsid` int(11) NOT NULL,
  `mac` varchar(200) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tactics_pc
-- ----------------------------

-- ----------------------------
-- Table structure for tactics_soft
-- ----------------------------
DROP TABLE IF EXISTS `tactics_soft`;
CREATE TABLE `tactics_soft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tacticsid` int(11) DEFAULT NULL,
  `listid` int(11) DEFAULT NULL,
  `statu` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tactics_soft
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `onlinestate` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '!#/)zWェC塉J��', '0');
