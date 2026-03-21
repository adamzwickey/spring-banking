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

-- Account 1: CHK-10001 (George Franklin), ending balance 5250.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-02-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=1);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-03-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 4000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=2);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-03-15', -85.50, 'WITHDRAWAL', 'Electric and gas bill', 3914.50 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=3);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-04-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 6414.50 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=4);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-04-10', -1200.00, 'WITHDRAWAL', 'Rent payment', 5214.50 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=5);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-05-01', 2500.00, 'DEPOSIT', 'Direct deposit - payroll', 7714.50 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=6);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 1, '2020-05-20', -2464.50, 'WITHDRAWAL', 'Wire transfer to savings', 5250.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=7);

-- Account 2: SAV-10002 (Betty Davis), ending balance 12500.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2020-04-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=8);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2020-07-15', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 7000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=9);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2020-10-20', 1500.00, 'DEPOSIT', 'Bonus savings', 8500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=10);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2021-01-05', 2000.00, 'DEPOSIT', 'Monthly savings transfer', 10500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=11);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2021-04-10', -500.00, 'WITHDRAWAL', 'Emergency fund withdrawal', 10000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=12);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2021-08-01', 1500.00, 'DEPOSIT', 'Monthly savings transfer', 11500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=13);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 2, '2021-12-15', 1000.00, 'DEPOSIT', 'Holiday bonus savings', 12500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=14);

-- Account 3: CHK-10003 (Eduardo Rodriguez), ending balance 3200.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-07-01', 1500.00, 'DEPOSIT', 'Opening deposit', 1500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=15);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-08-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 3300.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=16);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-08-15', -120.00, 'WITHDRAWAL', 'Grocery store', 3180.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=17);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-09-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 4980.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=18);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-09-10', -400.00, 'WITHDRAWAL', 'Rent payment', 4580.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=19);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-10-01', 1800.00, 'DEPOSIT', 'Payroll deposit', 6380.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=20);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 3, '2019-10-20', -3180.00, 'WITHDRAWAL', 'Wire transfer to savings', 3200.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=21);

-- Account 4: CHK-10004 (Eduardo Rodriguez), ending balance 8750.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2019-07-01', 3000.00, 'DEPOSIT', 'Opening deposit', 3000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=22);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2019-09-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 6000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=23);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2019-10-15', -250.00, 'WITHDRAWAL', 'Insurance payment', 5750.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=24);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2020-01-01', 3000.00, 'DEPOSIT', 'Payroll deposit', 8750.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=25);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2020-03-10', -500.00, 'WITHDRAWAL', 'Car repairs', 8250.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=26);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2020-06-01', 1500.00, 'DEPOSIT', 'Side income', 9750.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=27);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 4, '2020-08-15', -1000.00, 'WITHDRAWAL', 'Vacation expense', 8750.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=28);

-- Account 5: SAV-10005 (Harold Davis), ending balance 45000.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2021-03-01', 10000.00, 'DEPOSIT', 'Initial savings deposit', 10000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=29);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2021-06-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 20000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=30);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2021-08-15', 5000.00, 'DEPOSIT', 'Annual bonus savings', 25000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=31);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2021-09-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 35000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=32);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2021-11-10', -2000.00, 'WITHDRAWAL', 'Emergency home repair', 33000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=33);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2022-01-01', 10000.00, 'DEPOSIT', 'Quarterly savings contribution', 43000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=34);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 5, '2022-02-20', 2000.00, 'DEPOSIT', 'Tax refund deposit', 45000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=35);

-- Account 6: MMA-10006 (Peter McTavish), ending balance 15000.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2018-12-01', 8000.00, 'DEPOSIT', 'Initial MMA deposit', 8000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=36);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2019-03-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 10000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=37);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2019-06-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 12000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=38);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2019-09-01', 2000.00, 'DEPOSIT', 'Quarterly contribution', 14000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=39);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2019-11-15', -500.00, 'WITHDRAWAL', 'Emergency withdrawal', 13500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=40);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2020-01-01', 2000.00, 'DEPOSIT', 'Annual bonus deposit', 15500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=41);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 6, '2020-03-10', -500.00, 'WITHDRAWAL', 'Account management fee', 15000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=42);

-- Account 7: CHK-10007 (Jean Coleman), ending balance 2100.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-01-15', 500.00, 'DEPOSIT', 'Paycheck deposit', 2100.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=43);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-02-05', -75.00, 'WITHDRAWAL', 'Grocery store', 2025.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=44);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-03-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2525.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=45);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-03-10', -200.00, 'WITHDRAWAL', 'Utility bill payment', 2325.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=46);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-04-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 2825.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=47);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-04-15', -300.00, 'WITHDRAWAL', 'Rent contribution', 2525.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=48);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-05-01', 500.00, 'DEPOSIT', 'Paycheck deposit', 3025.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=49);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 7, '2023-05-20', -925.00, 'WITHDRAWAL', 'Car insurance payment', 2100.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=50);

-- Account 8: SAV-10008 (Jean Coleman), ending balance 8900.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-01-20', 200.00, 'DEPOSIT', 'Transfer from checking', 8700.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=51);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-02-01', -50.00, 'WITHDRAWAL', 'ATM withdrawal', 8650.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=52);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-03-01', 300.00, 'DEPOSIT', 'Monthly savings', 8950.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=53);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-04-01', 300.00, 'DEPOSIT', 'Monthly savings', 9250.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=54);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-05-01', -150.00, 'WITHDRAWAL', 'Transfer to checking', 9100.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=55);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-06-01', 300.00, 'DEPOSIT', 'Monthly savings', 9400.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=56);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 8, '2023-07-01', -500.00, 'WITHDRAWAL', 'Vacation fund withdrawal', 8900.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=57);

-- Account 9: CD-10009 (Jeff Black), ending balance 25000.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 9, '2022-01-02', 24000.00, 'DEPOSIT', 'CD opening deposit', 24000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=58);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 9, '2022-07-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 24500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=59);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 9, '2023-01-01', 500.00, 'DEPOSIT', 'Semi-annual interest payment', 25000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=60);

-- Account 10: CHK-10010 (Maria Escobito), ending balance 1500.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-08-01', 1000.00, 'DEPOSIT', 'Opening deposit', 1000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=61);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-09-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 2200.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=62);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-09-15', -350.00, 'WITHDRAWAL', 'Grocery shopping', 1850.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=63);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-10-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3050.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=64);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-10-20', -800.00, 'WITHDRAWAL', 'Monthly rent', 2250.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=65);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-11-01', 1200.00, 'DEPOSIT', 'Payroll deposit', 3450.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=66);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 10, '2020-11-25', -1950.00, 'WITHDRAWAL', 'Holiday shopping', 1500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=67);

-- Account 11: SAV-10011 (David Schroeder), ending balance 6700.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2019-05-01', 2000.00, 'DEPOSIT', 'Initial savings deposit', 2000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=68);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2019-08-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 3500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=69);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2019-11-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 5000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=70);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2020-02-01', 1500.00, 'DEPOSIT', 'Quarterly savings contribution', 6500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=71);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2020-05-01', -500.00, 'WITHDRAWAL', 'Home repair expense', 6000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=72);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2020-08-01', 1000.00, 'DEPOSIT', 'Quarterly savings contribution', 7000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=73);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 11, '2020-11-01', -300.00, 'WITHDRAWAL', 'Medical expense', 6700.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=74);

-- Account 12: CHK-10012 (Carlos Estaban), ending balance 4200.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-09-01', 2000.00, 'DEPOSIT', 'Opening deposit', 2000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=75);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-10-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 4500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=76);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-10-15', -150.00, 'WITHDRAWAL', 'Monthly subscriptions', 4350.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=77);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-11-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 6850.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=78);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-11-10', -1200.00, 'WITHDRAWAL', 'Monthly rent', 5650.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=79);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-12-01', 2500.00, 'DEPOSIT', 'Payroll deposit', 8150.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=80);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 12, '2021-12-20', -3950.00, 'WITHDRAWAL', 'Holiday expenses', 4200.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=81);

-- Account 13: SAV-10013 (Carlos Estaban), ending balance 18500.00
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2021-09-01', 5000.00, 'DEPOSIT', 'Initial savings deposit', 5000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=82);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2021-11-01', 3000.00, 'DEPOSIT', 'Savings contribution', 8000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=83);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2022-01-01', 3000.00, 'DEPOSIT', 'Savings contribution', 11000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=84);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2022-03-15', 3000.00, 'DEPOSIT', 'Tax refund savings', 14000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=85);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2022-06-01', 3000.00, 'DEPOSIT', 'Savings contribution', 17000.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=86);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2022-08-20', -500.00, 'WITHDRAWAL', 'Partial withdrawal', 16500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=87);
INSERT INTO transactions (account_id, transaction_date, amount, transaction_type, description, balance_after) SELECT 13, '2022-10-01', 2000.00, 'DEPOSIT', 'Savings contribution', 18500.00 WHERE NOT EXISTS (SELECT * FROM transactions WHERE id=88);
