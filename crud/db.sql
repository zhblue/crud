-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema crud
--

CREATE DATABASE IF NOT EXISTS crud;
USE crud;
--
-- Definition of table `config`
--

DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `value` text NOT NULL,
  `type` varchar(15) NOT NULL default 'report',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `config`
--

/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` (`id`,`name`,`value`) VALUES 
 (16,'存货报表','select g.id,g.name,sum(s.number) number from tb_storage s\r\nleft join tb_goods g on s.tb_goods_id= g.id\r\ngroup by s.tb_goods_id');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;


--
-- Definition of table `datadic`
--

DROP TABLE IF EXISTS `datadic`;
CREATE TABLE `datadic` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `field` varchar(45) collate utf8_bin default NULL,
  `name` varchar(45) collate utf8_bin default NULL,
  `transview` varchar(45) collate utf8_bin default NULL,
  `ftable` varchar(45) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `datadic`
--

/*!40000 ALTER TABLE `datadic` DISABLE KEYS */;
INSERT INTO `datadic` (`id`,`field`,`name`,`transview`,`ftable`) VALUES 
 (1,0x74657374,0xE6B58BE8AF95,NULL,NULL),
 (2,0x64617461646963,0xE695B0E68DAEE5AD97E585B8,NULL,NULL),
 (3,0x6E657773,0xE696B0E997BB,NULL,NULL),
 (4,0x6669656C64,0xE5AD97E6AEB5,NULL,NULL),
 (5,0x6E616D65,0xE5908DE7A7B0,NULL,NULL),
 (6,0x7469746C65,0xE6A087E9A298,NULL,NULL),
 (7,0x636F6E74656E74,0xE58685E5AEB9,NULL,NULL),
 (8,0x74625F77617265686F757365,0xE4BB93E5BA93,NULL,NULL),
 (9,0x77686E616D65,0xE4BB93E5BA93E5908DE7A7B0,NULL,NULL),
 (10,0x61646472657373,0xE59CB0E59D80,NULL,NULL),
 (11,0x70686F6E65,0xE794B5E8AF9D,NULL,NULL),
 (12,0x77686F776E6572,0xE5BA93E4B8BB,NULL,NULL),
 (13,0x74625F77617265726F6F6D,0xE5BA93E688BF,NULL,NULL),
 (14,0x77726E616D65,0xE5BA93E688BFE5908D,NULL,NULL),
 (15,0x74625F77617265686F7573655F6964,0xE4BB93E5BA93,NULL,NULL),
 (16,0x746F74616C7370616365,0xE680BBE5AEB9E9878F,NULL,NULL),
 (17,0x696E636861726765,0xE8B49FE8B4A3E4BABA,NULL,NULL),
 (18,0x74625F73746F72616765,0xE5BA93E5AD98,NULL,NULL),
 (19,0x74625F676F6F64735F6964,'',NULL,NULL),
 (20,0x74625F77617265686F7573655F6964,'',NULL,NULL),
 (21,0x74625F77617265726F6F6D5F6964,'',NULL,NULL),
 (22,0x6E756D626572,0xE695B0E9878F,NULL,NULL),
 (23,0x756E6974,0xE58D95E4BD8D,NULL,NULL),
 (24,0x74625F676F6F6473,0xE8B4A7E789A9,NULL,NULL),
 (25,0x6E616D65,0xE5908DE7A7B0,NULL,NULL),
 (26,0x6C6F636174696F6E,0xE4BAA7E59CB0,NULL,NULL),
 (27,0x74625F7368656C66,0xE8B4A7E69EB6,NULL,NULL),
 (28,0x6E616D65,'',NULL,NULL),
 (29,0x74625F77617265686F7573655F6964,'',NULL,NULL),
 (30,0x74625F77617265726F6F6D5F6964,'',NULL,NULL),
 (31,0x74625F7368656C66,0xE8B4A7E69EB6,NULL,NULL),
 (32,0x6E616D65,'',NULL,NULL),
 (33,0x74625F77617265686F7573655F6964,'',NULL,NULL),
 (34,0x74625F77617265726F6F6D5F6964,'',NULL,NULL),
 (35,0x70726976696C656765,0xE69D83E99990,'',NULL),
 (36,0x636F6E666967,0xE9858DE7BDAE,'',NULL),
 (37,0x75736572,0xE794A8E688B7,'',0x75736572),
 (38,0x696E7075745F75736572,0xE8BE93E585A5E88085,'',0x75736572),
 (39,0x74625F7465737432,0xE6B58BE8AF9532,NULL,NULL);
/*!40000 ALTER TABLE `datadic` ENABLE KEYS */;


--
-- Definition of table `news`
--

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(45) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `news`
--

/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` (`id`,`title`,`content`) VALUES 
 (1,'asdasd','<p>asdasd</p>\r\n');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;


--
-- Definition of table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL,
  `right` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `privilege`
--

/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` (`id`,`user_id`,`right`) VALUES 
 (8,1,'[]admin'),
 (9,1,'[user]read'),
 (10,1,'[datadic]read');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;



--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) collate utf8_bin default NULL,
  `password` varchar(45) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`name`,`password`) VALUES 
 (0,0x6775657374,0x67756573742063616E206E6F74206C6F67696E),
 (1,0x61646D696E,0x36633061363235376634393063633535663163666565366262353638623332363437323130336466);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

DROP TABLE IF EXISTS `tb_cam_detail`;
 CREATE TABLE `tb_cam_detail` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tb_manual_task_id` int DEFAULT NULL,
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cam_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tb_cam_move_id` int DEFAULT NULL,
  `tb_cam_held_id` int DEFAULT NULL,
  `cam_held_desc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tb_cam_angle_id` int DEFAULT NULL,
  `tb_cam_h_wide_id` int DEFAULT NULL,
  `tb_cam_v_wide_id` int DEFAULT NULL,
  `tb_cam_fade_id` int DEFAULT NULL,
  `object_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tb_object_position_id` int DEFAULT NULL,
  `object_h_scale` varchar(32) COLLATE utf8_bin DEFAULT '',
  `object_v_scale` varchar(32) COLLATE utf8_bin DEFAULT '',
  `tb_object_face_id` int DEFAULT NULL,
  `tb_object_head_id` int DEFAULT NULL,
  `object_part` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `clip_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '',
  `input_user` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
