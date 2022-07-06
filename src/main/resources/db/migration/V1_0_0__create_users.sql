CREATE TABLE `users` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255) UNIQUE,
    `password` varchar(255),
    `firstname` varchar(255),
    `lastname` varchar(255),
    `sex` varchar(255)
);