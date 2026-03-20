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

package org.springframework.samples.banking.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.banking.customer.Account;
import org.springframework.samples.banking.customer.AccountType;
import org.springframework.samples.banking.customer.AccountTypeRepository;
import org.springframework.samples.banking.customer.Customer;
import org.springframework.samples.banking.customer.CustomerRepository;
import org.springframework.samples.banking.customer.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BankingServiceTests {

	@Autowired
	protected CustomerRepository customers;

	@Autowired
	protected AccountTypeRepository types;

	private final Pageable pageable = Pageable.unpaged();

	@Test
	void shouldFindCustomersByLastName() {
		Page<Customer> customers = this.customers.findByLastNameStartingWith("Davis", pageable);
		assertThat(customers).hasSize(2);

		customers = this.customers.findByLastNameStartingWith("Daviss", pageable);
		assertThat(customers).isEmpty();
	}

	@Test
	void shouldFindSingleCustomerWithAccount() {
		Optional<Customer> optionalCustomer = this.customers.findById(1);
		assertThat(optionalCustomer).isPresent();
		Customer customer = optionalCustomer.get();
		assertThat(customer.getLastName()).startsWith("Franklin");
		assertThat(customer.getAccounts()).hasSize(1);
		assertThat(customer.getAccounts().get(0).getType()).isNotNull();
		assertThat(customer.getAccounts().get(0).getType().getName()).isEqualTo("Checking");
	}

	@Test
	@Transactional
	void shouldInsertCustomer() {
		Page<Customer> customers = this.customers.findByLastNameStartingWith("Schultz", pageable);
		int found = (int) customers.getTotalElements();

		Customer customer = new Customer();
		customer.setFirstName("Sam");
		customer.setLastName("Schultz");
		customer.setEmail("sam.schultz@email.com");
		customer.setPhone("4444444444");
		customer.setAddress("4, Evans Street");
		this.customers.save(customer);
		assertThat(customer.getId()).isNotZero();

		customers = this.customers.findByLastNameStartingWith("Schultz", pageable);
		assertThat(customers.getTotalElements()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateCustomer() {
		Optional<Customer> optionalCustomer = this.customers.findById(1);
		assertThat(optionalCustomer).isPresent();
		Customer customer = optionalCustomer.get();
		String oldLastName = customer.getLastName();
		String newLastName = oldLastName + "X";

		customer.setLastName(newLastName);
		this.customers.save(customer);

		optionalCustomer = this.customers.findById(1);
		assertThat(optionalCustomer).isPresent();
		customer = optionalCustomer.get();
		assertThat(customer.getLastName()).isEqualTo(newLastName);
	}

	@Test
	void shouldFindAllAccountTypes() {
		Collection<AccountType> accountTypes = this.types.findAccountTypes();

		AccountType accountType1 = EntityUtils.getById(accountTypes, AccountType.class, 1);
		assertThat(accountType1.getName()).isEqualTo("Checking");
		AccountType accountType4 = EntityUtils.getById(accountTypes, AccountType.class, 4);
		assertThat(accountType4.getName()).isEqualTo("CD");
	}

	@Test
	@Transactional
	void shouldInsertAccountIntoDatabaseAndGenerateId() {
		Optional<Customer> optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		Customer customer6 = optionalCustomer.get();

		int found = customer6.getAccounts().size();

		Account account = new Account();
		account.setAccountNumber("NEW-99999");
		Collection<AccountType> types = this.types.findAccountTypes();
		account.setType(EntityUtils.getById(types, AccountType.class, 2));
		account.setOpenedDate(LocalDate.now());
		account.setBalance(BigDecimal.ZERO);
		customer6.addAccount(account);
		assertThat(customer6.getAccounts()).hasSize(found + 1);

		this.customers.save(customer6);

		optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		customer6 = optionalCustomer.get();
		assertThat(customer6.getAccounts()).hasSize(found + 1);
		account = customer6.getAccount("NEW-99999");
		assertThat(account.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldUpdateAccountNumber() {
		Optional<Customer> optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		Customer customer6 = optionalCustomer.get();

		Account account7 = customer6.getAccount(7);
		String oldNumber = account7.getAccountNumber();

		String newNumber = oldNumber + "X";
		account7.setAccountNumber(newNumber);
		this.customers.save(customer6);

		optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		customer6 = optionalCustomer.get();
		account7 = customer6.getAccount(7);
		assertThat(account7.getAccountNumber()).isEqualTo(newNumber);
	}

	@Test
	@Transactional
	void shouldAddNewTransactionForAccount() {
		Optional<Customer> optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		Customer customer6 = optionalCustomer.get();

		Account account7 = customer6.getAccount(7);
		int found = account7.getTransactions().size();
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setTransactionType("DEPOSIT");
		transaction.setDescription("test");
		transaction.setBalanceAfter(account7.getBalance().add(new BigDecimal("100.00")));

		customer6.addTransaction(account7.getId(), transaction);
		this.customers.save(customer6);

		assertThat(account7.getTransactions()) //
			.hasSize(found + 1) //
			.allMatch(value -> value.getId() != null);
	}

	@Test
	void shouldFindTransactionsByAccountId() {
		Optional<Customer> optionalCustomer = this.customers.findById(6);
		assertThat(optionalCustomer).isPresent();
		Customer customer6 = optionalCustomer.get();

		Account account7 = customer6.getAccount(7);
		Collection<Transaction> transactions = account7.getTransactions();

		assertThat(transactions) //
			.hasSize(2) //
			.element(0)
			.extracting(Transaction::getDate)
			.isNotNull();
	}

}
