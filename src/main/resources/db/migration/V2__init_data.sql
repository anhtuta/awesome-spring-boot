INSERT INTO `book` VALUES (1,'Những tháng năm rực rỡ','Ae-ran Kim',96000,'2020-01-11 22:10:00','2020-01-14 00:00:00'),(2,'21 bài học cho thế kỉ 21','Yuval Noah Harari',210000,'2020-01-12 11:24:00','2020-01-14 00:00:00'),(3,'Conan 97','Gosho Aoyama',20000,'2020-01-13 13:21:00','2020-01-14 00:00:00'),(4,'Battle Royale','Koushun Takami',230000,'2020-01-14 07:18:00','2020-01-14 00:00:00'),(5,'Tôi là ai – và nếu vậy thì bao nhiêu','Richard David Precht',115000,'2020-01-14 09:10:00','2020-01-14 00:00:00');

INSERT INTO `user` (`id`, `username`, `name`, `password`) VALUES ('1', 'admin', 'Admin', '{bcrypt}$2a$10$4TENNUAwIX4uG8GX2UWPBOuVSB3mBIlMcSVzAePDI/DHieJKQ0P7O');
INSERT INTO `user` (`id`, `username`, `name`, `password`) VALUES ('2', 'att', 'Tạ Anh Tú', '{bcrypt}$2a$10$4TENNUAwIX4uG8GX2UWPBOuVSB3mBIlMcSVzAePDI/DHieJKQ0P7O');

INSERT INTO `role` (`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES ('2', 'USER');

INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('2', '1', '2');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('3', '2', '2');
