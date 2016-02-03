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
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `datadic`
--

/*!40000 ALTER TABLE `datadic` DISABLE KEYS */;
INSERT INTO `datadic` (`id`,`field`,`name`,`transview`) VALUES 
 (1,0x74657374,0xE6B58BE8AF95,NULL),
 (2,0x64617461646963,0xE695B0E68DAEE5AD97E585B8,NULL),
 (3,0x6E657773,0xE696B0E997BB,NULL),
 (4,0x6669656C64,0xE5AD97E6AEB5,NULL),
 (5,0x6E616D65,0xE5908DE7A7B0,NULL),
 (6,0x7469746C65,0xE6A087E9A298,NULL),
 (7,0x636F6E74656E74,0xE58685E5AEB9,NULL),
 (8,0x74625F77617265686F757365,0xE4BB93E5BA93,NULL),
 (9,0x77686E616D65,0xE4BB93E5BA93E5908DE7A7B0,NULL),
 (10,0x61646472657373,0xE59CB0E59D80,NULL),
 (11,0x70686F6E65,0xE794B5E8AF9D,NULL),
 (12,0x77686F776E6572,0xE5BA93E4B8BB,NULL),
 (13,0x74625F77617265726F6F6D,0xE5BA93E688BF,NULL),
 (14,0x77726E616D65,0xE5BA93E688BFE5908D,NULL),
 (15,0x74625F77617265686F7573655F6964,0xE4BB93E5BA93,NULL),
 (16,0x746F74616C7370616365,0xE680BBE5AEB9E9878F,NULL),
 (17,0x696E636861726765,0xE8B49FE8B4A3E4BABA,NULL),
 (18,0x74625F73746F72616765,0xE5BA93E5AD98,NULL),
 (19,0x74625F676F6F64735F6964,'',NULL),
 (20,0x74625F77617265686F7573655F6964,'',NULL),
 (21,0x74625F77617265726F6F6D5F6964,'',NULL),
 (22,0x6E756D626572,0xE695B0E9878F,NULL),
 (23,0x756E6974,0xE58D95E4BD8D,NULL),
 (24,0x74625F676F6F6473,0xE8B4A7E789A9,NULL),
 (25,0x6E616D65,0xE5908DE7A7B0,NULL),
 (26,0x6C6F636174696F6E,0xE4BAA7E59CB0,NULL),
 (27,0x74625F7368656C66,0xE8B4A7E69EB6,NULL),
 (28,0x6E616D65,'',NULL),
 (29,0x74625F77617265686F7573655F6964,'',NULL),
 (30,0x74625F77617265726F6F6D5F6964,'',NULL),
 (31,0x74625F7368656C66,0xE8B4A7E69EB6,NULL),
 (32,0x6E616D65,'',NULL),
 (33,0x74625F77617265686F7573655F6964,'',NULL),
 (34,0x74625F77617265726F6F6D5F6964,'',NULL),
 (35,0x70726976696C656765,0xE69D83E99990,''),
 (36,0x636F6E666967,0xE9858DE7BDAE,''),
 (37,0x75736572,0xE794A8E688B7,'');
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
-- Definition of table `tb_goods`
--

DROP TABLE IF EXISTS `tb_goods`;
CREATE TABLE `tb_goods` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(32) collate utf8_bin default NULL,
  `location` varchar(32) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tb_goods`
--

/*!40000 ALTER TABLE `tb_goods` DISABLE KEYS */;
INSERT INTO `tb_goods` (`id`,`name`,`location`) VALUES 
 (1,0xE8A5BFE7939C,0xE696B0E79686),
 (2,0xE59C9FE8B186,0xE5B1B1E4B89C);
/*!40000 ALTER TABLE `tb_goods` ENABLE KEYS */;


--
-- Definition of table `tb_shelf`
--

DROP TABLE IF EXISTS `tb_shelf`;
CREATE TABLE `tb_shelf` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(32) collate utf8_bin default NULL,
  `tb_warehouse_id` int(11) default NULL,
  `tb_wareroom_id` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tb_shelf`
--

/*!40000 ALTER TABLE `tb_shelf` DISABLE KEYS */;
INSERT INTO `tb_shelf` (`id`,`name`,`tb_warehouse_id`,`tb_wareroom_id`) VALUES 
 (1,0xE4B89CE58D9731E58FB7E69EB6,2,3),
 (2,0xE8A5BFE58C97E5818FE58C97E69EB6,1,1);
/*!40000 ALTER TABLE `tb_shelf` ENABLE KEYS */;


--
-- Definition of table `tb_storage`
--

DROP TABLE IF EXISTS `tb_storage`;
CREATE TABLE `tb_storage` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `tb_goods_id` int(11) default NULL,
  `tb_warehouse_id` int(11) default NULL,
  `tb_wareroom_id` int(11) default NULL,
  `tb_shelf_id` int(10) unsigned default NULL,
  `number` int(10) unsigned default NULL,
  `unit` varchar(45) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tb_storage`
--

/*!40000 ALTER TABLE `tb_storage` DISABLE KEYS */;
INSERT INTO `tb_storage` (`id`,`tb_goods_id`,`tb_warehouse_id`,`tb_wareroom_id`,`tb_shelf_id`,`number`,`unit`) VALUES 
 (1,2,2,4,1,5,0xE585ACE696A4),
 (2,1,2,3,1,1,0xE590A8),
 (3,1,2,3,1,1,0xE590A8),
 (4,1,2,3,1,9,0xE4B8AA),
 (5,2,1,1,2,10,0xE585ACE696A4),
 (6,2,1,1,2,10,0xE585ACE696A4);
/*!40000 ALTER TABLE `tb_storage` ENABLE KEYS */;


--
-- Definition of table `tb_warehouse`
--

DROP TABLE IF EXISTS `tb_warehouse`;
CREATE TABLE `tb_warehouse` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `whname` varchar(32) collate utf8_bin default NULL,
  `address` varchar(32) collate utf8_bin default NULL,
  `phone` varchar(32) collate utf8_bin default NULL,
  `whowner` varchar(32) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tb_warehouse`
--

/*!40000 ALTER TABLE `tb_warehouse` DISABLE KEYS */;
INSERT INTO `tb_warehouse` (`id`,`whname`,`address`,`phone`,`whowner`) VALUES 
 (1,0xE69DADE5B79EE99B86E695A3,0xE5AE81E6B5B7,0x31323334353637,0xE7BE8EE59BBDE9989FE995BF),
 (2,0xE4B88BE6B299E6B4BEE98081,0x36E58FB7E5A4A7E8A197,0x37383930,0xE992A2E99381E4BEA0);
/*!40000 ALTER TABLE `tb_warehouse` ENABLE KEYS */;


--
-- Definition of table `tb_wareroom`
--

DROP TABLE IF EXISTS `tb_wareroom`;
CREATE TABLE `tb_wareroom` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `wrname` varchar(32) collate utf8_bin default NULL,
  `tb_warehouse_id` int(11) default NULL,
  `totalspace` int(11) default NULL,
  `incharge` varchar(32) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tb_wareroom`
--

/*!40000 ALTER TABLE `tb_wareroom` DISABLE KEYS */;
INSERT INTO `tb_wareroom` (`id`,`wrname`,`tb_warehouse_id`,`totalspace`,`incharge`) VALUES 
 (1,0xE799BEE4B896E6B187E9809A,1,1000,0xE9BB91E5AFA1E5A687),
 (2,0xE4B8ADE9809A,1,2000,0xE58FA3E6A3AE),
 (3,0xE9AB98E69599E59BADE58CBA,2,500,0xE89C98E89B9BE4BEA0),
 (4,0xE5B7A5E4B89AE59BADE58CBA,2,500,0xE7ABA0E9B1BCE58D9AE5A3AB);
/*!40000 ALTER TABLE `tb_wareroom` ENABLE KEYS */;


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




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
