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
    status ENUM('AVAILABLE', 'BORROWED', 'RESERVED')
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `books` ADD CONSTRAINT `books_fk1` FOREIGN KEY (bd_id) REFERENCES `book_details` (id);

CREATE TABLE `tasks` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	status ENUM('DONE', 'UNDONE', 'PROCESSING'),
    description NVARCHAR(512),
    deadline DATE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `librarians` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    birth_date DATE,
    sex ENUM('UNKNOWN', 'OTHER', 'WOMEN', 'MEN')
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

