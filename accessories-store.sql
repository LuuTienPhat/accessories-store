-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: store2
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Kẹp ảnh','Kẹp ảnh','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(2,'Quà tặng','Quà tặng dịp đặc biệt, ngày đặc biệt, ngày lễ trong năm','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(3,'Giày/ thắt lưng','Giày dép đi chơi, đi trong nhà','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(4,'Khẩu trang','Khẩu trang dễ thương','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(5,'Kính mắt, gọng kính','Kính mắt cho phái đẹp','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(6,'Mũ nón/mũ len','Các loại nón đa dạng lứa tuổi','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(7,'Văn phòng phẩm','Sách, vở, đồ dùng học tập','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(8,'Đồ gia dụng','Vật dụng sử dụng trong gia đình','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00'),(9,'Quà tặng','Hộp quà tặng, giấy gói quà dễ thương','NEW','2022-10-10 21:41:00','2022-10-10 21:41:00');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `approved` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7k33yw505d347mw3avr93akao` (`user_id`),
  CONSTRAINT `FK7k33yw505d347mw3avr93akao` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (124);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Image_Category_idx` (`category_id`),
  KEY `FKgpextbyee3uk9u6o2381m7ft1` (`product_id`),
  CONSTRAINT `FK_Image_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKgpextbyee3uk9u6o2381m7ft1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,NULL,1,'1_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(2,NULL,2,'2_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(3,NULL,3,'3_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(4,NULL,4,'4_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(5,NULL,5,'5_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(6,NULL,6,'6_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(7,NULL,7,'7_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(8,NULL,8,'8_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(9,NULL,9,'9_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(10,NULL,10,'10_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(11,NULL,11,'11_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(12,NULL,12,'12_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(13,NULL,13,'13_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(14,NULL,14,'14_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(15,NULL,15,'15_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(16,NULL,16,'16_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(17,NULL,17,'17_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(18,NULL,18,'18_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(19,NULL,19,'19_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(20,NULL,20,'20_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(21,NULL,21,'21_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(22,NULL,22,'22_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(23,NULL,23,'23_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(24,NULL,24,'24_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(25,NULL,25,'25_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(26,NULL,26,'26_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(27,NULL,27,'27_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(28,NULL,28,'28_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(29,NULL,29,'29_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(30,NULL,30,'30_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(31,NULL,31,'31_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(32,NULL,32,'32_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(33,NULL,33,'33_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(34,NULL,34,'34_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(35,NULL,35,'35_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(36,NULL,36,'36_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(37,NULL,37,'37_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(38,NULL,38,'38_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(39,NULL,39,'39_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(40,NULL,40,'40_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(41,NULL,41,'41_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(42,NULL,42,'42_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(43,NULL,43,'43_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(44,NULL,44,'44_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(45,NULL,45,'45_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(46,NULL,46,'46_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(47,NULL,47,'47_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(48,NULL,48,'48_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(49,NULL,49,'49_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(50,NULL,50,'50_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(51,NULL,51,'51_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(52,NULL,52,'52_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(53,NULL,53,'53_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(54,NULL,54,'54_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(55,NULL,55,'55_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(56,NULL,56,'56_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(57,NULL,57,'57_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(58,NULL,58,'58_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(59,NULL,59,'59_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(60,NULL,60,'60_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(61,NULL,61,'61_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(62,NULL,62,'62_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(63,NULL,63,'63_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(64,NULL,64,'64_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(65,NULL,65,'65_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(66,NULL,66,'66_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(67,NULL,67,'67_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(68,NULL,68,'68_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(69,NULL,69,'69_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(70,NULL,70,'70_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(71,NULL,71,'71_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(72,NULL,72,'72_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(73,NULL,73,'73_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(74,NULL,74,'74_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(75,NULL,75,'75_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(76,NULL,76,'76_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(77,NULL,77,'77_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(78,NULL,78,'78_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(79,NULL,79,'79_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(80,NULL,80,'80_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(81,NULL,81,'81_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(82,NULL,82,'82_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(83,NULL,83,'83_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(84,NULL,84,'84_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(85,NULL,85,'85_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(86,NULL,86,'86_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(87,NULL,87,'87_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(88,NULL,88,'88_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(89,NULL,89,'89_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(90,NULL,90,'90_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(91,NULL,91,'91_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(92,NULL,92,'92_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(93,NULL,93,'93_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(94,NULL,94,'94_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(95,NULL,95,'95_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(96,NULL,96,'96_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(97,NULL,97,'97_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(98,NULL,98,'98_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(99,NULL,99,'99_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(100,NULL,100,'100_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(101,NULL,101,'101_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(102,NULL,102,'102_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(103,NULL,103,'103_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(104,NULL,104,'104_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(105,NULL,105,'105_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(106,NULL,106,'106_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(107,NULL,107,'107_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(108,NULL,108,'108_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(109,NULL,109,'109_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(110,NULL,110,'110_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(111,NULL,111,'111_1.jpg','NEW','2022-10-10 21:00:00','2022-10-10 21:00:00'),(118,NULL,117,'1670508148467.jpg','void','2022-12-08 21:02:52','2022-12-14 07:50:59'),(121,NULL,117,'1670978989085.jpg','void','2022-12-14 07:49:55','2022-12-14 07:50:59'),(122,NULL,117,'1670979055252.jpg','void','2022-12-14 07:50:59','2022-12-14 07:54:20'),(123,NULL,117,'1670979254752.jpg','NEW','2022-12-14 07:54:20','2022-12-14 07:54:20');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `invoice_type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqss90tikrowtmcc9gw6hegq7d` (`invoice_type_id`),
  KEY `FKjunvl5maki3unqdvljk31kns3` (`user_id`),
  CONSTRAINT `FKjunvl5maki3unqdvljk31kns3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqss90tikrowtmcc9gw6hegq7d` FOREIGN KEY (`invoice_type_id`) REFERENCES `invoice_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (2084,'2022-12-14 12:12:00.000000',1,1),(2818,'2022-12-14 12:00:00.000000',2,1),(9971,'2022-12-13 12:12:00.000000',1,1);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_detail`
--

DROP TABLE IF EXISTS `invoice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `invoice_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbe6c21nke5fy4m3vw00f23qsf` (`product_id`),
  KEY `FKit1rbx4thcr6gx6bm3gxub3y4` (`invoice_id`),
  CONSTRAINT `FKbe6c21nke5fy4m3vw00f23qsf` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKit1rbx4thcr6gx6bm3gxub3y4` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_detail`
--

LOCK TABLES `invoice_detail` WRITE;
/*!40000 ALTER TABLE `invoice_detail` DISABLE KEYS */;
INSERT INTO `invoice_detail` VALUES (1,50000,1,2818,24),(2,10000,4,2084,8),(3,60000,5,9971,1);
/*!40000 ALTER TABLE `invoice_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_type`
--

DROP TABLE IF EXISTS `invoice_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_type`
--

LOCK TABLES `invoice_type` WRITE;
/*!40000 ALTER TABLE `invoice_type` DISABLE KEYS */;
INSERT INTO `invoice_type` VALUES (1,'Import'),(2,'Export');
/*!40000 ALTER TABLE `invoice_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `ship_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ship_phone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ship_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ship_date` datetime DEFAULT NULL,
  `ship_note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `state` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Order_User_idx` (`user_id`),
  CONSTRAINT `FK_Order_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'Phat','090','HCM','2022-10-10 00:00:00',NULL,2,'2022-10-10 00:00:00','2022-12-07 04:00:47'),(2,5,'Pham Guest','','',NULL,'',2,'2022-12-03 19:04:20','2023-03-28 01:22:16'),(3,5,'Pham Guest','0796919429','242/16 S1',NULL,'',0,'2022-12-04 12:11:48','2022-12-04 12:11:48'),(4,5,'Pham Guest','','',NULL,'',2,'2022-12-14 03:51:30','2022-12-14 14:18:33'),(5,5,'Luu Tien Phat','0796919429','242/16 S1',NULL,'',0,'2022-12-14 14:12:28','2022-12-14 14:12:28'),(6,5,'Pham Guest','0796919429','242/16 S1',NULL,'',2,'2022-12-14 14:41:23','2022-12-14 14:42:12');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_detail`
--

DROP TABLE IF EXISTS `orders_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb8bg2bkty0oksa3wiq5mp5qnc` (`product_id`),
  KEY `FK_order_orderDetail_idx` (`order_id`),
  CONSTRAINT `FK_order_orderDetail` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_detail`
--

LOCK TABLES `orders_detail` WRITE;
/*!40000 ALTER TABLE `orders_detail` DISABLE KEYS */;
INSERT INTO `orders_detail` VALUES (1,5,1,1),(2,6,2,1),(3,1,3,2),(4,1,3,3),(5,1,4,2),(6,1,4,3),(7,1,5,3),(8,1,5,18),(9,1,5,1),(10,3,6,10),(11,1,6,4);
/*!40000 ALTER TABLE `orders_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `views` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Product_Status_idx` (`status`) /*!80000 INVISIBLE */,
  KEY `FK_Product_Category` (`category_id`),
  CONSTRAINT `FK_Product_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Product_Status` FOREIGN KEY (`status`) REFERENCES `status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Kính mát gọng nhựa dày basic color',49,'cái',100000,'',131,'NEW',5,'2022-10-10 21:00:00','2023-03-28 01:22:16'),(2,'Kính mát gọng nhựa Double B',66,'cái',100000,'',414,'NEW',5,'2022-10-10 21:00:00','2023-03-28 01:04:39'),(3,'Gối sưởi Baby bear gấu bịt mắt sạc điện 27X17',14,'cái',270000,'',188,'NEW',8,'2022-10-10 21:00:00','2022-12-14 14:18:33'),(4,'Thắt lưng dây nịt da Two Daisy Flowers 100cm',10,'cái',50000,'',866,'NEW',3,'2022-10-10 21:00:00','2022-12-14 14:42:12'),(5,'Kẹp gỗ Dinosaur Owl Frog Penguin mập set8',0,'cái',20000,'',363,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(6,'Thắt lưng dây nịt da khóa vòng xoắn 107cm',2,'cái',60000,'',412,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(7,'Ly cốc thuỷ tinh Geometry 300ml',10,'cái',80000,'',115,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(8,'Chai làm sạch giày trắng Little girl oh tiger 100ml',12,'lọ',30000,'',701,'NEW',8,'2022-10-10 21:00:00','2022-12-14 14:11:05'),(9,'Hộp quà MZ Lookii nắp rời Vintage polaroid constellation 8x20x25',12,'hộp',35000,'',193,'NEW',2,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(10,'Kẹp gỗ Mèo nằm cuộn tròn set10',15,'cái',20000,'',265,'NEW',1,'2022-10-10 21:00:00','2022-12-14 14:42:12'),(11,'Giày sandal Blue and white',0,'đôi',200000,'',81,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(12,'Thắt lưng dây nịt da móc cài lỗ trái tim basic 100cm',2,'cái',50000,'',888,'NEW',3,'2022-10-10 21:00:00','2022-12-14 12:40:35'),(13,'Kẹp gỗ Thỏ lông xù happy day set10',22,'cái',25000,'',522,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(14,'Kính gọng kim loại viền ép phẳng chân bọc nhựa',19,'cái',90000,'',442,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(15,'Miếng lót giày tăng chiều cao Fruit summer set2',0,'đôi',70000,'',779,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(16,'Thắt lưng dây nịt da khóa khối chữ nhật gold bản nhỏ 100cm',17,'cái',50000,'',417,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(17,'Thắt lưng dây nịt da nhiều lỗ phối móc xích 105cm',12,'cái',65000,'',653,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(18,'Thắt lưng dây nịt da A Daisy Flower 102cm',17,'cái',50000,'',181,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(19,'Dây giày dạ quang 1 màu',5,'đôi',20000,'',908,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(20,'Kính mát mắt bầu dục basic',12,'cái',135000,'Kính mát mắt bầu dục basic',746,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(21,'Thắt lưng dây nịt da viền chỉ khóa vuông basic 106cm - Đen',13,'chiếc',45000,'Thắt lưng da',219,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(22,'Thắt lưng dây nịt kim loại mắt xích basic 110cm - Gold',9,'chiếc',70000,'Thắt lưng kim loại',327,'NEW',2,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(23,'Khẩu trang MJ Astronaut space happy',8,'cái',40000,'Thắt lưng kim loại',834,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(24,'Dây giày basic một màu',99,'cái',15000,'Dây giày',754,'NEW',3,'2022-10-10 21:00:00','2022-12-14 04:49:14'),(25,'Thắt lưng dây nịt da trơn viền chỉ đồng màu 105cm',14,'cái',45000,'',986,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(26,'Gối sưởi Khủng long baby dinosaur sạc điện 21x29',21,'cái',160000,'',320,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(27,'Ly cốc sứ Duck peach fruit sweet life 420ml',9,'cái',100000,'',1,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(28,'Dây giày Daisy Flowers',30,'đôi',25000,'',643,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(29,'Đựng bút đa năng nhựa Cute duck 9x10',9,'cái',35000,'',797,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(30,'Thắt lưng dây nịt da trơn móc Tròn basic 105cm',11,'cái',60000,'',587,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(31,'Kính mát gọng nhựa dày mắt vuông basic',24,'cái',100000,'',292,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(32,'Kẹp gỗ Mèo NB text set10',24,'cái',20000,'',667,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(33,'Thắt lưng dây nịt dây da Basic đầu vuông 105cm',7,'cái',40000,'',689,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(34,'Kính mát gọng nhựa chữ nhật cắt cạnh basic',21,'cái',90000,'',192,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(35,'Khẩu trang MJ Cáo cục bông cartoon',22,'cái',55000,'',688,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(36,'Thắt lưng dây nịt da bản nhỏ móc chữ nhật basic',21,'cái',40000,'',36,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(37,'Miếng lót giày tăng chiều cao Basic color đường kẻ set2',5,'đôi',70000,'',359,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(38,'Khẩu trang MJ Astronaut space happy',20,'cái',40000,'',373,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(39,'Ly cốc thuỷ tinh Cat and cloud NB text 500ml',24,'cái',60000,'',2,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(40,'Kẹp gỗ Cat mermaid caticorn set10',12,'cái',200000,'',834,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(41,'Thắt lưng dây nịt da vòng tròn móc nối 105cm',3,'cái',50000,'',401,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(42,'Thắt lưng dây nịt da nứt móc vuông basic 106cm',4,'cái',50000,'',823,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(43,'Ly cốc sứ Baby bear astronaut cao cấp 470ml',22,'cái',170000,'',202,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(44,'Sticker túi Flowers and plants set30',12,'cái',30000,'',179,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(45,'Sổ vở A5 MZ Xmas Lookii Kat The christmas songs 80 trang',20,'quyển',20000,'',20,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(46,'Thắt lưng dây nịt da trơn không lỗ 105cm',22,'cái',70000,'mô tả',359,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(47,'Thắt lưng dây nịt da A Little PM Daisy Flower móc chữ nhật',17,'cái',40000,'',850,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(48,'Túi chườm giữ nhiệt bọc vải Baby bear',16,'cái',110000,'',970,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(49,'Kẹp gỗ Earth space thunder set10',12,'cái',20000,'',326,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(50,'Giày sneaker đạp gót rách Stamp viền trắng',0,'đôi',270000,'giày đạp gót',469,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(51,'Thắt lưng dây nịt da Gold Triangle 105cm',5,'cái',45000,'',385,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(52,'Khẩu trang Ninja 1 màu',0,'cái',30000,'',48,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(53,'Thắt lưng dây nịt da trơn chữ U basic 100cm',0,'cái',50000,'',2,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(54,'Ly cốc sứ Smile face sọc nổi 350ml',9,'cái',95000,'',939,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(55,'Recycle bin Cute pet 11x15',10,'cái',80000,'',811,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(56,'Kẹp gỗ Cat color emotion set10',20,'cái',20000,'',344,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(57,'Khẩu trang MJ Gradient pastel color set10',200,'set',30000,'',138,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(58,'Thắt lưng dây nịt da vòng tròn móc nối 105cm',7,'cái',50000,'',329,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(59,'Kính gọng nhựa Letter T',20,'cái',80000,'',148,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(60,'Thắt lưng dây nịt da mảnh vuông đường chéo cạnh 105cm',7,'cái',50000,'',391,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(61,'Ly cốc sứ Today is a lucky day sọc nổi 280ml',0,'cái',100000,'',729,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(62,'Thắt lưng dây nịt da nứt móc elip 107cm',0,'cái',55000,'',680,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(63,'Kẹp gỗ Mèo trái cây fruit set10',12,'cái',20000,'',465,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(64,'Dây đeo khẩu trang Smile dây sọc',12,'dây',50000,'',621,'NEW',2,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(65,'Kính mát gọng nhựa mắt mèo 2 chấm',12,'cái',100000,'',732,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(66,'Washi tape Scenery painting 3mx50mm',9,'cái',25000,'',976,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(67,'Thắt lưng dây nịt da móc trái tim 115cm',9,'cái',55000,'',886,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(68,'Bảo vệ khẩu trang Animals line hey guy',20,'cái',5000,'',518,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(69,'Thắt lưng dây nịt da trơn basic bản nhỏ 106cm',12,'cái',45000,'',283,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(70,'Thắt lưng dây nịt da khóa gold unique bản nhỏ 100cm',6,'cái',50000,'',600,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(71,'Thắt lưng dây nịt kim loại mắt xích basic 110cm',6,'cái',80000,'',940,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(72,'Kính mát gọng nhựa mắt mèo cắt góc',12,'cái',80000,'',680,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(73,'Thắt lưng dây nịt da khóa khối tròn gold bản nhỏ 100cm',12,'cái',50000,'',319,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(74,'Gối sưởi Baby bear gấu cún má hồng ngó đầu sạc điện 27x19',8,'cái',150000,'',332,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(75,'Thắt lưng dây nịt da móc tròn khoét chữ nhật 102cm',5,'cái',50000,'',955,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(76,'Kẹp gỗ Chân mèo cute set10',12,'cái',20000,'',447,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(77,'Thắt lưng dây nịt da trơn móc tròn bản lớn 107cm',18,'cái',55000,'',419,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(78,'Thắt lưng dây nịt da móc tròn bản to 105cm',9,'cái',55000,'',844,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(79,'Túi chườm giữ nhiệt bọc vải Cún thỏ vịt sweet things',21,'cái',40000,'',361,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(80,'Túi chườm giữ nhiệt bọc vải Rabbit and cat',12,'cái',40000,'',665,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(81,'Thắt lưng dây nịt da viền chỉ khóa vuông basic 106cm',4,'cái',60000,'',13,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(82,'Giày sneaker đạp gót Daisy Flower',0,'đôi',210000,'',752,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(83,'Kính mát gọng nhựa mắt cắt phẳng viền trên',12,'cái',60000,'',814,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(84,'Ly cốc sứ Baby bear phao bơi vịt vàng 450ml',21,'cái',135000,'',658,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(85,'Hộp bút nhựa Little girl oh tiger 21X3',21,'hộp',70000,'',820,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(86,'Thắt lưng dây nịt vải OFF-WHITE HOTTREND',24,'cái',60000,'',524,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(87,'Khẩu trang MJ Rabbit tai dài cục bông',15,'cái',45000,'',667,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(88,'Khẩu trang MJ trơn basic sợi đàn hồi set3',22,'set',45000,'',395,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(89,'Giày sneaker rách Stamp viền trắng',14,'đôi',270000,'',303,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(90,'Thắt lưng dây nịt da nhiều lỗ móc Trái tim 105cm',12,'cái',0,'',120,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(91,'Thắt lưng dây nịt da Double C 105cm',6,'cái',50000,'',194,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(92,'Kính mát gọng nhựa chữ nhật viền càng kính dày',25,'cái',80000,'',59,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(93,'Ly cốc sứ Bright stars 450ml',20,'cái',160000,'',434,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(94,'Giày sneaker đạp gót lượn sóng basic',20,'đôi',200000,'GIày',673,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(95,'Thắt lưng dây nịt da móc vuông xoắn 105cm',18,'cái',50000,'Thắt lưng',958,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(96,'Ly cốc sứ Cute cat claw happy time 500ml',12,'cái',100000,'',620,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(97,'Kẹp gỗ Daisy Duck cosplay cute animal set10',21,'cái',25000,'',471,'NEW',1,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(98,'Bút viết Cute animal',20,'cái',25000,'',990,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(99,'Hoa sáp bó hướng dương 3 bông 33cm - Vàng',76,'bó',95000,'',630,'NEW',2,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(100,'Túi chườm giữ nhiệt bọc vải Bé trái cây cute 14x19',21,'',100000,'',715,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(101,'Quà tặng bìa tranh vintage',0,'hộp',98000,'',11,'NEW',2,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(102,'Kính mát gọng nhựa mắt chữ nhật chân cắt vòng',12,'cái',80000,'',424,'NEW',5,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(103,'Thắt lưng dây nịt da khóa vuông chạm khắc 107cm',0,'cái',55000,'',137,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(104,'Khẩu trang MJ cún mèo alone nền màu',0,'cái',60000,'',123,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(105,'Ly cốc thuỷ tinh xmas Tree snowflake cao cấp 500ml',12,'cái',300000,'',10,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(106,'Thắt lưng dây nịt kim loại nhiều trái tim',4,'cái',50000,'',437,'NEW',3,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(107,'Sticker bảng MJ Alphabet number series',20,'cái',10000,'',319,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(108,'Ly cốc sứ Butter Rue d\' Saint Bernard 500ml',7,'cái',95000,'',35,'NEW',8,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(109,'Lịch 2022 MJ để bàn The little prince dream space',20,'quyển',60000,'',51,'NEW',7,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(110,'Khẩu trang MJ Cute animal cosplay fruit',20,'cái',35000,'',948,'NEW',4,'2022-10-10 21:00:00','2022-10-10 21:00:00'),(111,'Khẩu trang MJ Animals cục bông color',25,'cái',55000,'',59,'NEW',4,'2022-10-10 21:00:00','2022-12-14 14:12:42'),(117,'Quà tặng',0,'cái',500,'',NULL,'NEW',3,'2022-12-08 12:24:20','2022-12-08 12:24:20');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `alias` varchar(45) DEFAULT NULL,
  `created_date` varchar(45) DEFAULT NULL,
  `modified_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_MANAGER','Manager',NULL,NULL),(2,'ROLE_SALER','Saler',NULL,NULL),(3,'ROLE_SHIPPER','Shipper',NULL,NULL),(4,'ROLE_CUSTOMER','Customer',NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES ('NEW','New item','',NULL,NULL),('VOID','Deleted item',NULL,NULL,NULL);
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Phat','Luu','phat','$2a$12$qyy7PbEy1YlGGCAwDPF0ee32xKDr8i..ipxPGJHIj699sKQCboAVu','2022-06-01',0,'luutienphat@gmail.com','0796919429','HCM',NULL,'2022-06-01','2022-06-17'),(2,'Tuan','Bui','tuan','$2a$12$qyy7PbEy1YlGGCAwDPF0ee32xKDr8i..ipxPGJHIj699sKQCboAVu',NULL,0,NULL,NULL,'HCM',NULL,'2022-06-01','2022-06-17'),(3,'John','Richer','john','$2a$12$qyy7PbEy1YlGGCAwDPF0ee32xKDr8i..ipxPGJHIj699sKQCboAVu',NULL,0,NULL,NULL,'HCM',NULL,'2022-06-05','2022-06-17'),(4,'Kim','Nguyen','kim','$2a$12$qyy7PbEy1YlGGCAwDPF0ee32xKDr8i..ipxPGJHIj699sKQCboAVu',NULL,1,NULL,NULL,'HCM',NULL,'2022-06-01','2022-06-17'),(5,'Guest','Pham','guest','$2a$12$qyy7PbEy1YlGGCAwDPF0ee32xKDr8i..ipxPGJHIj699sKQCboAVu','2022-10-18',0,'luutienphat@gmail.com','0796919429','HCM',NULL,'2022-06-01','2022-12-14'),(6,'Guest','Luu','guest2','$2a$10$5wZmXHRkWTfmBRS93lydY.5ppWghLHAOfXwKOsSHarxErY1LWkqgy',NULL,1,'','','',NULL,'2022-05-03','2022-06-17'),(7,'Guest3','Truong','guest3','$2a$10$AGs0.SbaNZEZBcM41e6x9OMcSDJ4J4UjksInxjK8xQLlpLE1lqvD2',NULL,0,'','','',NULL,'2022-05-03','2022-06-17'),(8,'Guest','Luu','guest4','$2a$10$jF/LLh.Nf1IcnuXHc0jqi.toYbPvhjOxwk5Hn1O.EuWzfkILYGzg2','2022-06-20',0,'luutienphat@gmail.com','0796919429','242/16 S1',NULL,'2022-06-20','2022-06-20'),(9,'p','l','p','$2a$10$UIWlkqvQr/rRy8JwjU8/Aub5FKWSJ8SOMOVK3kieAg8IAMVyYR.yG','2000-11-12',0,'123@gmail.com','090','HCM',NULL,'2022-06-20','2022-06-20');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_products`
--

DROP TABLE IF EXISTS `user_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FavoriteProduct_User_idx` (`user_id`),
  KEY `FK_FavoriteProduct_Product_idx` (`product_id`),
  CONSTRAINT `FK_FavoriteProduct_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_FavoriteProduct_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_products`
--

LOCK TABLES `user_products` WRITE;
/*!40000 ALTER TABLE `user_products` DISABLE KEYS */;
INSERT INTO `user_products` VALUES (3,6,1,NULL,'like','2022-10-10','2022-10-10'),(5,6,3,NULL,'like','2022-10-10','2022-10-10'),(13,5,2,NULL,'like','2022-12-03','2022-12-03'),(16,5,23,NULL,'like','2022-12-03','2022-12-03'),(18,5,35,NULL,'like','2022-12-03','2022-12-03'),(19,5,22,NULL,'like','2022-12-03','2022-12-03'),(33,5,6,NULL,'like','2022-12-14','2022-12-14');
/*!40000 ALTER TABLE `user_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,1),(3,2),(4,3),(5,4),(6,4),(7,4),(8,4),(9,4);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-28  1:57:08
