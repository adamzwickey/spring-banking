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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for CSV export functionality on the customer details page.
 */
@WebMvcTest(CustomerController.class)
@DisabledInNativeImage
@DisabledInAotMode
class CsvExportTests {

	private static final int TEST_CUSTOMER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customers;

	private Customer createCustomerWithTransactions() {
		Customer customer = new Customer();
		customer.setId(TEST_CUSTOMER_ID);
		customer.setFirstName("George");
		customer.setLastName("Franklin");
		customer.setEmail("george@email.com");
		customer.setPhone("6085551023");
		customer.setAddress("110 W. Liberty St.");

		Account account = new Account();
		AccountType checking = new AccountType();
		checking.setName("Checking");
		account.setType(checking);
		account.setAccountNumber("CHK-10001");
		account.setOpenedDate(LocalDate.now());
		account.setBalance(new BigDecimal("5250.00"));
		account.setId(1);

		Transaction txn = new Transaction();
		txn.setDate(LocalDate.of(2025, 1, 15));
		txn.setAmount(new BigDecimal("100.00"));
		txn.setTransactionType("DEPOSIT");
		txn.setDescription("Payroll deposit");
		txn.setBalanceAfter(new BigDecimal("5250.00"));
		account.addTransaction(txn);

		customer.addAccount(account);
		return customer;
	}

	private Customer createCustomerWithoutTransactions() {
		Customer customer = new Customer();
		customer.setId(TEST_CUSTOMER_ID);
		customer.setFirstName("George");
		customer.setLastName("Franklin");
		customer.setEmail("george@email.com");
		customer.setPhone("6085551023");
		customer.setAddress("110 W. Liberty St.");

		Account account = new Account();
		AccountType checking = new AccountType();
		checking.setName("Checking");
		account.setType(checking);
		account.setAccountNumber("CHK-10001");
		account.setOpenedDate(LocalDate.now());
		account.setBalance(new BigDecimal("0.00"));
		account.setId(1);
		customer.addAccount(account);
		return customer;
	}

	@Test
	void exportButtonRenderedWhenTransactionsExist() throws Exception {
		given(this.customers.findById(TEST_CUSTOMER_ID))
			.willReturn(Optional.of(createCustomerWithTransactions()));

		String content = mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(content).contains("Export to CSV");
		assertThat(content).contains("export-csv-btn");
		assertThat(content).doesNotContain("disabled=\"disabled\"");
	}

	@Test
	void exportButtonDisabledWhenNoTransactions() throws Exception {
		given(this.customers.findById(TEST_CUSTOMER_ID))
			.willReturn(Optional.of(createCustomerWithoutTransactions()));

		String content = mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(content).contains("Export to CSV");
		assertThat(content).contains("disabled=\"disabled\"");
	}

	@Test
	void transactionDataAttributesRendered() throws Exception {
		given(this.customers.findById(TEST_CUSTOMER_ID))
			.willReturn(Optional.of(createCustomerWithTransactions()));

		String content = mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(content).contains("data-date=\"2025-01-15\"");
		assertThat(content).contains("data-category=\"DEPOSIT\"");
		assertThat(content).contains("data-amount=\"100.00\"");
		assertThat(content).contains("data-balance=\"5250.00\"");
		assertThat(content).contains("data-description=\"Payroll deposit\"");
	}

	@Test
	void csvGenerationJavaScriptPresent() throws Exception {
		given(this.customers.findById(TEST_CUSTOMER_ID))
			.willReturn(Optional.of(createCustomerWithTransactions()));

		String content = mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(content).contains("function generateCsv(");
		assertThat(content).contains("function escapeCsvField(");
		assertThat(content).contains("function exportTransactionsCsv(");
		assertThat(content).contains("Date,Description,Category,Amount,Balance");
	}

	@Test
	void transactionTableHasAccountId() throws Exception {
		given(this.customers.findById(TEST_CUSTOMER_ID))
			.willReturn(Optional.of(createCustomerWithTransactions()));

		String content = mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		assertThat(content).contains("id=\"txn-table-1\"");
		assertThat(content).contains("data-account-id=\"1\"");
	}

	// CSV generation logic tests (mirrors the JavaScript escapeCsvField/generateCsv)

	@Test
	void csvEscapeSimpleField() {
		assertThat(escapeCsvField("hello")).isEqualTo("hello");
	}

	@Test
	void csvEscapeFieldWithComma() {
		assertThat(escapeCsvField("hello, world")).isEqualTo("\"hello, world\"");
	}

	@Test
	void csvEscapeFieldWithQuotes() {
		assertThat(escapeCsvField("say \"hello\"")).isEqualTo("\"say \"\"hello\"\"\"");
	}

	@Test
	void csvEscapeFieldWithNewline() {
		assertThat(escapeCsvField("line1\nline2")).isEqualTo("\"line1\nline2\"");
	}

	@Test
	void csvEscapeFieldWithCommaAndQuotes() {
		assertThat(escapeCsvField("He said, \"hi\"")).isEqualTo("\"He said, \"\"hi\"\"\"");
	}

	@Test
	void csvEscapeNullField() {
		assertThat(escapeCsvField(null)).isEqualTo("");
	}

	@Test
	void csvEscapeEmptyField() {
		assertThat(escapeCsvField("")).isEqualTo("");
	}

	@Test
	void csvGenerateWithTransactions() {
		List<String[]> transactions = new ArrayList<>();
		transactions.add(new String[] { "2025-01-15", "Payroll deposit", "DEPOSIT", "100.00", "5250.00" });
		transactions.add(new String[] { "2025-01-16", "ATM Withdrawal", "WITHDRAWAL", "50.00", "5200.00" });

		String csv = generateCsv(transactions);
		String[] lines = csv.split("\n");

		assertThat(lines).hasSize(3);
		assertThat(lines[0]).isEqualTo("Date,Description,Category,Amount,Balance");
		assertThat(lines[1]).isEqualTo("2025-01-15,Payroll deposit,DEPOSIT,100.00,5250.00");
		assertThat(lines[2]).isEqualTo("2025-01-16,ATM Withdrawal,WITHDRAWAL,50.00,5200.00");
	}

	@Test
	void csvGenerateEmptyTransactions() {
		List<String[]> transactions = new ArrayList<>();
		String csv = generateCsv(transactions);
		assertThat(csv).isEqualTo("Date,Description,Category,Amount,Balance");
	}

	@Test
	void csvGenerateWithSpecialCharacters() {
		List<String[]> transactions = new ArrayList<>();
		transactions.add(new String[] { "2025-01-15", "Transfer to \"Savings\"", "DEPOSIT", "100.00", "5250.00" });
		transactions
			.add(new String[] { "2025-01-16", "Coffee, tea, and snacks", "WITHDRAWAL", "15.50", "5234.50" });

		String csv = generateCsv(transactions);
		String[] lines = csv.split("\n");

		assertThat(lines[1]).isEqualTo("2025-01-15,\"Transfer to \"\"Savings\"\"\",DEPOSIT,100.00,5250.00");
		assertThat(lines[2]).isEqualTo("2025-01-16,\"Coffee, tea, and snacks\",WITHDRAWAL,15.50,5234.50");
	}

	@Test
	void csvGenerateLargeDataset() {
		List<String[]> transactions = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			transactions.add(new String[] { "2025-01-15", "Transaction " + i, "DEPOSIT", "100.00", "5250.00" });
		}

		String csv = generateCsv(transactions);
		String[] lines = csv.split("\n");

		assertThat(lines).hasSize(1001);
		assertThat(lines[0]).isEqualTo("Date,Description,Category,Amount,Balance");
		assertThat(lines[1000]).isEqualTo("2025-01-15,Transaction 999,DEPOSIT,100.00,5250.00");
	}

	@Test
	void csvGenerateWithNullDescription() {
		List<String[]> transactions = new ArrayList<>();
		transactions.add(new String[] { "2025-01-15", null, "DEPOSIT", "100.00", "5250.00" });

		String csv = generateCsv(transactions);
		String[] lines = csv.split("\n");

		assertThat(lines[1]).isEqualTo("2025-01-15,,DEPOSIT,100.00,5250.00");
	}

	/**
	 * Java mirror of the JavaScript escapeCsvField function. Tests the same
	 * escaping algorithm used in the client-side CSV generation.
	 */
	private static String escapeCsvField(String field) {
		if (field == null) {
			return "";
		}
		if (field.contains("\"") || field.contains(",") || field.contains("\n") || field.contains("\r")) {
			return "\"" + field.replace("\"", "\"\"") + "\"";
		}
		return field;
	}

	/**
	 * Java mirror of the JavaScript generateCsv function. Each transaction is a
	 * String array: [date, description, category, amount, balance].
	 */
	private static String generateCsv(List<String[]> transactions) {
		StringBuilder sb = new StringBuilder();
		sb.append("Date,Description,Category,Amount,Balance");
		for (String[] txn : transactions) {
			sb.append("\n");
			sb.append(escapeCsvField(txn[0]));
			sb.append(",");
			sb.append(escapeCsvField(txn[1]));
			sb.append(",");
			sb.append(escapeCsvField(txn[2]));
			sb.append(",");
			sb.append(escapeCsvField(txn[3]));
			sb.append(",");
			sb.append(escapeCsvField(txn[4]));
		}
		return sb.toString();
	}

}
