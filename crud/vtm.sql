-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: vtm
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `value` text NOT NULL,
  `type` varchar(15) NOT NULL DEFAULT 'report',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (16,'任务报表','select * from tb_manual_task order by id ','report');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datadic`
--

DROP TABLE IF EXISTS `datadic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datadic` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `field` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `transview` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ftable` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datadic`
--

LOCK TABLES `datadic` WRITE;
/*!40000 ALTER TABLE `datadic` DISABLE KEYS */;
INSERT INTO `datadic` VALUES (1,'test','测试',NULL,NULL),(2,'datadic','数据字典',NULL,NULL),(3,'news','新闻',NULL,NULL),(4,'field','字段',NULL,NULL),(5,'name','名称',NULL,NULL),(6,'title','标题',NULL,NULL),(7,'content','内容',NULL,NULL),(8,'tb_warehouse','仓库',NULL,NULL),(9,'whname','仓库名称',NULL,NULL),(10,'address','地址',NULL,NULL),(11,'phone','电话',NULL,NULL),(12,'whowner','库主',NULL,NULL),(13,'tb_wareroom','库房',NULL,NULL),(14,'wrname','库房名',NULL,NULL),(15,'tb_warehouse_id','仓库',NULL,NULL),(16,'totalspace','总容量',NULL,NULL),(17,'incharge','负责人',NULL,NULL),(18,'tb_storage','库存',NULL,NULL),(19,'tb_goods_id','',NULL,NULL),(20,'tb_warehouse_id','',NULL,NULL),(21,'tb_wareroom_id','',NULL,NULL),(22,'number','数量',NULL,NULL),(23,'unit','单位',NULL,NULL),(24,'tb_goods','货物',NULL,NULL),(25,'name','名称',NULL,NULL),(26,'location','产地',NULL,NULL),(27,'tb_shelf','货架',NULL,NULL),(28,'name','',NULL,NULL),(29,'tb_warehouse_id','',NULL,NULL),(30,'tb_wareroom_id','',NULL,NULL),(31,'tb_shelf','货架',NULL,NULL),(32,'name','',NULL,NULL),(33,'tb_warehouse_id','',NULL,NULL),(34,'tb_wareroom_id','',NULL,NULL),(35,'privilege','权限','',NULL),(36,'config','配置','',NULL),(37,'user','用户','','user'),(38,'input_user','输入者','','user'),(39,'tb_test2','测试2',NULL,NULL),(40,'tb_movie','原片',NULL,NULL),(41,'movie_name','片名',NULL,NULL),(42,'director','导演',NULL,NULL),(43,'year','年份',NULL,NULL),(44,'movie_length','时长',NULL,NULL),(45,'movie_file','原片文件',NULL,NULL),(46,'tb_manual_task','人工任务',NULL,NULL),(47,'tb_movie_id','原片',NULL,NULL),(48,'description','任务描述',NULL,NULL),(50,'user_id','指派用户',NULL,NULL),(51,'fastsave','快速存档','\'打开工作台->\'',''),(52,'password','密码','',''),(53,'tb_build_task','构建任务',NULL,NULL),(54,'sub_task_num','子任务数量',NULL,NULL),(55,'finished_task_num','完成子任务数量',NULL,NULL),(56,'percent','完成百分比',NULL,NULL),(57,'inputer','完成人',NULL,NULL),(58,'tb_tag','标签',NULL,NULL),(59,'tag_name','标签名',NULL,NULL),(60,'update_time','更新时间*',NULL,NULL),(61,'tb_clips','片段',NULL,NULL),(62,'clip_file','片段文件',NULL,NULL);
/*!40000 ALTER TABLE `datadic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `right` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (8,1,'[]admin'),(9,1,'[user]read'),(10,1,'[datadic]read'),(14,0,'[tb_manual_task.fastsave]update'),(13,0,'[tb_manual_task]read'),(15,0,'[com.newsclan.crud.Tools]update'),(16,0,'[tb_build_task]update');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_build_task`
--

DROP TABLE IF EXISTS `tb_build_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_build_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tb_manual_task_id` int DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `sub_task_num` int DEFAULT NULL,
  `finished_task_num` int DEFAULT NULL,
  `percent` int DEFAULT NULL,
  `inputer` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_build_task`
--

LOCK TABLES `tb_build_task` WRITE;
/*!40000 ALTER TABLE `tb_build_task` DISABLE KEYS */;
INSERT INTO `tb_build_task` VALUES (1,5,'2021-11-10 01:41:49',3,3,100,'guest'),(2,4,'2021-11-10 01:43:14',27,27,100,'admin'),(3,1,'2021-11-10 06:50:53',78,78,100,'admin'),(4,2,'2021-11-10 07:06:37',36,36,100,'');
/*!40000 ALTER TABLE `tb_build_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_clips`
--

DROP TABLE IF EXISTS `tb_clips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_clips` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `clip_file` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_clips`
--

LOCK TABLES `tb_clips` WRITE;
/*!40000 ALTER TABLE `tb_clips` DISABLE KEYS */;
INSERT INTO `tb_clips` VALUES (1,'交谈',NULL,'output/1/交谈_0.mp4'),(2,'交谈',NULL,'output/1/交谈_1.mp4'),(3,'飞机',NULL,'output/1/飞机_2.mp4'),(4,'交谈','2021-11-10 11:39:38','output/3/交谈_0.mp4'),(5,'伪3D','2021-11-10 11:39:38','output/3/伪3D_1.mp4'),(6,'爆炸','2021-11-10 11:39:38','output/3/爆炸_2.mp4'),(7,'伪3D','2021-11-10 11:39:38','output/3/伪3D_3.mp4'),(8,'地图','2021-11-10 11:39:39','output/3/地图_4.mp4'),(9,'地图','2021-11-10 11:39:39','output/3/地图_5.mp4'),(10,'卡通','2021-11-10 11:39:39','output/3/卡通_6.mp4'),(11,'赛车','2021-11-10 11:39:39','output/3/赛车_7.mp4'),(12,'3D','2021-11-10 11:39:39','output/3/3D_8.mp4'),(13,'射击','2021-11-10 11:39:39','output/3/射击_9.mp4'),(14,'地图','2021-11-10 11:39:39','output/3/地图_10.mp4'),(15,'射击','2021-11-10 11:39:39','output/3/射击_11.mp4'),(16,'赛车','2021-11-10 11:39:39','output/3/赛车_12.mp4'),(17,'迷宫','2021-11-10 11:39:39','output/3/迷宫_13.mp4'),(18,'卡通','2021-11-10 11:39:39','output/3/卡通_14.mp4'),(19,'地图','2021-11-10 11:39:39','output/3/地图_15.mp4'),(20,'格斗','2021-11-10 11:39:39','output/3/格斗_16.mp4'),(21,'伪3D','2021-11-10 11:39:40','output/3/伪3D_17.mp4'),(22,'格斗','2021-11-10 11:39:40','output/3/格斗_18.mp4'),(23,'格斗','2021-11-10 11:39:40','output/3/格斗_19.mp4'),(24,'射击','2021-11-10 11:39:40','output/3/射击_20.mp4'),(25,'飞机','2021-11-10 11:39:40','output/3/飞机_21.mp4'),(26,'飞机','2021-11-10 11:39:40','output/3/飞机_22.mp4'),(27,'飞机','2021-11-10 11:39:40','output/3/飞机_23.mp4'),(28,'飞机','2021-11-10 11:39:40','output/3/飞机_24.mp4'),(29,'射击','2021-11-10 11:39:40','output/3/射击_25.mp4'),(30,'地图','2021-11-10 11:39:40','output/3/地图_26.mp4'),(31,'卡通','2021-11-10 11:39:40','output/3/卡通_27.mp4'),(32,'射击','2021-11-10 11:39:40','output/3/射击_28.mp4'),(33,'射击','2021-11-10 11:39:41','output/3/射击_29.mp4'),(34,'燃烧','2021-11-10 11:39:41','output/3/燃烧_30.mp4'),(35,'卡通','2021-11-10 11:39:41','output/3/卡通_31.mp4'),(36,'飞机','2021-11-10 11:39:41','output/3/飞机_32.mp4'),(37,'飞机','2021-11-10 11:39:41','output/3/飞机_33.mp4'),(38,'飞机','2021-11-10 11:39:41','output/3/飞机_34.mp4'),(39,'飞机','2021-11-10 11:39:41','output/3/飞机_35.mp4'),(40,'飞机','2021-11-10 11:39:41','output/3/飞机_36.mp4'),(41,'地图','2021-11-10 11:39:41','output/3/地图_37.mp4'),(42,'射击','2021-11-10 11:39:41','output/3/射击_38.mp4'),(43,'射击','2021-11-10 11:39:41','output/3/射击_39.mp4'),(44,'地图','2021-11-10 11:39:41','output/3/地图_40.mp4'),(45,'射击','2021-11-10 11:39:41','output/3/射击_41.mp4'),(46,'射击','2021-11-10 11:39:42','output/3/射击_42.mp4'),(47,'射击','2021-11-10 11:39:42','output/3/射击_43.mp4'),(48,'射击','2021-11-10 11:39:42','output/3/射击_44.mp4'),(49,'射击','2021-11-10 11:39:42','output/3/射击_45.mp4'),(50,'飞机','2021-11-10 11:39:42','output/3/飞机_46.mp4'),(51,'地图','2021-11-10 11:39:42','output/3/地图_47.mp4'),(52,'赛车','2021-11-10 11:39:42','output/3/赛车_48.mp4'),(53,'射击','2021-11-10 11:39:42','output/3/射击_49.mp4'),(54,'射击','2021-11-10 11:39:42','output/3/射击_50.mp4'),(55,'射击','2021-11-10 11:39:42','output/3/射击_51.mp4'),(56,'爆炸','2021-11-10 11:39:42','output/3/爆炸_52.mp4'),(57,'交谈','2021-11-10 11:39:42','output/3/交谈_53.mp4'),(58,'地图','2021-11-10 11:39:43','output/3/地图_54.mp4'),(59,'伪3D','2021-11-10 11:39:43','output/3/伪3D_55.mp4'),(60,'伪3D','2021-11-10 11:39:43','output/3/伪3D_56.mp4'),(61,'地图','2021-11-10 11:39:43','output/3/地图_57.mp4'),(62,'射击','2021-11-10 11:39:43','output/3/射击_58.mp4'),(63,'地图','2021-11-10 11:39:43','output/3/地图_59.mp4'),(64,'男性','2021-11-10 11:39:43','output/3/男性_60.mp4'),(65,'伪3D','2021-11-10 11:39:43','output/3/伪3D_61.mp4'),(66,'赛车','2021-11-10 11:39:43','output/3/赛车_62.mp4'),(67,'射击','2021-11-10 11:39:43','output/3/射击_63.mp4'),(68,'爆炸','2021-11-10 11:39:43','output/3/爆炸_64.mp4'),(69,'爆炸','2021-11-10 11:39:43','output/3/爆炸_65.mp4'),(70,'爆炸','2021-11-10 11:39:44','output/3/爆炸_66.mp4'),(71,'爆炸','2021-11-10 11:39:44','output/3/爆炸_67.mp4'),(72,'伪3D','2021-11-10 11:39:44','output/3/伪3D_68.mp4'),(73,'伪3D','2021-11-10 11:39:44','output/3/伪3D_69.mp4'),(74,'星空图','2021-11-10 11:39:44','output/3/星空图_70.mp4'),(75,'星空图','2021-11-10 11:39:44','output/3/星空图_71.mp4'),(76,'帐篷','2021-11-10 11:39:44','output/3/帐篷_72.mp4'),(77,'女性','2021-11-10 11:39:44','output/3/女性_73.mp4'),(78,'伪3D','2021-11-10 11:39:44','output/3/伪3D_74.mp4'),(79,'赛车','2021-11-10 11:39:44','output/3/赛车_75.mp4'),(80,'格斗','2021-11-10 11:39:44','output/3/格斗_76.mp4'),(81,'夫妻不合','2021-11-10 11:39:44','output/3/夫妻不合_77.mp4');
/*!40000 ALTER TABLE `tb_clips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_manual_task`
--

DROP TABLE IF EXISTS `tb_manual_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_manual_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tb_movie_id` int DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `fastsave` text CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_manual_task`
--

LOCK TABLES `tb_manual_task` WRITE;
/*!40000 ALTER TABLE `tb_manual_task` DISABLE KEYS */;
INSERT INTO `tb_manual_task` VALUES (1,1,'DOS-标注所有爆炸','2021-11-10 07:06:04','1','1\n00:00:02,240 --> 00:00:07,040\n交谈\n(105,33)-[193,109]\n\n2\n00:00:07,942 --> 00:00:12,957\n伪3D\n(21,25)-[166,161]\n\n3\n00:00:13,157 --> 00:00:18,536\n爆炸\n(39,54)-[283,173]\n\n4\n00:00:19,602 --> 00:00:24,885\n伪3D\n(87,62)-[267,162]\n\n5\n00:00:25,451 --> 00:00:30,743\n地图\n(154,46)-[285,203]\n\n6\n00:00:31,449 --> 00:00:33,294\n地图\n(89,104)-[313,175]\n\n7\n00:00:33,523 --> 00:00:42,390\n卡通\n(207,122)-[192,94]\n\n8\n00:00:43,439 --> 00:00:48,994\n赛车\n(204,106)-[220,86]\n\n9\n00:00:52,434 --> 00:00:55,026\n3D\n(95,100)-[210,144]\n\n10\n00:00:55,816 --> 00:01:00,980\n射击\n(174,100)-[168,173]\n\n11\n00:01:01,347 --> 00:01:06,870\n地图\n(23,64)-[372,197]\n\n12\n00:01:07,882 --> 00:01:08,578\n射击\n(138,95)-[202,169]\n\n13\n00:01:13,804 --> 00:01:18,672\n赛车\n(127,115)-[229,117]\n\n14\n00:01:19,537 --> 00:01:21,939\n迷宫\n(308,89)-[96,144]\n\n15\n00:01:31,257 --> 00:01:36,833\n卡通\n(123,62)-[195,154]\n\n16\n00:01:38,274 --> 00:01:39,487\n地图\n(99,67)-[251,200]\n\n17\n00:01:43,209 --> 00:01:48,972\n格斗\n(229,93)-[210,177]\n\n18\n00:01:50,028 --> 00:01:50,403\n伪3D\n(146,6)-[231,165]\n\n19\n00:01:51,392 --> 00:01:54,829\n格斗\n(9,100)-[410,174]\n\n20\n00:01:55,717 --> 00:01:59,360\n格斗\n(270,120)-[194,107]\n\n21\n00:02:01,517 --> 00:02:04,254\n射击\n(151,143)-[169,131]\n\n22\n00:02:08,025 --> 00:02:08,903\n飞机\n(313,182)-[157,95]\n\n23\n00:02:10,017 --> 00:02:11,017\n飞机\n(328,278)-[0,0]\n\n24\n00:02:11,467 --> 00:02:12,871\n飞机\n(81,242)-[185,35]\n\n25\n00:02:13,065 --> 00:02:13,071\n飞机\n(300,17)-[175,106]\n\n26\n00:02:13,098 --> 00:02:16,868\n射击\n(95,121)-[353,133]\n\n27\n00:02:19,580 --> 00:02:21,935\n地图\n(103,54)-[336,216]\n\n28\n00:02:22,539 --> 00:02:24,961\n卡通\n(27,89)-[249,142]\n\n29\n00:02:25,446 --> 00:02:28,255\n射击\n(132,109)-[162,113]\n\n30\n00:02:28,521 --> 00:02:29,806\n射击\n(350,115)-[96,75]\n\n31\n00:02:29,990 --> 00:02:30,904\n燃烧\n(275,92)-[86,80]\n\n32\n00:02:31,578 --> 00:02:36,999\n卡通\n(18,123)-[365,118]\n\n33\n00:02:37,327 --> 00:02:39,688\n飞机\n(207,219)-[116,51]\n\n34\n00:02:39,947 --> 00:02:40,428\n飞机\n(98,212)-[118,62]\n\n35\n00:02:40,515 --> 00:02:40,883\n飞机\n(236,213)-[121,61]\n\n36\n00:02:41,002 --> 00:02:42,525\n飞机\n(98,196)-[188,81]\n\n37\n00:02:42,577 --> 00:02:43,002\n飞机\n(269,205)-[128,59]\n\n38\n00:02:43,251 --> 00:02:47,888\n地图\n(47,65)-[343,195]\n\n39\n00:02:50,722 --> 00:02:50,774\n射击\n(181,156)-[0,0]\n\n40\n00:02:51,268 --> 00:02:52,351\n射击\n(140,101)-[230,162]\n\n41\n00:02:58,497 --> 00:02:58,801\n地图\n(240,124)-[183,151]\n\n42\n00:03:02,616 --> 00:03:06,987\n射击\n(257,203)-[85,72]\n\n43\n00:03:10,608 --> 00:03:11,194\n射击\n(269,212)-[171,63]\n\n44\n00:03:08,812 --> 00:03:11,194\n射击\n(183,197)-[437,273]\n\n45\n00:03:11,281 --> 00:03:11,318\n射击\n(425,271)-[0,0]\n\n46\n00:03:12,050 --> 00:03:12,393\n射击\n(277,205)-[138,69]\n\n47\n00:03:13,818 --> 00:03:18,670\n飞机\n(68,43)-[211,171]\n\n48\n00:03:19,695 --> 00:03:24,126\n地图\n(239,51)-[178,208]\n\n49\n00:03:25,629 --> 00:03:30,471\n赛车\n(161,112)-[258,124]\n\n50\n00:03:31,544 --> 00:03:32,228\n射击\n(125,153)-[265,82]\n\n51\n00:03:33,709 --> 00:03:34,234\n射击\n(269,162)-[71,58]\n\n52\n00:03:34,472 --> 00:03:37,056\n射击\n(140,158)-[104,72]\n\n53\n00:03:39,125 --> 00:03:43,176\n爆炸\n(112,244)-[59,35]\n\n54\n00:03:43,459 --> 00:03:44,731\n交谈\n(64,8)-[316,93]\n\n55\n00:03:46,264 --> 00:03:48,412\n地图\n(99,31)-[331,202]\n\n56\n00:03:48,774 --> 00:03:50,589\n伪3D\n(254,65)-[126,199]\n\n57\n00:03:50,680 --> 00:03:55,208\n伪3D\n(116,81)-[296,190]\n\n58\n00:03:55,558 --> 00:04:00,868\n地图\n(111,61)-[338,197]\n\n59\n00:04:03,394 --> 00:04:06,913\n射击\n(147,41)-[164,166]\n\n60\n00:04:07,357 --> 00:04:10,797\n地图\n(100,48)-[303,179]\n\n61\n00:04:10,959 --> 00:04:13,309\n男性\n(225,23)-[165,220]\n\n62\n00:04:13,493 --> 00:04:19,294\n伪3D\n(121,62)-[336,203]\n\n63\n00:04:19,500 --> 00:04:25,147\n赛车\n(75,43)-[374,235]\n\n64\n00:04:27,995 --> 00:04:29,991\n射击\n(78,110)-[141,84]\n\n65\n00:04:30,122 --> 00:04:31,017\n爆炸\n(321,69)-[135,196]\n\n66\n00:04:33,526 --> 00:04:34,424\n爆炸\n(69,134)-[344,117]\n\n67\n00:04:34,774 --> 00:04:36,746\n爆炸\n(305,155)-[95,81]\n\n68\n00:04:36,921 --> 00:04:36,937\n爆炸\n(67,145)-[127,84]\n\n69\n00:04:37,998 --> 00:04:38,998\n伪3D\n(176,109)-[161,150]\n\n70\n00:04:41,128 --> 00:04:43,065\n伪3D\n(80,53)-[339,203]\n\n71\n00:04:44,283 --> 00:04:45,949\n星空图\n(87,44)-[320,235]\n\n72\n00:04:46,469 --> 00:04:47,236\n星空图\n(189,48)-[205,126]\n\n73\n00:04:53,819 --> 00:04:55,054\n帐篷\n(376,18)-[112,86]\n\n74\n00:04:55,440 --> 00:04:58,221\n女性\n(122,21)-[293,235]\n\n75\n00:05:01,709 --> 00:05:07,358\n伪3D\n(207,12)-[283,192]\n\n76\n00:05:07,587 --> 00:05:12,657\n赛车\n(105,50)-[357,227]\n\n77\n00:05:13,860 --> 00:05:16,684\n格斗\n(89,104)-[313,175]\n\n78\n00:05:19,449 --> 00:05:24,935\n夫妻不合\n(65,36)-[386,223]\n\n'),(5,4,'test','2021-11-10 01:48:47','1','1\n00:00:06,428 --> 00:00:07,307\n交谈\n(123,23)-[245,175]\n\n2\n00:00:50,103 --> 00:00:52,409\n交谈\n(153,97)-[354,156]\n\n3\n00:00:58,255 --> 00:00:59,175\n飞机\n(39,54)-[300,168]\n\n'),(2,2,'T2-常规任务','2021-11-10 07:05:58','0','1\n00:00:07,258 --> 00:00:08,344\n男性\n(272,173)-[85,86]\n\n2\n00:00:09,067 --> 00:00:09,845\n男性\n(253,84)-[101,93]\n\n3\n00:00:12,084 --> 00:00:14,906\n交谈\n(225,74)-[324,174]\n\n4\n00:00:15,492 --> 00:00:19,683\n格斗\n(249,118)-[249,132]\n\n5\n00:00:27,333 --> 00:00:29,147\n交谈\n(35,52)-[528,191]\n\n6\n00:00:34,102 --> 00:00:35,102\n交谈\n(229,21)-[315,120]\n\n7\n00:00:42,263 --> 00:00:44,040\n男性\n(258,58)-[130,113]\n\n8\n00:00:46,180 --> 00:00:47,472\n交谈\n(179,77)-[274,136]\n\n9\n00:00:49,006 --> 00:00:51,354\n射击\n(370,127)-[179,81]\n\n10\n00:00:51,497 --> 00:00:53,307\n射击\n(178,109)-[171,107]\n\n11\n00:00:55,278 --> 00:00:56,384\n爆炸\n(389,13)-[176,185]\n\n12\n00:01:00,321 --> 00:01:04,670\n赛车\n(135,71)-[444,184]\n\n13\n00:01:05,285 --> 00:01:06,235\n射击\n(177,65)-[238,128]\n\n14\n00:01:06,359 --> 00:01:07,485\n爆炸\n(31,84)-[317,188]\n\n15\n00:01:12,135 --> 00:01:13,224\n射击\n(361,85)-[105,137]\n\n16\n00:01:13,774 --> 00:01:14,508\n交谈\n(144,40)-[314,162]\n\n17\n00:01:22,275 --> 00:01:23,158\n格斗\n(302,111)-[190,142]\n\n18\n00:01:26,720 --> 00:01:27,733\n射击\n(324,126)-[149,124]\n\n19\n00:01:27,780 --> 00:01:29,005\n射击\n(200,140)-[145,104]\n\n20\n00:01:38,188 --> 00:01:39,188\n射击\n(319,205)-[172,70]\n\n21\n00:01:58,928 --> 00:02:02,332\n交谈\n(125,119)-[271,135]\n\n22\n00:02:02,718 --> 00:02:06,913\n交谈\n(220,123)-[300,137]\n\n23\n00:02:09,612 --> 00:02:10,611\n爆炸\n(309,185)-[150,88]\n\n24\n00:02:25,644 --> 00:02:27,598\n射击\n(125,130)-[415,126]\n\n25\n00:02:30,695 --> 00:02:31,026\n爆炸\n(18,18)-[543,253]\n\n26\n00:02:31,763 --> 00:02:32,763\n射击\n(239,71)-[318,184]\n\n27\n00:02:34,119 --> 00:02:35,669\n爆炸\n(393,54)-[183,194]\n\n28\n00:02:36,550 --> 00:02:37,550\n爆炸\n(333,105)-[232,168]\n\n29\n00:02:42,155 --> 00:02:45,119\n射击\n(97,57)-[179,130]\n\n30\n00:02:47,244 --> 00:02:48,244\n射击\n(143,96)-[183,143]\n\n31\n00:02:51,244 --> 00:02:52,947\n射击\n(343,187)-[142,83]\n\n32\n00:03:01,054 --> 00:03:04,427\n射击\n(151,174)-[136,95]\n\n33\n00:03:05,371 --> 00:03:05,873\n射击\n(471,173)-[92,86]\n\n34\n00:03:06,073 --> 00:03:06,866\n爆炸\n(339,167)-[117,103]\n\n35\n00:03:09,477 --> 00:03:10,477\n交谈\n(327,102)-[252,74]\n\n36\n00:03:11,480 --> 00:03:12,480\n交谈\n(334,104)-[234,68]\n\n'),(3,4,'PX3测试','2021-11-10 07:06:13','1','1\n00:00:13,019 --> 00:00:16,607\n太空飞船\n(59,43)-[486,195]\n\n2\n00:01:18,982 --> 00:01:22,415\n通讯兵\n(156,26)-[363,272]\n\n3\n00:01:25,155 --> 00:01:26,753\n外星蜘蛛\n(26,85)-[237,159]\n\n4\n00:01:37,674 --> 00:01:39,132\n外星金字塔\n(169,17)-[383,217]\n\n5\n00:01:43,609 --> 00:01:46,065\n外星士兵\n(288,19)-[261,253]\n\n6\n00:01:49,709 --> 00:01:52,209\n人类士兵\n(21,83)-[198,193]\n\n7\n00:01:55,518 --> 00:01:57,377\n外星坦克残骸\n(225,52)-[323,180]\n\n8\n00:01:59,410 --> 00:02:19,280\n人类殖民者\n(66,87)-[487,181]\n\n9\n00:02:25,337 --> 00:02:29,211\nPlanetX3殖民地代表\n(89,18)-[462,160]\n\n'),(4,3,'NES评论字幕','2021-11-10 07:06:23','1','1\n00:00:00,000 --> 00:00:02,767\n逆向模拟\n(10,30)-[500,108]\n\n2\n00:00:05,457 --> 00:00:16,420\nCRT电视上的诡异粗糙卡通已经在暗示，你看到的画面正来自于UP主接下来要讲解的Project\n(124,192)-[340,54]\n\n3\n00:00:16,919 --> 00:00:21,845\n当UP说Joke这个词的时候，忍不住快笑场了\n(148,210)-[320,60]\n\n4\n00:00:49,805 --> 00:00:52,127\n吃鞋子的人，不好笑\n(192,43)-[241,149]\n\n5\n00:00:53,256 --> 00:01:10,024\n吃得津津有味，才好笑\n(202,9)-[199,137]\n\n6\n00:01:17,956 --> 00:01:25,314\n马里奥大叔吃鞋不好笑\n(148,258)-[247,36]\n\n7\n00:01:41,817 --> 00:01:51,258\n自我介绍不好笑\n(171,262)-[259,31]\n\n8\n00:01:57,392 --> 00:01:58,976\n递归表达式，好笑\n(162,248)-[150,35]\n\n9\n00:02:02,409 --> 00:02:22,558\n这段C语言程序，运行的结果，就是打印它自己的源代码！\n(30,137)-[211,85]\n\n10\n00:02:23,211 --> 00:02:39,099\n这是最NB的技术笑话！之一\n(346,144)-[166,65]\n\n11\n00:02:50,593 --> 00:03:01,376\n反常金字塔（层级）\n(43,29)-[167,77]\n\n12\n00:03:45,198 --> 00:03:53,893\nNES主板（美国销售的FC主机）\n(5,65)-[85,92]\n\n13\n00:03:57,181 --> 00:04:00,110\n约等于显卡\n(389,21)-[93,50]\n\n14\n00:04:07,002 --> 00:04:23,472\n反盗版芯片，懂的都懂\n(184,28)-[181,27]\n\n15\n00:04:45,404 --> 00:04:46,404\n6502程序\n(185,205)-[84,42]\n\n16\n00:04:47,776 --> 00:04:54,448\n显存贴图\n(338,192)-[84,39]\n\n17\n00:04:58,629 --> 00:05:01,578\n反盗版芯片，艹\n(403,35)-[135,77]\n\n18\n00:05:06,165 --> 00:05:11,622\n这里好空啊，不如放个啥进去？\n(181,156)-[238,41]\n\n19\n00:05:26,313 --> 00:05:35,372\n正向的模拟\n(340,34)-[150,42]\n\n20\n00:05:39,610 --> 00:06:05,053\n逆向的模拟\n(369,57)-[112,38]\n\n21\n00:06:06,365 --> 00:06:18,719\n树莓派0, 主角登场！\n(147,16)-[242,35]\n\n22\n00:06:21,548 --> 00:06:44,919\n把树莓派的GPIO接到NES的总线上去！\n(109,72)-[340,47]\n\n23\n00:07:15,200 --> 00:07:21,320\nFCC说了，它必须接受任何外设！hiahiahia\n(145,8)-[325,40]\n\n24\n00:07:31,691 --> 00:07:39,504\n坦白了，这个视频就是在插着树莓派的NES主机上播放的PPT！！！！！\n(106,206)-[468,56]\n\n25\n00:07:39,604 --> 00:07:41,195\n甚至能用手柄控制播放！！！\n(61,161)-[401,109]\n\n26\n00:08:06,765 --> 00:08:25,092\n任天堂游戏机显示拼图原理\n(90,20)-[198,7]\n\n27\n00:08:37,988 --> 00:08:39,914\n每个讲解这个原理的视频都会提到，云彩和树丛用的是相同的贴图\n(32,207)-[260,47]\n\n');
/*!40000 ALTER TABLE `tb_manual_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_movie`
--

DROP TABLE IF EXISTS `tb_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_movie` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `movie_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `director` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `year` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `movie_length` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `movie_file` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_movie`
--

LOCK TABLES `tb_movie` WRITE;
/*!40000 ALTER TABLE `tb_movie` DISABLE KEYS */;
INSERT INTO `tb_movie` VALUES (1,'DOS经典游戏','youtube','2020','5 mins','upload/2021/1/file1636527442873.mp4'),(2,'8位终结者2','油管','2018','3分36秒','upload/2021/1/file1636199769879.mp4'),(3,'Reverse emulation','youtube','2018','20 mins','upload/2021/1/file1636254425962.mp4'),(4,'Planet X3','The 8 Bit Guy','2018','5 mins','upload/2021/1/file1636254552375.mp4');
/*!40000 ALTER TABLE `tb_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_tag`
--

DROP TABLE IF EXISTS `tb_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tag`
--

LOCK TABLES `tb_tag` WRITE;
/*!40000 ALTER TABLE `tb_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'guest','4143ee6ed7ff640f2dc9a15271ff304d4bb5f117'),(1,'admin','6c0a6257f490cc55f1cfee6bb568b326472103df');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'vtm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-10 19:54:59
