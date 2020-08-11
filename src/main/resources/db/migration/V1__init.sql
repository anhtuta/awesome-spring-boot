CREATE TABLE `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(100) NOT NULL,
  `price` INT NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT now(),
  `modified_date` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`));

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
  