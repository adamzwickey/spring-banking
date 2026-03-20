INSERT INTO account_types (name) SELECT 'Checking' WHERE NOT EXISTS (SELECT * FROM account_types WHERE name='Checking');
INSERT INTO account_types (name) SELECT 'Savings' WHERE NOT EXISTS (SELECT * FROM account_types WHERE name='Savings');
INSERT INTO account_types (name) SELECT 'Money Market' WHERE NOT EXISTS (SELECT * FROM account_types WHERE name='Money Market');
INSERT INTO account_types (name) SELECT 'CD' WHERE NOT EXISTS (SELECT * FROM account_types WHERE name='CD');

INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'George', 'Franklin', 'george.franklin@email.com', '6085551023', '110 W. Liberty St.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=1);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Betty', 'Davis', 'betty.davis@email.com', '6085551749', '638 Cardinal Ave.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=2);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Eduardo', 'Rodriquez', 'eduardo.rodriquez@email.com', '6085558763', '2693 Commerce St.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=3);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Harold', 'Davis', 'harold.davis@email.com', '6085553198', '563 Friendly St.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=4);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Peter', 'McTavish', 'peter.mctavish@email.com', '6085552765', '2387 S. Fair Way' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=5);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Jean', 'Coleman', 'jean.coleman@email.com', '6085552654', '105 N. Lake St.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=6);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Jeff', 'Black', 'jeff.black@email.com', '6085555387', '1450 Oak Blvd.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=7);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Maria', 'Escobito', 'maria.escobito@email.com', '6085557683', '345 Maple St.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=8);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'David', 'Schroeder', 'david.schroeder@email.com', '6085559435', '2749 Blackhawk Trail' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=9);
INSERT INTO customers (first_name, last_name, email, phone, address) SELECT 'Carlos', 'Estaban', 'carlos.estaban@email.com', '6085555487', '2335 Independence La.' WHERE NOT EXISTS (SELECT * FROM customers WHERE id=10);

INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10001', '2020-01-15', 5250.00, 1, 1 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=1);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'SAV-10002', '2020-03-20', 12500.00, 2, 2 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=2);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10003', '2019-06-10', 3200.00, 1, 3 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=3);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10004', '2019-06-10', 8750.00, 1, 3 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=4);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'SAV-10005', '2021-02-28', 45000.00, 2, 4 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=5);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'MMA-10006', '2018-11-05', 15000.00, 3, 5 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=6);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10007', '2017-09-12', 2100.00, 1, 6 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=7);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'SAV-10008', '2017-09-12', 8900.00, 2, 6 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=8);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CD-10009', '2022-01-01', 25000.00, 4, 7 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=9);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10010', '2020-07-15', 1500.00, 1, 8 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=10);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'SAV-10011', '2019-04-22', 6700.00, 2, 9 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=11);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'CHK-10012', '2021-08-30', 4200.00, 1, 10 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=12);
INSERT INTO accounts (account_number, opened_date, balance, type_id, customer_id) SELECT 'SAV-10013', '2021-08-30', 18500.00, 2, 10 WHERE NOT EXISTS (SELECT * FROM accounts WHERE id=13);

INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-01-15', 500.00, 'DEPOSIT', 'Paycheck deposit', 2100.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=1);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-01-20', 200.00, 'DEPOSIT', 'Transfer from checking', 8900.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=2);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-02-01', -50.00, 'WITHDRAWAL', 'ATM withdrawal', 8850.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=3);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-02-05', -75.00, 'WITHDRAWAL', 'Grocery store', 2025.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=4);
