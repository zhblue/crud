-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: crud
-- ------------------------------------------------------
-- Server version	5.7.18-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `s_config`
--

DROP TABLE IF EXISTS `s_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `value` text NOT NULL,
  `type` varchar(15) NOT NULL DEFAULT 'report',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_config`
--

LOCK TABLES `s_config` WRITE;
/*!40000 ALTER TABLE `s_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_datadic`
--

DROP TABLE IF EXISTS `s_datadic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_datadic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `field` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `transview` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ftable` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_datadic`
--

LOCK TABLES `s_datadic` WRITE;
/*!40000 ALTER TABLE `s_datadic` DISABLE KEYS */;
INSERT INTO `s_datadic` VALUES (2,'s_datadic','数据字典',NULL,NULL),(40,'classroom','教室','',''),(4,'field','字段',NULL,NULL),(5,'name','名称',NULL,NULL),(6,'title','标题',NULL,NULL),(7,'content','内容',NULL,NULL),(41,'t_class','课程','',''),(11,'phone','电话',NULL,NULL),(42,'schedule','上课时间','',''),(43,'maxnum','最大人数','',''),(44,'t_student_class','选课',NULL,NULL),(45,'stu_name','姓名','',''),(46,'stu_phone','电话','',''),(47,'qq','QQ','',''),(35,'s_privilege','权限','',NULL),(36,'s_config','配置','',NULL),(37,'s_user','用户','','user'),(38,'input_user','输入者','','user');
/*!40000 ALTER TABLE `s_datadic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_privilege`
--

DROP TABLE IF EXISTS `s_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_privilege` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `right` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_privilege`
--

LOCK TABLES `s_privilege` WRITE;
/*!40000 ALTER TABLE `s_privilege` DISABLE KEYS */;
INSERT INTO `s_privilege` VALUES (8,1,'[]admin'),(9,1,'[user]read'),(10,1,'[datadic]read');
/*!40000 ALTER TABLE `s_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_user`
--

DROP TABLE IF EXISTS `s_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_user`
--

LOCK TABLES `s_user` WRITE;
/*!40000 ALTER TABLE `s_user` DISABLE KEYS */;
INSERT INTO `s_user` VALUES (0,'guest','guest can not login'),(1,'admin','6c0a6257f490cc55f1cfee6bb568b326472103df');
/*!40000 ALTER TABLE `s_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_class`
--

DROP TABLE IF EXISTS `t_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_class` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `classroom` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `schedule` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `maxnum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_class`
--

LOCK TABLES `t_class` WRITE;
/*!40000 ALTER TABLE `t_class` DISABLE KEYS */;
INSERT INTO `t_class` VALUES (1,'基础1','X504A','周一 3-4',40),(2,'基础２','X504A','周一 1-2',40);
/*!40000 ALTER TABLE `t_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_student_class`
--

DROP TABLE IF EXISTS `t_student_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_student_class` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `t_class_id` int(11) DEFAULT NULL,
  `input_user` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `stu_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `stu_phone` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_student_class`
--

LOCK TABLES `t_student_class` WRITE;
/*!40000 ALTER TABLE `t_student_class` DISABLE KEYS */;
INSERT INTO `t_student_class` VALUES (1,1,'1',NULL,NULL,NULL),(2,2,'1',NULL,NULL,NULL),(8,2,'20080775',NULL,NULL,NULL),(10,1,'1','zhb7lue','1309372861','10982766'),(11,1,'20080775','20080775','zhblue','13093729617');
/*!40000 ALTER TABLE `t_student_class` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-07 10:12:45
