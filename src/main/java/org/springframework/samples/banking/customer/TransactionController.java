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
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for transaction operations.
 */
@Controller
class TransactionController {

	private final CustomerRepository customers;

	private final AccountService accountService;

	public TransactionController(CustomerRepository customers, AccountService accountService) {
		this.customers = customers;
		this.accountService = accountService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("transaction")
	public Transaction loadAccountWithTransaction(@PathVariable("customerId") int customerId,
			@PathVariable("accountId") int accountId, Map<String, Object> model) {
		Optional<Customer> optionalCustomer = customers.findById(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new IllegalArgumentException(
				"Customer not found with id: " + customerId + ". Please ensure the ID is correct "));

		Account account = customer.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException(
					"Account with id " + accountId + " not found for customer with id " + customerId + ".");
		}
		model.put("account", account);
		model.put("customer", customer);

		Transaction transaction = new Transaction();
		account.addTransaction(transaction);
		return transaction;
	}

	@GetMapping("/customers/{customerId}/accounts/{accountId}/transactions/new")
	public String initNewTransactionForm() {
		return "accounts/createOrUpdateTransactionForm";
	}

	@PostMapping("/customers/{customerId}/accounts/{accountId}/transactions/new")
	public String processNewTransactionForm(@ModelAttribute Customer customer, @PathVariable int accountId,
			@Valid Transaction transaction, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "accounts/createOrUpdateTransactionForm";
		}

		try {
			BigDecimal amount = transaction.getAmount();
			String type = transaction.getTransactionType();
			String description = transaction.getDescription();

			if ("DEPOSIT".equals(type)) {
				accountService.deposit(customer, accountId, amount, description);
			}
			else if ("WITHDRAWAL".equals(type)) {
				accountService.withdraw(customer, accountId, amount, description);
			}
			else {
				customer.addTransaction(accountId, transaction);
				this.customers.save(customer);
			}
		}
		catch (InsufficientFundsException ex) {
			result.rejectValue("amount", "insufficientFunds", ex.getMessage());
			return "accounts/createOrUpdateTransactionForm";
		}

		redirectAttributes.addFlashAttribute("message", "Your transaction has been recorded");
		return "redirect:/customers/{customerId}";
	}

}
