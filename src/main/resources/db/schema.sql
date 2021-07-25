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
    title VARCHAR(256) NOT NULL,
    genre VARCHAR(64) NOT NULL,
    publisher VARCHAR(128) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `books` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bd_id INT NOT NULL,
    status ENUM('AVAILABLE', 'BORROWED', 'RESERVED') NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `books` ADD CONSTRAINT `books_fk1` FOREIGN KEY (bd_id) REFERENCES `book_details` (id);

CREATE TABLE `librarians` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    birth_date DATE NOT NULL,
    sex ENUM('UNKNOWN', 'OTHER', 'WOMEN', 'MEN'),
    telephone VARCHAR(9) NOT NULL,
    email NVARCHAR(128) NOT NULL UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `librarians` ADD CONSTRAINT `librarian_fk1` FOREIGN KEY (email) REFERENCES `users` (username);

CREATE TABLE `tasks` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    librarian_id INT NOT NULL,
	status ENUM('DONE', 'UNDONE', 'PROCESSING'),
    description NVARCHAR(512),
    deadline DATE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `tasks` ADD CONSTRAINT `tasks_fk1` FOREIGN KEY (librarian_id) REFERENCES `librarians` (id);

CREATE TABLE `readers` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    birth_date DATE NOT NULL,
    sex ENUM('UNKNOWN', 'OTHER', 'WOMEN', 'MEN'),
    telephone VARCHAR(9) NOT NULL,
    email NVARCHAR(128) NOT NULL UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `readers` ADD CONSTRAINT `readers_fk1` FOREIGN KEY (email) REFERENCES `users` (username);

CREATE TABLE `reservations` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    reader_id INT NOT NULL,
    to_date DATE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `reservations` ADD CONSTRAINT `reservation_fk1` FOREIGN KEY (book_id) REFERENCES `books` (id);
ALTER TABLE `reservations` ADD CONSTRAINT `reservation_fk2` FOREIGN KEY (reader_id) REFERENCES `readers` (id);

CREATE TABLE `rentals` (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	book_id INT NOT NULL,
    reader_id INT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    return_date DATE,
    fee DECIMAL(10,2)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `rentals` ADD CONSTRAINT `rentals_fk1` FOREIGN KEY (book_id) REFERENCES `books` (id);
ALTER TABLE `rentals` ADD CONSTRAINT `rentals_fk2` FOREIGN KEY (reader_id) REFERENCES `readers` (id);