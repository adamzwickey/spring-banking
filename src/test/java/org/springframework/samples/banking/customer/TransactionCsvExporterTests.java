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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;

/**
 * Test class for {@link TransactionCsvExporter}
 */
@DisabledInNativeImage
class TransactionCsvExporterTests {

	@Test
	void testGenerateCsvWithEmptyList() {
		String csv = TransactionCsvExporter.generateCsv(Collections.emptyList());
		assertThat(csv).isEqualTo("Date,Description,Category,Amount,Running Balance");
	}

	@Test
	void testGenerateCsvWithNullList() {
		String csv = TransactionCsvExporter.generateCsv(null);
		assertThat(csv).isEqualTo("Date,Description,Category,Amount,Running Balance");
	}

	@Test
	void testGenerateCsvWithSingleTransaction() {
		Transaction txn = createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT", "Initial deposit",
				new BigDecimal("1000.00"), new BigDecimal("1000.00"));

		String csv = TransactionCsvExporter.generateCsv(List.of(txn));

		assertThat(csv).isEqualTo(
				"Date,Description,Category,Amount,Running Balance\n" + "2025-01-15,Initial deposit,DEPOSIT,1000.00,1000.00\n");
	}

	@Test
	void testGenerateCsvWithMultipleTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT", "Initial deposit",
				new BigDecimal("1000.00"), new BigDecimal("1000.00")));
		transactions.add(createTransaction(LocalDate.of(2025, 1, 20), "WITHDRAWAL", "ATM withdrawal",
				new BigDecimal("50.00"), new BigDecimal("950.00")));
		transactions.add(createTransaction(LocalDate.of(2025, 1, 25), "DEPOSIT", "Paycheck",
				new BigDecimal("2500.00"), new BigDecimal("3450.00")));

		String csv = TransactionCsvExporter.generateCsv(transactions);

		String expected = "Date,Description,Category,Amount,Running Balance\n"
				+ "2025-01-15,Initial deposit,DEPOSIT,1000.00,1000.00\n"
				+ "2025-01-20,ATM withdrawal,WITHDRAWAL,50.00,950.00\n"
				+ "2025-01-25,Paycheck,DEPOSIT,2500.00,3450.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void testEscapeCsvFieldWithComma() {
		String result = TransactionCsvExporter.escapeCsvField("Payment for invoice 123, thank you");
		assertThat(result).isEqualTo("\"Payment for invoice 123, thank you\"");
	}

	@Test
	void testEscapeCsvFieldWithQuote() {
		String result = TransactionCsvExporter.escapeCsvField("Payment for \"special\" service");
		assertThat(result).isEqualTo("\"Payment for \"\"special\"\" service\"");
	}

	@Test
	void testEscapeCsvFieldWithCommaAndQuote() {
		String result = TransactionCsvExporter.escapeCsvField("Payment for \"invoice\", #123");
		assertThat(result).isEqualTo("\"Payment for \"\"invoice\"\", #123\"");
	}

	@Test
	void testEscapeCsvFieldWithNewline() {
		String result = TransactionCsvExporter.escapeCsvField("Line 1\nLine 2");
		assertThat(result).isEqualTo("\"Line 1\nLine 2\"");
	}

	@Test
	void testEscapeCsvFieldWithCarriageReturn() {
		String result = TransactionCsvExporter.escapeCsvField("Line 1\r\nLine 2");
		assertThat(result).isEqualTo("\"Line 1\r\nLine 2\"");
	}

	@Test
	void testEscapeCsvFieldWithSimpleText() {
		String result = TransactionCsvExporter.escapeCsvField("Simple payment");
		assertThat(result).isEqualTo("Simple payment");
	}

	@Test
	void testEscapeCsvFieldWithNull() {
		String result = TransactionCsvExporter.escapeCsvField(null);
		assertThat(result).isEmpty();
	}

	@Test
	void testEscapeCsvFieldWithEmptyString() {
		String result = TransactionCsvExporter.escapeCsvField("");
		assertThat(result).isEmpty();
	}

	@Test
	void testFormatCsvRowWithSpecialCharacters() {
		Transaction txn = createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT",
				"Payment for \"invoice\", #123", new BigDecimal("1000.00"), new BigDecimal("1000.00"));

		String row = TransactionCsvExporter.formatCsvRow(txn);

		assertThat(row).isEqualTo("2025-01-15,\"Payment for \"\"invoice\"\", #123\",DEPOSIT,1000.00,1000.00");
	}

	@Test
	void testFormatCsvRowWithNullDescription() {
		Transaction txn = createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT", null,
				new BigDecimal("1000.00"), new BigDecimal("1000.00"));

		String row = TransactionCsvExporter.formatCsvRow(txn);

		assertThat(row).isEqualTo("2025-01-15,,DEPOSIT,1000.00,1000.00");
	}

	@Test
	void testFormatCsvRowWithNullDate() {
		Transaction txn = new Transaction();
		txn.setDate(null);
		txn.setTransactionType("DEPOSIT");
		txn.setDescription("Test");
		txn.setAmount(new BigDecimal("100.00"));
		txn.setBalanceAfter(new BigDecimal("100.00"));

		String row = TransactionCsvExporter.formatCsvRow(txn);

		assertThat(row).isEqualTo(",Test,DEPOSIT,100.00,100.00");
	}

	@Test
	void testGenerateCsvWithLargeDataset() {
		List<Transaction> transactions = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			transactions.add(createTransaction(LocalDate.of(2025, 1, 1).plusDays(i), "DEPOSIT",
					"Transaction " + i, new BigDecimal("100.00"), new BigDecimal(String.valueOf(100 * (i + 1)))));
		}

		String csv = TransactionCsvExporter.generateCsv(transactions);

		String[] lines = csv.split("\n");
		assertThat(lines).hasSize(1001); // Header + 1000 transactions
		assertThat(lines[0]).isEqualTo("Date,Description,Category,Amount,Running Balance");
		assertThat(lines[1]).startsWith("2025-01-01,Transaction 0,DEPOSIT,100.00,100");
		assertThat(lines[1000]).contains("Transaction 999");
	}

	@Test
	void testGenerateCsvWithComplexSpecialCharacters() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT",
				"Payment from \"Smith, John\" - Invoice #123", new BigDecimal("1000.00"), new BigDecimal("1000.00")));
		transactions.add(createTransaction(LocalDate.of(2025, 1, 20), "WITHDRAWAL",
				"ATM withdrawal\nat Main Street", new BigDecimal("50.00"), new BigDecimal("950.00")));
		transactions.add(createTransaction(LocalDate.of(2025, 1, 25), "TRANSFER",
				"Transfer to savings\r\nAccount: 9876", new BigDecimal("200.00"), new BigDecimal("750.00")));

		String csv = TransactionCsvExporter.generateCsv(transactions);

		String expected = "Date,Description,Category,Amount,Running Balance\n"
				+ "2025-01-15,\"Payment from \"\"Smith, John\"\" - Invoice #123\",DEPOSIT,1000.00,1000.00\n"
				+ "2025-01-20,\"ATM withdrawal\nat Main Street\",WITHDRAWAL,50.00,950.00\n"
				+ "2025-01-25,\"Transfer to savings\r\nAccount: 9876\",TRANSFER,200.00,750.00\n";

		assertThat(csv).isEqualTo(expected);
	}

	@Test
	void testGenerateCsvWithDecimalPrecision() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(createTransaction(LocalDate.of(2025, 1, 15), "DEPOSIT", "Test",
				new BigDecimal("1234.56"), new BigDecimal("1234.56")));
		transactions.add(createTransaction(LocalDate.of(2025, 1, 16), "WITHDRAWAL", "Test",
				new BigDecimal("0.01"), new BigDecimal("1234.55")));

		String csv = TransactionCsvExporter.generateCsv(transactions);

		assertThat(csv).contains("1234.56,1234.56");
		assertThat(csv).contains("0.01,1234.55");
	}

	private Transaction createTransaction(LocalDate date, String type, String description, BigDecimal amount,
			BigDecimal balanceAfter) {
		Transaction txn = new Transaction();
		txn.setDate(date);
		txn.setTransactionType(type);
		txn.setDescription(description);
		txn.setAmount(amount);
		txn.setBalanceAfter(balanceAfter);
		return txn;
	}

}
