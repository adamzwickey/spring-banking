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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Test class for the {@link AccountController}
 */
@WebMvcTest(value = AccountController.class,
		includeFilters = @ComponentScan.Filter(value = AccountTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
@DisabledInNativeImage
@DisabledInAotMode
class AccountControllerTests {

	private static final int TEST_CUSTOMER_ID = 1;

	private static final int TEST_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customers;

	@MockBean
	private AccountTypeRepository types;

	@MockBean
	private TransactionCsvService csvService;

	@BeforeEach
	void setup() {
		AccountType checking = new AccountType();
		checking.setId(1);
		checking.setName("Checking");
		given(this.types.findAccountTypes()).willReturn(List.of(checking));

		Customer customer = new Customer();
		Account account = new Account();
		Account savings = new Account();
		customer.addAccount(account);
		customer.addAccount(savings);
		account.setId(TEST_ACCOUNT_ID);
		savings.setId(TEST_ACCOUNT_ID + 1);
		account.setAccountNumber("CHK-10001");
		savings.setAccountNumber("SAV-10002");
		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(customer));
	}

	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andExpect(view().name("accounts/createOrUpdateAccountForm"))
			.andExpect(model().attributeExists("account"));
	}

	@Test
	void processCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID).param("accountNumber", "CHK-99999")
				.param("type", "Checking")
				.param("openedDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/{customerId}"));
	}

	@Nested
	class ProcessCreationFormHasErrors {

		@Test
		void processCreationFormWithBlankAccountNumber() throws Exception {
			mockMvc
				.perform(post("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID).param("accountNumber", "\t \n")
					.param("openedDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "accountNumber"))
				.andExpect(model().attributeHasFieldErrorCode("account", "accountNumber", "required"))
				.andExpect(status().isOk())
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

		@Test
		void processCreationFormWithDuplicateAccountNumber() throws Exception {
			mockMvc
				.perform(post("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID)
					.param("accountNumber", "CHK-10001")
					.param("openedDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "accountNumber"))
				.andExpect(model().attributeHasFieldErrorCode("account", "accountNumber", "duplicate"))
				.andExpect(status().isOk())
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

		@Test
		void processCreationFormWithMissingAccountType() throws Exception {
			mockMvc
				.perform(post("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID)
					.param("accountNumber", "CHK-99999")
					.param("openedDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "type"))
				.andExpect(model().attributeHasFieldErrorCode("account", "type", "required"))
				.andExpect(status().isOk())
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

		@Test
		void processCreationFormWithInvalidOpenedDate() throws Exception {
			LocalDate currentDate = LocalDate.now();
			String futureDate = currentDate.plusMonths(1).toString();

			mockMvc
				.perform(post("/customers/{customerId}/accounts/new", TEST_CUSTOMER_ID)
					.param("accountNumber", "CHK-99999")
					.param("openedDate", futureDate))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "openedDate"))
				.andExpect(model().attributeHasFieldErrorCode("account", "openedDate", "typeMismatch.openedDate"))
				.andExpect(status().isOk())
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

		@Test
		void initUpdateForm() throws Exception {
			mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/edit", TEST_CUSTOMER_ID, TEST_ACCOUNT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("account"))
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

	}

	@Test
	void processUpdateFormSuccess() throws Exception {
		mockMvc
			.perform(post("/customers/{customerId}/accounts/{accountId}/edit", TEST_CUSTOMER_ID, TEST_ACCOUNT_ID)
				.param("accountNumber", "CHK-99999")
				.param("type", "Checking")
				.param("openedDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/{customerId}"));
	}

	@Nested
	class ProcessUpdateFormHasErrors {

		@Test
		void processUpdateFormWithInvalidOpenedDate() throws Exception {
			mockMvc
				.perform(post("/customers/{customerId}/accounts/{accountId}/edit", TEST_CUSTOMER_ID, TEST_ACCOUNT_ID)
					.param("accountNumber", " ")
					.param("openedDate", "2015/02/12"))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "openedDate"))
				.andExpect(model().attributeHasFieldErrorCode("account", "openedDate", "typeMismatch"))
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

		@Test
		void processUpdateFormWithBlankAccountNumber() throws Exception {
			mockMvc
				.perform(post("/customers/{customerId}/accounts/{accountId}/edit", TEST_CUSTOMER_ID, TEST_ACCOUNT_ID)
					.param("accountNumber", "  ")
					.param("openedDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("customer"))
				.andExpect(model().attributeHasErrors("account"))
				.andExpect(model().attributeHasFieldErrors("account", "accountNumber"))
				.andExpect(model().attributeHasFieldErrorCode("account", "accountNumber", "required"))
				.andExpect(view().name("accounts/createOrUpdateAccountForm"));
		}

	}

	@Nested
	class ExportTransactionsToCsv {

		@Test
		void exportTransactionsSuccess() throws Exception {
			String csvContent = "Date,Type,Amount,Description,Balance After\n"
					+ "2025-01-15,DEPOSIT,100.00,Initial deposit,100.00\n";
			given(csvService.generateCsv(org.mockito.ArgumentMatchers.any())).willReturn(csvContent);

			mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv"))
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"transactions_CHK-10001.csv\""))
				.andExpect(content().string(csvContent));
		}

		@Test
		void exportTransactionsWithMultipleTransactions() throws Exception {
			String csvContent = "Date,Type,Amount,Description,Balance After\n"
					+ "2025-01-15,DEPOSIT,100.00,Initial deposit,100.00\n"
					+ "2025-01-20,WITHDRAWAL,25.50,ATM withdrawal,74.50\n";
			given(csvService.generateCsv(org.mockito.ArgumentMatchers.any())).willReturn(csvContent);

			String result = mockMvc
				.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
						TEST_ACCOUNT_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv"))
				.andReturn()
				.getResponse()
				.getContentAsString();

			assertThat(result).contains("Date,Type,Amount,Description,Balance After");
			assertThat(result).contains("DEPOSIT");
			assertThat(result).contains("WITHDRAWAL");
		}

		@Test
		void exportTransactionsVerifiesFilename() throws Exception {
			String csvContent = "Date,Type,Amount,Description,Balance After\n";
			given(csvService.generateCsv(org.mockito.ArgumentMatchers.any())).willReturn(csvContent);

			mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/transactions/export", TEST_CUSTOMER_ID,
					TEST_ACCOUNT_ID))
				.andExpect(status().isOk())
				.andExpect(header().exists("Content-Disposition"))
				.andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("CHK-10001")));
		}

	}

}
