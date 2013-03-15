SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `CarPooling` ;
CREATE SCHEMA IF NOT EXISTS `CarPooling` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
DROP SCHEMA IF EXISTS `carpooling` ;
CREATE SCHEMA IF NOT EXISTS `carpooling` DEFAULT CHARACTER SET utf8 ;
USE `CarPooling` ;

-- -----------------------------------------------------
-- Table `CarPooling`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`User` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`User` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `Login` VARCHAR(45) ,
  `Password` VARCHAR(45) ,
  `LastName` VARCHAR(45) COMMENT '	' ,
  `FirstName` VARCHAR(45) ,
  `AccountNumber` INT NULL ,
  `PhoneNumber` VARCHAR(45) NULL ,
  `Email` VARCHAR(45) ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Car`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Car` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Car` (
  `PlateNumber` VARCHAR(7) NOT NULL ,
  `UserID` INT ,
  `Model` VARCHAR(45) NULL ,
  `Color` VARCHAR(45) NULL ,
  PRIMARY KEY (`PlateNumber`) ,
  INDEX `Owning_idx` (`UserID` ASC) ,
  CONSTRAINT `Owning`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Proposal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Proposal` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Proposal` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `UserID` INT ,
  `CarID` VARCHAR(7) ,
  `KmCost` FLOAT ,
  `AvailableSeats` TINYINT ,
  PRIMARY KEY (`ID`) ,
  INDEX `Proposing_idx` (`UserID` ASC) ,
  INDEX `Using_idx` (`CarID` ASC) ,
  CONSTRAINT `Proposing`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Using`
    FOREIGN KEY (`CarID` )
    REFERENCES `CarPooling`.`Car` (`PlateNumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Account` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Account` (
  `UserID` INT NOT NULL ,
  `Balance` INT ,
  UNIQUE INDEX `UserID_UNIQUE` (`UserID` ASC) ,
  PRIMARY KEY (`UserID`) ,
  CONSTRAINT `Prossessing`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Assessment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Assessment` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Assessment` (
  `UserID` INT NOT NULL ,
  `Rating` SMALLINT ,
  `Comment` LONGTEXT NULL ,
  `Type` TINYINT(1) ,
  INDEX `Grading_idx` (`UserID` ASC) ,
  PRIMARY KEY (`UserID`) ,
  CONSTRAINT `Grading`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Request` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Request` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `UserID` INT ,
  `DepartureCoordinateX` DOUBLE ,
  `DepartureCoordinateY` DOUBLE ,
  `DepartureAddress` VARCHAR(45) ,
  `ArrivalCoordinateX` DOUBLE ,
  `ArrivalCoordinateY` DOUBLE ,
  `ArrivalAddress` VARCHAR(45) ,
  `NecessarySeats` TINYINT ,
  `ToleranceTime` INT NULL ,
  `ToleranceWalkDistance` INT NULL ,
  `TolerancePrice` INT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `Submitting_idx` (`UserID` ASC) ,
  CONSTRAINT `Submitting`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Traject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Traject` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Traject` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `UserID` INT NULL ,
  `RequestID` INT ,
  `ProposalID` INT ,
  `RerservedSeats` TINYINT ,
  `TotalCost` FLOAT ,
  PRIMARY KEY (`ID`) ,
  INDEX `Chossing_idx` (`UserID` ASC) ,
  INDEX `Corresponding_idx` (`RequestID` ASC) ,
  INDEX `Including_idx` (`ProposalID` ASC) ,
  CONSTRAINT `Chossing`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Corresponding`
    FOREIGN KEY (`RequestID` )
    REFERENCES `CarPooling`.`Request` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Including`
    FOREIGN KEY (`ProposalID` )
    REFERENCES `CarPooling`.`Proposal` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`PickupPoint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`PickupPoint` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`PickupPoint` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `Name` VARCHAR(45) NULL ,
  `CoordinateX` DOUBLE ,
  `CoordinateY` DOUBLE ,
  `Address` VARCHAR(45) ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Itineray`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Itinerary` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Itinerary` (
  `PickupPointID` INT NOT NULL ,
  `ProposalID` INT NOT NULL ,
  `DepartureTime` DATETIME ,
  `ArrivalTime` DATETIME NULL ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
  INDEX `prop_idx` (`ProposalID` ASC) ,
  PRIMARY KEY (`PickupPointID`, `ProposalID`) ,
  CONSTRAINT `pp`
    FOREIGN KEY (`PickupPointID` )
    REFERENCES `CarPooling`.`PickupPoint` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `prop`
    FOREIGN KEY (`ProposalID` )
    REFERENCES `CarPooling`.`Proposal` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Composition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CarPooling`.`Composition` ;

CREATE  TABLE IF NOT EXISTS `CarPooling`.`Composition` (
  `PickupPointID` INT NOT NULL ,
  `TrajectID` INT NOT NULL ,
  `Type` TINYINT(1) ,
  `Time` DATETIME ,
  INDEX `traject_idx` (`TrajectID` ASC) ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
  PRIMARY KEY (`PickupPointID`, `TrajectID`) ,
  CONSTRAINT `pp2`
    FOREIGN KEY (`PickupPointID` )
    REFERENCES `CarPooling`.`PickupPoint` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `traject`
    FOREIGN KEY (`TrajectID` )
    REFERENCES `CarPooling`.`Traject` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `carpooling` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
