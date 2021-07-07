package com.bnhp.db;

import com.bnhp.utils.DBUtils;

import java.sql.SQLException;


public class DatabaseManager {

    public static final String URL = "jdbc:mysql://localhost:3306" +
            "?createDatabaseIfNotExist=FALSE" +
            "&useTimezone=TRUE" +
            "&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "efrat1988";


    private static final String CREATE_SCHEMA = "CREATE SCHEMA `bnhp-pro1`";
    private static final String DROP_SCHEMA = "DROP SCHEMA `bnhp-pro1`";
    private static final String CREATE_TABLE_COMPANY = "CREATE TABLE `bnhp-pro1`.`company` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `bnhp-pro1`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `bnhp-pro1`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `bnhp-pro1`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `bnhp-pro1`.`company` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `bnhp-pro1`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";
    private static final String CREATE_TABLE_CUSTOMER_VS_COUPONS = "CREATE TABLE `bnhp-pro1`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `bnhp-pro1`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `bnhp-pro1`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";


    public static void dropAndCreate() throws SQLException {
        DBUtils.runStatement(DROP_SCHEMA);
        DBUtils.runStatement(CREATE_SCHEMA);
        DBUtils.runStatement(CREATE_TABLE_COMPANY);
        DBUtils.runStatement(CREATE_TABLE_CUSTOMERS);
        DBUtils.runStatement(CREATE_TABLE_CATEGORIES);
        DBUtils.runStatement(CREATE_TABLE_COUPONS);
        DBUtils.runStatement(CREATE_TABLE_CUSTOMER_VS_COUPONS);
    }
}


