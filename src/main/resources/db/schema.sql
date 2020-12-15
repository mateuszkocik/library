DROP DATABASE IF EXISTS `LIBRARY`;
CREATE DATABASE `LIBRARY`;
USE `LIBRARY`;


CREATE TABLE `users` (
    username NVARCHAR(128) PRIMARY KEY,
    password NVARCHAR(128) NOT NULL
);


CREATE TABLE `authorities` (
	username NVARCHAR(128) NOT NULL,
    authority NVARCHAR(128) NOT NULL
);


ALTER TABLE `authorities` ADD CONSTRAINT `authorities_unique` UNIQUE (username, authority);
ALTER TABLE `authorities` ADD CONSTRAINT `authorities_fk1` FOREIGN KEY (username) REFERENCES `users` (username);


CREATE TABLE `book_details` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256),
    genre VARCHAR(64),
    publisher VARCHAR(128)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `books` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bd_id INT NOT NULL,
    available ENUM('Y','N')
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `books` ADD CONSTRAINT `books_fk1` FOREIGN KEY (bd_id) REFERENCES `book_details` (id)