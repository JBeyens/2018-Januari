drop table if exists Address;
drop table if exists Person;
drop table if exists Remote;
create database if not exists DbRemote /*!40100 DEFAULT CHARACTER SET latin1 */;
use DbRemote;

-- Host: 127.0.0.1    Database: DbRemote
-- ------------------------------------------------------
-- Server version	5.6.21-enterprise-commercial-advanced

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

CREATE USER if not exists 'userDB' IDENTIFIED BY 'userDB';
GRANT USAGE on *.* to 'userDB' identified by 'userDB';
grant all privileges on feedback.* to userDB@localhost;

--
-- Table structure for table `Address`
--
CREATE TABLE Address(
id INT(6) PRIMARY KEY auto_increment,
street VARCHAR(50) NOT NULL,
nr int (10) NOT NULL,
mailBox int(10) NOT NULL,
postalCode INT(10) NOT NULL,
city VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
CONSTRAINT UC_Address UNIQUE (street, nr, mailBox, postalCode, city, country)
);

--
-- Table structure for table `Remote`
--
CREATE TABLE Remote (
id INT(6) PRIMARY KEY auto_increment,
serialNumber VARCHAR(50) NOT NULL,
frequency LONG NULL,
isActive BIT NOT NULL,
CONSTRAINT UC_Remote UNIQUE (serialNumber));

--
-- Table structure for table `Person`
--
CREATE TABLE Person (
id INT(6)  PRIMARY KEY auto_increment,
firstName VARCHAR(30) NOT NULL,
lastName VARCHAR(30) NOT NULL,
addressId INT(6) NULL,
remoteId INT(6) NULL,
endOfContract DATE NOT NULL,
FOREIGN KEY fk_a(addressId) REFERENCES Address(id) ON UPDATE no action ON DELETE no action,
FOREIGN KEY fk_r(remoteId) REFERENCES Remote(id) ON UPDATE no action ON DELETE no action
);