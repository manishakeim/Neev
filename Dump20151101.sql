CREATE DATABASE  IF NOT EXISTS `neev` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `neev`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: neev
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `Customer_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Customer_Name` varchar(45) NOT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Customer_Phone` varchar(15) DEFAULT NULL,
  `customercol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Customer_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Nidhi','Faridabad','9953730444',NULL),(2,'jhfhjg','gcvhgvh','9887521336',NULL),(3,'jhvgxcjhvgxcvhb','jygiuyhgiughiu','8787566265',NULL),(4,'tyagiji','yiuhi coudccnd','9953179270',NULL),(5,'kavita','paschim vihar','9953157940',NULL),(6,'sharandatt tyagi','goa','9650551021',NULL),(7,'Saroj','Sector-37, Faridabad','9868720664',NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `Driver_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Driver_Name` varchar(45) NOT NULL,
  `Driver_Address` varchar(50) DEFAULT NULL,
  `Driver_Phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Driver_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (2,'Ramesh','Delhi','9876543210'),(3,'Suresh','DakshinPuri','8765432190');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('user','puser');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furnished_products`
--

DROP TABLE IF EXISTS `furnished_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furnished_products` (
  `Product_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Product_Name` varchar(45) NOT NULL,
  `Product_Price` double NOT NULL,
  `Available_Quantity` int(11) DEFAULT NULL,
  `Status` varchar(20) NOT NULL DEFAULT 'Available',
  PRIMARY KEY (`Product_ID`),
  UNIQUE KEY `Product_Name_UNIQUE` (`Product_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furnished_products`
--

LOCK TABLES `furnished_products` WRITE;
/*!40000 ALTER TABLE `furnished_products` DISABLE KEYS */;
INSERT INTO `furnished_products` VALUES (4,'bags',50,5,'Available'),(8,'Pillow Covers',100,200,'Available');
/*!40000 ALTER TABLE `furnished_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_transit`
--

DROP TABLE IF EXISTS `in_transit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `in_transit` (
  `Order_ID` int(10) NOT NULL,
  `Driver_ID` int(10) NOT NULL,
  PRIMARY KEY (`Order_ID`,`Driver_ID`),
  KEY `Customer_ID_idx` (`Driver_ID`),
  CONSTRAINT `o_id` FOREIGN KEY (`Order_ID`) REFERENCES `order_products` (`Order_ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_transit`
--

LOCK TABLES `in_transit` WRITE;
/*!40000 ALTER TABLE `in_transit` DISABLE KEYS */;
/*!40000 ALTER TABLE `in_transit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_track`
--

DROP TABLE IF EXISTS `material_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_track` (
  `Item_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Quantity` double DEFAULT NULL,
  PRIMARY KEY (`Item_ID`),
  CONSTRAINT `item_id` FOREIGN KEY (`Item_ID`) REFERENCES `raw_materials` (`Item_ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_track`
--

LOCK TABLES `material_track` WRITE;
/*!40000 ALTER TABLE `material_track` DISABLE KEYS */;
INSERT INTO `material_track` VALUES (2,5),(3,5),(101,18);
/*!40000 ALTER TABLE `material_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_products`
--

DROP TABLE IF EXISTS `order_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_products` (
  `Order_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Product_ID` int(10) DEFAULT NULL,
  `Customer_ID` int(10) DEFAULT NULL,
  `Expected_Quantity` int(11) DEFAULT NULL,
  `Booking_Date` date DEFAULT NULL,
  `Expected_Delivery_Date` date DEFAULT NULL,
  `Delivered_On` date DEFAULT NULL,
  `Delivery_Status` varchar(45) DEFAULT NULL,
  `Delivered_Quantity` int(11) DEFAULT NULL,
  `Unit_Price` double DEFAULT NULL,
  PRIMARY KEY (`Order_ID`),
  KEY `prod_id2_idx` (`Product_ID`),
  KEY `cust_id1_idx` (`Customer_ID`),
  CONSTRAINT `cust_id1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1211 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_products`
--

LOCK TABLES `order_products` WRITE;
/*!40000 ALTER TABLE `order_products` DISABLE KEYS */;
INSERT INTO `order_products` VALUES (1,2,1,1,NULL,NULL,'2015-10-31','Delivered',1,NULL),(2,4,1,10,NULL,NULL,'2015-10-31','Delivered',2,NULL),(3,2,1,2,NULL,NULL,'2015-10-31','Delievered',2,10),(1210,4,7,5,NULL,'2015-11-01','2015-11-01','Delivered',5,NULL);
/*!40000 ALTER TABLE `order_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raw_materials`
--

DROP TABLE IF EXISTS `raw_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `raw_materials` (
  `Item_ID` int(10) NOT NULL AUTO_INCREMENT,
  `SID` int(10) NOT NULL,
  `Item_Name` varchar(45) NOT NULL,
  `Quantity` double NOT NULL,
  `Unit_Price` double DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`Item_ID`,`SID`),
  KEY `SID_idx` (`SID`),
  CONSTRAINT `s_id` FOREIGN KEY (`SID`) REFERENCES `supplier` (`SID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_materials`
--

LOCK TABLES `raw_materials` WRITE;
/*!40000 ALTER TABLE `raw_materials` DISABLE KEYS */;
INSERT INTO `raw_materials` VALUES (2,101,'Cotton',5,20,NULL),(3,102,'Glue',5,10,NULL),(101,101,'Jute',10,5,NULL),(101,103,'Jute',10,50,NULL),(102,101,'Cotton',10,50,NULL),(113,102,'Ronak',20,12,'2015-10-27'),(114,102,'Shweta',2,12,'2015-10-27'),(115,1010,'Cotton',20,4,'2015-11-01'),(116,1010,'Cotton',20,4,'2015-11-01'),(117,1010,'Cotton',20,4,'2015-11-01');
/*!40000 ALTER TABLE `raw_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `SID` int(10) NOT NULL AUTO_INCREMENT,
  `Supplier_Name` varchar(45) NOT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (101,'ronak','23641','delhi'),(102,'nidhi','100','new delhi'),(103,'jigyasa','23655789','cp'),(104,'shweta','56892112','delhi'),(105,'jainit','5324854','rghj'),(111,'manisha','85536241','xyz'),(1001,'Jigyasa','9876543210','New Delhi'),(1006,'Shweta','9876543210','New Delhi'),(1007,'hj','123','aa'),(1008,'aa','46633','aaa'),(1009,'S1','5432','hjjkll'),(1010,'RawExport','9717634303','Faridabad');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'neev'
--

--
-- Dumping routines for database 'neev'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-01 15:55:26
