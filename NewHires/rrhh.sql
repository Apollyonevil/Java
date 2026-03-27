-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: rrhh
-- ------------------------------------------------------
-- Server version	12.2.2-MariaDB-ubu2404

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
-- Table structure for table `admin_users`
--

DROP TABLE IF EXISTS `admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_users` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_users`
--

LOCK TABLES `admin_users` WRITE;
/*!40000 ALTER TABLE `admin_users` DISABLE KEYS */;
INSERT INTO `admin_users` VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890','admin','$2a$10$c7wgk0aF9APQJ22VvjQyIe2F9zrABmB6xKrCyIOcuxtNkg9lHAafy',1),('f1a3c983-0370-4547-94d5-6790131a3224','pocoyo','$2a$10$Ca6TkzL/yxCroYquOIgjr.AJn5XFVvrLgSlnfDoscmnoWHW4ACdKi',1);
/*!40000 ALTER TABLE `admin_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidates`
--

DROP TABLE IF EXISTS `candidates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidates` (
  `id` varchar(36) NOT NULL,
  `candidate_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `employee_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidates`
--

LOCK TABLES `candidates` WRITE;
/*!40000 ALTER TABLE `candidates` DISABLE KEYS */;
INSERT INTO `candidates` VALUES ('413b7e6c-5ece-44cd-affc-6915d189be98','Manuel Probador','neverland85@hotmail.es','413b7e6c-5ece-44cd-affc-6915d189be98'),('81cce86e-8247-4227-b961-c3be06005dbf','Prueba2','prueba2@civica.com','81cce86e-8247-4227-b961-c3be06005dbf'),('aa7d31a3-a5d1-4bc7-941d-3907fb009845','probando','prueba@civica.com','aa7d31a3-a5d1-4bc7-941d-3907fb009845'),('caf86017-2c48-4b3d-9dd9-fab58ad24c99','Probando','probando@civica.com','caf86017-2c48-4b3d-9dd9-fab58ad24c99'),('d2c9964a-515a-4ce3-a53f-b1461a40458a','Manuel','manuelpruebaqueteprueba@civica.com','d2c9964a-515a-4ce3-a53f-b1461a40458a');
/*!40000 ALTER TABLE `candidates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_definitions`
--

DROP TABLE IF EXISTS `field_definitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_definitions` (
  `id` uuid NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `placeholder` varchar(255) DEFAULT NULL,
  `required` bit(1) NOT NULL,
  `type` enum('JPG','NUMBER','PDF','SELECT','TEXT') DEFAULT NULL,
  `sort_order` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_definitions`
--

LOCK TABLES `field_definitions` WRITE;
/*!40000 ALTER TABLE `field_definitions` DISABLE KEYS */;
INSERT INTO `field_definitions` VALUES ('0e524bed-b653-4544-b9e8-21c8be48e6e6','Apellidos',NULL,_binary '\0','TEXT',1),('5400ca90-5376-433d-9977-416786a83cac','Nombre',NULL,_binary '\0','TEXT',0),('34c48959-21ea-11f1-8e6b-46e816e51124','Copia de tu DNI/NIE (PDF)','Formato nombre archivo: DNI apellido apellido, nombre',_binary '','PDF',3),('34c4d71f-21ea-11f1-8e6b-46e816e51124','Copia de Titulación Académica (PDF)','Formato nombre archivo: TITULO apellido apellido, nombre',_binary '','PDF',4),('34c4db04-21ea-11f1-8e6b-46e816e51124','Número de Seguridad Social','Lo puedes consultar en cualquier nómina o contrato.',_binary '','NUMBER',5),('34c4db36-21ea-11f1-8e6b-46e816e51124','Número IBAN y justificante de titularidad (PDF)','Formato nombre archivo: TITULARIDAD CUENTA apellido apellido, nombre',_binary '','PDF',6),('34c4db44-21ea-11f1-8e6b-46e816e51124','Dirección de residencia actual',NULL,_binary '','TEXT',7),('34c4dbb7-21ea-11f1-8e6b-46e816e51124','Alta como demandante de empleo (si aplica)','Formato nombre archivo: DEMANDANTE apellido apellido, nombre  ',_binary '\0','PDF',8),('34c4dbc7-21ea-11f1-8e6b-46e816e51124','Justificante Garantía Juvenil (si aplica)','Formato nombre archivo: GARANTIA apellido apellido, nombre',_binary '\0','PDF',9),('34c4dbd2-21ea-11f1-8e6b-46e816e51124','Modelo 145 cumplimentado (PDF)','Formato nombre archivo: MOD145 apellido apellido, nombre',_binary '','PDF',10),('34c4dbdc-21ea-11f1-8e6b-46e816e51124','Foto (JPG)','Formato nombre archivo: apellido apellido, nombre. Hasta los hombros en fondo blanco',_binary '','JPG',11),('34c4dbe5-21ea-11f1-8e6b-46e816e51124','Preferencia modalidad de cobro (12/14 pagas)','Elección: 12 pagas/14 pagas',_binary '','SELECT',12),('34c4dbf2-21ea-11f1-8e6b-46e816e51124','Protección de datos firmada (PDF)','Formato nombre archivo: PROTECCIÓN DATOS apellido apellido, nombre',_binary '','PDF',13),('34c4dbfd-21ea-11f1-8e6b-46e816e51124','Uso de imágenes firmada (PDF)','Formato nombre archivo: USO IMAGENES apellido apellido, nombre',_binary '','PDF',14),('34c4dc06-21ea-11f1-8e6b-46e816e51124','Teléfono de emergencia (Nombre - Teléfono - Parentesco)',NULL,_binary '','TEXT',15),('34c4dc10-21ea-11f1-8e6b-46e816e51124','Talla de camiseta (XS, S, M, L, XL, XXL)',NULL,_binary '','SELECT',16),('34c4dc18-21ea-11f1-8e6b-46e816e51124','Tipo de vehículo (Turismo / Motocicleta)',NULL,_binary '','SELECT',17),('34c4dc20-21ea-11f1-8e6b-46e816e51124','Tipo de combustible (Gasolina / Diésel)',NULL,_binary '','SELECT',18),('34c4dc29-21ea-11f1-8e6b-46e816e51124','¿Deseas reconocimiento médico?','Opción: si, acepto / no, renuncio al reconocimiento médico',_binary '','SELECT',19),('c04b21c5-212e-11f1-8314-a6ac6c94ec55','Teléfono de contacto','',_binary '\0','NUMBER',2);
/*!40000 ALTER TABLE `field_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_options`
--

DROP TABLE IF EXISTS `field_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_options` (
  `field_id` uuid NOT NULL,
  `options` varchar(255) DEFAULT NULL,
  `option_order` int(11) NOT NULL,
  KEY `FKkaa65b3gqf9cmdwmtdriq8l9s` (`field_id`),
  CONSTRAINT `FKkaa65b3gqf9cmdwmtdriq8l9s` FOREIGN KEY (`field_id`) REFERENCES `field_definitions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_options`
--

LOCK TABLES `field_options` WRITE;
/*!40000 ALTER TABLE `field_options` DISABLE KEYS */;
INSERT INTO `field_options` VALUES ('34c4dbe5-21ea-11f1-8e6b-46e816e51124','12',0),('34c4dbe5-21ea-11f1-8e6b-46e816e51124','14',1),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XS',0),('34c4dc10-21ea-11f1-8e6b-46e816e51124','S',1),('34c4dc10-21ea-11f1-8e6b-46e816e51124','M',2),('34c4dc10-21ea-11f1-8e6b-46e816e51124','L',3),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XL',4),('34c4dc18-21ea-11f1-8e6b-46e816e51124','Turismo',0),('34c4dc18-21ea-11f1-8e6b-46e816e51124','Motocicleta',1),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XXL',5),('34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí',0),('34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina',0),('34c4dc29-21ea-11f1-8e6b-46e816e51124','No',1),('34c4dc20-21ea-11f1-8e6b-46e816e51124','Diésel',1);
/*!40000 ALTER TABLE `field_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_values`
--

DROP TABLE IF EXISTS `field_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_values` (
  `id` uuid NOT NULL,
  `employeeId` uuid DEFAULT NULL,
  `fieldDefinitionId` uuid DEFAULT NULL,
  `value` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_values`
--

LOCK TABLES `field_values` WRITE;
/*!40000 ALTER TABLE `field_values` DISABLE KEYS */;
INSERT INTO `field_values` VALUES ('7f3a898e-c0ad-4dd8-9bbe-01006afc017b','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc06-21ea-11f1-8e6b-46e816e51124','Lucifer 666666666 Suegra'),('5747ea24-988d-460b-9269-070ae100f3fe','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbd2-21ea-11f1-8e6b-46e816e51124','MOD145 Apellido1 Apellido2 Manuel.pdf'),('7cab19a5-2d80-469a-8836-085543ed69d3','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db04-21ea-11f1-8e6b-46e816e51124','645468468445454'),('b03bc2a3-878c-43c2-b312-09137e7f86ed','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c48959-21ea-11f1-8e6b-46e816e51124','DNI Apellido1 Apellido2 Manuel.pdf'),('2f84115b-7415-46a7-b192-0e5fae8a7ef9','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbdc-21ea-11f1-8e6b-46e816e51124','APELLIDO qweqweqwe qweqweq.jpg'),('cb8e9e07-57b8-412e-ac36-0e66bce7f92b','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbe5-21ea-11f1-8e6b-46e816e51124','12'),('2cf6ceb8-266f-48ff-8314-12ce715f5d31','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbd2-21ea-11f1-8e6b-46e816e51124','MOD145 qweqweqwe qweqweq.pdf'),('a703653a-1ce7-4582-9fe8-14751b270536','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4db04-21ea-11f1-8e6b-46e816e51124','44664864864848'),('a5c9ad3e-8fa2-4a8d-bd07-1926cf4aedf8','413b7e6c-5ece-44cd-affc-6915d189be98','34c4d71f-21ea-11f1-8e6b-46e816e51124','TITULO qweqweqwe qweqweq.pdf'),('2d3fc7ec-d1c1-4dae-bcb9-2048a3def4c2','aa7d31a3-a5d1-4bc7-941d-3907fb009845','0e524bed-b653-4544-b9e8-21c8be48e6e6','Sánchez Soria'),('7741e7dc-0e0e-44b9-b291-23c00b56a951','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dc10-21ea-11f1-8e6b-46e816e51124','M'),('07d97998-44a7-47a2-93b5-25142a345f2d','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbf2-21ea-11f1-8e6b-46e816e51124','PROTECCIÓN DATOS Sánchez Soria Paco.pdf'),('cc4a10f9-7356-4538-abb5-25e89c7a1228','413b7e6c-5ece-44cd-affc-6915d189be98','34c4db44-21ea-11f1-8e6b-46e816e51124','ewrerwerwer'),('e2eed1c4-70cb-410e-9c99-2f88ea7e214b','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbf2-21ea-11f1-8e6b-46e816e51124','PROTECCIÓN DATOS Pasojasoifjasoi Probando.pdf'),('521e91e8-75ce-47fe-a05e-34bffe514e61','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí'),('1f778d4d-1c70-4d6e-9330-3526be56c64e','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbf2-21ea-11f1-8e6b-46e816e51124','PROTECCIÓN DATOS qweqweqwe qweqweq.pdf'),('3aa5ba1f-c0a7-460f-b53d-365d4a5d7c04','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina'),('c1acfc06-8b9f-4988-90de-3cf7fac944fc','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbe5-21ea-11f1-8e6b-46e816e51124','14'),('a3a431e7-7f0a-4bc4-9247-3fe004b3059e','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbc7-21ea-11f1-8e6b-46e816e51124',''),('eba38ca2-1832-4d03-ad19-484a0bfbf260','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dc10-21ea-11f1-8e6b-46e816e51124','XL'),('7fe21166-3f55-49df-b8a3-49bbc35bff42','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db44-21ea-11f1-8e6b-46e816e51124','Civica'),('44e07782-4ac6-4a39-8a61-4b8182234234','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4db44-21ea-11f1-8e6b-46e816e51124','ASdasdaSDSAD'),('d99a0960-0f67-48eb-8e26-50ad4048e39d','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbd2-21ea-11f1-8e6b-46e816e51124','MOD145 Pasojasoifjasoi Probando.pdf'),('089ebdae-8f7c-49a3-90f4-5940cbfaf99d','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbfd-21ea-11f1-8e6b-46e816e51124','USO IMAGENES Sánchez Soria Paco.pdf'),('0bb9a08f-572a-4405-a8f9-6064365e2b0c','413b7e6c-5ece-44cd-affc-6915d189be98','c04b21c5-212e-11f1-8314-a6ac6c94ec55','56151561561'),('b19fc885-1883-452e-ac8b-6229db6c63fc','d2c9964a-515a-4ce3-a53f-b1461a40458a','c04b21c5-212e-11f1-8314-a6ac6c94ec55','666123123'),('b0b9e076-1244-4265-a12b-63edec78936e','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c48959-21ea-11f1-8e6b-46e816e51124','DNI Sánchez Soria Paco.pdf'),('ae1c3441-3faa-4ad6-9cb5-6599507bf1b7','d2c9964a-515a-4ce3-a53f-b1461a40458a','0e524bed-b653-4544-b9e8-21c8be48e6e6','Apellido1 Apellido2'),('c696afc8-028d-4a28-82ee-6bca9da6d7f4','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí'),('b18002e3-4332-47d1-aea2-6cc906266aac','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc18-21ea-11f1-8e6b-46e816e51124','Motocicleta'),('8ec333a3-6e24-4c9d-998c-6cd09c31bafc','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbb7-21ea-11f1-8e6b-46e816e51124',''),('7ce7ea38-75e7-4917-87cb-6e2a2334f4b4','caf86017-2c48-4b3d-9dd9-fab58ad24c99','5400ca90-5376-433d-9977-416786a83cac','Probando'),('0ecb9002-f0ef-460d-8a17-6f594f3d1b0e','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbfd-21ea-11f1-8e6b-46e816e51124','USO IMAGENES Apellido1 Apellido2 Manuel.pdf'),('5d551c31-8fd7-4803-9018-78a344e5628a','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc10-21ea-11f1-8e6b-46e816e51124','L'),('00277296-1492-43b2-a9f7-79a1d352dd95','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbd2-21ea-11f1-8e6b-46e816e51124','MOD145 Sánchez Soria Paco.pdf'),('da8a51d2-7d46-448a-ae55-79e945616df6','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbfd-21ea-11f1-8e6b-46e816e51124','USO IMAGENES Pasojasoifjasoi Probando.pdf'),('7fa18d51-15ee-4c92-b162-7cf999fad015','413b7e6c-5ece-44cd-affc-6915d189be98','0e524bed-b653-4544-b9e8-21c8be48e6e6','qweqweqwe'),('0ffbc3e8-3701-43d2-8134-7d0e55ea2e94','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbb7-21ea-11f1-8e6b-46e816e51124',''),('6c9c3953-5a87-41e6-b4f1-7d8c1cddccf5','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4d71f-21ea-11f1-8e6b-46e816e51124','TITULO Apellido1 Apellido2 Manuel.pdf'),('d66870ed-82c5-448e-bf52-7ea19496bc69','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbb7-21ea-11f1-8e6b-46e816e51124',''),('fd8ee6bb-d596-4108-8991-88013a77366d','d2c9964a-515a-4ce3-a53f-b1461a40458a','5400ca90-5376-433d-9977-416786a83cac','Manuel'),('55e114d6-5fff-4932-85e1-88c7a86dc0bd','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4d71f-21ea-11f1-8e6b-46e816e51124','TITULO Pasojasoifjasoi Probando.pdf'),('93063a7d-b8e0-4365-a14f-8c608c39c53d','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4db04-21ea-11f1-8e6b-46e816e51124','4112123123'),('8148c07b-e012-42c0-8c0d-8c9d5758a549','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbe5-21ea-11f1-8e6b-46e816e51124','14'),('8ccde62e-3b17-4e8f-8ac7-8cb4da591f71','aa7d31a3-a5d1-4bc7-941d-3907fb009845','5400ca90-5376-433d-9977-416786a83cac','Paco'),('e8e90362-0120-4458-81c0-8fd47e3ecafe','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí'),('7d8a45ff-69e4-450f-b7ec-9018cc1622ff','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dc18-21ea-11f1-8e6b-46e816e51124','Motocicleta'),('cc6fbce0-d9d8-4a1d-bc43-937115fc4fbf','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbdc-21ea-11f1-8e6b-46e816e51124','APELLIDO Pasojasoifjasoi Probando.jpg'),('6c65c728-0de9-4a76-adf1-988b6fed207f','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dc18-21ea-11f1-8e6b-46e816e51124','Turismo'),('880214f2-2a5a-475f-b3ab-9ddfc4340903','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina'),('de2197db-dc17-453b-bd72-a17be52decb6','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4db36-21ea-11f1-8e6b-46e816e51124','TITULARIDAD CUENTA Pasojasoifjasoi Probando.pdf'),('abfc2aa1-e7ba-479c-9b5a-a7a2b2d24650','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dc06-21ea-11f1-8e6b-46e816e51124','eewfewrewrwerwer'),('899d022a-2761-4641-ab1b-aabd33074084','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbc7-21ea-11f1-8e6b-46e816e51124',''),('a6468fee-d5e2-4638-bfdf-adc957dce121','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db36-21ea-11f1-8e6b-46e816e51124','TITULARIDAD CUENTA Sánchez Soria Paco.pdf'),('a9533032-64ef-4c31-84b1-b2e711add6ac','413b7e6c-5ece-44cd-affc-6915d189be98','5400ca90-5376-433d-9977-416786a83cac','qweqweq'),('bcba9904-96bc-43f6-87c2-b3b52ce6f330','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4db44-21ea-11f1-8e6b-46e816e51124','Pedregosa 1'),('05e3c311-6143-491c-9914-b4315af3a900','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c48959-21ea-11f1-8e6b-46e816e51124','DNI Pasojasoifjasoi Probando.pdf'),('dea58914-abca-4e5a-a41a-b7938f4c6b10','413b7e6c-5ece-44cd-affc-6915d189be98','34c4db04-21ea-11f1-8e6b-46e816e51124','561515115116161'),('11edb9d8-e1bd-4e74-aa4a-bc2db0073ff2','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbfd-21ea-11f1-8e6b-46e816e51124','USO IMAGENES qweqweqwe qweqweq.pdf'),('0e925ee4-5cb7-4241-922f-bc923213f1c3','413b7e6c-5ece-44cd-affc-6915d189be98','34c4db36-21ea-11f1-8e6b-46e816e51124','TITULARIDAD CUENTA qweqweqwe qweqweq.pdf'),('5500d90b-0eae-438f-a07d-bcbd410def97','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbe5-21ea-11f1-8e6b-46e816e51124','12'),('0aae8196-8bd4-4cff-863c-c01669bb6d1d','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbb7-21ea-11f1-8e6b-46e816e51124',''),('2ac56e80-9901-4755-9870-c1ac40efdf5c','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4db36-21ea-11f1-8e6b-46e816e51124','TITULARIDAD CUENTA Apellido1 Apellido2 Manuel.pdf'),('c04003db-d136-4000-87ec-c1efc18f2fbe','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dc06-21ea-11f1-8e6b-46e816e51124','Lucifer 666666666 Suegra'),('ecb8d208-1cdb-4fde-8d44-c6ec88e5c8c9','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dc18-21ea-11f1-8e6b-46e816e51124','Turismo'),('c15efad4-1651-4d93-be9a-c87070f9f3fe','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina'),('675f9521-12a9-4522-bf7a-c932aaae5d93','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbdc-21ea-11f1-8e6b-46e816e51124','APELLIDO Apellido1 Apellido2 Manuel.jpg'),('966d27e3-640e-4527-879a-d1f93cb63086','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dc06-21ea-11f1-8e6b-46e816e51124','SADas das  masdsadas asdasjidasd'),('f4bed769-57a4-4b54-8d4f-d4f8fc28dda4','413b7e6c-5ece-44cd-affc-6915d189be98','34c48959-21ea-11f1-8e6b-46e816e51124','DNI qweqweqwe qweqweq.pdf'),('4cb1371a-07c4-4a29-9c83-d5c490e32e0d','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí'),('557e6a2b-059c-4298-bfe3-d6a4d2ee9b2d','caf86017-2c48-4b3d-9dd9-fab58ad24c99','0e524bed-b653-4544-b9e8-21c8be48e6e6','Pasojasoifjasoi'),('0e70b52a-2afb-4f38-b474-d8373f6e8b90','d2c9964a-515a-4ce3-a53f-b1461a40458a','34c4dbf2-21ea-11f1-8e6b-46e816e51124','PROTECCIÓN DATOS Apellido1 Apellido2 Manuel.pdf'),('bacd733c-308a-4e46-ad49-dd7d47ee9901','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dc10-21ea-11f1-8e6b-46e816e51124','XL'),('d99954e3-c5cc-446b-b1ba-ddbab1a0c4f9','aa7d31a3-a5d1-4bc7-941d-3907fb009845','c04b21c5-212e-11f1-8314-a6ac6c94ec55','666666666'),('912c25cf-16ab-4f77-a401-e0359e2e4b0a','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbdc-21ea-11f1-8e6b-46e816e51124','APELLIDO Sánchez Soria Paco.jpg'),('60ef1f79-8647-453e-8670-e21c93aa4479','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina'),('ee0f4fc6-1d99-4695-b157-e263f2c7ac33','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4d71f-21ea-11f1-8e6b-46e816e51124','TITULO Sánchez Soria Paco.pdf'),('ea891899-d842-42d3-b2f6-e8372cd53a59','413b7e6c-5ece-44cd-affc-6915d189be98','34c4dbc7-21ea-11f1-8e6b-46e816e51124',''),('6305bc0a-d506-47a8-89d0-e85c5330104f','caf86017-2c48-4b3d-9dd9-fab58ad24c99','c04b21c5-212e-11f1-8314-a6ac6c94ec55','4654565456'),('dc57a076-c31a-4ee3-8c6a-faa8eb940384','caf86017-2c48-4b3d-9dd9-fab58ad24c99','34c4dbc7-21ea-11f1-8e6b-46e816e51124','');
/*!40000 ALTER TABLE `field_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_version_fields`
--

DROP TABLE IF EXISTS `form_version_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_version_fields` (
  `id` uuid NOT NULL,
  `version_id` uuid NOT NULL,
  `field_id` uuid NOT NULL,
  `sort_order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_version_fields_version` (`version_id`),
  KEY `fk_version_fields_field` (`field_id`),
  CONSTRAINT `fk_version_fields_field` FOREIGN KEY (`field_id`) REFERENCES `field_definitions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_version_fields_version` FOREIGN KEY (`version_id`) REFERENCES `form_versions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_version_fields`
--

LOCK TABLES `form_version_fields` WRITE;
/*!40000 ALTER TABLE `form_version_fields` DISABLE KEYS */;
INSERT INTO `form_version_fields` VALUES ('016426f6-cf37-4685-a17e-0af109616c27','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dc06-21ea-11f1-8e6b-46e816e51124',15),('8203c35f-d567-4407-9ff8-110e93471369','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbdc-21ea-11f1-8e6b-46e816e51124',11),('08cd73ea-934a-4168-bf7c-218d7f65a991','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4db04-21ea-11f1-8e6b-46e816e51124',5),('09118dde-5d1a-4a63-9041-3560f995d42c','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbd2-21ea-11f1-8e6b-46e816e51124',10),('1602342f-bec1-4089-820f-3fa9ad1b158e','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbfd-21ea-11f1-8e6b-46e816e51124',14),('296c7f24-a099-46bf-80d3-41d9807fe068','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dc18-21ea-11f1-8e6b-46e816e51124',17),('10958b9e-5d58-4bfd-b384-4429f3763410','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4db44-21ea-11f1-8e6b-46e816e51124',7),('e66cb838-ca1f-414e-b131-48573ef0ff5f','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbe5-21ea-11f1-8e6b-46e816e51124',12),('dee77401-2d10-4741-a3dc-4f1dc5c70a8e','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4db36-21ea-11f1-8e6b-46e816e51124',6),('58670c85-54b9-435c-ac2f-53975af0162b','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbc7-21ea-11f1-8e6b-46e816e51124',9),('3ff2494e-1019-46fa-9e71-697d9dc67400','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dc20-21ea-11f1-8e6b-46e816e51124',18),('0416ad0c-1047-4582-878f-6c90fbf777ec','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4d71f-21ea-11f1-8e6b-46e816e51124',4),('741caa20-0220-4508-865c-72d6b682c098','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbb7-21ea-11f1-8e6b-46e816e51124',8),('b6ebfe4f-be6a-4742-b18e-83ad7c1ccf5a','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dc10-21ea-11f1-8e6b-46e816e51124',16),('31209b6b-75ef-40ba-81ca-85811ff7aab2','5ea7a27a-72a9-4518-8827-ecffde2546ae','0e524bed-b653-4544-b9e8-21c8be48e6e6',1),('01f20e6d-9db0-4a5e-85a3-90178258ab3f','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dc29-21ea-11f1-8e6b-46e816e51124',19),('cfaa3457-eab2-4b43-82fe-b089d3bef6ff','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c48959-21ea-11f1-8e6b-46e816e51124',3),('71677eab-4423-4b96-bd50-cae92dafcc9d','5ea7a27a-72a9-4518-8827-ecffde2546ae','5400ca90-5376-433d-9977-416786a83cac',0),('ff8406b5-471f-4bb5-9dae-d311e71218f0','5ea7a27a-72a9-4518-8827-ecffde2546ae','34c4dbf2-21ea-11f1-8e6b-46e816e51124',13),('617d9b28-e38a-41a4-8c2c-d5ad5f1c82a9','5ea7a27a-72a9-4518-8827-ecffde2546ae','c04b21c5-212e-11f1-8314-a6ac6c94ec55',2);
/*!40000 ALTER TABLE `form_version_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_versions`
--

DROP TABLE IF EXISTS `form_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_versions` (
  `id` uuid NOT NULL,
  `version_number` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_versions`
--

LOCK TABLES `form_versions` WRITE;
/*!40000 ALTER TABLE `form_versions` DISABLE KEYS */;
INSERT INTO `form_versions` VALUES ('5ea7a27a-72a9-4518-8827-ecffde2546ae',1,'2026-03-23 10:56:20','admin','v1',0);
/*!40000 ALTER TABLE `form_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submissions` (
  `id` varchar(36) NOT NULL,
  `candidate_id` varchar(36) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires_at` datetime DEFAULT NULL,
  `submitted_at` datetime DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `candidate_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employee_id` varchar(36) DEFAULT NULL,
  `version_id` uuid DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_submission_candidate` (`candidate_id`),
  KEY `fk_submission_version` (`version_id`),
  CONSTRAINT `fk_submission_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidates` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_submission_version` FOREIGN KEY (`version_id`) REFERENCES `form_versions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submissions`
--

LOCK TABLES `submissions` WRITE;
/*!40000 ALTER TABLE `submissions` DISABLE KEYS */;
INSERT INTO `submissions` VALUES ('02392aa9-9421-44a0-943f-1a70cabe1a1c','d2c9964a-515a-4ce3-a53f-b1461a40458a','dcf6655f-c4b2-4e88-9f83-80e6341c36cc','2026-03-29 11:07:09','2026-03-27 11:07:09','APPROVED',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `submissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'rrhh'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-27 11:17:51
