CREATE TABLE `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(100) NOT NULL,
  `price` INT NOT NULL,
  `category_id` INT NULL,
  `created_date` DATETIME NOT NULL DEFAULT now(),
  `modified_date` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`));

ALTER TABLE `book` 
ADD INDEX `fk_book_category_idx` (`category_id` ASC);

ALTER TABLE `book` 
ADD CONSTRAINT `fk_book_category`
  FOREIGN KEY (`category_id`)
  REFERENCES `category` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

 CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`, `username`));
  
CREATE TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role_user_id_idx` (`user_id` ASC),
  INDEX `fk_user_role_role_id_idx` (`role_id` ASC),
  CONSTRAINT `fk_user_role_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `store` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NULL,
  `address` VARCHAR(500) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `store_id` INT NOT NULL,
  `first_name` VARCHAR(200) NOT NULL,
  `last_name` VARCHAR(200) NOT NULL,
  `gender` VARCHAR(45) NOT NULL DEFAULT 'unknown' COMMENT '{\"male\", \"female\", \"gay\", \"lesbian\", \"unknown\"}',
  `email` VARCHAR(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_staff_store_idx` (`store_id` ASC),
  CONSTRAINT `fk_staff_store`
    FOREIGN KEY (`store_id`)
    REFERENCES `store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `staff` 
ADD COLUMN `is_alive` TINYINT NULL COMMENT 'is_alive = 1: alive\nis_alive = 0: deceased' AFTER `email`;
