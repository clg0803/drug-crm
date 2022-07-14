-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: drug_db
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `t_drug`
--

DROP TABLE IF EXISTS `t_drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_drug` (
  `drug_id` int NOT NULL,
  `drug_name` varchar(10) NOT NULL,
  `description` varchar(30) DEFAULT NULL,
  `unit` varchar(5) NOT NULL,
  `class` bit(2) NOT NULL,
  `inventory` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`drug_id`),
  KEY `idx_class_drugname` (`class`,`drug_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_drug`
--

LOCK TABLES `t_drug` WRITE;
/*!40000 ALTER TABLE `t_drug` DISABLE KEYS */;
INSERT INTO `t_drug` VALUES (7001,'黄芩','清热燥湿','克',_binary '',2000),(7002,'陈皮','理气','克',_binary '',901),(7003,'人参','补气','克',_binary '',2000),(7004,'土茯苓','清热解毒','克',_binary '',2000),(8001,'蒙脱石散','腹泻用药','盒',_binary '',100),(8002,'炉甘石洗剂','皮肤瘙痒','瓶',_binary '',1000),(8003,'布洛芬','缓解疼痛','盒',_binary '',100),(8004,'盐酸氨溴索','镇咳祛痰','瓶',_binary '',100),(9001,'克林霉素','厌氧菌引起的腹腔和妇科感染','盒',_binary '',100),(9002,'阿莫西林','敏感菌所致的呼吸道、尿路和胆道感染以及伤寒等','盒',_binary '',100),(9003,'阿奇霉素','呼吸道 皮肤软组织感染','盒',_binary '',100),(9004,'盐酸倍他司汀','血管性头痛 脑动脉硬化','盒',_binary '',100);
/*!40000 ALTER TABLE `t_drug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_hr`
--

DROP TABLE IF EXISTS `t_hr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_hr` (
  `hr_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(10) DEFAULT NULL,
  `ident` bit(2) DEFAULT NULL,
  PRIMARY KEY (`hr_id`),
  UNIQUE KEY `username` (`username`),
  KEY `ident` (`ident`),
  CONSTRAINT `t_hr_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_verify_code` (`username`),
  CONSTRAINT `t_hr_ibfk_2` FOREIGN KEY (`ident`) REFERENCES `t_drug` (`class`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_hr`
--

LOCK TABLES `t_hr` WRITE;
/*!40000 ALTER TABLE `t_hr` DISABLE KEYS */;
INSERT INTO `t_hr` VALUES (2,'admin','123456',_binary ''),(3,'xiyi','112233',_binary ''),(4,'zhongyi','332211',_binary ''),(5,'gcl','nmmdw',_binary '');
/*!40000 ALTER TABLE `t_hr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ip_info`
--

DROP TABLE IF EXISTS `t_ip_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ip_info` (
  `info_id` int NOT NULL AUTO_INCREMENT COMMENT 'AUTOINCREMENT',
  `ip_addr` varchar(40) NOT NULL,
  `hr_id` int NOT NULL,
  PRIMARY KEY (`info_id`),
  UNIQUE KEY `idx_ip_addr` (`ip_addr`),
  KEY `t_ip_hr_id--t_hr_hr_id` (`hr_id`),
  CONSTRAINT `t_ip_hr_id--t_hr_hr_id` FOREIGN KEY (`hr_id`) REFERENCES `t_hr` (`hr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ip_info`
--

LOCK TABLES `t_ip_info` WRITE;
/*!40000 ALTER TABLE `t_ip_info` DISABLE KEYS */;
INSERT INTO `t_ip_info` VALUES (-27566079,'0:0:0:0:0:0:0:1',2);
/*!40000 ALTER TABLE `t_ip_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_recipe`
--

DROP TABLE IF EXISTS `t_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_recipe` (
  `recipe_id` bigint NOT NULL AUTO_INCREMENT,
  `doc_name` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL,
  `detail` json NOT NULL,
  PRIMARY KEY (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_recipe`
--

LOCK TABLES `t_recipe` WRITE;
/*!40000 ALTER TABLE `t_recipe` DISABLE KEYS */;
INSERT INTO `t_recipe` VALUES (1002,'xiyi','2022-07-11 09:06:33','{\"布洛芬\": 2, \"克林霉素\": 3}'),(1003,'zhongyi','2022-07-12 01:44:49','{\"人参\": 3, \"陈皮\": 3, \"炉甘石洗剂\": 3}'),(1004,'gcl','2022-07-12 07:01:00','{\"人参\": 100, \"黄芩\": 100}'),(1005,'admin','2022-07-12 07:05:43','{\"人参\": 3, \"陈皮\": 888, \"炉甘石洗剂\": 999}'),(1008,'admin','2022-07-13 10:34:00','{\"陈皮\": 99, \"黄芩\": 66}');
/*!40000 ALTER TABLE `t_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_verify_code`
--

DROP TABLE IF EXISTS `t_verify_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_verify_code` (
  `code_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `valid_code` int NOT NULL,
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=660238346 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_verify_code`
--

LOCK TABLES `t_verify_code` WRITE;
/*!40000 ALTER TABLE `t_verify_code` DISABLE KEYS */;
INSERT INTO `t_verify_code` VALUES (2,'admin',100022),(3,'xiyi',111222),(4,'zhongyi',123321),(660238343,'gcl',770094);
/*!40000 ALTER TABLE `t_verify_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-14  9:14:16
