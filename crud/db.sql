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
-- Definition of table `datadic`
--

DROP TABLE IF EXISTS `datadic`;
CREATE TABLE `datadic` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `field` varchar(45) collate utf8_bin default NULL,
  `name` varchar(45) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `datadic`
--

/*!40000 ALTER TABLE `datadic` DISABLE KEYS */;
INSERT INTO `datadic` (`id`,`field`,`name`) VALUES 
 (1,0x74657374,0xE6B58BE8AF95),
 (2,0x64617461646963,0xE695B0E68DAEE5AD97E585B8),
 (3,0x6E657773,0xE696B0E997BB),
 (4,0x6669656C64,0xE5AD97E6AEB5),
 (5,0x6E616D65,0xE5908DE7A7B0),
 (6,0x7469746C65,0xE6A087E9A298),
 (7,0x636F6E74656E74,0xE58685E5AEB9);
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
 (8,1,'[privilege]read'),
 (9,1,'[user]read'),
 (10,1,'[datadic]read'),
 (11,1,'[news]read');
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
 (0,'guest','guest can not login'),
 (1,0x61646D696E,0x36633061363235376634393063633535663163666565366262353638623332363437323130336466);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
