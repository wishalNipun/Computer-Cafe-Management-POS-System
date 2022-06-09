Drop DATABASE IF EXISTS computerCafeManagementSystem;
CREATE DATABASE IF NOT EXISTS computerCafeManagementSystem;
USE computerCafeManagementSystem;

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User`(
    id VARCHAR(6) NOT NULL,
    name VARCHAR (30)NOT NULL,
    role VARCHAR (10)NOT NULL,
    telNo VARCHAR (13),
    email VARCHAR (30),
    userName VARCHAR (20)NOT NULL,
    password VARCHAR (20)NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
    );

INSERT INTO `User` values('U-001','wishal Nipun','Owner','+94701234567','wishal@gmail.com','wisha','1234');
INSERT INTO `User` values('U-002','Nipun Siriwardana','Staff','+94703456789','nipun@gmail.com','nip','1234');


DROP TABLE IF EXISTS Member;
CREATE TABLE IF NOT EXISTS Member(
    id VARCHAR(6),
    name VARCHAR (30)NOT NULL,
    telNo VARCHAR (13),
    address VARCHAR (30),
    CONSTRAINT PRIMARY KEY(id)
    );


DROP TABLE IF EXISTS PackagePlan;
CREATE TABLE IF NOT EXISTS PackagePlan(
    id VARCHAR(6)  NOT NULL,
    name VARCHAR (30) NOT NULL,
    price DOUBLE  NOT NULL,
    CONSTRAINT PRIMARY KEY(id)
    );

DROP TABLE IF EXISTS Computer;
CREATE TABLE IF NOT EXISTS Computer(
    id VARCHAR(6)  NOT NULL,
    type VARCHAR (30) NOT NULL,
    price DOUBLE  NOT NULL,
    CONSTRAINT PRIMARY KEY(id)
    );


DROP TABLE IF EXISTS Supplier;
CREATE TABLE IF NOT EXISTS Supplier(
    id VARCHAR(6) NOT NULL,
    name VARCHAR (30)NOT NULL,
    telNo VARCHAR (13),
    address VARCHAR (30),
    CONSTRAINT PRIMARY KEY(id)
    );

DESC `User`;
SELECT * FROM `User`;
DESC  Member;
SELECT * FROM Member;
DESC  PackagePlan;
SELECT * FROM PackagePlan;
DESC Computer;
SELECT * FROM Computer;
DESC Supplier;
SELECT * FROM Supplier;


DROP TABLE IF EXISTS SupplierComputerDetail;
CREATE TABLE IF NOT EXISTS SupplierComputerDetail(
    sId VARCHAR(6) NOT NULL,
    cId VARCHAR(6) NOT NULL,
    supplydate Date,
    CONSTRAINT PRIMARY KEY(cId,sId),
    FOREIGN KEY (sId) REFERENCES Supplier (id)ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cId) REFERENCES Computer (id)ON DELETE CASCADE ON UPDATE CASCADE
    );

DESC SupplierComputerDetail;
SELECT * FROM SupplierComputerDetail;

DROP TABLE IF EXISTS PackageComputerDetail;
CREATE TABLE IF NOT EXISTS PackageComputerDetail(
    pId VARCHAR(6) NOT NULL,
    cId VARCHAR(6) NOT NULL,
    CONSTRAINT PRIMARY KEY(pId,cId),
    FOREIGN KEY (pId) REFERENCES PackagePlan (id)ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cId) REFERENCES Computer (id)ON DELETE CASCADE ON UPDATE CASCADE
    );

DESC PackageComputerDetail;
SELECT * FROM PackageComputerDetail;


DROP TABLE IF EXISTS MemberPackagePaymentDetail;
CREATE TABLE IF NOT EXISTS MemberPackagePaymentDetail(
    payId VARCHAR(6) NOT NULL,
    memId VARCHAR(6) NOT NULL,
    pId VARCHAR(6) NOT NULL,
    cost DOUBLE,
    payDate Date,
    payTime Time,
    CONSTRAINT PRIMARY KEY(payId),
    FOREIGN KEY (memId) REFERENCES Member (id)ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (pId) REFERENCES PackagePlan (id)ON DELETE CASCADE ON UPDATE CASCADE
    );

DESC MemberPackagePaymentDetail;
