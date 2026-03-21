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

-- Account 1: CHK-10001 (George Franklin), ending balance 5250.00
INSERT IGNORE INTO transactions VALUES (1, 1, '2020-02-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00);
INSERT IGNORE INTO transactions VALUES (2, 1, '2020-03-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 4000.00);
INSERT IGNORE INTO transactions VALUES (3, 1, '2020-03-15', -85.50, 'WITHDRAWAL', 'Electric and gas bill', 3914.50);
INSERT IGNORE INTO transactions VALUES (4, 1, '2020-04-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 6414.50);
INSERT IGNORE INTO transactions VALUES (5, 1, '2020-04-10', -1200.00, 'WITHDRAWAL', 'Rent payment', 5214.50);
INSERT IGNORE INTO transactions VALUES (6, 1, '2020-05-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 7714.50);
INSERT IGNORE INTO transactions VALUES (7, 1, '2020-05-20', -2464.50, 'WITHDRAWAL', 'Wire transfer to savings', 5250.00);

-- Account 2: SAV-10002 (Betty Davis), ending balance 12500.00
INSERT IGNORE INTO transactions VALUES (8, 2, '2020-04-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00);
INSERT IGNORE INTO transactions VALUES (9, 2, '2020-07-15', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 7000.00);
INSERT IGNORE INTO transactions VALUES (10, 2, '2020-10-20', 1500.00, 'DEPOSIT', 'Bonus savings', 8500.00);
INSERT IGNORE INTO transactions VALUES (11, 2, '2021-01-05', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 10500.00);
INSERT IGNORE INTO transactions VALUES (12, 2, '2021-04-10', -500.00, 'WITHDRAWAL', 'Emergency fund withdrawal', 10000.00);
INSERT IGNORE INTO transactions VALUES (13, 2, '2021-08-01', 1500.00, 'DEPOSIT', 'Monthly savings transfer', 11500.00);
INSERT IGNORE INTO transactions VALUES (14, 2, '2021-12-15', 1000.00, 'DEPOSIT', 'Holiday bonus savings', 12500.00);

-- Account 3: CHK-10003 (Eduardo Rodriguez), ending balance 3200.00
INSERT IGNORE INTO transactions VALUES (15, 3, '2019-07-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00);
INSERT IGNORE INTO transactions VALUES (16, 3, '2019-08-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 3300.00);
INSERT IGNORE INTO transactions VALUES (17, 3, '2019-08-15', -120.00, 'WITHDRAWAL', 'Grocery store', 3180.00);
INSERT IGNORE INTO transactions VALUES (18, 3, '2019-09-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 4980.00);
INSERT IGNORE INTO transactions VALUES (19, 3, '2019-09-10', -400.00, 'WITHDRAWAL', 'Rent payment', 4580.00);
INSERT IGNORE INTO transactions VALUES (20, 3, '2019-10-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 6380.00);
INSERT IGNORE INTO transactions VALUES (21, 3, '2019-10-20', -3180.00, 'WITHDRAWAL', 'Wire transfer to savings', 3200.00);

-- Account 4: CHK-10004 (Eduardo Rodriguez), ending balance 8750.00
INSERT IGNORE INTO transactions VALUES (22, 4, '2019-07-01', 3000.00, 'DEPOSIT', 'Opening deposit', 3000.00);
INSERT IGNORE INTO transactions VALUES (23, 4, '2019-09-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 6000.00);
INSERT IGNORE INTO transactions VALUES (24, 4, '2019-10-15', -250.00, 'WITHDRAWAL', 'Insurance payment', 5750.00);
INSERT IGNORE INTO transactions VALUES (25, 4, '2020-01-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 8750.00);
INSERT IGNORE INTO transactions VALUES (26, 4, '2020-03-10', -500.00, 'WITHDRAWAL', 'Car repairs', 8250.00);
INSERT IGNORE INTO transactions VALUES (27, 4, '2020-06-01', 1500.00, 'DEPOSIT', 'Side income', 9750.00);
INSERT IGNORE INTO transactions VALUES (28, 4, '2020-08-15', -1000.00, 'WITHDRAWAL', 'Vacation expense', 8750.00);

-- Account 5: SAV-10005 (Harold Davis), ending balance 45000.00
INSERT IGNORE INTO transactions VALUES (29, 5, '2021-03-01', 10000.00, 'DEPOSIT', 'Initial savings deposit', 10000.00);
INSERT IGNORE INTO transactions VALUES (30, 5, '2021-06-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 20000.00);
INSERT IGNORE INTO transactions VALUES (31, 5, '2021-08-15', 5000.00, 'DEPOSIT', 'Annual bonus savings', 25000.00);
INSERT IGNORE INTO transactions VALUES (32, 5, '2021-09-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 35000.00);
INSERT IGNORE INTO transactions VALUES (33, 5, '2021-11-10', -2000.00, 'WITHDRAWAL', 'Emergency home repair', 33000.00);
INSERT IGNORE INTO transactions VALUES (34, 5, '2022-01-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 43000.00);
INSERT IGNORE INTO transactions VALUES (35, 5, '2022-02-20', 2000.00, 'DEPOSIT', 'Tax refund deposit', 45000.00);

-- Account 6: MMA-10006 (Peter McTavish), ending balance 15000.00
INSERT IGNORE INTO transactions VALUES (36, 6, '2018-12-01', 8000.00, 'DEPOSIT', 'Initial MMA deposit', 8000.00);
INSERT IGNORE INTO transactions VALUES (37, 6, '2019-03-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 10000.00);
INSERT IGNORE INTO transactions VALUES (38, 6, '2019-06-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 12000.00);
INSERT IGNORE INTO transactions VALUES (39, 6, '2019-09-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 14000.00);
INSERT IGNORE INTO transactions VALUES (40, 6, '2019-11-15', -500.00, 'WITHDRAWAL', 'Emergency withdrawal', 13500.00);
INSERT IGNORE INTO transactions VALUES (41, 6, '2020-01-01', 2000.00, 'DEPOSIT', 'Annual bonus deposit', 15500.00);
INSERT IGNORE INTO transactions VALUES (42, 6, '2020-03-10', -500.00, 'WITHDRAWAL', 'Account management fee', 15000.00);

-- Account 7: CHK-10007 (Jean Coleman), ending balance 2100.00
INSERT IGNORE INTO transactions VALUES (43, 7, '2023-01-15', 500.00, 'DEPOSIT', 'Paycheck deposit', 2100.00);
INSERT IGNORE INTO transactions VALUES (44, 7, '2023-02-05', -75.00, 'WITHDRAWAL', 'Grocery store', 2025.00);
INSERT IGNORE INTO transactions VALUES (45, 7, '2023-03-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2525.00);
INSERT IGNORE INTO transactions VALUES (46, 7, '2023-03-10', -200.00, 'WITHDRAWAL', 'Utility bill payment', 2325.00);
INSERT IGNORE INTO transactions VALUES (47, 7, '2023-04-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2825.00);
INSERT IGNORE INTO transactions VALUES (48, 7, '2023-04-15', -300.00, 'WITHDRAWAL', 'Rent contribution', 2525.00);
INSERT IGNORE INTO transactions VALUES (49, 7, '2023-05-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 3025.00);
INSERT IGNORE INTO transactions VALUES (50, 7, '2023-05-20', -925.00, 'WITHDRAWAL', 'Car insurance payment', 2100.00);

-- Account 8: SAV-10008 (Jean Coleman), ending balance 8900.00
INSERT IGNORE INTO transactions VALUES (51, 8, '2023-01-20', 200.00, 'DEPOSIT', 'Transfer from checking', 8700.00);
INSERT IGNORE INTO transactions VALUES (52, 8, '2023-02-01', -50.00, 'WITHDRAWAL', 'ATM withdrawal', 8650.00);
INSERT IGNORE INTO transactions VALUES (53, 8, '2023-03-01', 300.00, 'DEPOSIT', 'Monthly savings', 8950.00);
INSERT IGNORE INTO transactions VALUES (54, 8, '2023-04-01', 300.00, 'DEPOSIT', 'Monthly savings', 9250.00);
INSERT IGNORE INTO transactions VALUES (55, 8, '2023-05-01', -150.00, 'WITHDRAWAL', 'Transfer to checking', 9100.00);
INSERT IGNORE INTO transactions VALUES (56, 8, '2023-06-01', 300.00, 'DEPOSIT', 'Monthly savings', 9400.00);
INSERT IGNORE INTO transactions VALUES (57, 8, '2023-07-01', -500.00, 'WITHDRAWAL', 'Vacation fund withdrawal', 8900.00);

-- Account 9: CD-10009 (Jeff Black), ending balance 25000.00
INSERT IGNORE INTO transactions VALUES (58, 9, '2022-01-02', 24000.00, 'DEPOSIT', 'CD opening deposit', 24000.00);
INSERT IGNORE INTO transactions VALUES (59, 9, '2022-07-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 24500.00);
INSERT IGNORE INTO transactions VALUES (60, 9, '2023-01-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 25000.00);

-- Account 10: CHK-10010 (Maria Escobito), ending balance 1500.00
INSERT IGNORE INTO transactions VALUES (61, 10, '2020-08-01', 1000.00, 'DEPOSIT', 'Opening deposit', 1000.00);
INSERT IGNORE INTO transactions VALUES (62, 10, '2020-09-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 2200.00);
INSERT IGNORE INTO transactions VALUES (63, 10, '2020-09-15', -350.00, 'WITHDRAWAL', 'Grocery shopping', 1850.00);
INSERT IGNORE INTO transactions VALUES (64, 10, '2020-10-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3050.00);
INSERT IGNORE INTO transactions VALUES (65, 10, '2020-10-20', -800.00, 'WITHDRAWAL', 'Monthly rent', 2250.00);
INSERT IGNORE INTO transactions VALUES (66, 10, '2020-11-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3450.00);
INSERT IGNORE INTO transactions VALUES (67, 10, '2020-11-25', -1950.00, 'WITHDRAWAL', 'Holiday shopping', 1500.00);

-- Account 11: SAV-10011 (David Schroeder), ending balance 6700.00
INSERT IGNORE INTO transactions VALUES (68, 11, '2019-05-01', 2000.00, 'DEPOSIT', 'Initial savings deposit', 2000.00);
INSERT IGNORE INTO transactions VALUES (69, 11, '2019-08-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 3500.00);
INSERT IGNORE INTO transactions VALUES (70, 11, '2019-11-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 5000.00);
INSERT IGNORE INTO transactions VALUES (71, 11, '2020-02-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 6500.00);
INSERT IGNORE INTO transactions VALUES (72, 11, '2020-05-01', -500.00, 'WITHDRAWAL', 'Home repair expense', 6000.00);
INSERT IGNORE INTO transactions VALUES (73, 11, '2020-08-01', 1000.00, 'DEPOSIT', 'Quarterly savings contribution', 7000.00);
INSERT IGNORE INTO transactions VALUES (74, 11, '2020-11-01', -300.00, 'WITHDRAWAL', 'Medical expense', 6700.00);

-- Account 12: CHK-10012 (Carlos Estaban), ending balance 4200.00
INSERT IGNORE INTO transactions VALUES (75, 12, '2021-09-01', 2000.00, 'DEPOSIT', 'Opening deposit', 2000.00);
INSERT IGNORE INTO transactions VALUES (76, 12, '2021-10-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 4500.00);
INSERT IGNORE INTO transactions VALUES (77, 12, '2021-10-15', -150.00, 'WITHDRAWAL', 'Monthly subscriptions', 4350.00);
INSERT IGNORE INTO transactions VALUES (78, 12, '2021-11-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 6850.00);
INSERT IGNORE INTO transactions VALUES (79, 12, '2021-11-10', -1200.00, 'WITHDRAWAL', 'Monthly rent', 5650.00);
INSERT IGNORE INTO transactions VALUES (80, 12, '2021-12-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 8150.00);
INSERT IGNORE INTO transactions VALUES (81, 12, '2021-12-20', -3950.00, 'WITHDRAWAL', 'Holiday expenses', 4200.00);

-- Account 13: SAV-10013 (Carlos Estaban), ending balance 18500.00
INSERT IGNORE INTO transactions VALUES (82, 13, '2021-09-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00);
INSERT IGNORE INTO transactions VALUES (83, 13, '2021-11-01', 3000.00, 'DEPOSIT', 'Savings contribution', 8000.00);
INSERT IGNORE INTO transactions VALUES (84, 13, '2022-01-01', 3000.00, 'DEPOSIT', 'Savings contribution', 11000.00);
INSERT IGNORE INTO transactions VALUES (85, 13, '2022-03-15', 3000.00, 'DEPOSIT', 'Tax refund savings', 14000.00);
INSERT IGNORE INTO transactions VALUES (86, 13, '2022-06-01', 3000.00, 'DEPOSIT', 'Savings contribution', 17000.00);
INSERT IGNORE INTO transactions VALUES (87, 13, '2022-08-20', -500.00, 'WITHDRAWAL', 'Partial withdrawal', 16500.00);
INSERT IGNORE INTO transactions VALUES (88, 13, '2022-10-01', 2000.00, 'DEPOSIT', 'Savings contribution', 18500.00);
