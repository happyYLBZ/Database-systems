-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: school management system
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Temporary view structure for view `new_view`
--

DROP TABLE IF EXISTS `new_view`;
/*!50001 DROP VIEW IF EXISTS `new_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `new_view` AS SELECT 
 1 AS `学号`,
 1 AS `姓名`,
 1 AS `性别`,
 1 AS `所学专业`,
 1 AS `入学时间`,
 1 AS `称谓`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='账号和密码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (11111,'123456'),(22222,'123456'),(33333,'123456'),(100086,'123456'),(191909,'123456'),(202367,'123456'),(2113666,'123456'),(2113737,'123456'),(2118999,'123456'),(2119780,'123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `参加`
--

DROP TABLE IF EXISTS `参加`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `参加` (
  `参加的社团名称` char(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `学生学号` int NOT NULL,
  PRIMARY KEY (`学生学号`,`参加的社团名称`),
  KEY `学生学号_idx` (`学生学号`) /*!80000 INVISIBLE */,
  KEY `社团_idx` (`参加的社团名称`),
  CONSTRAINT `学生学号` FOREIGN KEY (`学生学号`) REFERENCES `本科生` (`学号`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `社团` FOREIGN KEY (`参加的社团名称`) REFERENCES `社团` (`社团名称`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='学生参加社团信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `参加`
--

LOCK TABLES `参加` WRITE;
/*!40000 ALTER TABLE `参加` DISABLE KEYS */;
INSERT INTO `参加` VALUES ('篮球社团',2113666),('篮球社团',2113737),('篮球社团',2119780);
/*!40000 ALTER TABLE `参加` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `学院`
--

DROP TABLE IF EXISTS `学院`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `学院` (
  `学院名称` char(30) NOT NULL,
  `建立时间` date DEFAULT NULL,
  PRIMARY KEY (`学院名称`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='学院信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `学院`
--

LOCK TABLES `学院` WRITE;
/*!40000 ALTER TABLE `学院` DISABLE KEYS */;
INSERT INTO `学院` VALUES ('周恩来政府管理学院','1919-10-08'),('网络空间安全学院','1919-10-01'),('计算机学院','1919-11-09');
/*!40000 ALTER TABLE `学院` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `成绩`
--

DROP TABLE IF EXISTS `成绩`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `成绩` (
  `课号` int NOT NULL,
  `学生学号` int NOT NULL,
  `成绩` float DEFAULT '0',
  PRIMARY KEY (`课号`,`学生学号`),
  KEY `学生_idx` (`学生学号`),
  CONSTRAINT `学生` FOREIGN KEY (`学生学号`) REFERENCES `本科生` (`学号`),
  CONSTRAINT `课` FOREIGN KEY (`课号`) REFERENCES `课程` (`课程代号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='成绩信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `成绩`
--

LOCK TABLES `成绩` WRITE;
/*!40000 ALTER TABLE `成绩` DISABLE KEYS */;
INSERT INTO `成绩` VALUES (396,2113666,67),(396,2113737,98),(876,2118999,91),(876,2119780,92);
/*!40000 ALTER TABLE `成绩` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `教师`
--

DROP TABLE IF EXISTS `教师`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `教师` (
  `姓名` char(10) NOT NULL,
  `职工号` int NOT NULL,
  `职称` char(10) DEFAULT NULL,
  `所属学院` char(10) NOT NULL,
  `入职时间` date DEFAULT NULL,
  PRIMARY KEY (`职工号`),
  KEY `所属学院_idx` (`所属学院`),
  CONSTRAINT `所属学院` FOREIGN KEY (`所属学院`) REFERENCES `学院` (`学院名称`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='教师信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `教师`
--

LOCK TABLES `教师` WRITE;
/*!40000 ALTER TABLE `教师` DISABLE KEYS */;
INSERT INTO `教师` VALUES ('穆里尼奥',100086,'教授','网络空间安全学院','2020-09-08'),('瓜迪奥拉',191909,'教授','计算机学院','2003-09-08'),('全拓禹',202367,'教授','周恩来政府管理学院','2008-09-23');
/*!40000 ALTER TABLE `教师` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `本科专业`
--

DROP TABLE IF EXISTS `本科专业`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `本科专业` (
  `专业名称` char(10) NOT NULL,
  `培养目标` varchar(45) NOT NULL,
  `开设学院` char(30) NOT NULL,
  PRIMARY KEY (`专业名称`),
  KEY `所属学院_idx` (`开设学院`),
  KEY `开设学院_idx` (`开设学院`),
  CONSTRAINT `开设学院` FOREIGN KEY (`开设学院`) REFERENCES `学院` (`学院名称`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `本科专业`
--

LOCK TABLES `本科专业` WRITE;
/*!40000 ALTER TABLE `本科专业` DISABLE KEYS */;
INSERT INTO `本科专业` VALUES ('可视化编程','培养可以编优秀游戏的人','计算机学院'),('密码科学与技术','培养密码精英人才','网络空间安全学院'),('心理学','培养可以洞察别人心理的人','周恩来政府管理学院');
/*!40000 ALTER TABLE `本科专业` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `本科生`
--

DROP TABLE IF EXISTS `本科生`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `本科生` (
  `姓名` char(10) NOT NULL,
  `学号` int NOT NULL,
  `入学时间` date NOT NULL,
  `性别` char(1) NOT NULL,
  `所学专业` char(10) NOT NULL,
  `称谓` varchar(45) NOT NULL DEFAULT '普通学生',
  PRIMARY KEY (`学号`),
  KEY `所学专业_idx` (`所学专业`),
  CONSTRAINT `所学专业` FOREIGN KEY (`所学专业`) REFERENCES `本科专业` (`专业名称`),
  CONSTRAINT `用户学号` FOREIGN KEY (`学号`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='本科生信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `本科生`
--

LOCK TABLES `本科生` WRITE;
/*!40000 ALTER TABLE `本科生` DISABLE KEYS */;
INSERT INTO `本科生` VALUES ('李六',2113666,'2009-03-13','男','心理学','普通学生'),('张三',2113737,'2019-09-08','男','可视化编程','优秀学生'),('李四',2118999,'2020-08-10','男','密码科学与技术','优秀学生'),('李湘',2119780,'1999-09-08','男','密码科学与技术','普通学生');
/*!40000 ALTER TABLE `本科生` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `社团`
--

DROP TABLE IF EXISTS `社团`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `社团` (
  `社团名称` char(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `成立时间` varchar(20) NOT NULL,
  PRIMARY KEY (`社团名称`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='社团信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `社团`
--

LOCK TABLES `社团` WRITE;
/*!40000 ALTER TABLE `社团` DISABLE KEYS */;
INSERT INTO `社团` VALUES ('武术社团','1979-09-08'),('篮球社团','2003-07-06');
/*!40000 ALTER TABLE `社团` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `管理者`
--

DROP TABLE IF EXISTS `管理者`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `管理者` (
  `账号` int NOT NULL,
  `密码` varchar(45) NOT NULL,
  PRIMARY KEY (`账号`),
  CONSTRAINT `管理者账户` FOREIGN KEY (`账号`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='管理者信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `管理者`
--

LOCK TABLES `管理者` WRITE;
/*!40000 ALTER TABLE `管理者` DISABLE KEYS */;
INSERT INTO `管理者` VALUES (11111,'123456'),(22222,'123456'),(33333,'123456');
/*!40000 ALTER TABLE `管理者` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `课程`
--

DROP TABLE IF EXISTS `课程`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `课程` (
  `课程名称` varchar(20) NOT NULL,
  `课程代号` int NOT NULL,
  `开课老师` int NOT NULL,
  `上课时间` varchar(45) NOT NULL,
  PRIMARY KEY (`课程代号`),
  KEY `开课老师_idx` (`开课老师`),
  CONSTRAINT `开课老师` FOREIGN KEY (`开课老师`) REFERENCES `教师` (`职工号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='课程信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `课程`
--

LOCK TABLES `课程` WRITE;
/*!40000 ALTER TABLE `课程` DISABLE KEYS */;
INSERT INTO `课程` VALUES ('密码学基础',367,100086,'周五上午八点'),('数据库系统',396,100086,'周五上午九点'),('毛概',876,191909,'周四晚上'),('爱情社会学',1314,100086,'周三早上八点'),('计算机原理',121211,202367,'周天晚上十点'),('信息论',999999,202367,'周一中午12：55');
/*!40000 ALTER TABLE `课程` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `开设课程` BEFORE INSERT ON `课程` FOR EACH ROW BEGIN
	declare msg1 varchar(90);
    declare msg2 varchar(90);
    IF (SELECT COUNT(*)  FROM 课程 WHERE 开课老师 = NEW.开课老师)>1 THEN
    set msg1 = "同一个老师最多只能开设两个课程!";
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg1;
    end if;
    IF(SELECT COUNT(*)  FROM 课程 WHERE NEW.上课时间 = 课程.上课时间)>0 THEN
	set msg2 = "课程时间冲突";
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg2;
	end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `new_view`
--

/*!50001 DROP VIEW IF EXISTS `new_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `new_view` AS select `本科生`.`学号` AS `学号`,`本科生`.`姓名` AS `姓名`,`本科生`.`性别` AS `性别`,`本科生`.`所学专业` AS `所学专业`,`本科生`.`入学时间` AS `入学时间`,`本科生`.`称谓` AS `称谓` from (`本科生` join `参加` on((`本科生`.`学号` = `参加`.`学生学号`))) where (`参加`.`参加的社团名称` = `return_clubname`()) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-06 23:14:47
