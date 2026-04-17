CREATE DATABASE IF NOT EXISTS wedding_planner;
USE wedding_planner;

CREATE TABLE IF NOT EXISTS weddings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    partner1_name VARCHAR(255),
    partner2_name VARCHAR(255),
    wedding_date DATE,
    guest_count INT,
    package_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS budgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wedding_id BIGINT,
    category VARCHAR(255),
    allocated_amount DOUBLE,
    spent_amount DOUBLE,
    FOREIGN KEY (wedding_id) REFERENCES weddings(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wedding_id BIGINT,
    amount DOUBLE,
    type VARCHAR(255),
    status VARCHAR(255),
    method VARCHAR(255),
    FOREIGN KEY (wedding_id) REFERENCES weddings(id)
);