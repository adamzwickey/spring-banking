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

import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Utility class for exporting transaction data to CSV format.
 */
public final class TransactionCsvExporter {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final String CSV_HEADER = "Date,Description,Category,Amount,Running Balance";

	private TransactionCsvExporter() {
		// Utility class
	}

	/**
	 * Generates CSV content from a collection of transactions.
	 * @param transactions the transactions to export
	 * @return CSV formatted string
	 */
	public static String generateCsv(Collection<Transaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return CSV_HEADER;
		}

		StringBuilder csv = new StringBuilder();
		csv.append(CSV_HEADER).append("\n");

		for (Transaction txn : transactions) {
			csv.append(formatCsvRow(txn)).append("\n");
		}

		return csv.toString();
	}

	/**
	 * Formats a single transaction as a CSV row.
	 * @param txn the transaction
	 * @return CSV formatted row
	 */
	static String formatCsvRow(Transaction txn) {
		StringBuilder row = new StringBuilder();

		// Date
		row.append(escapeCsvField(txn.getDate() != null ? DATE_FORMATTER.format(txn.getDate()) : ""));
		row.append(",");

		// Description
		row.append(escapeCsvField(txn.getDescription() != null ? txn.getDescription() : ""));
		row.append(",");

		// Category (transaction type)
		row.append(escapeCsvField(txn.getTransactionType() != null ? txn.getTransactionType() : ""));
		row.append(",");

		// Amount
		row.append(escapeCsvField(txn.getAmount() != null ? txn.getAmount().toString() : "0.00"));
		row.append(",");

		// Running Balance (balanceAfter)
		row.append(escapeCsvField(txn.getBalanceAfter() != null ? txn.getBalanceAfter().toString() : "0.00"));

		return row.toString();
	}

	/**
	 * Escapes a CSV field according to RFC 4180. Fields containing commas, quotes, or
	 * newlines are enclosed in double quotes. Quotes within fields are escaped by doubling
	 * them.
	 * @param field the field to escape
	 * @return the escaped field
	 */
	static String escapeCsvField(String field) {
		if (field == null || field.isEmpty()) {
			return "";
		}

		// Check if field needs quoting
		boolean needsQuoting = field.contains(",") || field.contains("\"") || field.contains("\n")
				|| field.contains("\r");

		if (!needsQuoting) {
			return field;
		}

		// Escape quotes by doubling them and wrap in quotes
		String escaped = field.replace("\"", "\"\"");
		return "\"" + escaped + "\"";
	}

}
