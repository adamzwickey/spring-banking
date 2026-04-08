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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link TransactionExportController}
 */
@WebMvcTest(TransactionExportController.class)
@DisabledInNativeImage
@DisabledInAotMode
class TransactionExportControllerTests {

	private static final int TEST_CUSTOMER_ID = 1;

	private static final int TEST_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customers;

	@BeforeEach
	void init() {
		Customer customer = new Customer();
		Account account = new Account();
		account.setAccountNumber("1234567890");
		account.setBalance(new BigDecimal("1000.00"));
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);

		Transaction txn1 = new Transaction();
		txn1.setDate(LocalDate.of(2025, 1, 15));
		txn1.setAmount(new BigDecimal("500.00"));
		txn1.setTransactionType("DEPOSIT");
		txn1.setDescription("Initial deposit");
		txn1.setBalanceAfter(new BigDecimal("500.00"));
		account.addTransaction(txn1);

		Transaction txn2 = new Transaction();
		txn2.setDate(LocalDate.of(2025, 2, 1));
		txn2.setAmount(new BigDecimal("200.00"));
		txn2.setTransactionType("WITHDRAWAL");
		txn2.setDescription("ATM withdrawal");
		txn2.setBalanceAfter(new BigDecimal("300.00"));
		account.addTransaction(txn2);

		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(customer));
	}

	@Test
	void exportTransactionsCsv() throws Exception {
		mockMvc
			.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/csv"))
			.andExpect(header().string("Content-Disposition", containsString("attachment")))
			.andExpect(header().string("Content-Disposition", containsString("transactions_1234567890.csv")))
			.andExpect(content().string(containsString("Date,Type,Amount,Description,Balance After")))
			.andExpect(content().string(containsString("2025-01-15,DEPOSIT,500.00,Initial deposit,500.00")))
			.andExpect(content().string(containsString("2025-02-01,WITHDRAWAL,200.00,ATM withdrawal,300.00")));
	}

	@Test
	void exportTransactionsCsvWithEmptyAccount() throws Exception {
		Customer customer = new Customer();
		Account emptyAccount = new Account();
		emptyAccount.setAccountNumber("9999999999");
		emptyAccount.setBalance(BigDecimal.ZERO);
		customer.addAccount(emptyAccount);
		emptyAccount.setId(TEST_ACCOUNT_ID);
		given(this.customers.findById(2)).willReturn(Optional.of(customer));

		String result = mockMvc
			.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", 2, TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/csv"))
			.andExpect(content().string(containsString("Date,Type,Amount,Description,Balance After")))
			.andReturn()
			.getResponse()
			.getContentAsString();

		// Should only contain the header line, no data rows
		String[] lines = result.trim().split("\n");
		assert lines.length == 1 : "Expected only header line but got " + lines.length + " lines";
	}

	@Test
	void exportTransactionsCsvEscapesCommasInDescription() throws Exception {
		Customer customer = new Customer();
		Account account = new Account();
		account.setAccountNumber("1111111111");
		account.setBalance(new BigDecimal("100.00"));
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);

		Transaction txn = new Transaction();
		txn.setDate(LocalDate.of(2025, 3, 1));
		txn.setAmount(new BigDecimal("50.00"));
		txn.setTransactionType("DEPOSIT");
		txn.setDescription("Rent, utilities");
		txn.setBalanceAfter(new BigDecimal("100.00"));
		account.addTransaction(txn);

		given(this.customers.findById(3)).willReturn(Optional.of(customer));

		mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", 3, TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"Rent, utilities\"")));
	}

	@Test
	void exportTransactionsCsvHandlesNullDescription() throws Exception {
		Customer customer = new Customer();
		Account account = new Account();
		account.setAccountNumber("2222222222");
		account.setBalance(new BigDecimal("100.00"));
		customer.addAccount(account);
		account.setId(TEST_ACCOUNT_ID);

		Transaction txn = new Transaction();
		txn.setDate(LocalDate.of(2025, 4, 1));
		txn.setAmount(new BigDecimal("75.00"));
		txn.setTransactionType("DEPOSIT");
		txn.setDescription(null);
		txn.setBalanceAfter(new BigDecimal("100.00"));
		account.addTransaction(txn);

		given(this.customers.findById(4)).willReturn(Optional.of(customer));

		mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", 4, TEST_ACCOUNT_ID))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("2025-04-01,DEPOSIT,75.00,,100.00")));
	}

}
