-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema compressor_db
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema compressor_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `compressor_db` DEFAULT CHARACTER
SET
  utf8;

USE `compressor_db`;

-- -----------------------------------------------------
-- Table `compressor_db`.`usuario`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`usuario` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(45) NOT NULL,
    `senha` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`falha`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`falha` (
    `id` VARCHAR(4) NOT NULL,
    `descricao` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;

  CREATE TABLE
  IF NOT EXISTS `compressor_db`.`alerta` (
    `id` VARCHAR(4) NOT NULL,
    `descricao` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`compressor`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`compressor` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(45) NULL,
    `senai` VARCHAR(45) NULL,
    `estado` VARCHAR(10) NULL,
    `ligado` BOOLEAN NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`comandos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compressor_db`.`comandos` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `compressor_id` INT NOT NULL,
    `comando` TINYINT NULL,
    `data_hora` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_comandos_compressor1_idx` (`compressor_id` ASC),
    CONSTRAINT `fk_comandos_compressor1`
        FOREIGN KEY (`compressor_id`)
        REFERENCES `compressor_db`.`compressor` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`registro_compressor`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`registro_compressor` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `data_hora` DATETIME NULL,
    `ligado` BOOLEAN NULL,
    `estado` VARCHAR(10) NULL,
    `temperatura_ar_Comprimido` FLOAT NULL,
    `temperatura_ambiente` FLOAT NULL,
    `temperatura_oleo` FLOAT NULL,
    `temperatura_orvalho` FLOAT NULL,
    `pressao_ar_comprimido` FLOAT NULL,
    `hora_carga` FLOAT NULL,
    `hora_total` FLOAT NULL,
    `pressao_carga` FLOAT NULL,
    `pressao_alivio` FLOAT NULL,
    `falha_idFalha` VARCHAR(4) NULL,
    `compressor_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_registro compressor_falha1_idx` (`falha_idFalha` ASC) VISIBLE,
    INDEX `fk_registro_compressor_compressor1_idx` (`compressor_id` ASC) VISIBLE,
    CONSTRAINT `fk_registro compressor_falha1` FOREIGN KEY (`falha_idFalha`) REFERENCES `compressor_db`.`falha` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_registro_compressor_compressor1` FOREIGN KEY (`compressor_id`) REFERENCES `compressor_db`.`compressor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`roles`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`roles` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`usuario_roles`
-- -----------------------------------------------------
CREATE TABLE
  IF NOT EXISTS `compressor_db`.`usuario_roles` (
    `roles_id` INT NOT NULL,
    `usuario_id` INT NOT NULL,
    PRIMARY KEY (`roles_id`, `usuario_id`),
    INDEX `fk_roles_has_usuario_usuario1_idx` (`usuario_id` ASC) VISIBLE,
    INDEX `fk_roles_has_usuario_roles1_idx` (`roles_id` ASC) VISIBLE,
    CONSTRAINT `fk_roles_has_usuario_roles1` FOREIGN KEY (`roles_id`) REFERENCES `compressor_db`.`roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_roles_has_usuario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `compressor_db`.`usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `compressor_db`.`comando_agendado`
-- -----------------------------------------------------

  CREATE TABLE
    IF NOT EXISTS `compressor_db`.`comando_agendado` (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    compressor_id       INT NOT NULL,
    comando             TINYINT(1) NOT NULL,
    recorrencia                VARCHAR(20) NOT NULL,
    data_hora_execucao  DATETIME NULL,
    dia_semana          VARCHAR(20) NULL,
    hora_execucao       TIME NULL,
    dia_mes             INT NULL,
    executado           TINYINT(1) NOT NULL DEFAULT 0,
    data_hora_execucao_real     DATETIME NULL,
    descricao           VARCHAR(255),
    CONSTRAINT fk_cmd_agendado_compressor
        FOREIGN KEY (compressor_id) REFERENCES compressor(id)
);