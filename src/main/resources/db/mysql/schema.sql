CREATE TABLE IF NOT EXISTS account_types (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS customers (
  id         INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  email      VARCHAR(255),
  phone      VARCHAR(20),
  address    VARCHAR(255),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS accounts (
  id             INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(20),
  opened_date    DATE,
  balance        DECIMAL(19,2) DEFAULT 0.00,
  type_id        INT(4) UNSIGNED NOT NULL,
  customer_id    INT(4) UNSIGNED,
  INDEX(account_number),
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (type_id) REFERENCES account_types(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS transactions (
  id               INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  account_id       INT(4) UNSIGNED,
  transaction_date DATE,
  amount           DECIMAL(19,2),
  transaction_type VARCHAR(20),
  description      VARCHAR(255),
  balance_after    DECIMAL(19,2),
  FOREIGN KEY (account_id) REFERENCES accounts(id)
) engine=InnoDB;
