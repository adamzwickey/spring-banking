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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;

/**
 * Test class for {@link AccountService}
 */
@DisabledInNativeImage
class AccountServiceTests {

	private AccountService accountService;

	private CustomerRepository customerRepository;

	private Customer customer;

	private Account account;

	@BeforeEach
	void setup() {
		customerRepository = mock(CustomerRepository.class);
		accountService = new AccountService(customerRepository);

		customer = new Customer();
		customer.setFirstName("Test");
		customer.setLastName("User");
		customer.setEmail("test@email.com");
		customer.setPhone("1234567890");
		customer.setAddress("123 Test St");

		account = new Account();
		account.setId(1);
		account.setAccountNumber("CHK-10001");
		account.setBalance(new BigDecimal("1000.00"));
		customer.getAccounts().add(account);
	}

	@Test
	void depositIncreasesBalance() {
		accountService.deposit(customer, 1, new BigDecimal("500.00"), "Test deposit");
		assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal("1500.00"));
		assertThat(account.getTransactions()).hasSize(1);
	}

	@Test
	void withdrawDecreasesBalance() {
		accountService.withdraw(customer, 1, new BigDecimal("300.00"), "Test withdrawal");
		assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal("700.00"));
		assertThat(account.getTransactions()).hasSize(1);
	}

	@Test
	void withdrawRejectsOverdraft() {
		assertThatThrownBy(() -> accountService.withdraw(customer, 1, new BigDecimal("2000.00"), "Overdraft"))
			.isInstanceOf(InsufficientFundsException.class);
	}

	@Test
	void transferMovesBalance() {
		Customer toCustomer = new Customer();
		Account toAccount = new Account();
		toAccount.setId(2);
		toAccount.setAccountNumber("SAV-10002");
		toAccount.setBalance(new BigDecimal("500.00"));
		toCustomer.getAccounts().add(toAccount);

		accountService.transfer(customer, 1, toCustomer, 2, new BigDecimal("200.00"), "Test transfer");

		assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal("800.00"));
		assertThat(toAccount.getBalance()).isEqualByComparingTo(new BigDecimal("700.00"));
	}

}
