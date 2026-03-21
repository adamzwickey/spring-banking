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

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller for exporting transactions as CSV.
 */
@Controller
class TransactionExportController {

	private final CustomerRepository customers;

	TransactionExportController(CustomerRepository customers) {
		this.customers = customers;
	}

	@GetMapping("/customers/{customerId}/accounts/{accountId}/transactions/export")
	public void exportTransactionsCsv(@PathVariable("customerId") int customerId,
			@PathVariable("accountId") int accountId, HttpServletResponse response) throws IOException {
		Optional<Customer> optionalCustomer = customers.findById(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new IllegalArgumentException(
				"Customer not found with id: " + customerId + ". Please ensure the ID is correct "));

		Account account = customer.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException(
					"Account with id " + accountId + " not found for customer with id " + customerId + ".");
		}

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"transactions_" + account.getAccountNumber() + ".csv\"");

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try (PrintWriter writer = response.getWriter()) {
			writer.println("Date,Type,Amount,Description,Balance After");
			for (Transaction txn : account.getTransactions()) {
				writer.printf("%s,%s,%s,%s,%s%n", txn.getDate().format(dateFormatter), txn.getTransactionType(),
						txn.getAmount().toPlainString(), escapeCsv(txn.getDescription()),
						txn.getBalanceAfter() != null ? txn.getBalanceAfter().toPlainString() : "");
			}
		}
	}

	private String escapeCsv(String value) {
		if (value == null) {
			return "";
		}
		if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
			return "\"" + value.replace("\"", "\"\"") + "\"";
		}
		return value;
	}

}
