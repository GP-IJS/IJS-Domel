-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: domel
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `request_parameters`
--

DROP TABLE IF EXISTS `request_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_parameters` (
  `ParametersID` char(36) NOT NULL,
  `P138_Hub_BladeInletDiameter` double NOT NULL,
  `P142_SHROUD_BladeInletDiameter` double NOT NULL,
  `P145_SideHUB_R1` double NOT NULL,
  `P146_SideHUB_R2` double NOT NULL,
  `P147_SideHUB_M_Height` double NOT NULL,
  `P151_SideSHROUD_R1` double NOT NULL,
  `P152_SideSHROUD_R2` double NOT NULL,
  `P149_CK_OUT_Height` double NOT NULL,
  `P209_SideSHROUD_MID_height_change` double NOT NULL,
  `P153_HUB_Blade_InletAngle` double NOT NULL,
  `P154_HUB_Blade_OutletAngle` double NOT NULL,
  `P159_HUB_Blade_MidAng` double NOT NULL,
  `P31_SHROUD_BladeInletAngleAddon` double NOT NULL,
  `P32_SHROUD_BladeOutletAngleAddon` double NOT NULL,
  `P160_SHROUD_Blade_MidAng` double NOT NULL,
  `P30_BLADE_Number` double NOT NULL,
  `Processing_Time` int(11) DEFAULT NULL,
  PRIMARY KEY (`ParametersID`),
  UNIQUE KEY `ParametersID_UNIQUE` (`ParametersID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-22 16:02:24
