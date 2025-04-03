-- MySQL dump 10.13  Distrib 8.0.32, for Linux (aarch64)
--
-- Host: localhost    Database: group_buy_market
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
-- Table structure for table `activity_tags_config`
--

DROP TABLE IF EXISTS `activity_tags_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_tags_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `tag_id` varchar(32) NOT NULL COMMENT '人群标签规则标识',
  `tag_scope` varchar(4) NOT NULL COMMENT '人群标签规则范围（多选: 1可见限制、2参与限制）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_tags_config_id` (`activity_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='活动标签配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_tags_config`
--

LOCK TABLES `activity_tags_config` WRITE;
/*!40000 ALTER TABLE `activity_tags_config` DISABLE KEYS */;
INSERT INTO `activity_tags_config` VALUES (1,20001,'TAG_0001','1,2','2025-03-30 17:27:05','2025-03-30 21:13:41'),(3,20001,'TAG_0002','1','2025-03-30 21:24:20','2025-03-30 21:24:20');
/*!40000 ALTER TABLE `activity_tags_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crowd_tags`
--

DROP TABLE IF EXISTS `crowd_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crowd_tags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tag_id` varchar(32) NOT NULL COMMENT '人群ID',
  `tag_name` varchar(64) NOT NULL COMMENT '人群名称',
  `tag_desc` varchar(256) NOT NULL COMMENT '人群描述',
  `statistics` int NOT NULL COMMENT '人群标签统计量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_tag_id` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人群标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crowd_tags`
--

LOCK TABLES `crowd_tags` WRITE;
/*!40000 ALTER TABLE `crowd_tags` DISABLE KEYS */;
INSERT INTO `crowd_tags` VALUES (1,'TAG_0001','潜在消费用户','潜在消费用户',2,'2025-03-30 11:36:03','2025-03-30 21:20:39');
/*!40000 ALTER TABLE `crowd_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crowd_tags_detail`
--

DROP TABLE IF EXISTS `crowd_tags_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crowd_tags_detail` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tag_id` varchar(32) NOT NULL COMMENT '人群ID',
  `user_id` varchar(16) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_tag_user` (`tag_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人群标签明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crowd_tags_detail`
--

LOCK TABLES `crowd_tags_detail` WRITE;
/*!40000 ALTER TABLE `crowd_tags_detail` DISABLE KEYS */;
INSERT INTO `crowd_tags_detail` VALUES (7,'TAG_0001','user001','2025-03-30 21:20:39','2025-03-30 21:20:39'),(8,'TAG_0001','user002','2025-03-30 21:20:39','2025-03-30 21:20:39'),(9,'TAG_0002','user003','2025-03-30 21:23:42','2025-03-30 21:23:42');
/*!40000 ALTER TABLE `crowd_tags_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crowd_tags_job`
--

DROP TABLE IF EXISTS `crowd_tags_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crowd_tags_job` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tag_id` varchar(32) NOT NULL COMMENT '标签ID',
  `batch_id` varchar(8) NOT NULL COMMENT '批次ID',
  `tag_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '标签类型（参与量、消费金额）',
  `tag_rule` varchar(8) NOT NULL COMMENT '标签规则（限定类型 N次）',
  `stat_start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计数据，开始时间',
  `stat_end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计数据，结束时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态；0初始、1计划（进入执行阶段）、2重置、3完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_batch_id` (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人群标签任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crowd_tags_job`
--

LOCK TABLES `crowd_tags_job` WRITE;
/*!40000 ALTER TABLE `crowd_tags_job` DISABLE KEYS */;
INSERT INTO `crowd_tags_job` VALUES (1,'TAG_0001','0001',0,'5','2025-03-30 11:37:21','2025-04-30 11:37:23',1,'2025-03-30 11:38:03','2025-03-30 21:14:55'),(3,'TAG_0002','0002',0,'10','2025-03-30 21:23:07','2025-04-30 21:23:09',0,'2025-03-30 21:23:12','2025-03-30 21:23:12');
/*!40000 ALTER TABLE `crowd_tags_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buy_activity`
--

DROP TABLE IF EXISTS `group_buy_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buy_activity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(128) NOT NULL COMMENT '活动名称',
  `discount_id` varchar(8) NOT NULL COMMENT '折扣ID',
  `group_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '拼团方式（0自动成团、1达成目标拼团）',
  `take_limit_count` int NOT NULL DEFAULT '1' COMMENT '拼团次数限制',
  `target_count` int NOT NULL DEFAULT '1' COMMENT '拼团目标人数',
  `valid_time` int NOT NULL DEFAULT '15' COMMENT '拼团时长（分钟）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '活动状态（0创建、1生效、2过期、3废弃）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动结束时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='拼团活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buy_activity`
--

LOCK TABLES `group_buy_activity` WRITE;
/*!40000 ALTER TABLE `group_buy_activity` DISABLE KEYS */;
INSERT INTO `group_buy_activity` VALUES (1,20001,'测试活动','30001',1,3,3,15,1,'2025-03-28 21:15:20','2025-04-28 21:15:23','2025-03-28 21:16:31','2025-04-02 11:18:51');
/*!40000 ALTER TABLE `group_buy_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buy_discount`
--

DROP TABLE IF EXISTS `group_buy_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buy_discount` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `discount_id` varchar(8) NOT NULL COMMENT '折扣ID',
  `discount_name` varchar(64) NOT NULL COMMENT '折扣标题',
  `discount_desc` varchar(256) NOT NULL COMMENT '折扣描述',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '折扣类型（0:base、1:tag）',
  `market_plan` varchar(4) NOT NULL DEFAULT 'ZJ' COMMENT '营销优惠计划（ZJ:直减、MJ:满减、ZK:折扣、N:N元购）',
  `market_expr` varchar(32) NOT NULL COMMENT '营销优惠表达式',
  `tag_id` varchar(8) DEFAULT NULL COMMENT '人群标签，特定优惠限定',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_discount_id` (`discount_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='折扣配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buy_discount`
--

LOCK TABLES `group_buy_discount` WRITE;
/*!40000 ALTER TABLE `group_buy_discount` DISABLE KEYS */;
INSERT INTO `group_buy_discount` VALUES (1,'30001','直减优惠','直减20元',1,'ZJ','20','','2025-03-27 14:47:51','2025-03-31 22:47:00'),(2,'30002','满减优惠','满100元优惠10元',0,'MJ','100,10',NULL,'2025-03-29 11:57:53','2025-03-29 11:57:53'),(3,'30003','折扣优惠','8折优惠',0,'ZK','0.8',NULL,'2025-03-29 11:58:52','2025-03-29 11:58:52'),(4,'30004','N元购买优惠','9.9元直购',0,'N','9.99',NULL,'2025-03-29 12:01:03','2025-03-29 14:54:40');
/*!40000 ALTER TABLE `group_buy_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buy_order`
--

DROP TABLE IF EXISTS `group_buy_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buy_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `team_id` varchar(8) NOT NULL COMMENT '拼单组队ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `source` varchar(12) NOT NULL COMMENT '渠道',
  `channel` varchar(12) NOT NULL COMMENT '来源',
  `original_price` decimal(8,2) NOT NULL COMMENT '原始价格',
  `discount_deduction` decimal(8,2) NOT NULL COMMENT '折扣扣除的金额',
  `pay_price` decimal(8,2) NOT NULL COMMENT '支付价格',
  `target_count` int NOT NULL COMMENT '目标数量',
  `complete_count` int NOT NULL COMMENT '完成数量',
  `lock_count` int NOT NULL COMMENT '锁单数量',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-拼单中、1-完成、2-失败）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_team_id` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='拼单订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buy_order`
--

LOCK TABLES `group_buy_order` WRITE;
/*!40000 ALTER TABLE `group_buy_order` DISABLE KEYS */;
INSERT INTO `group_buy_order` VALUES (2,'zSNNwzQK',20001,'SOURCE_001','CHANNEL_001',100.00,20.00,80.00,3,0,2,0,'2025-03-31 22:40:54','2025-04-02 12:16:02'),(4,'qUWFqAWe',20001,'SOURCE_001','CHANNEL_001',100.00,20.00,80.00,3,0,1,0,'2025-03-31 23:54:25','2025-04-02 12:16:02'),(8,'UXDPnVLR',20001,'SOURCE_001','CHANNEL_001',100.00,20.00,80.00,3,0,1,0,'2025-04-02 11:07:05','2025-04-02 12:16:02'),(9,'AJhqXVvo',20001,'SOURCE_001','CHANNEL_001',100.00,20.00,80.00,3,0,1,0,'2025-04-02 11:18:53','2025-04-02 12:16:02'),(11,'yGetPSIm',20001,'SOURCE_001','CHANNEL_001',100.00,20.00,80.00,3,0,1,0,'2025-04-02 12:15:21','2025-04-02 12:15:21');
/*!40000 ALTER TABLE `group_buy_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buy_order_list`
--

DROP TABLE IF EXISTS user_group_buy_order_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buy_order_list` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `team_id` varchar(8) NOT NULL COMMENT '拼单组队ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
  `source` varchar(12) NOT NULL COMMENT '渠道',
  `channel` varchar(12) NOT NULL COMMENT '来源',
  `original_price` decimal(8,2) NOT NULL COMMENT '原始价格',
  `pay_price` decimal(8,2) DEFAULT NULL COMMENT '支付金额',
  `discount_deduction` decimal(8,2) NOT NULL COMMENT '折扣扣除的金额',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态；0初始锁定、1消费完成',
  `out_trade_no` varchar(12) NOT NULL COMMENT '外部交易单号-确保外部调用唯一幂等',
  `biz_id` varchar(64) NOT NULL COMMENT '业务唯一ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_trade_no` (`out_trade_no`),
  UNIQUE KEY `uq_biz_id` (`biz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='拼单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buy_order_list`
--

LOCK TABLES user_group_buy_order_detail WRITE;
/*!40000 ALTER TABLE user_group_buy_order_detail DISABLE KEYS */;
INSERT INTO user_group_buy_order_detail VALUES (1,'user001','zSNNwzQK','707917163738',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300907','1','2025-03-31 22:40:54','2025-04-02 11:57:40'),(3,'user002','qUWFqAWe','960844371445',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300908','2','2025-03-31 23:54:25','2025-04-02 11:57:40'),(4,'user002','zSNNwzQK','673884257848',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300909','3','2025-03-31 23:54:58','2025-04-02 11:57:40'),(7,'user002','UXDPnVLR','239209687907',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300910','20001_user002_3','2025-04-02 11:07:05','2025-04-02 11:57:40'),(8,'user003','AJhqXVvo','695108540208',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300912','20001_user003_1','2025-04-02 11:18:53','2025-04-02 11:57:40'),(9,'user003','yGetPSIm','869327888973',20001,'2025-03-28 21:15:20','2025-04-28 21:15:23','10001','SOURCE_001','CHANNEL_001',100.00,80.00,20.00,0,'2010300913','20001_user003_2','2025-04-02 12:15:21','2025-04-02 12:15:21');
/*!40000 ALTER TABLE user_group_buy_order_detail ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_sku_activity`
--

DROP TABLE IF EXISTS `sc_sku_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_sku_activity` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `source` varchar(12) NOT NULL COMMENT '渠道',
  `channel` varchar(12) NOT NULL COMMENT '来源',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_goodsId` (`source`,`channel`,`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='渠道商品活动配置关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_sku_activity`
--

LOCK TABLES `sc_sku_activity` WRITE;
/*!40000 ALTER TABLE `sc_sku_activity` DISABLE KEYS */;
INSERT INTO `sc_sku_activity` VALUES (1,'SOURCE_001','CHANNEL_001',20001,'10001','2025-03-30 13:55:26','2025-03-30 13:56:43');
/*!40000 ALTER TABLE `sc_sku_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sku`
--

DROP TABLE IF EXISTS `sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sku` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `source` varchar(12) NOT NULL COMMENT '渠道',
  `channel` varchar(12) NOT NULL COMMENT '来源',
  `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(128) NOT NULL COMMENT '商品名称',
  `original_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sku`
--

LOCK TABLES `sku` WRITE;
/*!40000 ALTER TABLE `sku` DISABLE KEYS */;
INSERT INTO `sku` VALUES (1,'SOURCE_001','CHANNEL_001','10001','测试商品',100.00,'2025-03-28 21:28:54','2025-03-28 21:28:54');
/*!40000 ALTER TABLE `sku` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-02 12:59:14
