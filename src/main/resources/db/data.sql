USE `LIBRARY`;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM `rentals`;
DELETE FROM `reservations`;
DELETE FROM `books`;
DELETE FROM `book_details`;
DELETE FROM `readers`;
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
('test3@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6'),
('johnsmith@gmail.com', '$2y$12$TLo0xjM.dXfoi8PkBl.NNOOp6/12YWcep3NGT1RzClD1N8kBARkE6');

INSERT INTO `authorities` VALUES
('manager@gmail.com', 'ROLE_MANAGER'),
('test123@gmail.com', 'ROLE_READER'),
('librarian@gmail.com', 'ROLE_LIBRARIAN'),
('inactive@gmail.com', 'ROLE_INACTIVE'),
('test1@gmail.com', 'ROLE_READER'),
('test2@gmail.com', 'ROLE_READER'),
('test3@gmail.com', 'ROLE_READER'),
('johnsmith@gmail.com', 'ROLE_READER');

-- INSERT INTO `book_details` VALUES
-- (1, 'Romeo and Juliet', 'Shakespearean tragedy', 'Penguin Random House'),
-- (2, 'Ulysses', 'Modernist novel', 'Shakespeare and Company'),
-- (3, 'In Search of Lost Time', 'Modernist', 'Grasset and Gallimard'),
-- (4, 'Don Quixote', 'Novel', 'Francisco de Robles'),
-- (5, 'The Great Gatsby', 'Tragedy', 'Charles Scribners Sons'),
-- (6, 'War and Peace', 'Novel', 'The Russian Messenger ');

INSERT INTO `book_details` VALUES
(1, 'Romeo and Juliet', 'Novel', 'Penguin Random House', null, null, null, null),
(2, 'Ulysses', 'Novel', 'Shakespeare and Company', null, null, null, null),
(3, 'In Search of Lost Time', 'Novel', 'Grasset and Gallimard', null, null, null, null),
(4, 'Don Quixote', 'Novel', 'Francisco de Robles', null, null, null, null),
(5, 'The Great Gatsby', 'Novel', 'Charles Scribners Sons', null, null, null, null),
(6, 'War and Peace', 'Novel', 'The Russian Messenger ', null, null, null, null);


INSERT INTO `books` VALUES
(2146, 6, 'BORROWED'),
(3215, 2, 'RESERVED'),
(1328, 3, 'AVAILABLE'),
(7893, 4, 'AVAILABLE'),
(321, 5, 'AVAILABLE'),
(1,1, 'AVAILABLE'),
(7,6, 'AVAILABLE'),
(8,3, 'AVAILABLE'),
(9,2, 'AVAILABLE');

INSERT INTO `managers` VALUES
(1, 'manager_name', 'manager_lastname', 'MAN', '123456789', 'manager@gmail.com');

INSERT INTO `readers` VALUES
(1, 'test123_name', 'test_123_lastname', 'MAN', '123456789', 'test123@gmail.com'),
(2, 'test1', 'test1', 'MAN', '123456789', 'test1@gmail.com'),
(3, 'test2', 'test2', 'MAN', '123456789', 'test2@gmail.com'),
(4, 'test3', 'test3', 'MAN', '123456789', 'test3@gmail.com'),
(5, 'John', 'Smith', 'MAN', '691382177', 'johnsmith@gmail.com');

INSERT INTO `librarians` VALUES
(1, 'librarian_name', 'librarian_lastname', 'WOMAN', '123456789', 'librarian@gmail.com');

INSERT INTO `rentals` VALUES
(1, 2146, 5, '2021-08-20', '2021-09-20', NULL, false),
(2, 1328, 5, '2021-07-15', '2021-08-15', NULL, false),
(3, 7893, 5, '2021-06-17', '2021-07-17', NULL, true),
(4, 321, 5, '2021-05-01', '2021-06-01', NULL, true);

INSERT INTO `reservations` VALUES
(1, 3215, 5, '2021-09-10');