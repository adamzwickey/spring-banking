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

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Service for exporting transactions to CSV format.
 */
@Service
public class TransactionCsvService {

	private static final String CSV_HEADER = "Date,Type,Amount,Description,Balance After\n";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public String generateCsv(Collection<Transaction> transactions) {
		StringWriter writer = new StringWriter();
		writer.append(CSV_HEADER);

		for (Transaction transaction : transactions) {
			writer.append(formatTransaction(transaction));
		}

		return writer.toString();
	}

	private String formatTransaction(Transaction transaction) {
		StringBuilder sb = new StringBuilder();

		sb.append(escapeField(transaction.getDate().format(DATE_FORMATTER))).append(',');
		sb.append(escapeField(transaction.getTransactionType())).append(',');
		sb.append(formatAmount(transaction.getAmount())).append(',');
		sb.append(escapeField(transaction.getDescription())).append(',');
		sb.append(formatAmount(transaction.getBalanceAfter()));
		sb.append('\n');

		return sb.toString();
	}

	private String escapeField(String field) {
		if (field == null) {
			return "";
		}
		if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
			return "\"" + field.replace("\"", "\"\"") + "\"";
		}
		return field;
	}

	private String formatAmount(BigDecimal amount) {
		if (amount == null) {
			return "0.00";
		}
		return amount.toString();
	}

}
