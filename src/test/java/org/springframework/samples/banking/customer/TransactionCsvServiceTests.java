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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link TransactionCsvService}
 */
class TransactionCsvServiceTests {

	private TransactionCsvService csvService;

	@BeforeEach
	void setup() {
		this.csvService = new TransactionCsvService();
	}

	@Test
	void generateCsvWithEmptyTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		String csv = csvService.generateCsv(transactions);

		assertThat(csv).isEqualTo("Date,Type,Amount,Description,Balance After\n");
	}

	@Test
	void generateCsvWithSingleTransaction() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription("Initial deposit");
		transaction.setBalanceAfter(new BigDecimal("100.00"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,100.00,Initial deposit,100.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithMultipleTransactions() {
		Transaction deposit = new Transaction();
		deposit.setDate(LocalDate.of(2025, 1, 15));
		deposit.setTransactionType("DEPOSIT");
		deposit.setAmount(new BigDecimal("100.00"));
		deposit.setDescription("Initial deposit");
		deposit.setBalanceAfter(new BigDecimal("100.00"));

		Transaction withdrawal = new Transaction();
		withdrawal.setDate(LocalDate.of(2025, 1, 20));
		withdrawal.setTransactionType("WITHDRAWAL");
		withdrawal.setAmount(new BigDecimal("25.50"));
		withdrawal.setDescription("ATM withdrawal");
		withdrawal.setBalanceAfter(new BigDecimal("74.50"));

		List<Transaction> transactions = List.of(deposit, withdrawal);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,100.00,Initial deposit,100.00\n"
				+ "2025-01-20,WITHDRAWAL,25.50,ATM withdrawal,74.50\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithNullDescription() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription(null);
		transaction.setBalanceAfter(new BigDecimal("100.00"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,100.00,,100.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithCommasInDescription() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription("Deposit from Smith, John, Jr.");
		transaction.setBalanceAfter(new BigDecimal("100.00"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n"
				+ "2025-01-15,DEPOSIT,100.00,\"Deposit from Smith, John, Jr.\",100.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithQuotesInDescription() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription("Payment for \"consulting\"");
		transaction.setBalanceAfter(new BigDecimal("100.00"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n"
				+ "2025-01-15,DEPOSIT,100.00,\"Payment for \"\"consulting\"\"\",100.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithNewlinesInDescription() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription("Line 1\nLine 2");
		transaction.setBalanceAfter(new BigDecimal("100.00"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,100.00,\"Line 1\nLine 2\",100.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithNullBalanceAfter() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setDescription("Test");
		transaction.setBalanceAfter(null);

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,100.00,Test,0.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void generateCsvWithDecimalAmounts() {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.of(2025, 1, 15));
		transaction.setTransactionType("DEPOSIT");
		transaction.setAmount(new BigDecimal("1234.567"));
		transaction.setDescription("Test");
		transaction.setBalanceAfter(new BigDecimal("5678.901"));

		List<Transaction> transactions = List.of(transaction);
		String csv = csvService.generateCsv(transactions);

		String expected = "Date,Type,Amount,Description,Balance After\n" + "2025-01-15,DEPOSIT,1234.567,Test,5678.901\n";

		assertThat(csv).isEqualTo(expected);
	}

}
