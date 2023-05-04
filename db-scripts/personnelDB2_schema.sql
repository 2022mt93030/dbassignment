-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema personnelDb2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema personnelDb2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `personnelDb2` DEFAULT CHARACTER SET utf8 ;
USE `personnelDb2` ;

-- -----------------------------------------------------
-- Table `personnelDb2`.`DEPT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`DEPT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`SERVICE_LOG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`SERVICE_LOG` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `END_DATE` DATE NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`EMPLOYEE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`EMPLOYEE` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30) NOT NULL,
  `TITLE` VARCHAR(45) NOT NULL,
  `DOB` DATE NOT NULL,
  `DOJ` DATE NOT NULL,
  `EDUCATION` VARCHAR(30) NOT NULL,
  `SALARY` DECIMAL(12,2) NOT NULL,
  `CURRENCY` CHAR(4) NOT NULL,
  `DEPT_ID` INT NULL,
  `SERVICE_LOG_ID` BIGINT(9) NULL,
  `SID` BIGINT(9) NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_DEPT_EMPLOYEE_idx` (`DEPT_ID` ASC) VISIBLE,
  INDEX `FK_SERVICE_LOG_EMPLOYEE_idx` (`SERVICE_LOG_ID` ASC) VISIBLE,
  INDEX `FK_SID_idx` (`SID` ASC) VISIBLE,
  CONSTRAINT `FK_DEPT_EMPLOYEE`
    FOREIGN KEY (`DEPT_ID`)
    REFERENCES `personnelDb2`.`DEPT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_SERVICE_LOG_EMPLOYEE`
    FOREIGN KEY (`SERVICE_LOG_ID`)
    REFERENCES `personnelDb2`.`SERVICE_LOG` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_SID`
    FOREIGN KEY (`SID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`ROLES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`ROLES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`SKILLS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`SKILLS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`EMP_SKILLS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`EMP_SKILLS` (
  `EMP_ID` BIGINT(9) NOT NULL,
  `SKILL_ID` INT NOT NULL,
  `PROFICIENCY` INT NOT NULL,
  PRIMARY KEY (`EMP_ID`, `SKILL_ID`),
  INDEX `EMP_ID_idx` (`EMP_ID` ASC) VISIBLE,
  INDEX `SKILL_ID_idx` (`SKILL_ID` ASC) VISIBLE,
  CONSTRAINT `FK_EMPLOYEE_EMP_SKILLS`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_SKILL_EMP_SKILLS`
    FOREIGN KEY (`SKILL_ID`)
    REFERENCES `personnelDb2`.`SKILLS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`COURSES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`COURSES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NOT NULL,
  `TUTOR` VARCHAR(45) NULL,
  `IS_OPTIONAL` TINYINT NULL,
  `SKILLS_ID` INT NULL,
  `IS_DISABLED` TINYINT NULL,
  INDEX `SKILL_ID_idx` (`SKILLS_ID` ASC) VISIBLE,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_SKILLS_COURSES`
    FOREIGN KEY (`SKILLS_ID`)
    REFERENCES `personnelDb2`.`SKILLS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`VACANCY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`VACANCY` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NOT NULL,
  `STATUS` TINYINT NOT NULL,
  `COUNT` INT NOT NULL,
  `DEPT_ID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `DEPT_ID_idx` (`DEPT_ID` ASC) VISIBLE,
  CONSTRAINT `FK_DEPT_VACANCY`
    FOREIGN KEY (`DEPT_ID`)
    REFERENCES `personnelDb2`.`DEPT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`CANDIDATE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`CANDIDATE` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `EDUCATION` VARCHAR(45) NOT NULL,
  `EXPERIENCE` DECIMAL NOT NULL,
  `VACANCY_ID` BIGINT(9) NULL,
  `MOBILE_NO` VARCHAR(15) NULL,
  `EMAIL` VARCHAR(30) NULL,
  PRIMARY KEY (`ID`),
  INDEX `V_ID_idx` (`VACANCY_ID` ASC) VISIBLE,
  CONSTRAINT `FK_VACANCY_CANDIDATE`
    FOREIGN KEY (`VACANCY_ID`)
    REFERENCES `personnelDb2`.`VACANCY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`COUNTRY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`COUNTRY` (
  `COUNTRY_ABBR` VARCHAR(3) NOT NULL,
  `CURRENCY` VARCHAR(4) NULL,
  PRIMARY KEY (`COUNTRY_ABBR`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`LOCATION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`LOCATION` (
  `LCOUNTRY` VARCHAR(3) NOT NULL,
  `PIN` INT(10) NOT NULL,
  `CITY` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`LCOUNTRY`, `PIN`),
  CONSTRAINT `FK_COUNTRY`
    FOREIGN KEY (`LCOUNTRY`)
    REFERENCES `personnelDb2`.`COUNTRY` (`COUNTRY_ABBR`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`DEPT_EXISTS_AT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`DEPT_EXISTS_AT` (
  `DEPT_ID` INT NOT NULL,
  `COUNTRY` VARCHAR(3) NOT NULL,
  `PIN` INT(10) NOT NULL,
  INDEX `L_ID_idx` (`COUNTRY` ASC, `PIN` ASC) INVISIBLE,
  PRIMARY KEY (`DEPT_ID`, `COUNTRY`, `PIN`),
  CONSTRAINT `FK_DEPT_DEPT_EXISTS_AT`
    FOREIGN KEY (`DEPT_ID`)
    REFERENCES `personnelDb2`.`DEPT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_LOCATION_DEPT_EXISTS_AT`
    FOREIGN KEY (`COUNTRY` , `PIN`)
    REFERENCES `personnelDb2`.`LOCATION` (`LCOUNTRY` , `PIN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`USER_CREDENTIAL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`USER_CREDENTIAL` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `CREATE_OR_UPDATE_DATE` DATETIME(6) NOT NULL,
  `EMP_ID` BIGINT(9) NULL,
  `EMAIL` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  INDEX `EID_idx` (`EMP_ID` ASC) VISIBLE,
  CONSTRAINT `FK_EMPLOYEE_USER_CREDENTIAL`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`PROJECT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`PROJECT` (
  `ID` BIGINT(9) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(20) NULL,
  `CLIENT` VARCHAR(10) NULL,
  `DESCRIPTION` VARCHAR(200) NULL,
  `START_DATE` DATE NULL,
  `END_DATE` DATE NULL,
  `DEPT_ID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_DEPT_PROJECT_idx` (`DEPT_ID` ASC) VISIBLE,
  CONSTRAINT `FK_DEPT_PROJECT`
    FOREIGN KEY (`DEPT_ID`)
    REFERENCES `personnelDb2`.`DEPT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`WORKS_ON`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`WORKS_ON` (
  `PROJECT_ID` BIGINT(9) NOT NULL,
  `EMP_ID` BIGINT(9) NOT NULL,
  PRIMARY KEY (`PROJECT_ID`, `EMP_ID`),
  INDEX `FK_EMPLOYEE_WORKS_ON_idx` (`EMP_ID` ASC) VISIBLE,
  CONSTRAINT `FK_PROJECT_WORKS_ON`
    FOREIGN KEY (`PROJECT_ID`)
    REFERENCES `personnelDb2`.`PROJECT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_EMPLOYEE_WORKS_ON`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`SERVICE_LOG_CONTAINS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`SERVICE_LOG_CONTAINS` (
  `SERVICE_LOG_ID` BIGINT(9) NOT NULL,
  `PROJECT_ID` BIGINT(9) NOT NULL,
  `PROJECT_JOIN_DATE` DATE NULL,
  `PROJECT_EXIT_DATE` DATE NULL,
  `COMMENT` VARCHAR(250) NULL,
  `GRADE` CHAR(3) NULL,
  `MGR_ID` BIGINT(9) NULL,
  PRIMARY KEY (`SERVICE_LOG_ID`, `PROJECT_ID`),
  INDEX `FK_PROJECT_ID_idx` (`PROJECT_ID` ASC) VISIBLE,
  CONSTRAINT `FK_SERVICE_LOG_ID`
    FOREIGN KEY (`SERVICE_LOG_ID`)
    REFERENCES `personnelDb2`.`SERVICE_LOG` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROJECT_ID`
    FOREIGN KEY (`PROJECT_ID`)
    REFERENCES `personnelDb2`.`PROJECT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`EMP_ROLES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`EMP_ROLES` (
  `EMP_ID` BIGINT(9) NOT NULL,
  `ROLE_ID` INT NOT NULL,
  PRIMARY KEY (`EMP_ID`, `ROLE_ID`),
  INDEX `FK_ROLES_EMP_ROLES_idx` (`ROLE_ID` ASC) VISIBLE,
  CONSTRAINT `FK_EMPLOYEE_EMP_ROLES`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ROLES_EMP_ROLES`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `personnelDb2`.`ROLES` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`CANDIDATE_SKILLS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`CANDIDATE_SKILLS` (
  `SKILL_ID` INT NOT NULL,
  `CANDIDATE_ID` BIGINT(9) NOT NULL,
  PRIMARY KEY (`SKILL_ID`, `CANDIDATE_ID`),
  INDEX `FK_CANDIDATE_CANDIDATE_SKILLS_idx` (`CANDIDATE_ID` ASC) VISIBLE,
  CONSTRAINT `FK_SKILLS_CANDIDATE_SKILLS`
    FOREIGN KEY (`SKILL_ID`)
    REFERENCES `personnelDb2`.`SKILLS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CANDIDATE_CANDIDATE_SKILLS`
    FOREIGN KEY (`CANDIDATE_ID`)
    REFERENCES `personnelDb2`.`CANDIDATE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`INTERVIEW_ON`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`INTERVIEW_ON` (
  `EMP_ID` BIGINT(9) NOT NULL,
  `CANDIDATE_ID` BIGINT(9) NOT NULL,
  `INTERVIEW_DATE` DATE NULL,
  `STATUS` TINYINT(1) NULL,
  `FEEDBACK` VARCHAR(100) NULL,
  `LEVEL` INT(1) NULL,
  PRIMARY KEY (`EMP_ID`, `CANDIDATE_ID`),
  INDEX `FK_CANDIDATE_ID_idx` (`CANDIDATE_ID` ASC) VISIBLE,
  CONSTRAINT `FK_EMP_ID`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CANDIDATE_ID`
    FOREIGN KEY (`CANDIDATE_ID`)
    REFERENCES `personnelDb2`.`CANDIDATE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personnelDb2`.`ENROLL_OR_ADD_REMOVE_COURSES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personnelDb2`.`ENROLL_OR_ADD_REMOVE_COURSES` (
  `COURSE_ID` INT NOT NULL,
  `EMP_ID` BIGINT(9) NOT NULL,
  `IS_ENROLLED` TINYINT NULL,
  `IS_ADDED` TINYINT NULL,
  `IS_REMOVED` TINYINT NULL,
  PRIMARY KEY (`COURSE_ID`, `EMP_ID`),
  INDEX `FK_EMP_ID_idx` (`EMP_ID` ASC) VISIBLE,
  CONSTRAINT `FK_EMPLOYEE_ID`
    FOREIGN KEY (`EMP_ID`)
    REFERENCES `personnelDb2`.`EMPLOYEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_COURSE_ID`
    FOREIGN KEY (`COURSE_ID`)
    REFERENCES `personnelDb2`.`COURSES` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
