USE `LIBRARY`;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM `rentals`;
DELETE FROM `reservations`;
DELETE FROM `books`;
DELETE FROM `book_details`;
DELETE FROM `readers`;
DELETE FROM `tasks`;
DELETE FROM `librarians`;
DELETE FROM `authorities`;
DELETE FROM `users`;

INSERT INTO `users` VALUES
('test123@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'), -- test123@gmail.com test123
('librarian@gmail.com', '$2y$12$3vOD4VzNBSXt.fAm5/Xm0.NekB4n/Y4k2OWj/h5zB7FDppvbPtUiC'); -- librarian@gmail.com test123

INSERT INTO `authorities` VALUES
('test123@gmail.com', 'ROLE_USER'),
('librarian@gmail.com', 'ROLE_LIBRARIAN');

INSERT INTO `book_details` VALUES
(1, 'Romeo and Juliet', '	Shakespearean tragedy', 'Penguin Random House');

INSERT INTO `books` VALUES
(1, 1, 'AVAILABLE');

INSERT INTO `readers` VALUES
(1, 'test123_name', 'test_123_lastname', 'MEN', '123456789', 'test123@gmail.com');

INSERT INTO `librarians` VALUES
(1, 'librarian_name', 'librarian_lastname', 'WOMEN', '123456789', 'librarian@gmail.com');

INSERT INTO `rentals` VALUES
(1, 1, 1, '2021-07-20', '2021-08-20', NULL, NULL);

INSERT INTO `reservations` VALUES
(1, 1, 1, '2021-10-10');