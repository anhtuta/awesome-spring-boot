INSERT INTO `category` (`id`, `name`) VALUES ('1', 'Tiểu thuyết');
INSERT INTO `category` (`id`, `name`) VALUES ('2', 'Manga');
INSERT INTO `category` (`id`, `name`) VALUES ('3', 'Sách tham khảo');
INSERT INTO `category` (`id`, `name`) VALUES ('4', 'Kinh tế học');
INSERT INTO `category` (`id`, `name`) VALUES ('5', 'Kỹ năng sống');
INSERT INTO `category` (`id`, `name`) VALUES ('6', 'Triết học');

INSERT INTO `book` VALUES (1,'Những tháng năm rực rỡ','Ae-ran Kim',96000,1,'2020-01-11 22:10:00','2020-01-14 00:00:00'),(2,'21 bài học cho thế kỉ 21','Yuval Noah Harari',210000,3,'2020-01-12 11:24:00','2020-01-14 00:00:00'),(3,'Conan 97','Gosho Aoyama',20000,2,'2020-01-13 13:21:00','2020-01-14 00:00:00'),(4,'Battle Royale','Koushun Takami',230000,1,'2020-01-14 07:18:00','2020-01-14 00:00:00'),(5,'Tôi là ai – và nếu vậy thì bao nhiêu','Richard David Precht',115000,6,'2020-01-14 09:10:00','2020-01-14 00:00:00'),(6,'Sapiens: Lược Sử Loài Người','Yuval Noah Harari',209000,3,'2020-08-13 23:30:35','2020-08-11 23:30:35'),(7,'Internet Của Tiền Tệ','Andreas M. Antonopoulos',199000,4,'2020-08-17 23:31:46','2020-08-11 23:31:46'),(8,'Nóng Giận Là Bản Năng , Tĩnh Lặng Là Bản Lĩnh','Tống Mặc',89000,5,'2020-08-19 23:31:27','2020-08-11 23:35:27'),(9,'Trên Đường Băng','Tony Buổi Sáng',80000,5,'2020-08-12 23:32:27','2020-08-11 23:35:27'),(10,'The Little Prince','Antoine De Saint-Exupéry',231000,1,'2020-08-10 23:33:27','2020-08-11 23:35:27'),(11,'Nhà Giả Kim','Paulo Coelho',231000,1,'2020-08-18 23:34:27','2020-08-11 23:35:27'),(12,'Khi Hơi Thở Hóa Thinh Không','Paul Kalanithi',109000,1,'2020-08-12 23:35:27','2020-08-11 23:35:27'),(13,'Putin - Logic Của Quyền Lực','Hubert Seipel',138000,1,'2020-08-11 23:36:17','2020-08-11 23:36:17'),(14,'Đừng Lựa Chọn An Nhàn Khi Còn Trẻ','Cảnh Thiên',81000,5,'2020-08-15 23:36:49','2020-08-11 23:36:49'),(15,'Sống Thực Tế Giữa Đời Thực Dụng','Mễ Mông',98000,1,'2020-08-11 23:37:12','2020-08-11 23:37:12'),(16,'Chủ Nghĩa Khắc Kỷ - Phong Cách Sống Bản Lĩnh Và Bình Thản','William B. Irvine',125000,1,'2020-08-18 23:37:35','2020-08-11 23:37:35');

INSERT INTO `user` (`id`, `username`, `name`, `password`) VALUES ('1', 'admin', 'Admin', '{bcrypt}$2a$10$4TENNUAwIX4uG8GX2UWPBOuVSB3mBIlMcSVzAePDI/DHieJKQ0P7O');
INSERT INTO `user` (`id`, `username`, `name`, `password`) VALUES ('2', 'att', 'Tạ Anh Tú', '{bcrypt}$2a$10$4TENNUAwIX4uG8GX2UWPBOuVSB3mBIlMcSVzAePDI/DHieJKQ0P7O');

INSERT INTO `role` (`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES ('2', 'USER');

INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('2', '1', '2');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES ('3', '2', '2');
