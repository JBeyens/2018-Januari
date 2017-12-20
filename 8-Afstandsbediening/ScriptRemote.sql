create database DbRemote;
use DbRemote;

CREATE USER userDB IDENTIFIED BY 'userDB';

grant usage on *.* to userDB@localhost identified by 'userDB';
grant all privileges on feedback.* to userDB@localhost;

CREATE TABLE Address(
id INT(5) PRIMARY KEY auto_increment,
street VARCHAR(50) NOT NULL,
nr int (10) NOT NULL,
mailBox int(10) NOT NULL,
postalCode INT(10) NOT NULL,
city VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
CONSTRAINT UC_Address UNIQUE (street, nr, mailBox, postalCode, city, country)
);

CREATE TABLE Remote (
id INT(5) PRIMARY KEY auto_increment,
serialNumber VARCHAR(50) NOT NULL,
frequency LONG NOT NULL,
isActive BIT NOT NULL,
CONSTRAINT UC_Remote UNIQUE (serialNumber)
);

CREATE TABLE Person (
id INT(6)  PRIMARY KEY auto_increment,
firstName VARCHAR(30) NOT NULL,
lastName VARCHAR(30) NOT NULL,
addressId INT(5) NOT NULL,
remoteId int(5) NOT NULL,
endOfContract DATE NOT NULL,
FOREIGN KEY fk_a(addressId) REFERENCES Address(id) ON UPDATE no action ON DELETE no action,
FOREIGN KEY fk_r(remoteId) REFERENCES Remote(id) ON UPDATE no action ON DELETE no action
);

select * from Remote;
select * from Address;
select * from Person;
delete from Person;
delete from Remote;
delete from Address;


drop table Person;
drop table Remote;







