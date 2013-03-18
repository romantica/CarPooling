SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `carpooling` DEFAULT CHARACTER SET utf8 ;
USE `carpooling` ;

-- -----------------------------------------------------
-- Table `carpooling`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`User` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `Login` VARCHAR(45) NOT NULL ,
  `Password` VARCHAR(45) NOT NULL ,
  `LastName` VARCHAR(45) NOT NULL COMMENT '	' ,
  `FirstName` VARCHAR(45) NOT NULL ,
  `AccountNumber` INT(11) NULL DEFAULT NULL ,
  `PhoneNumber` VARCHAR(45) NULL DEFAULT NULL ,
  `Email` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Account`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Account` (
  `UserID` INT(11) NOT NULL ,
  `Balance` INT(11) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`UserID`) ,
  UNIQUE INDEX `UserID_UNIQUE` (`UserID` ASC) ,
  CONSTRAINT `Prossessing`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Assessment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Assessment` (
  `UserID` INT(11) NOT NULL ,
  `Rating` SMALLINT(6) NOT NULL ,
  `Comment` LONGTEXT NULL DEFAULT NULL ,
  `Type` TINYINT(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`UserID`) ,
  INDEX `Grading_idx` (`UserID` ASC) ,
  CONSTRAINT `Grading`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Car`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Car` (
  `PlateNumber` VARCHAR(7) NOT NULL ,
  `UserID` INT(11) NOT NULL ,
  `Model` VARCHAR(45) NULL DEFAULT NULL ,
  `Color` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`PlateNumber`) ,
  INDEX `Owning_idx` (`UserID` ASC) ,
  CONSTRAINT `Owning`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`PickupPoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`PickupPoint` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `Name` VARCHAR(45) NULL DEFAULT NULL ,
  `CoordinateX` DOUBLE NOT NULL ,
  `CoordinateY` DOUBLE NOT NULL ,
  `Address` VARCHAR(45) NULL DEFAULT NULL ,
  `Description` LONGTEXT NULL DEFAULT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Request`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Request` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `UserID` INT(11) NOT NULL ,
  `DepartureCoordinateX` DOUBLE NOT NULL ,
  `DepartureCoordinateY` DOUBLE NOT NULL ,
  `DepartureAddress` VARCHAR(45) NOT NULL ,
  `ArrivalCoordinateX` DOUBLE NOT NULL ,
  `ArrivalCoordinateY` DOUBLE NOT NULL ,
  `ArrivalAddress` VARCHAR(45) NOT NULL ,
  `NecessarySeats` TINYINT(4) NOT NULL ,
  `ToleranceTime` INT(11) NOT NULL ,
  `ToleranceWalkDistance` INT(11) NOT NULL ,
  `TolerancePrice` INT(11) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `Submitting_idx` (`UserID` ASC) ,
  CONSTRAINT `Submitting`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Proposal`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Proposal` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `UserID` INT(11) NOT NULL ,
  `CarID` VARCHAR(7) NOT NULL ,
  `KmCost` FLOAT NOT NULL ,
  `AvailableSeats` TINYINT(4) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `Proposing_idx` (`UserID` ASC) ,
  INDEX `Using_idx` (`CarID` ASC) ,
  CONSTRAINT `Proposing`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Using`
    FOREIGN KEY (`CarID` )
    REFERENCES `carpooling`.`Car` (`PlateNumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Traject`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Traject` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `UserID` INT(11) NOT NULL ,
  `RequestID` INT(11) NOT NULL ,
  `ProposalID` INT(11) NOT NULL ,
  `RerservedSeats` TINYINT(4) NOT NULL ,
  `TotalCost` FLOAT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `Chossing_idx` (`UserID` ASC) ,
  INDEX `Corresponding_idx` (`RequestID` ASC) ,
  INDEX `Including_idx` (`ProposalID` ASC) ,
  CONSTRAINT `Chossing`
    FOREIGN KEY (`UserID` )
    REFERENCES `carpooling`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Corresponding`
    FOREIGN KEY (`RequestID` )
    REFERENCES `carpooling`.`Request` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Including`
    FOREIGN KEY (`ProposalID` )
    REFERENCES `carpooling`.`Proposal` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Composition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Composition` (
  `PickupPointID` INT(11) NOT NULL ,
  `TrajectID` INT(11) NOT NULL ,
  `Type` TINYINT(1) NOT NULL ,
  `Time` DATETIME NOT NULL ,
  PRIMARY KEY (`PickupPointID`, `TrajectID`) ,
  INDEX `traject_idx` (`TrajectID` ASC) ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
  CONSTRAINT `pp2`
    FOREIGN KEY (`PickupPointID` )
    REFERENCES `carpooling`.`PickupPoint` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `traject`
    FOREIGN KEY (`TrajectID` )
    REFERENCES `carpooling`.`Traject` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `carpooling`.`Itinerary`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `carpooling`.`Itinerary` (
  `PickupPointID` INT(11) NOT NULL ,
  `ProposalID` INT(11) NOT NULL ,
  `DepartureTime` DATETIME NOT NULL ,
  `ArrivalTime` DATETIME NOT NULL ,
  PRIMARY KEY (`PickupPointID`, `ProposalID`) ,
  INDEX `pp_idx` (`PickupPointID` ASC) ,
  INDEX `prop_idx` (`ProposalID` ASC) ,
  CONSTRAINT `pp`
    FOREIGN KEY (`PickupPointID` )
    REFERENCES `carpooling`.`PickupPoint` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `prop`
    FOREIGN KEY (`ProposalID` )
    REFERENCES `carpooling`.`Proposal` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `carpooling` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
