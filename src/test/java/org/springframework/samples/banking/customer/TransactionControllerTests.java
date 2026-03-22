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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link TransactionController}
 */
@WebMvcTest(TransactionController.class)
@DisabledInNativeImage
@DisabledInAotMode
class TransactionControllerTests {

	private static final int TEST_CUSTOMER_ID = 1;

	private static final int TEST_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customers;

	@MockBean
	private AccountService accountService;

	@BeforeEach
	void init() {
		Customer customer = new Customer();
		Account account = new Account();
		account.setBalance(new BigDecimal("1000.00"));
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);
		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(customer));
	}

	@Test
	void initNewTransactionForm() throws Exception {
		mockMvc
			.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/new", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(view().name("accounts/createOrUpdateTransactionForm"));
	}

	@Test
	void processNewTransactionFormSuccess() throws Exception {
		mockMvc.perform(
				post("/customers/{customerId}/accounts/{accountId}/transactions/new", TEST_CUSTOMER_ID, TEST_ACCOUNT_ID)
					.param("amount", "100.00")
					.param("transactionType", "DEPOSIT")
					.param("description", "Test deposit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/{customerId}"));
	}

	@Test
	void processNewTransactionFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/customers/{customerId}/accounts/{accountId}/transactions/new", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID)
				.param("amount", "100.00"))
			.andExpect(model().attributeHasErrors("transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("accounts/createOrUpdateTransactionForm"));
	}

	@Test
	void exportTransactionsToCsv() throws Exception {
		Customer customer = new Customer();
		Account account = new Account();
		account.setAccountNumber("ACC-12345");
		account.setBalance(new BigDecimal("1000.00"));

		Transaction txn1 = new Transaction();
		txn1.setDate(LocalDate.of(2025, 1, 15));
		txn1.setTransactionType("DEPOSIT");
		txn1.setAmount(new BigDecimal("500.00"));
		txn1.setDescription("Initial deposit");
		txn1.setBalanceAfter(new BigDecimal("500.00"));

		Transaction txn2 = new Transaction();
		txn2.setDate(LocalDate.of(2025, 2, 10));
		txn2.setTransactionType("WITHDRAWAL");
		txn2.setAmount(new BigDecimal("-200.00"));
		txn2.setDescription("ATM withdrawal");
		txn2.setBalanceAfter(new BigDecimal("300.00"));

		account.addTransaction(txn1);
		account.addTransaction(txn2);
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);

		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(customer));

		String result = mockMvc
			.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/csv"))
			.andExpect(header().string("Content-Disposition",
					"attachment; filename=\"transactions_account_ACC-12345.csv\""))
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(result).contains("Date,Type,Amount,Description,Balance After");
		assertThat(result).contains("2025-01-15,DEPOSIT,500.00,Initial deposit,500.00");
		assertThat(result).contains("2025-02-10,WITHDRAWAL,-200.00,ATM withdrawal,300.00");
	}

	@Test
	void exportTransactionsToCsvWithSpecialCharactersInDescription() throws Exception {
		Customer customer = new Customer();
		Account account = new Account();
		account.setAccountNumber("ACC-67890");
		account.setBalance(new BigDecimal("1500.00"));

		Transaction txn = new Transaction();
		txn.setDate(LocalDate.of(2025, 3, 20));
		txn.setTransactionType("DEPOSIT");
		txn.setAmount(new BigDecimal("100.00"));
		txn.setDescription("Payment for \"services\", including tax");
		txn.setBalanceAfter(new BigDecimal("1600.00"));

		account.addTransaction(txn);
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);

		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(customer));

		String result = mockMvc
			.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(result).contains("\"Payment for \"\"services\"\", including tax\"");
	}

}
