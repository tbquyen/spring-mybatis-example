CREATE TABLE IF NOT EXISTS users (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100),
  `status` int,
  PRIMARY KEY (`id`)
);
INSERT INTO `users` (`username`, `password`, `role`, `status`)
VALUES ('admin', '$2a$10$jMh127E9CdVXuYV447ucnu3Jm1sX3BnNY1dZ4ySscPg9SnOPuimWm', 'ROLE_ADMIN', '0');
