USE `LIBRARY`;

INSERT INTO `users` VALUES('test123@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'); -- test123@gmail.com test123

INSERT INTO `authorities` VALUES('test123@gmail.com', 'ROLE_USER');

INSERT INTO `book_details` VALUES(1, 'Romeo and Juliet', '	Shakespearean tragedy', 'Penguin Random House');

INSERT INTO `books` VALUES(1, 1, 'AVAILABLE');

INSERT INTO `readers` VALUES(1, 'test123_name', 'test_123_lastname', '2000-01-01', 'MEN', '123456789', 'test123@gmail.com');