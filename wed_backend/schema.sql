CREATE DATABASE IF NOT EXISTS wedding_planner;
USE wedding_planner;

CREATE TABLE IF NOT EXISTS weddings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    partner1_name VARCHAR(255),
    partner2_name VARCHAR(255),
    wedding_date DATE,
    guest_count INT,
    package_type VARCHAR(255),
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS budgets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wedding_id INT,
    category VARCHAR(255),
    allocated_amount DECIMAL(10,2),
    spent_amount DECIMAL(10,2),
    remaining_amount DECIMAL(10,2),
    FOREIGN KEY (wedding_id) REFERENCES weddings(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wedding_id INT,
    amount DECIMAL(10,2),
    type VARCHAR(50),
    status VARCHAR(50),
    payment_date DATETIME,
    payment_method VARCHAR(255),
    transaction_id VARCHAR(255),
    FOREIGN KEY (wedding_id) REFERENCES weddings(id)
);
