ALTER TABLE `friends` ADD FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`);
