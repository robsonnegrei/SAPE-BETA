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
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sape`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sape`.`usuario` ;

CREATE TABLE IF NOT EXISTS `sape`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB
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
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO `sape`.`usuario` (`idUsuario`, `email`, `senha`) VALUES ('1', 'admin@sape.com', '@#12345');
INSERT INTO `sape`.`usuario_funcao` (`idUsuario`, `funcao`) VALUES ('1', 'ADMIN');
-- Regionais
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Califórnia');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Campo Novo');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Campo Velho');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Centro');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Cipó dos Anjos');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Custódio');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Dom Maurício');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('José Jucá');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Juá');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Juatama');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Nemésio Bezerra');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Riacho Verde');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('São João');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('São João dos Queiroz');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Tapuiará');
INSERT INTO `sape`.`regional` (`nome`) VALUES ('Várzea da Onça');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

ALTER TABLE `sape`.`pclei` 
DROP FOREIGN KEY `FK_Professor_post`;

ALTER TABLE `sape`.`post` 
ADD INDEX `FK_Post_Professor_idx` (`idProfessor` ASC);
ALTER TABLE `sape`.`post` 
ADD CONSTRAINT `FK_Post_Professor`
  FOREIGN KEY (`idProfessor`)
  REFERENCES `sape`.`pclei` (`idProfessor`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
ALTER TABLE `sape`.`avaliacao` 
CHANGE COLUMN `ano` `ano` INT NULL DEFAULT NULL ;

CREATE TABLE `sape`.`configuracao` (
  `AnoAvaliacaoAtual` INT NOT NULL DEFAULT 2016,
  `PeriodoAvaliacaoAtual` INT NOT NULL DEFAULT 1,
  `PeriodosPorAno` INT NOT NULL DEFAULT 5,
  `LimiteDiasReavaliacao` INT NOT NULL DEFAULT 15)
COMMENT = 'Dados de configuracao do sistema';

ALTER TABLE `sape`.`aluno` 
ADD COLUMN `nivelAtual` VARCHAR(45) NULL AFTER `nome`;

ALTER TABLE `sape`.`avaliacao` 
CHANGE COLUMN `nivel` `nivel` VARCHAR(25) NULL DEFAULT NULL ;
