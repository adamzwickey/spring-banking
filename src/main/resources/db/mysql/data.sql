INSERT IGNORE INTO account_types VALUES (1, 'Checking');
INSERT IGNORE INTO account_types VALUES (2, 'Savings');
INSERT IGNORE INTO account_types VALUES (3, 'Money Market');
INSERT IGNORE INTO account_types VALUES (4, 'CD');

INSERT IGNORE INTO customers VALUES (1, 'George', 'Franklin', 'george.franklin@email.com', '6085551023', '110 W. Liberty St.');
INSERT IGNORE INTO customers VALUES (2, 'Betty', 'Davis', 'betty.davis@email.com', '6085551749', '638 Cardinal Ave.');
INSERT IGNORE INTO customers VALUES (3, 'Eduardo', 'Rodriquez', 'eduardo.rodriquez@email.com', '6085558763', '2693 Commerce St.');
INSERT IGNORE INTO customers VALUES (4, 'Harold', 'Davis', 'harold.davis@email.com', '6085553198', '563 Friendly St.');
INSERT IGNORE INTO customers VALUES (5, 'Peter', 'McTavish', 'peter.mctavish@email.com', '6085552765', '2387 S. Fair Way');
INSERT IGNORE INTO customers VALUES (6, 'Jean', 'Coleman', 'jean.coleman@email.com', '6085552654', '105 N. Lake St.');
INSERT IGNORE INTO customers VALUES (7, 'Jeff', 'Black', 'jeff.black@email.com', '6085555387', '1450 Oak Blvd.');
INSERT IGNORE INTO customers VALUES (8, 'Maria', 'Escobito', 'maria.escobito@email.com', '6085557683', '345 Maple St.');
INSERT IGNORE INTO customers VALUES (9, 'David', 'Schroeder', 'david.schroeder@email.com', '6085559435', '2749 Blackhawk Trail');
INSERT IGNORE INTO customers VALUES (10, 'Carlos', 'Estaban', 'carlos.estaban@email.com', '6085555487', '2335 Independence La.');

INSERT IGNORE INTO accounts VALUES (1, 'CHK-10001', '2020-01-15', 5250.00, 1, 1);
INSERT IGNORE INTO accounts VALUES (2, 'SAV-10002', '2020-03-20', 12500.00, 2, 2);
INSERT IGNORE INTO accounts VALUES (3, 'CHK-10003', '2019-06-10', 3200.00, 1, 3);
INSERT IGNORE INTO accounts VALUES (4, 'CHK-10004', '2019-06-10', 8750.00, 1, 3);
INSERT IGNORE INTO accounts VALUES (5, 'SAV-10005', '2021-02-28', 45000.00, 2, 4);
INSERT IGNORE INTO accounts VALUES (6, 'MMA-10006', '2018-11-05', 15000.00, 3, 5);
INSERT IGNORE INTO accounts VALUES (7, 'CHK-10007', '2017-09-12', 2100.00, 1, 6);
INSERT IGNORE INTO accounts VALUES (8, 'SAV-10008', '2017-09-12', 8900.00, 2, 6);
INSERT IGNORE INTO accounts VALUES (9, 'CD-10009', '2022-01-01', 25000.00, 4, 7);
INSERT IGNORE INTO accounts VALUES (10, 'CHK-10010', '2020-07-15', 1500.00, 1, 8);
INSERT IGNORE INTO accounts VALUES (11, 'SAV-10011', '2019-04-22', 6700.00, 2, 9);
INSERT IGNORE INTO accounts VALUES (12, 'CHK-10012', '2021-08-30', 4200.00, 1, 10);
INSERT IGNORE INTO accounts VALUES (13, 'SAV-10013', '2021-08-30', 18500.00, 2, 10);

INSERT IGNORE INTO transactions VALUES (1, 7, '2023-01-15', 500.00, 'DEPOSIT', 'Paycheck deposit', 2100.00);
INSERT IGNORE INTO transactions VALUES (2, 8, '2023-01-20', 200.00, 'DEPOSIT', 'Transfer from checking', 8900.00);
INSERT IGNORE INTO transactions VALUES (3, 8, '2023-02-01', -50.00, 'WITHDRAWAL', 'ATM withdrawal', 8850.00);
INSERT IGNORE INTO transactions VALUES (4, 7, '2023-02-05', -75.00, 'WITHDRAWAL', 'Grocery store', 2025.00);
