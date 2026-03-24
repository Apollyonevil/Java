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
INSERT INTO `candidates` VALUES ('81cce86e-8247-4227-b961-c3be06005dbf','Prueba2','prueba2@civica.com','81cce86e-8247-4227-b961-c3be06005dbf'),('aa7d31a3-a5d1-4bc7-941d-3907fb009845','probando','prueba@civica.com','aa7d31a3-a5d1-4bc7-941d-3907fb009845'),('caf86017-2c48-4b3d-9dd9-fab58ad24c99','Probando','probando@civica.com','caf86017-2c48-4b3d-9dd9-fab58ad24c99');
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
INSERT INTO `field_options` VALUES ('34c4dbe5-21ea-11f1-8e6b-46e816e51124','12',0),('34c4dbe5-21ea-11f1-8e6b-46e816e51124','14',1),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XS',0),('34c4dc10-21ea-11f1-8e6b-46e816e51124','S',1),('34c4dc10-21ea-11f1-8e6b-46e816e51124','M',2),('34c4dc10-21ea-11f1-8e6b-46e816e51124','L',3),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XL',4),('34c4dc10-21ea-11f1-8e6b-46e816e51124','XXL',5),('34c4dc18-21ea-11f1-8e6b-46e816e51124','Turismo',0),('34c4dc18-21ea-11f1-8e6b-46e816e51124','Motocicleta',1),('34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina',0),('34c4dc20-21ea-11f1-8e6b-46e816e51124','Diésel',1),('34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí',0),('34c4dc29-21ea-11f1-8e6b-46e816e51124','No',1);
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
INSERT INTO `field_values` VALUES ('7f3a898e-c0ad-4dd8-9bbe-01006afc017b','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc06-21ea-11f1-8e6b-46e816e51124','Lucifer 666666666 Suegra'),('7cab19a5-2d80-469a-8836-085543ed69d3','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db04-21ea-11f1-8e6b-46e816e51124','645468468445454'),('2d3fc7ec-d1c1-4dae-bcb9-2048a3def4c2','aa7d31a3-a5d1-4bc7-941d-3907fb009845','0e524bed-b653-4544-b9e8-21c8be48e6e6','Sánchez Soria'),('07d97998-44a7-47a2-93b5-25142a345f2d','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbf2-21ea-11f1-8e6b-46e816e51124','PROTECCIÓN DATOS Sánchez Soria Paco.pdf'),('c1acfc06-8b9f-4988-90de-3cf7fac944fc','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbe5-21ea-11f1-8e6b-46e816e51124','14'),('a3a431e7-7f0a-4bc4-9247-3fe004b3059e','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbc7-21ea-11f1-8e6b-46e816e51124',''),('7fe21166-3f55-49df-b8a3-49bbc35bff42','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db44-21ea-11f1-8e6b-46e816e51124','Civica'),('089ebdae-8f7c-49a3-90f4-5940cbfaf99d','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbfd-21ea-11f1-8e6b-46e816e51124','USO IMAGENES Sánchez Soria Paco.pdf'),('b0b9e076-1244-4265-a12b-63edec78936e','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c48959-21ea-11f1-8e6b-46e816e51124','DNI Sánchez Soria Paco.pdf'),('c696afc8-028d-4a28-82ee-6bca9da6d7f4','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc29-21ea-11f1-8e6b-46e816e51124','Sí'),('b18002e3-4332-47d1-aea2-6cc906266aac','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc18-21ea-11f1-8e6b-46e816e51124','Motocicleta'),('5d551c31-8fd7-4803-9018-78a344e5628a','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc10-21ea-11f1-8e6b-46e816e51124','L'),('00277296-1492-43b2-a9f7-79a1d352dd95','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbd2-21ea-11f1-8e6b-46e816e51124','MOD145 Sánchez Soria Paco.pdf'),('0ffbc3e8-3701-43d2-8134-7d0e55ea2e94','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbb7-21ea-11f1-8e6b-46e816e51124',''),('8ccde62e-3b17-4e8f-8ac7-8cb4da591f71','aa7d31a3-a5d1-4bc7-941d-3907fb009845','5400ca90-5376-433d-9977-416786a83cac','Paco'),('a6468fee-d5e2-4638-bfdf-adc957dce121','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4db36-21ea-11f1-8e6b-46e816e51124','TITULARIDAD CUENTA Sánchez Soria Paco.pdf'),('d99954e3-c5cc-446b-b1ba-ddbab1a0c4f9','aa7d31a3-a5d1-4bc7-941d-3907fb009845','c04b21c5-212e-11f1-8314-a6ac6c94ec55','666666666'),('912c25cf-16ab-4f77-a401-e0359e2e4b0a','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dbdc-21ea-11f1-8e6b-46e816e51124','APELLIDO Sánchez Soria Paco.jpg'),('60ef1f79-8647-453e-8670-e21c93aa4479','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4dc20-21ea-11f1-8e6b-46e816e51124','Gasolina'),('ee0f4fc6-1d99-4695-b157-e263f2c7ac33','aa7d31a3-a5d1-4bc7-941d-3907fb009845','34c4d71f-21ea-11f1-8e6b-46e816e51124','TITULO Sánchez Soria Paco.pdf');
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
INSERT INTO `form_versions` VALUES ('5ea7a27a-72a9-4518-8827-ecffde2546ae',1,'2026-03-23 10:56:20','admin','v1',1);
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
INSERT INTO `submissions` VALUES ('0348b506-4460-47fd-bb4d-e9dfc00437f7','caf86017-2c48-4b3d-9dd9-fab58ad24c99','0a0f126b-5c46-4738-bd9b-6f0b3e8d4532','2026-03-26 10:20:52','2026-03-24 10:20:52','PENDING_INVITE',NULL,NULL,NULL,NULL);
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

-- Dump completed on 2026-03-24 11:01:22
