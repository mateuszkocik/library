USE `LIBRARY`;

INSERT INTO `users` VALUES('test123', '$2y$04$rYK4fCOwHbSzDYh8tR21Cuca0nwy1j8EuZfV6EdN3BXltQ9LhMmrO');

INSERT INTO `authorities` VALUES('test123', 'ROLE_USER');

INSERT INTO `book_details` VALUES(1, 'Romeo and Juliet', '	Shakespearean tragedy', 'Penguin Random House');

INSERT INTO `books` VALUES(1, 1, 'Y')
