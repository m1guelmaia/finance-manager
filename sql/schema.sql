CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(75) NOT NULL,
    value DECIMAL(13, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    category VARCHAR(75) NOT NULL,
    transaction_type ENUM('INCOME', 'EXPENSE') NOT NULL
);