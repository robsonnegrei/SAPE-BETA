-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sape
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `sape` ;

-- -----------------------------------------------------
-- Schema sape
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sape` DEFAULT CHARACTER SET utf8 ;
USE `sape` ;

-- -----------------------------------------------------
-- Table `sape`.`regional`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`regional` ;

CREATE TABLE IF NOT EXISTS `sape`.`regional` (
  `idRegional` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idRegional`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`escola`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`escola` ;

CREATE TABLE IF NOT EXISTS `sape`.`escola` (
  `idEscola` INT(11) NOT NULL AUTO_INCREMENT,
  `idRegional` INT(11) NULL DEFAULT NULL,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idEscola`),
  INDEX `FK_escola_regional_idx` (`idRegional` ASC),
  CONSTRAINT `FK_escola_regional`
    FOREIGN KEY (`idRegional`)
    REFERENCES `sape`.`regional` (`idRegional`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`aluno`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`aluno` ;

CREATE TABLE IF NOT EXISTS `sape`.`aluno` (
  `idAluno` INT(11) NOT NULL AUTO_INCREMENT,
  `idEscola` INT(11) NOT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idAluno`),
  INDEX `FK_aluno_escola_idx` (`idEscola` ASC),
  CONSTRAINT `FK_aluno_escola`
    FOREIGN KEY (`idEscola`)
    REFERENCES `sape`.`escola` (`idEscola`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`avaliacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`avaliacao` ;

CREATE TABLE IF NOT EXISTS `sape`.`avaliacao` (
  `idAvaliacao` INT(11) NOT NULL AUTO_INCREMENT,
  `ano` DATE NULL DEFAULT NULL,
  `data` DATETIME NOT NULL,
  `periodo` INT(11) NULL DEFAULT NULL,
  `idAluno` INT(11) NOT NULL,
  `nivel` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`idAvaliacao`),
  INDEX `FK_avaliacao_aluno_idx` (`idAluno` ASC),
  CONSTRAINT `FK_avaliacao_aluno`
    FOREIGN KEY (`idAluno`)
    REFERENCES `sape`.`aluno` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`post` ;

CREATE TABLE IF NOT EXISTS `sape`.`post` (
  `idPost` INT(11) NOT NULL AUTO_INCREMENT,
  `idProfessor` INT(11) NULL DEFAULT NULL,
  `mensagem` MEDIUMTEXT NULL DEFAULT NULL,
  `data` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`idPost`))
ENGINE = InnoDB
AUTO_INCREMENT = 66
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`imagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`imagem` ;

CREATE TABLE IF NOT EXISTS `sape`.`imagem` (
  `idImagem` INT(11) NOT NULL AUTO_INCREMENT,
  `idPost` INT(11) NOT NULL,
  `nome` VARCHAR(300) NULL DEFAULT NULL,
  `tamanho` VARCHAR(20) NOT NULL,
  `tipo` VARCHAR(20) NOT NULL,
  `bytes` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`idImagem`),
  INDEX `FK_imagem_post_idx` (`idPost` ASC),
  CONSTRAINT `FK_imagem_post`
    FOREIGN KEY (`idPost`)
    REFERENCES `sape`.`post` (`idPost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`usuario` ;

CREATE TABLE IF NOT EXISTS `sape`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  `isProfCoordenadorLei` TINYINT(1) NULL DEFAULT NULL,
  `isAdmin` TINYINT(1) NULL DEFAULT NULL,
  `isProfessor` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 54
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`pclei`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`pclei` ;

CREATE TABLE IF NOT EXISTS `sape`.`pclei` (
  `idProfessor` INT(11) NOT NULL AUTO_INCREMENT,
  `idRegional` INT(11) NOT NULL,
  `idUsuario` INT(11) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idProfessor`),
  INDEX `FK_Professor_Usuario_idx` (`idUsuario` ASC),
  INDEX `FK_Professor_regional_idx` (`idRegional` ASC),
  CONSTRAINT `FK_Professor_Usuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `sape`.`usuario` (`idUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_Professor_post`
    FOREIGN KEY (`idProfessor`)
    REFERENCES `sape`.`post` (`idPost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Professor_regional`
    FOREIGN KEY (`idRegional`)
    REFERENCES `sape`.`regional` (`idRegional`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`usuario_funcao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`usuario_funcao` ;

CREATE TABLE IF NOT EXISTS `sape`.`usuario_funcao` (
  `idUsuario` INT(11) NOT NULL,
  `funcao` VARCHAR(10) NOT NULL DEFAULT 'PCLEI',
  PRIMARY KEY (`idUsuario`, `funcao`),
  CONSTRAINT `FK_usuario_usuario_funcao`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `sape`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- em 15/08/2016, altera tabela de imagem
ALTER TABLE `sape`.`imagem` 
DROP COLUMN `linkImagem`,
ADD COLUMN `nome` VARCHAR(300) NULL AFTER `idPost`,
ADD COLUMN `tamanho` VARCHAR(20) NOT NULL AFTER `nome`,
ADD COLUMN `tipo` VARCHAR(20) NOT NULL AFTER `tamanho`,
ADD COLUMN `bytes` MEDIUMBLOB NOT NULL AFTER `tipo`;
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
