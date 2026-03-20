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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for account operations.
 */
@Controller
@RequestMapping("/customers/{customerId}")
class AccountController {

	private static final String VIEWS_ACCOUNTS_CREATE_OR_UPDATE_FORM = "accounts/createOrUpdateAccountForm";

	private final CustomerRepository customers;

	private final AccountTypeRepository types;

	public AccountController(CustomerRepository customers, AccountTypeRepository types) {
		this.customers = customers;
		this.types = types;
	}

	@ModelAttribute("types")
	public Collection<AccountType> populateAccountTypes() {
		return this.types.findAccountTypes();
	}

	@ModelAttribute("customer")
	public Customer findCustomer(@PathVariable("customerId") int customerId) {
		Optional<Customer> optionalCustomer = this.customers.findById(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new IllegalArgumentException(
				"Customer not found with id: " + customerId + ". Please ensure the ID is correct "));
		return customer;
	}

	@ModelAttribute("account")
	public Account findAccount(@PathVariable("customerId") int customerId,
			@PathVariable(name = "accountId", required = false) Integer accountId) {

		if (accountId == null) {
			return new Account();
		}

		Optional<Customer> optionalCustomer = this.customers.findById(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new IllegalArgumentException(
				"Customer not found with id: " + customerId + ". Please ensure the ID is correct "));
		return customer.getAccount(accountId);
	}

	@InitBinder("customer")
	public void initCustomerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("account")
	public void initAccountBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new AccountValidator());
	}

	@GetMapping("/accounts/new")
	public String initCreationForm(Customer customer, ModelMap model) {
		Account account = new Account();
		customer.addAccount(account);
		return VIEWS_ACCOUNTS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/accounts/new")
	public String processCreationForm(Customer customer, @Valid Account account, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (StringUtils.hasText(account.getAccountNumber()) && account.isNew()
				&& customer.getAccount(account.getAccountNumber(), true) != null) {
			result.rejectValue("accountNumber", "duplicate", "already exists");
		}

		LocalDate currentDate = LocalDate.now();
		if (account.getOpenedDate() != null && account.getOpenedDate().isAfter(currentDate)) {
			result.rejectValue("openedDate", "typeMismatch.openedDate");
		}

		if (result.hasErrors()) {
			return VIEWS_ACCOUNTS_CREATE_OR_UPDATE_FORM;
		}

		customer.addAccount(account);
		this.customers.save(customer);
		redirectAttributes.addFlashAttribute("message", "New Account has been Added");
		return "redirect:/customers/{customerId}";
	}

	@GetMapping("/accounts/{accountId}/edit")
	public String initUpdateForm() {
		return VIEWS_ACCOUNTS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/accounts/{accountId}/edit")
	public String processUpdateForm(Customer customer, @Valid Account account, BindingResult result,
			RedirectAttributes redirectAttributes) {

		String accountNumber = account.getAccountNumber();

		if (StringUtils.hasText(accountNumber)) {
			Account existingAccount = customer.getAccount(accountNumber, false);
			if (existingAccount != null && !Objects.equals(existingAccount.getId(), account.getId())) {
				result.rejectValue("accountNumber", "duplicate", "already exists");
			}
		}

		LocalDate currentDate = LocalDate.now();
		if (account.getOpenedDate() != null && account.getOpenedDate().isAfter(currentDate)) {
			result.rejectValue("openedDate", "typeMismatch.openedDate");
		}

		if (result.hasErrors()) {
			return VIEWS_ACCOUNTS_CREATE_OR_UPDATE_FORM;
		}

		updateAccountDetails(customer, account);
		redirectAttributes.addFlashAttribute("message", "Account details has been edited");
		return "redirect:/customers/{customerId}";
	}

	private void updateAccountDetails(Customer customer, Account account) {
		Integer id = account.getId();
		Assert.state(id != null, "'account.getId()' must not be null");
		Account existingAccount = customer.getAccount(id);
		if (existingAccount != null) {
			existingAccount.setAccountNumber(account.getAccountNumber());
			existingAccount.setOpenedDate(account.getOpenedDate());
			existingAccount.setType(account.getType());
		}
		else {
			customer.addAccount(account);
		}
		this.customers.save(customer);
	}

}
