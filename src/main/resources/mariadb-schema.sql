-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for users
DROP DATABASE IF EXISTS `users`;
CREATE DATABASE IF NOT EXISTS `users` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `users`;

-- Dumping structure for table users.client_role
DROP TABLE IF EXISTS `client_role`;
CREATE TABLE IF NOT EXISTS `client_role` (
  `authority` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `last_modified_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2client_authorized_grant_types
DROP TABLE IF EXISTS `oauth2client_authorized_grant_types`;
CREATE TABLE IF NOT EXISTS `oauth2client_authorized_grant_types` (
  `oauth2client_client_id` varchar(255) NOT NULL,
  `authorized_grant_types` varchar(255) NOT NULL,
  KEY `FKrva9mosn9v2urqdwn2svrcp7i` (`oauth2client_client_id`),
  CONSTRAINT `FKrva9mosn9v2urqdwn2svrcp7i` FOREIGN KEY (`oauth2client_client_id`) REFERENCES `oauth_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2client_auto_approve_scope
DROP TABLE IF EXISTS `oauth2client_auto_approve_scope`;
CREATE TABLE IF NOT EXISTS `oauth2client_auto_approve_scope` (
  `oauth2client_client_id` varchar(255) NOT NULL,
  `auto_approve_scope` varchar(255) NOT NULL,
  KEY `FKe1es6er4b3nmxj6vvffh2iwlf` (`oauth2client_client_id`),
  CONSTRAINT `FKe1es6er4b3nmxj6vvffh2iwlf` FOREIGN KEY (`oauth2client_client_id`) REFERENCES `oauth_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2client_registered_redirect_uri
DROP TABLE IF EXISTS `oauth2client_registered_redirect_uri`;
CREATE TABLE IF NOT EXISTS `oauth2client_registered_redirect_uri` (
  `oauth2client_client_id` varchar(255) NOT NULL,
  `registered_redirect_uri` varchar(255) NOT NULL,
  KEY `FKqwlhco6clpgridvmavovyumik` (`oauth2client_client_id`),
  CONSTRAINT `FKqwlhco6clpgridvmavovyumik` FOREIGN KEY (`oauth2client_client_id`) REFERENCES `oauth_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2client_resource_ids
DROP TABLE IF EXISTS `oauth2client_resource_ids`;
CREATE TABLE IF NOT EXISTS `oauth2client_resource_ids` (
  `oauth2client_client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) NOT NULL,
  KEY `FK1h9sneonrokajo5t4xuu7uvk6` (`oauth2client_client_id`),
  CONSTRAINT `FK1h9sneonrokajo5t4xuu7uvk6` FOREIGN KEY (`oauth2client_client_id`) REFERENCES `oauth_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2client_scope
DROP TABLE IF EXISTS `oauth2client_scope`;
CREATE TABLE IF NOT EXISTS `oauth2client_scope` (
  `oauth2client_client_id` varchar(255) NOT NULL,
  `scope` varchar(255) NOT NULL,
  KEY `FK3buxrmvaa0f8149wlu67qm1xk` (`oauth2client_client_id`),
  CONSTRAINT `FK3buxrmvaa0f8149wlu67qm1xk` FOREIGN KEY (`oauth2client_client_id`) REFERENCES `oauth_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth2_client_role
DROP TABLE IF EXISTS `oauth2_client_role`;
CREATE TABLE IF NOT EXISTS `oauth2_client_role` (
  `client_id` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`client_id`,`authority`),
  KEY `FKsyd2nttfb4m1v6ulnngv3rdk9` (`authority`),
  CONSTRAINT `FK83t072l40r0v90qnmucv03kpc` FOREIGN KEY (`client_id`) REFERENCES `oauth_client` (`client_id`),
  CONSTRAINT `FKsyd2nttfb4m1v6ulnngv3rdk9` FOREIGN KEY (`authority`) REFERENCES `client_role` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.oauth_client
DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE IF NOT EXISTS `oauth_client` (
  `client_id` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `last_modified_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `access_token_validity_seconds` int(11) NOT NULL DEFAULT 10800,
  `additional_info` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) NOT NULL,
  `refresh_token_validity_seconds` int(11) NOT NULL DEFAULT 2592000,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `authority` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `last_modified_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `last_modified_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `account_non_expired` bit(1) NOT NULL DEFAULT b'1',
  `account_non_locked` bit(1) NOT NULL DEFAULT b'1',
  `credentials_non_expired` bit(1) NOT NULL DEFAULT b'1',
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_589idila9li6a4arw1t8ht1gx` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table users.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `username` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`authority`),
  KEY `FKep5tcvsl2ep5uuysgi1fmfp0a` (`authority`),
  CONSTRAINT `FKep5tcvsl2ep5uuysgi1fmfp0a` FOREIGN KEY (`authority`) REFERENCES `role` (`authority`),
  CONSTRAINT `FKnircs1pyebpo0eucojumm0aed` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data export

INSERT INTO `role` (authority) VALUES ('ADMIN');
INSERT INTO `role` (authority) VALUES ('SYSADMIN');
INSERT INTO `role` (authority) VALUES ('USER_CREATE');
INSERT INTO `role` (authority) VALUES ('USER_READ');
INSERT INTO `role` (authority) VALUES ('USER_UPDATE');
INSERT INTO `role` (authority) VALUES ('USER_DELETE');
INSERT INTO `role` (authority) VALUES ('CLIENT_CREATE');
INSERT INTO `role` (authority) VALUES ('CLIENT_READ');
INSERT INTO `role` (authority) VALUES ('CLIENT_UPDATE');
INSERT INTO `role` (authority) VALUES ('CLIENT_DELETE');

INSERT INTO `user`  (username, password, first_name, last_name, email, phone)
  VALUES ('admin', /*Admin@123*/'$2a$08$5vQswSs56JCZX5ERAGFC8erdfoaj0Rbfh88Tv0P4V77KF8eCViWhi', 'Sanjib', 'Talukdar', 'sanjeeb.talukdar@gmail.com', '9833375042');

INSERT INTO `user`  (username, password, first_name, last_name, email, phone)
  VALUES ('sysadmin', /*Admin@123*/'$2a$08$5vQswSs56JCZX5ERAGFC8erdfoaj0Rbfh88Tv0P4V77KF8eCViWhi', 'Anal', 'Ghosh', 'expogrow.org@gmail.com', '9007042660');
  
INSERT INTO `user_role` (username, authority) VALUES ('admin', 'ADMIN');
INSERT INTO `user_role` (username, authority) VALUES ('sysadmin', 'SYSADMIN');

INSERT INTO `client_role` (authority) VALUES ('USER');

INSERT INTO `oauth_client` (client_id, client_secret)
	VALUES ('oauth2-read-client', /*Pass@123*/'$2a$08$4zmA6shHXQcl1WzYWzy8YOgq9SaPyQkQpakyVOr3iLbKOEdPUBb6q');

INSERT INTO `oauth_client` (client_id, client_secret)
	VALUES ('oauth2-read-write-client', /*Pass@123*/'$2a$08$4zmA6shHXQcl1WzYWzy8YOgq9SaPyQkQpakyVOr3iLbKOEdPUBb6q');
	
INSERT INTO `oauth2_client_role` (client_id, authority) VALUES ('oauth2-read-client', 'USER');
INSERT INTO `oauth2_client_role` (client_id, authority) VALUES ('oauth2-read-write-client', 'USER');
	
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'read');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'read');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'write');

INSERT INTO `oauth2client_scope` (oauth2client_client_id, scope) VALUES ('oauth2-read-write-client', 'openid');
INSERT INTO `oauth2client_scope` (oauth2client_client_id, scope) VALUES ('oauth2-read-client', 'openid');

INSERT INTO `oauth2client_auto_approve_scope` (oauth2client_client_id, auto_approve_scope) VALUES ('oauth2-read-write-client', 'openid');
INSERT INTO `oauth2client_auto_approve_scope` (oauth2client_client_id, auto_approve_scope) VALUES ('oauth2-read-client', 'openid');


INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'password');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'authorization_code');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'refresh_token');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'implicit');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-client', 'password');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'password');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'authorization_code');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'refresh_token');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'implicit');
INSERT INTO `oauth2client_authorized_grant_types` (oauth2client_client_id, authorized_grant_types) VALUES ('oauth2-read-write-client', 'password');

INSERT INTO `oauth2client_registered_redirect_uri`  (oauth2client_client_id, registered_redirect_uri) VALUES ('oauth2-read-client', 'http://localhost:8083/login');
INSERT INTO `oauth2client_registered_redirect_uri`  (oauth2client_client_id, registered_redirect_uri) VALUES ('oauth2-read-client', 'https://localhost:8083/login');
INSERT INTO `oauth2client_registered_redirect_uri`  (oauth2client_client_id, registered_redirect_uri) VALUES ('oauth2-read-write-client', 'https://localhost:8083/login');
INSERT INTO `oauth2client_registered_redirect_uri`  (oauth2client_client_id, registered_redirect_uri) VALUES ('oauth2-read-write-client', 'http://localhost:8083/login');

