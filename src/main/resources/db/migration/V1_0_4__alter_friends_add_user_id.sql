ALTER TABLE `friends` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
