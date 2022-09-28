ALTER TABLE `book` 
DROP FOREIGN KEY `fk_book_category`;
ALTER TABLE `book` 
DROP COLUMN `category_id`,
DROP INDEX `fk_book_category_idx`;

CREATE TABLE `book_category` (
  `book_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `vote_count` INT NULL DEFAULT 0 COMMENT 'Số lượng người dùng vote cho cuốn sách book_id thuộc thể loại category_id',
  PRIMARY KEY (`book_id`, `category_id`),
  INDEX `book_category_category_fk_idx` (`category_id` ASC),
  CONSTRAINT `book_category_book_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `book_category_category_fk`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

