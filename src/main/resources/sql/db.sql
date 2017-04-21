/*!40101 SET NAMES utf8 */;

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(30) DEFAULT NULL,
  `department_code` varchar(50) DEFAULT NULL,
  `parent_department_id` int(11) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `principal_person_id` int(11) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;


insert  into `department`(`department_id`,`department_name`,`department_code`,`parent_department_id`,`level`,`type`,`principal_person_id`,`creator_id`,`created_at`,`is_deleted`)
values 
(1,'人力资源部','01',1,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(2,'生产调度室','02',2,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(3,'物资部','03',3,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(4,'安质部','04',4,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(5,'财务部','05',5,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(6,'新线办','06',6,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(7,'改造办','07',7,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(8,'企发、法规','08',8,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(9,'项目部','09',9,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(10,'中心项目部','W0',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0');


DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单、连接、按钮权限',
  `permission_str` varchar(50) DEFAULT NULL COMMENT '菜单权限辨识字符串',
  `parent_permission_id` int(11) DEFAULT NULL COMMENT '父级菜单ID',
  `parent_permission_name` varchar(50) DEFAULT NULL COMMENT '父级菜单名字',
  `comment` text COMMENT '备注,说明',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(10) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `permission` VALUES ('1', '权限基础字段', '权限基础字段', '1', null, null, '1', '2016-10-31 10:37:47', '0');
INSERT INTO `permission` VALUES ('2', '系统管理', '系统管理', '1', null, null, '1', '2016-10-31 10:39:45', '0');
INSERT INTO `permission` VALUES ('3', '人员管理', '系统管理-人员管理', '2', '系统管理', null, '1', '2016-10-31 10:40:30', '0');
INSERT INTO `permission` VALUES ('4', '角色管理', '系统管理-角色管理', '2', '系统管理', null, '1', '2016-10-31 10:40:59', '0');
INSERT INTO `permission` VALUES ('5', '新增', '人员管理-新增', '3', '人员管理', null, '1', '2016-10-31 11:47:35', '0');
INSERT INTO `permission` VALUES ('6', '编辑', '人员管理-编辑', '3', '人员管理', null, '1', '2016-10-31 11:47:37', '0');
INSERT INTO `permission` VALUES ('7', '停用', '人员管理-停用', '3', '人员管理', null, '1', '2016-10-31 11:48:36', '0');
INSERT INTO `permission` VALUES ('8', '新增', '角色管理-新增', '4', '角色管理', null, '1', '2016-10-31 11:49:40', '0');
INSERT INTO `permission` VALUES ('9', '人员', '角色管理-人员', '4', '角色管理', null, '1', '2016-10-31 11:50:19', '0');
INSERT INTO `permission` VALUES ('10', '权限配置', '角色管理-权限配置', '4', '角色管理', null, '1', '2016-10-31 11:50:49', '0');
INSERT INTO `permission` VALUES ('11', '停用', '角色管理-停用', '4', '角色管理', null, '1', '2016-10-31 11:51:45', '0');


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


INSERT INTO `role` (`role_id`, `role_name`, `comment`, `creator_id`, `created_at`, `is_deleted`) VALUES ('1', 'admin', '最高权限', '1', '2016-09-07 10:09:07', '0');


DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('1', '5');
INSERT INTO `role_permission` VALUES ('1', '6');
INSERT INTO `role_permission` VALUES ('1', '7');
INSERT INTO `role_permission` VALUES ('1', '8');
INSERT INTO `role_permission` VALUES ('1', '9');
INSERT INTO `role_permission` VALUES ('1', '10');
INSERT INTO `role_permission` VALUES ('1', '11');


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(30) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL,
  `secret_key` varchar(64) DEFAULT NULL,
  `employee_code` varchar(50) DEFAULT NULL COMMENT '工号',
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `creator_id` int(11) DEFAULT '1',
  `created_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  KEY `index_login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;


INSERT INTO `user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `creator_id`, `created_at`, `is_deleted`) VALUES ('1', 'admin', '7394630bee36f2bd8b793f88320efdf8', 'cb7e52304f0d11e6965c00ff2c2e2b3f', '1', '系统管理员', NULL, '男', NULL, '1', '1', '2015-10-10 12:14:17', '0');



DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES ('1', '1');
