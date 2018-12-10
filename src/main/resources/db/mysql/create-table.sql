SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8 ;
USE `test` ;

-- -----------------------------------------------------
-- Table `test`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`User` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(64) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `userId_UNIQUE` (`id` ASC),
  UNIQUE INDEX `userName_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户信息'
PACK_KEYS = DEFAULT;


-- -----------------------------------------------------
-- Table `test`.`UserGroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`UserGroup` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(64) NOT NULL COMMENT '用户组名称',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `userGroupId_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户组信息';


-- -----------------------------------------------------
-- Table `test`.`UserPriviledge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`UserPriviledge` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(64) NOT NULL COMMENT '权限内容',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户权限';


-- -----------------------------------------------------
-- Table `test`.`UserWithGroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`UserWithGroup` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `userId` INT UNSIGNED NOT NULL COMMENT '用户主键',
  `groupId` INT UNSIGNED NOT NULL COMMENT '用户组主键',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `userWithGroup_UNIQUE` (`userId` ASC, `groupId` ASC),
  INDEX `fk_user_with_group_group_id_idx` (`groupId` ASC),
  CONSTRAINT `fk_user_with_group_user_id`
    FOREIGN KEY (`userId`)
    REFERENCES `test`.`User` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_with_group_group_id`
    FOREIGN KEY (`groupId`)
    REFERENCES `test`.`UserGroup` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户-用户组信息';


-- -----------------------------------------------------
-- Table `test`.`GroupWithPriviledge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`GroupWithPriviledge` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `groupId` INT UNSIGNED NOT NULL,
  `priviledgeId` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `groupWithPriviledge_UNIQUE` (`groupId` ASC, `priviledgeId` ASC),
  INDEX `fk_group_with_priviledge_priviledge_id_idx` (`priviledgeId` ASC),
  CONSTRAINT `fk_group_with_priviledge_group_id`
    FOREIGN KEY (`groupId`)
    REFERENCES `test`.`UserGroup` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_group_with_priviledge_priviledge_id`
    FOREIGN KEY (`priviledgeId`)
    REFERENCES `test`.`UserPriviledge` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户组-权限信息';


-- -----------------------------------------------------
-- Table `test`.`PRODUCTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`PRODUCTS` (
  `ID` VARCHAR(25) NULL DEFAULT NULL,
  `NAME` VARCHAR(50) NULL DEFAULT NULL,
  `DESCRIPTION` VARCHAR(250) NULL DEFAULT NULL,
  `UNIT_PRICE` DECIMAL NULL DEFAULT NULL,
  `MANUFACTURER` VARCHAR(50) NULL DEFAULT NULL,
  `CATEGORY` VARCHAR(50) NULL DEFAULT NULL,
  `CONDITION` VARCHAR(50) NULL DEFAULT NULL,
  `UNITS_IN_STOCK` BIGINT NULL DEFAULT NULL,
  `UNITS_IN_ORDER` BIGINT NULL DEFAULT NULL,
  `DISCONTINUED` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`));


-- -----------------------------------------------------
-- Table `test`.`CART`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`CART` (
  `ID` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`));


-- -----------------------------------------------------
-- Table `test`.`CART_ITEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`CART_ITEM` (
  `ID` INT NULL DEFAULT NULL AUTO_INCREMENT,
  `PRODUCT_ID` VARCHAR(25) NULL DEFAULT NULL,
  `CART_ID` VARCHAR(50) NULL DEFAULT NULL,
  `QUANTITY` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_product_id` (`PRODUCT_ID` ASC),
  INDEX `fk_cart_id` (`CART_ID` ASC),
  CONSTRAINT `fk_product_id`
    FOREIGN KEY (`PRODUCT_ID`)
    REFERENCES `test`.`PRODUCTS` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_id`
    FOREIGN KEY (`CART_ID`)
    REFERENCES `test`.`CART` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
