INSERT INTO account_types VALUES (default, 'Checking');
INSERT INTO account_types VALUES (default, 'Savings');
INSERT INTO account_types VALUES (default, 'Money Market');
INSERT INTO account_types VALUES (default, 'CD');

INSERT INTO customers VALUES (default, 'George', 'Franklin', 'george.franklin@email.com', '6085551023', '110 W. Liberty St.');
INSERT INTO customers VALUES (default, 'Betty', 'Davis', 'betty.davis@email.com', '6085551749', '638 Cardinal Ave.');
INSERT INTO customers VALUES (default, 'Eduardo', 'Rodriquez', 'eduardo.rodriquez@email.com', '6085558763', '2693 Commerce St.');
INSERT INTO customers VALUES (default, 'Harold', 'Davis', 'harold.davis@email.com', '6085553198', '563 Friendly St.');
INSERT INTO customers VALUES (default, 'Peter', 'McTavish', 'peter.mctavish@email.com', '6085552765', '2387 S. Fair Way');
INSERT INTO customers VALUES (default, 'Jean', 'Coleman', 'jean.coleman@email.com', '6085552654', '105 N. Lake St.');
INSERT INTO customers VALUES (default, 'Jeff', 'Black', 'jeff.black@email.com', '6085555387', '1450 Oak Blvd.');
INSERT INTO customers VALUES (default, 'Maria', 'Escobito', 'maria.escobito@email.com', '6085557683', '345 Maple St.');
INSERT INTO customers VALUES (default, 'David', 'Schroeder', 'david.schroeder@email.com', '6085559435', '2749 Blackhawk Trail');
INSERT INTO customers VALUES (default, 'Carlos', 'Estaban', 'carlos.estaban@email.com', '6085555487', '2335 Independence La.');

INSERT INTO accounts VALUES (default, 'CHK-10001', '2020-01-15', 5250.00, 1, 1);
INSERT INTO accounts VALUES (default, 'SAV-10002', '2020-03-20', 12500.00, 2, 2);
INSERT INTO accounts VALUES (default, 'CHK-10003', '2019-06-10', 3200.00, 1, 3);
INSERT INTO accounts VALUES (default, 'CHK-10004', '2019-06-10', 8750.00, 1, 3);
INSERT INTO accounts VALUES (default, 'SAV-10005', '2021-02-28', 45000.00, 2, 4);
INSERT INTO accounts VALUES (default, 'MMA-10006', '2018-11-05', 15000.00, 3, 5);
INSERT INTO accounts VALUES (default, 'CHK-10007', '2017-09-12', 2100.00, 1, 6);
INSERT INTO accounts VALUES (default, 'SAV-10008', '2017-09-12', 8900.00, 2, 6);
INSERT INTO accounts VALUES (default, 'CD-10009', '2022-01-01', 25000.00, 4, 7);
INSERT INTO accounts VALUES (default, 'CHK-10010', '2020-07-15', 1500.00, 1, 8);
INSERT INTO accounts VALUES (default, 'SAV-10011', '2019-04-22', 6700.00, 2, 9);
INSERT INTO accounts VALUES (default, 'CHK-10012', '2021-08-30', 4200.00, 1, 10);
INSERT INTO accounts VALUES (default, 'SAV-10013', '2021-08-30', 18500.00, 2, 10);

-- Account 1: CHK-10001 (George Franklin), ending balance 5250.00
INSERT INTO transactions VALUES (default, 1, '2020-02-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00);
INSERT INTO transactions VALUES (default, 1, '2020-03-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 4000.00);
INSERT INTO transactions VALUES (default, 1, '2020-03-15', -85.50, 'WITHDRAWAL', 'Electric and gas bill', 3914.50);
INSERT INTO transactions VALUES (default, 1, '2020-04-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 6414.50);
INSERT INTO transactions VALUES (default, 1, '2020-04-10', -1200.00, 'WITHDRAWAL', 'Rent payment', 5214.50);
INSERT INTO transactions VALUES (default, 1, '2020-05-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 7714.50);
INSERT INTO transactions VALUES (default, 1, '2020-05-20', -2464.50, 'WITHDRAWAL', 'Wire transfer to savings', 5250.00);

-- Account 2: SAV-10002 (Betty Davis), ending balance 12500.00
INSERT INTO transactions VALUES (default, 2, '2020-04-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00);
INSERT INTO transactions VALUES (default, 2, '2020-07-15', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 7000.00);
INSERT INTO transactions VALUES (default, 2, '2020-10-20', 1500.00, 'DEPOSIT', 'Bonus savings', 8500.00);
INSERT INTO transactions VALUES (default, 2, '2021-01-05', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 10500.00);
INSERT INTO transactions VALUES (default, 2, '2021-04-10', -500.00, 'WITHDRAWAL', 'Emergency fund withdrawal', 10000.00);
INSERT INTO transactions VALUES (default, 2, '2021-08-01', 1500.00, 'DEPOSIT', 'Monthly savings transfer', 11500.00);
INSERT INTO transactions VALUES (default, 2, '2021-12-15', 1000.00, 'DEPOSIT', 'Holiday bonus savings', 12500.00);

-- Account 3: CHK-10003 (Eduardo Rodriguez), ending balance 3200.00
INSERT INTO transactions VALUES (default, 3, '2019-07-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00);
INSERT INTO transactions VALUES (default, 3, '2019-08-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 3300.00);
INSERT INTO transactions VALUES (default, 3, '2019-08-15', -120.00, 'WITHDRAWAL', 'Grocery store', 3180.00);
INSERT INTO transactions VALUES (default, 3, '2019-09-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 4980.00);
INSERT INTO transactions VALUES (default, 3, '2019-09-10', -400.00, 'WITHDRAWAL', 'Rent payment', 4580.00);
INSERT INTO transactions VALUES (default, 3, '2019-10-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 6380.00);
INSERT INTO transactions VALUES (default, 3, '2019-10-20', -3180.00, 'WITHDRAWAL', 'Wire transfer to savings', 3200.00);

-- Account 4: CHK-10004 (Eduardo Rodriguez), ending balance 8750.00
INSERT INTO transactions VALUES (default, 4, '2019-07-01', 3000.00, 'DEPOSIT', 'Opening deposit', 3000.00);
INSERT INTO transactions VALUES (default, 4, '2019-09-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 6000.00);
INSERT INTO transactions VALUES (default, 4, '2019-10-15', -250.00, 'WITHDRAWAL', 'Insurance payment', 5750.00);
INSERT INTO transactions VALUES (default, 4, '2020-01-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 8750.00);
INSERT INTO transactions VALUES (default, 4, '2020-03-10', -500.00, 'WITHDRAWAL', 'Car repairs', 8250.00);
INSERT INTO transactions VALUES (default, 4, '2020-06-01', 1500.00, 'DEPOSIT', 'Side income', 9750.00);
INSERT INTO transactions VALUES (default, 4, '2020-08-15', -1000.00, 'WITHDRAWAL', 'Vacation expense', 8750.00);

-- Account 5: SAV-10005 (Harold Davis), ending balance 45000.00
INSERT INTO transactions VALUES (default, 5, '2021-03-01', 10000.00, 'DEPOSIT', 'Initial savings deposit', 10000.00);
INSERT INTO transactions VALUES (default, 5, '2021-06-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 20000.00);
INSERT INTO transactions VALUES (default, 5, '2021-08-15', 5000.00, 'DEPOSIT', 'Annual bonus savings', 25000.00);
INSERT INTO transactions VALUES (default, 5, '2021-09-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 35000.00);
INSERT INTO transactions VALUES (default, 5, '2021-11-10', -2000.00, 'WITHDRAWAL', 'Emergency home repair', 33000.00);
INSERT INTO transactions VALUES (default, 5, '2022-01-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 43000.00);
INSERT INTO transactions VALUES (default, 5, '2022-02-20', 2000.00, 'DEPOSIT', 'Tax refund deposit', 45000.00);

-- Account 6: MMA-10006 (Peter McTavish), ending balance 15000.00
INSERT INTO transactions VALUES (default, 6, '2018-12-01', 8000.00, 'DEPOSIT', 'Initial MMA deposit', 8000.00);
INSERT INTO transactions VALUES (default, 6, '2019-03-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 10000.00);
INSERT INTO transactions VALUES (default, 6, '2019-06-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 12000.00);
INSERT INTO transactions VALUES (default, 6, '2019-09-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 14000.00);
INSERT INTO transactions VALUES (default, 6, '2019-11-15', -500.00, 'WITHDRAWAL', 'Emergency withdrawal', 13500.00);
INSERT INTO transactions VALUES (default, 6, '2020-01-01', 2000.00, 'DEPOSIT', 'Annual bonus deposit', 15500.00);
INSERT INTO transactions VALUES (default, 6, '2020-03-10', -500.00, 'WITHDRAWAL', 'Account management fee', 15000.00);

-- Account 7: CHK-10007 (Jean Coleman), ending balance 2100.00
INSERT INTO transactions VALUES (default, 7, '2023-01-15', 500.00, 'DEPOSIT', 'Paycheck deposit', 2100.00);
INSERT INTO transactions VALUES (default, 7, '2023-02-05', -75.00, 'WITHDRAWAL', 'Grocery store', 2025.00);
INSERT INTO transactions VALUES (default, 7, '2023-03-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2525.00);
INSERT INTO transactions VALUES (default, 7, '2023-03-10', -200.00, 'WITHDRAWAL', 'Utility bill payment', 2325.00);
INSERT INTO transactions VALUES (default, 7, '2023-04-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2825.00);
INSERT INTO transactions VALUES (default, 7, '2023-04-15', -300.00, 'WITHDRAWAL', 'Rent contribution', 2525.00);
INSERT INTO transactions VALUES (default, 7, '2023-05-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 3025.00);
INSERT INTO transactions VALUES (default, 7, '2023-05-20', -925.00, 'WITHDRAWAL', 'Car insurance payment', 2100.00);

-- Account 8: SAV-10008 (Jean Coleman), ending balance 8900.00
INSERT INTO transactions VALUES (default, 8, '2023-01-20', 200.00, 'DEPOSIT', 'Transfer from checking', 8700.00);
INSERT INTO transactions VALUES (default, 8, '2023-02-01', -50.00, 'WITHDRAWAL', 'ATM withdrawal', 8650.00);
INSERT INTO transactions VALUES (default, 8, '2023-03-01', 300.00, 'DEPOSIT', 'Monthly savings', 8950.00);
INSERT INTO transactions VALUES (default, 8, '2023-04-01', 300.00, 'DEPOSIT', 'Monthly savings', 9250.00);
INSERT INTO transactions VALUES (default, 8, '2023-05-01', -150.00, 'WITHDRAWAL', 'Transfer to checking', 9100.00);
INSERT INTO transactions VALUES (default, 8, '2023-06-01', 300.00, 'DEPOSIT', 'Monthly savings', 9400.00);
INSERT INTO transactions VALUES (default, 8, '2023-07-01', -500.00, 'WITHDRAWAL', 'Vacation fund withdrawal', 8900.00);

-- Account 9: CD-10009 (Jeff Black), ending balance 25000.00
INSERT INTO transactions VALUES (default, 9, '2022-01-02', 24000.00, 'DEPOSIT', 'CD opening deposit', 24000.00);
INSERT INTO transactions VALUES (default, 9, '2022-07-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 24500.00);
INSERT INTO transactions VALUES (default, 9, '2023-01-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 25000.00);

-- Account 10: CHK-10010 (Maria Escobito), ending balance 1500.00
INSERT INTO transactions VALUES (default, 10, '2020-08-01', 1000.00, 'DEPOSIT', 'Opening deposit', 1000.00);
INSERT INTO transactions VALUES (default, 10, '2020-09-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 2200.00);
INSERT INTO transactions VALUES (default, 10, '2020-09-15', -350.00, 'WITHDRAWAL', 'Grocery shopping', 1850.00);
INSERT INTO transactions VALUES (default, 10, '2020-10-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3050.00);
INSERT INTO transactions VALUES (default, 10, '2020-10-20', -800.00, 'WITHDRAWAL', 'Monthly rent', 2250.00);
INSERT INTO transactions VALUES (default, 10, '2020-11-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3450.00);
INSERT INTO transactions VALUES (default, 10, '2020-11-25', -1950.00, 'WITHDRAWAL', 'Holiday shopping', 1500.00);

-- Account 11: SAV-10011 (David Schroeder), ending balance 6700.00
INSERT INTO transactions VALUES (default, 11, '2019-05-01', 2000.00, 'DEPOSIT', 'Initial savings deposit', 2000.00);
INSERT INTO transactions VALUES (default, 11, '2019-08-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 3500.00);
INSERT INTO transactions VALUES (default, 11, '2019-11-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 5000.00);
INSERT INTO transactions VALUES (default, 11, '2020-02-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 6500.00);
INSERT INTO transactions VALUES (default, 11, '2020-05-01', -500.00, 'WITHDRAWAL', 'Home repair expense', 6000.00);
INSERT INTO transactions VALUES (default, 11, '2020-08-01', 1000.00, 'DEPOSIT', 'Quarterly savings contribution', 7000.00);
INSERT INTO transactions VALUES (default, 11, '2020-11-01', -300.00, 'WITHDRAWAL', 'Medical expense', 6700.00);

-- Account 12: CHK-10012 (Carlos Estaban), ending balance 4200.00
INSERT INTO transactions VALUES (default, 12, '2021-09-01', 2000.00, 'DEPOSIT', 'Opening deposit', 2000.00);
INSERT INTO transactions VALUES (default, 12, '2021-10-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 4500.00);
INSERT INTO transactions VALUES (default, 12, '2021-10-15', -150.00, 'WITHDRAWAL', 'Monthly subscriptions', 4350.00);
INSERT INTO transactions VALUES (default, 12, '2021-11-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 6850.00);
INSERT INTO transactions VALUES (default, 12, '2021-11-10', -1200.00, 'WITHDRAWAL', 'Monthly rent', 5650.00);
INSERT INTO transactions VALUES (default, 12, '2021-12-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 8150.00);
INSERT INTO transactions VALUES (default, 12, '2021-12-20', -3950.00, 'WITHDRAWAL', 'Holiday expenses', 4200.00);

-- Account 13: SAV-10013 (Carlos Estaban), ending balance 18500.00
INSERT INTO transactions VALUES (default, 13, '2021-09-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00);
INSERT INTO transactions VALUES (default, 13, '2021-11-01', 3000.00, 'DEPOSIT', 'Savings contribution', 8000.00);
INSERT INTO transactions VALUES (default, 13, '2022-01-01', 3000.00, 'DEPOSIT', 'Savings contribution', 11000.00);
INSERT INTO transactions VALUES (default, 13, '2022-03-15', 3000.00, 'DEPOSIT', 'Tax refund savings', 14000.00);
INSERT INTO transactions VALUES (default, 13, '2022-06-01', 3000.00, 'DEPOSIT', 'Savings contribution', 17000.00);
INSERT INTO transactions VALUES (default, 13, '2022-08-20', -500.00, 'WITHDRAWAL', 'Partial withdrawal', 16500.00);
INSERT INTO transactions VALUES (default, 13, '2022-10-01', 2000.00, 'DEPOSIT', 'Savings contribution', 18500.00);
