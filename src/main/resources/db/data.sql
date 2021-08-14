USE `LIBRARY`;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM `rentals`;
DELETE FROM `reservations`;
DELETE FROM `books`;
DELETE FROM `book_details`;
DELETE FROM `readers`;
DELETE FROM `tasks`;
DELETE FROM `librarians`;
DELETE FROM `managers`;
DELETE FROM `authorities`;
DELETE FROM `users`;

INSERT INTO `users` VALUES
('manager@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'), -- manager@gmail.com test123
('test123@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'), -- test123@gmail.com test123
('librarian@gmail.com', '$2y$12$3vOD4VzNBSXt.fAm5/Xm0.NekB4n/Y4k2OWj/h5zB7FDppvbPtUiC'), -- librarian@gmail.com test123
('inactive@gmail.com', '$2y$12$cg7VliRBuIW./QtzIZVj4eLevqkK4U3vTl4I/XFSKc7i/5BtkYHY2'), -- inactive@gmail.com test123
('test1@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'),
('test2@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'),
('test3@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6');

INSERT INTO `authorities` VALUES
('manager@gmail.com', 'ROLE_MANAGER'),
('test123@gmail.com', 'ROLE_READER'),
('librarian@gmail.com', 'ROLE_LIBRARIAN'),
('inactive@gmail.com', 'ROLE_INACTIVE'),
('test1@gmail.com', 'ROLE_READER'),
('test2@gmail.com', 'ROLE_READER'),
('test3@gmail.com', 'ROLE_READER');

-- INSERT INTO `book_details` VALUES
-- (1, 'Romeo and Juliet', 'Shakespearean tragedy', 'Penguin Random House'),
-- (2, 'Ulysses', 'Modernist novel', 'Shakespeare and Company'),
-- (3, 'In Search of Lost Time', 'Modernist', 'Grasset and Gallimard'),
-- (4, 'Don Quixote', 'Novel', 'Francisco de Robles'),
-- (5, 'The Great Gatsby', 'Tragedy', 'Charles Scribners Sons'),
-- (6, 'War and Peace', 'Novel', 'The Russian Messenger ');

INSERT INTO `book_details` VALUES
(1, 'Romeo and Juliet', 'Novel', 'Penguin Random House'),
(2, 'Ulysses', 'Novel', 'Shakespeare and Company'),
(3, 'In Search of Lost Time', 'Novel', 'Grasset and Gallimard'),
(4, 'Don Quixote', 'Novel', 'Francisco de Robles'),
(5, 'The Great Gatsby', 'Novel', 'Charles Scribners Sons'),
(6, 'War and Peace', 'Novel', 'The Russian Messenger ');

INSERT INTO `books` VALUES
(1, 1, 'BORROWED'),
(2, 2, 'RESERVED'),
(3,1, 'AVAILABLE'),
(4,3, 'AVAILABLE'),
(5,4, 'AVAILABLE'),
(6,5, 'AVAILABLE'),
(7,6, 'AVAILABLE'),
(8,3, 'AVAILABLE');

INSERT INTO `managers` VALUES
(1, 'manager_name', 'manager_lastname', 'MEN', '123456789', 'manager@gmail.com');

INSERT INTO `readers` VALUES
(1, 'test123_name', 'test_123_lastname', 'MEN', '123456789', 'test123@gmail.com'),
(2, 'test1', 'test1', 'MEN', '123456789', 'test1@gmail.com'),
(3, 'test2', 'test2', 'MEN', '123456789', 'test2@gmail.com'),
(4, 'test3', 'test3', 'MEN', '123456789', 'test3@gmail.com');

INSERT INTO `librarians` VALUES
(1, 'librarian_name', 'librarian_lastname', 'WOMEN', '123456789', 'librarian@gmail.com');

INSERT INTO `rentals` VALUES
(1, 1, 1, '2021-07-20', '2021-08-20', NULL, false),
(2, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(3, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(4, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(5, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(6, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(7, 1, 1, '2021-08-20', '2021-08-20', NULL, false),
(8, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(9, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(10, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(11, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(12, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(13, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(14, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(15, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(16, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(17, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(18, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(19, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(20, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(21, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(22, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(23, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(24, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(25, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(26, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(27, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(28, 1, 1, '2021-06-20', '2021-08-20', NULL, false),
(29, 2, 1, '2021-06-20', '2021-08-20', NULL, false),
(30, 1, 3, '2021-06-20', '2021-08-20', NULL, true),
(31, 3, 3, '2021-06-20', '2021-08-20', NULL, true),
(32, 4, 3, '2021-06-20', '2021-08-20', NULL, true),
(33, 1, 2, '2021-06-20', '2021-08-20', NULL, true);

INSERT INTO `reservations` VALUES
(1, 2, 1, '2021-10-10');