-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema globemed_healthcare
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `globemed_healthcare` DEFAULT CHARACTER SET utf8 ;
USE `globemed_healthcare` ;

-- -----------------------------------------------------
-- Table `globemed_healthcare`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`gender`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`gender` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gender` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`branch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`branch` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `branch` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `nic` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `address` VARCHAR(300) NOT NULL,
  `password` VARCHAR(255) NOT NULL, -- Increased password length for hashing
  `status_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `last_login` DATETIME NULL,
  `gender_id` INT NOT NULL,
  `branch_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_admin_status_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_staff_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_staff_gender1_idx` (`gender_id` ASC) VISIBLE,
  INDEX `fk_staff_branch1_idx` (`branch_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `globemed_healthcare`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `globemed_healthcare`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_gender1`
    FOREIGN KEY (`gender_id`)
    REFERENCES `globemed_healthcare`.`gender` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_branch1`
    FOREIGN KEY (`branch_id`)
    REFERENCES `globemed_healthcare`.`branch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`blood_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`blood_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `blood_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`patient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `nic` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `gender_id` INT NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `blood_type_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  -- For encryption, sensitive fields could be stored as BLOB/VARBINARY
  `encrypted_medical_history` BLOB NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_patient_gender1_idx` (`gender_id` ASC) VISIBLE,
  INDEX `fk_patient_blood_type1_idx` (`blood_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_patient_gender1`
    FOREIGN KEY (`gender_id`)
    REFERENCES `globemed_healthcare`.`gender` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_blood_type1`
    FOREIGN KEY (`blood_type_id`)
    REFERENCES `globemed_healthcare`.`blood_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`doctor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `nic` VARCHAR(45) NOT NULL,
  `license_number` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `gender_id` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL, -- Increased password length for hashing
  `status_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `specialization` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_doctor_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_doctor_gender1_idx` (`gender_id` ASC) VISIBLE,
  CONSTRAINT `fk_doctor_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `globemed_healthcare`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctor_gender1`
    FOREIGN KEY (`gender_id`)
    REFERENCES `globemed_healthcare`.`gender` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`patient_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`patient_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `patient_id` INT NOT NULL,
  `visit_date` DATETIME NOT NULL,
  `doctor_id` INT NOT NULL,
  `diagnosis` VARCHAR(300) NOT NULL,
  `treatment` VARCHAR(300) NOT NULL,
  `prescription` VARCHAR(300) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_patient_history_patient1_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_patient_history_doctor1_idx` (`doctor_id` ASC) VISIBLE,
  CONSTRAINT `fk_patient_history_patient1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `globemed_healthcare`.`patient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_history_doctor1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `globemed_healthcare`.`doctor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`hospital`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`hospital` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `phone_1` VARCHAR(15) NOT NULL,
  `phone_2` VARCHAR(15) NULL,
  `email` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `branch_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_hospital_branch1_idx` (`branch_id` ASC) VISIBLE,
  CONSTRAINT `fk_hospital_branch1`
    FOREIGN KEY (`branch_id`)
    REFERENCES `globemed_healthcare`.`branch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`admin` - DEPRECATED
-- This table seems redundant if staff can have an 'Admin' role.
-- Keeping it for now as per original schema but should be reviewed.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`pharmacy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`pharmacy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `hospital_id` INT NOT NULL,
  `opening_h` TIME NOT NULL,
  `closing_h` TIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pharmacy_hospital1_idx` (`hospital_id` ASC) VISIBLE,
  CONSTRAINT `fk_pharmacy_hospital1`
    FOREIGN KEY (`hospital_id`)
    REFERENCES `globemed_healthcare`.`hospital` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`insurance_provider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`insurance_provider` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`insurance_agent`
-- This might be better modeled as a staff member with a specific role.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`insurance_agent` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `nic` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `last_login` DATETIME NULL,
  `gender_id` INT NOT NULL,
  `insurance_provider_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_insurance_agent_gender1_idx` (`gender_id` ASC) VISIBLE,
  INDEX `fk_insurance_agent_insurance_provider1_idx` (`insurance_provider_id` ASC) VISIBLE,
  CONSTRAINT `fk_insurance_agent_gender1`
    FOREIGN KEY (`gender_id`)
    REFERENCES `globemed_healthcare`.`gender` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_insurance_agent_insurance_provider1`
    FOREIGN KEY (`insurance_provider_id`)
    REFERENCES `globemed_healthcare`.`insurance_provider` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `globemed_healthcare`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`appointment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `patient_id` INT NOT NULL,
  `doctor_id` INT NOT NULL,
  `date_time` DATETIME NOT NULL,
  `reason` VARCHAR(200) NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `status_id` INT NOT NULL,
  `total` DOUBLE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_appointment_patient1_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_appointment_doctor1_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `fk_appointment_status1_idx` (`status_id` ASC) VISIBLE,
  CONSTRAINT `fk_appointment_patient1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `globemed_healthcare`.`patient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_doctor1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `globemed_healthcare`.`doctor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `globemed_healthcare`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- ADDED TABLES FOR NEW REQUIREMENTS
-- -----------------------------------------------------

-- For Part D (Staff Roles & Permissions)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL UNIQUE,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`role_permission` (
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`),
  INDEX `fk_role_permission_permission1_idx` (`permission_id` ASC) VISIBLE,
  INDEX `fk_role_permission_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_role_permission_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `globemed_healthcare`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_permission_permission1`
    FOREIGN KEY (`permission_id`)
    REFERENCES `globemed_healthcare`.`permission` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- For Part A & F (Audit Logging)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`audit_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `staff_id` INT NULL, -- Can be null if action is not user-initiated
  `action` VARCHAR(255) NOT NULL,
  `target_entity` VARCHAR(100) NULL,
  `target_id` VARCHAR(255) NULL,
  `timestamp` DATETIME NOT NULL,
  `details` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_audit_log_staff1_idx` (`staff_id` ASC) VISIBLE,
  CONSTRAINT `fk_audit_log_staff1`
    FOREIGN KEY (`staff_id`)
    REFERENCES `globemed_healthcare`.`staff` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- For Part F (Memento Snapshots)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`memento_snapshots` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `patient_id` INT NOT NULL,
  `snapshot_data` JSON NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by_staff_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_memento_snapshots_patient1_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_memento_snapshots_staff1_idx` (`created_by_staff_id` ASC) VISIBLE,
  CONSTRAINT `fk_memento_snapshots_patient1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `globemed_healthcare`.`patient` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_memento_snapshots_staff1`
    FOREIGN KEY (`created_by_staff_id`)
    REFERENCES `globemed_healthcare`.`staff` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- For Part C (Billing & Insurance Claims)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`claim` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id` INT NOT NULL,
  `insurance_provider_id` INT NULL,
  `amount` DOUBLE NOT NULL,
  `status` VARCHAR(45) NOT NULL, -- e.g., 'PENDING', 'APPROVED', 'REJECTED'
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_claim_appointment1_idx` (`appointment_id` ASC) VISIBLE,
  INDEX `fk_claim_insurance_provider1_idx` (`insurance_provider_id` ASC) VISIBLE,
  CONSTRAINT `fk_claim_appointment1`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `globemed_healthcare`.`appointment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_claim_insurance_provider1`
    FOREIGN KEY (`insurance_provider_id`)
    REFERENCES `globemed_healthcare`.`insurance_provider` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`claim_handler_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `claim_id` INT NOT NULL,
  `handler_name` VARCHAR(100) NOT NULL,
  `outcome` VARCHAR(255) NOT NULL,
  `processed_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_claim_handler_history_claim1_idx` (`claim_id` ASC) VISIBLE,
  CONSTRAINT `fk_claim_handler_history_claim1`
    FOREIGN KEY (`claim_id`)
    REFERENCES `globemed_healthcare`.`claim` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- For Part B (Appointment Scheduling)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globemed_healthcare`.`appointment_conflicts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id_1` INT NOT NULL,
  `appointment_id_2` INT NOT NULL,
  `conflict_type` VARCHAR(100) NOT NULL, -- e.g., 'DOCTOR_DOUBLE_BOOKED', 'ROOM_UNAVAILABLE'
  `detected_at` DATETIME NOT NULL,
  `resolved_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_conflicts_appointment1_idx` (`appointment_id_1` ASC) VISIBLE,
  INDEX `fk_conflicts_appointment2_idx` (`appointment_id_2` ASC) VISIBLE,
  CONSTRAINT `fk_conflicts_appointment1`
    FOREIGN KEY (`appointment_id_1`)
    REFERENCES `globemed_healthcare`.`appointment` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_conflicts_appointment2`
    FOREIGN KEY (`appointment_id_2`)
    REFERENCES `globemed_healthcare`.`appointment` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
