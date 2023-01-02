-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema authdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema authdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `authdb` DEFAULT CHARACTER SET utf8 ;
USE `authdb` ;

-- -----------------------------------------------------
-- Table `authdb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authdb`.`users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `enabled` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `authdb`.`balances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authdb`.`balances` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `cashable` FLOAT UNSIGNED NULL,
  `bonus` FLOAT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_balances_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_balances_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `authdb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `authdb`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authdb`.`transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `circuit` VARCHAR(45) NOT NULL,
  `amount` FLOAT UNSIGNED NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `authdb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

