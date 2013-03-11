SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `CarPooling` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `CarPooling` ;

-- -----------------------------------------------------
-- Table `CarPooling`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CarPooling`.`User` (
  `ID` INT NOT NULL ,
  `Login` VARCHAR(45) NOT NULL ,
  `LastName` VARCHAR(45) NOT NULL COMMENT '	' ,
  `FirstName` VARCHAR(45) NOT NULL ,
  `AccountNumber` INT NULL ,
  `PhoneNumber` VARCHAR(45) NULL ,
  `Email` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Car`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Car` (
  `PlateNumber` VARCHAR(7) NOT NULL ,
  `UserID` INT NOT NULL ,
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
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Proposal` (
  `ID` INT NOT NULL ,
  `UserID` INT NOT NULL ,
  `CarID` VARCHAR(7) NOT NULL ,
  `KmCost` FLOAT NOT NULL ,
  `AvailableSeats` TINYINT NOT NULL ,
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
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Account` (
  `UserID` INT NOT NULL ,
  `Balance` INT NOT NULL ,
  UNIQUE INDEX `UserID_UNIQUE` (`UserID` ASC) ,
  CONSTRAINT `Prossessing`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Assessment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Assessment` (
  `UserID` INT NOT NULL ,
  `Rating` SMALLINT NOT NULL ,
  `Comment` LONGTEXT NULL ,
  `Type` TINYINT(1) NOT NULL ,
  INDEX `Grading_idx` (`UserID` ASC) ,
  CONSTRAINT `Grading`
    FOREIGN KEY (`UserID` )
    REFERENCES `CarPooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Request`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Request` (
  `ID` INT NOT NULL ,
  `UserID` INT NOT NULL ,
  `DepartureCoordinateX` FLOAT NOT NULL ,
  `DepartureCoordinateY` FLOAT NOT NULL ,
  `DepartureAddress` VARCHAR(45) NOT NULL ,
  `ArrivalCoordinateX` FLOAT NOT NULL ,
  `ArrivalCoordinateY` FLOAT NOT NULL ,
  `ArrivalAddress` VARCHAR(45) NOT NULL ,
  `NecessarySeats` TINYINT NOT NULL ,
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
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Traject` (
  `ID` INT NOT NULL ,
  `UserID` INT NULL ,
  `RequestID` INT NOT NULL ,
  `ProposalID` INT NOT NULL ,
  `RerservedSeats` TINYINT NOT NULL ,
  `TotalCost` FLOAT NOT NULL ,
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
CREATE  TABLE IF NOT EXISTS `CarPooling`.`PickupPoint` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(45) NULL ,
  `CoordinateX` FLOAT NOT NULL ,
  `CoordinateY` FLOAT NOT NULL ,
  `Address` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CarPooling`.`Itineray`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Itineray` (
  `PickupPointID` INT NOT NULL ,
  `ProposalID` INT NOT NULL ,
  `DepartureTime` DATETIME NOT NULL ,
  `ArrivalTime` DATETIME NULL ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
  INDEX `prop_idx` (`ProposalID` ASC) ,
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
CREATE  TABLE IF NOT EXISTS `CarPooling`.`Composition` (
  `PickupPointID` INT NOT NULL ,
  `TrajectID` INT NOT NULL ,
  `Type` TINYINT(1) NOT NULL ,
  `Time` DATETIME NOT NULL ,
  INDEX `traject_idx` (`TrajectID` ASC) ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
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

USE `CarPooling` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
