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
-- Table structure for table `access_tokens`
--

DROP TABLE IF EXISTS `access_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_tokens` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expires_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `used` bit(1) NOT NULL,
  `submission_id` uuid NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2nbkblqhr9bb6y8u3870rk4hv` (`token`),
  KEY `FKmn0l3kr8lhjy994d67irj0hgn` (`submission_id`),
  CONSTRAINT `FKmn0l3kr8lhjy994d67irj0hgn` FOREIGN KEY (`submission_id`) REFERENCES `submissions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_tokens`
--

LOCK TABLES `access_tokens` WRITE;
/*!40000 ALTER TABLE `access_tokens` DISABLE KEYS */;
INSERT INTO `access_tokens` VALUES (38,'2026-04-15 09:25:03.970657','e4465850-4c49-4a63-a43e-1536626a94ec',_binary '\0','6965af77-556a-42f5-bf96-8ac59d4ff2c2');
/*!40000 ALTER TABLE `access_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidates`
--

DROP TABLE IF EXISTS `candidates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidates` (
  `id` uuid NOT NULL,
  `candidate_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidates`
--

LOCK TABLES `candidates` WRITE;
/*!40000 ALTER TABLE `candidates` DISABLE KEYS */;
INSERT INTO `candidates` VALUES ('6b9996d4-edf8-4606-b8da-ada5d7107f67','prueba','prueba@probandotodoeldia.com');
/*!40000 ALTER TABLE `candidates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','EMPLOYEE') NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3gqbimdf7fckjbwt1kcud141m` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('367fc0b7-867d-49f6-ab06-64ae4117a930',_binary '','$2a$10$Dx914EPRl6PR/LXOh5npz.xijNKvuqqTYZDcSeia/QXhTW0Vq.Xfy','EMPLOYEE','RRHH'),('88888888-4444-4444-4444-121212121212',_binary '','$2a$10$4PiBDDpxFCJvghmERegd/.rEI2qMHFFDSJSc3ZncVf9.cb4WLXv.2','ADMIN','admin');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
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
  `file_naming_prefix` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_definitions`
--

LOCK TABLES `field_definitions` WRITE;
/*!40000 ALTER TABLE `field_definitions` DISABLE KEYS */;
INSERT INTO `field_definitions` VALUES ('18d7c41d-ecdf-46e4-9f01-04536c237c53','Preferencia en la modalidad de cobro de nómina',NULL,_binary '','SELECT',11,NULL,_binary ''),('d3c97628-ced1-443a-9f31-0c003ada813a','Titulación Académica','TITULO apellido apellido, nombre',_binary '','PDF',3,NULL,_binary ''),('ffa030f9-2393-4e5a-ad1c-0f6ab41df2ab','Foto','FOTO apellido apellido, nombre',_binary '','JPG',10,NULL,_binary ''),('c3757405-a658-44d7-84ef-1ae90e0711bf','Nombre',NULL,_binary '','TEXT',1,NULL,_binary ''),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','Talla Camiseta',NULL,_binary '','SELECT',15,NULL,_binary ''),('ddf89894-9294-4c3b-8b8f-2132e72f9864','Apellidos',NULL,_binary '','TEXT',1,NULL,_binary ''),('259d9873-0116-490f-9475-2cdbd0c3d7e0','Dirección de Residencia Actual',NULL,_binary '','TEXT',6,NULL,_binary ''),('0b144fb4-67d8-48da-b9f1-2d73f459bf18','Teléfono de emergencia',NULL,_binary '','NUMBER',14,NULL,_binary ''),('28ab0aa3-0424-4543-8322-35fb2c955901','Número de Seguridad Social',NULL,_binary '','NUMBER',4,NULL,_binary ''),('95b41381-af9a-44bb-aed9-3c8c5143f386','Justificante de registro de alta en Garantía Juvenil (en caso de que aplique)','GARANTIA apellido apellido, nombre',_binary '\0','PDF',8,NULL,_binary ''),('d4acadd5-7c39-4104-8ebf-478090d0f94e','Justificante IBAN Bancario','TITULARIDAD CUENTA apellido apellido, nombre',_binary '','PDF',5,NULL,_binary ''),('9cb96332-3acb-4bfd-910a-5da99d596f0b','Formato 145 adjunto debidamente cumplimentado y firmado','MOD145 apellido apellido, nombre',_binary '','PDF',9,NULL,_binary ''),('4018672a-01cc-431e-a250-7bd27e8865df','Tipo de Vehiculo',NULL,_binary '','SELECT',16,NULL,_binary ''),('a7a7095e-41d5-442b-9de4-7d665e132d54','Alta como demandante de empleo (en caso de que aplique)','DEMANDANTE apellido apellido, nombre ',_binary '\0','PDF',7,NULL,_binary ''),('0c6fcbc5-ffc7-49f6-b066-9499d0eff7a9','Cláusula de protección de datos','PROTECCIÓN DATOS apellido apellido, nombre',_binary '','PDF',12,NULL,_binary ''),('65ebe480-64cf-40b1-994c-b7041b796f02','Cláusulas de uso de imágenes','USO IMAGENES apellido apellido, nombre',_binary '','PDF',13,NULL,_binary ''),('0be54e8a-72a9-46f0-9542-dba9fed9a624','¿Quieres que organicemos reconocimiento médico inicial?',NULL,_binary '','SELECT',18,NULL,_binary ''),('ece0e377-23c6-4951-a368-e5016f56fa85','DNI','DNI apellido apellido, nombre',_binary '','PDF',2,NULL,_binary ''),('09bbc513-c2ef-4531-9708-f674c174c2ba','Tipo de Combustible',NULL,_binary '','SELECT',17,NULL,_binary '');
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
INSERT INTO `field_options` VALUES ('18d7c41d-ecdf-46e4-9f01-04536c237c53','12',0),('18d7c41d-ecdf-46e4-9f01-04536c237c53','14',1),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','XS',0),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','S',1),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','X',2),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','XL',3),('a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e','XXL',4),('4018672a-01cc-431e-a250-7bd27e8865df','Turismo',0),('4018672a-01cc-431e-a250-7bd27e8865df','Motocicleta',1),('4018672a-01cc-431e-a250-7bd27e8865df','Furgoneta',2),('09bbc513-c2ef-4531-9708-f674c174c2ba','Gasolina',0),('09bbc513-c2ef-4531-9708-f674c174c2ba','Diésel',1),('09bbc513-c2ef-4531-9708-f674c174c2ba','GLP',2),('09bbc513-c2ef-4531-9708-f674c174c2ba','Eléctrico',3),('09bbc513-c2ef-4531-9708-f674c174c2ba','Híbrido',4),('0be54e8a-72a9-46f0-9542-dba9fed9a624','Sí',0),('0be54e8a-72a9-46f0-9542-dba9fed9a624','No',1);
/*!40000 ALTER TABLE `field_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_values`
--

DROP TABLE IF EXISTS `field_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_values` (
  `employee_id` uuid DEFAULT NULL,
  `id` varchar(36) NOT NULL,
  `field_definition_id` uuid NOT NULL,
  `file_resource_id` uuid DEFAULT NULL,
  `submission_id` uuid NOT NULL,
  `value` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_values`
--

LOCK TABLES `field_values` WRITE;
/*!40000 ALTER TABLE `field_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `field_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_resources`
--

DROP TABLE IF EXISTS `file_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_resources` (
  `id` varchar(36) NOT NULL,
  `path` varchar(255) NOT NULL,
  `original_name` varchar(255) NOT NULL,
  `mime_type` varchar(255) NOT NULL,
  `size` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_resources`
--

LOCK TABLES `file_resources` WRITE;
/*!40000 ALTER TABLE `file_resources` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_resources` ENABLE KEYS */;
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
INSERT INTO `form_version_fields` VALUES ('490c0765-b6e2-4a99-86be-02f0bc4c85f0','e6842cc3-8b52-41ad-9153-141255e40ee6','65ebe480-64cf-40b1-994c-b7041b796f02',13),('d0b5d188-0427-4f06-9a9b-0f4c019defa0','e6842cc3-8b52-41ad-9153-141255e40ee6','18d7c41d-ecdf-46e4-9f01-04536c237c53',11),('c67e9635-8279-4e27-b6d2-1ba4b482747f','e6842cc3-8b52-41ad-9153-141255e40ee6','09bbc513-c2ef-4531-9708-f674c174c2ba',17),('f59a2291-ef6e-4294-b531-1d65cc801df9','e6842cc3-8b52-41ad-9153-141255e40ee6','259d9873-0116-490f-9475-2cdbd0c3d7e0',6),('15066e45-5566-4084-b461-2e09cb987aea','e6842cc3-8b52-41ad-9153-141255e40ee6','ffa030f9-2393-4e5a-ad1c-0f6ab41df2ab',10),('54f777bb-0922-4ce8-a73e-4aeef3c4920d','e6842cc3-8b52-41ad-9153-141255e40ee6','ece0e377-23c6-4951-a368-e5016f56fa85',2),('5b7b6f1a-3593-4faf-bf23-5698f43f0012','e6842cc3-8b52-41ad-9153-141255e40ee6','c3757405-a658-44d7-84ef-1ae90e0711bf',0),('9e2560e1-4992-4747-802d-64b9c7f7fe42','e6842cc3-8b52-41ad-9153-141255e40ee6','0be54e8a-72a9-46f0-9542-dba9fed9a624',18),('c39f4c33-0bcf-44a8-bd88-7fe8a54f29d5','e6842cc3-8b52-41ad-9153-141255e40ee6','d3c97628-ced1-443a-9f31-0c003ada813a',3),('da51957e-d71f-4f7b-85c4-88792ed6e942','e6842cc3-8b52-41ad-9153-141255e40ee6','a7a40eed-63e0-41fa-8fdd-1f74fe8a6d9e',15),('2d5d0c6d-fe4e-46eb-b8e3-a03e050c8eff','e6842cc3-8b52-41ad-9153-141255e40ee6','ddf89894-9294-4c3b-8b8f-2132e72f9864',1),('de22eb2a-76a7-4e9e-9f56-a5e72e59fe56','e6842cc3-8b52-41ad-9153-141255e40ee6','4018672a-01cc-431e-a250-7bd27e8865df',16),('f41bb29a-b313-4144-b3cd-b192a19a0fc3','e6842cc3-8b52-41ad-9153-141255e40ee6','28ab0aa3-0424-4543-8322-35fb2c955901',4),('3343d861-5143-4436-b327-b22ddae3a2e3','e6842cc3-8b52-41ad-9153-141255e40ee6','a7a7095e-41d5-442b-9de4-7d665e132d54',7),('62bea78d-3f7c-4174-8a0f-c486eda0e97e','e6842cc3-8b52-41ad-9153-141255e40ee6','d4acadd5-7c39-4104-8ebf-478090d0f94e',5),('adee7bb4-a45b-43a1-b6f0-c89a8cd341ce','e6842cc3-8b52-41ad-9153-141255e40ee6','95b41381-af9a-44bb-aed9-3c8c5143f386',8),('2542311c-7cfd-4fba-8c53-d8040fcc7723','e6842cc3-8b52-41ad-9153-141255e40ee6','9cb96332-3acb-4bfd-910a-5da99d596f0b',9),('70bc0d20-d94d-48ce-8ab9-df9c95b3a904','e6842cc3-8b52-41ad-9153-141255e40ee6','0b144fb4-67d8-48da-b9f1-2d73f459bf18',14),('ec9d4e7e-091c-40b5-9cdd-e70c2001f3cd','e6842cc3-8b52-41ad-9153-141255e40ee6','0c6fcbc5-ffc7-49f6-b066-9499d0eff7a9',12);
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
INSERT INTO `form_versions` VALUES ('e6842cc3-8b52-41ad-9153-141255e40ee6',1,'2026-04-14 14:05:11','RRHH','V1',1);
/*!40000 ALTER TABLE `form_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submission_status_history`
--

DROP TABLE IF EXISTS `submission_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submission_status_history` (
  `id` varchar(36) NOT NULL,
  `changed_at` datetime(6) NOT NULL,
  `changed_by` varchar(255) NOT NULL,
  `status` enum('APPROVED','COMPLETED','PENDING_INVITE','PENDING_REVIEW','REJECTED','SUBMITTED') NOT NULL,
  `submission_id` uuid NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKptsv4wdbpf2i4n8u2o4sfekcb` (`submission_id`),
  CONSTRAINT `FKptsv4wdbpf2i4n8u2o4sfekcb` FOREIGN KEY (`submission_id`) REFERENCES `submissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission_status_history`
--

LOCK TABLES `submission_status_history` WRITE;
/*!40000 ALTER TABLE `submission_status_history` DISABLE KEYS */;
INSERT INTO `submission_status_history` VALUES ('ac28476f-f6e9-49b7-85f8-f963a9cf20e7','2026-04-10 14:22:52.955958','88888888-4444-4444-4444-121212121212','PENDING_INVITE','6965af77-556a-42f5-bf96-8ac59d4ff2c2');
/*!40000 ALTER TABLE `submission_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submissions` (
  `id` uuid NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `employee_id` uuid DEFAULT NULL,
  `status` enum('APPROVED','COMPLETED','PENDING_INVITE','PENDING_REVIEW','REJECTED','SUBMITTED') DEFAULT NULL,
  `submitted_at` datetime(6) DEFAULT NULL,
  `version_id` uuid DEFAULT NULL,
  `candidate_id` uuid DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5ogfr0wjgc1vcj7bvsh83tffu` (`candidate_id`),
  CONSTRAINT `FK5ogfr0wjgc1vcj7bvsh83tffu` FOREIGN KEY (`candidate_id`) REFERENCES `candidates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submissions`
--

LOCK TABLES `submissions` WRITE;
/*!40000 ALTER TABLE `submissions` DISABLE KEYS */;
INSERT INTO `submissions` VALUES ('6965af77-556a-42f5-bf96-8ac59d4ff2c2','2026-04-10 14:22:52.729315','88888888-4444-4444-4444-121212121212','PENDING_INVITE',NULL,'2d9c3f28-9ac1-43a6-8c0a-5172bc4abc62','6b9996d4-edf8-4606-b8da-ada5d7107f67');
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

-- Dump completed on 2026-04-14 14:09:36
