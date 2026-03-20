/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.banking.customer;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for banking operations that require transactional integrity.
 */
@Service
public class AccountService {

	private final CustomerRepository customers;

	public AccountService(CustomerRepository customers) {
		this.customers = customers;
	}

	@Transactional
	public void deposit(Customer customer, Integer accountId, BigDecimal amount, String description) {
		Account account = customer.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException("Account not found");
		}
		BigDecimal newBalance = account.getBalance().add(amount);
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setTransactionType("DEPOSIT");
		transaction.setDescription(description);
		transaction.setBalanceAfter(newBalance);
		account.addTransaction(transaction);

		this.customers.save(customer);
	}

	@Transactional
	public void withdraw(Customer customer, Integer accountId, BigDecimal amount, String description) {
		Account account = customer.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException("Account not found");
		}
		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientFundsException("Insufficient funds. Available balance: " + account.getBalance());
		}
		BigDecimal newBalance = account.getBalance().subtract(amount);
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setAmount(amount.negate());
		transaction.setTransactionType("WITHDRAWAL");
		transaction.setDescription(description);
		transaction.setBalanceAfter(newBalance);
		account.addTransaction(transaction);

		this.customers.save(customer);
	}

	@Transactional
	public void transfer(Customer fromCustomer, Integer fromAccountId, Customer toCustomer, Integer toAccountId,
			BigDecimal amount, String description) {
		withdraw(fromCustomer, fromAccountId, amount, "Transfer out: " + description);
		deposit(toCustomer, toAccountId, amount, "Transfer in: " + description);
	}

}
