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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for customer operations.
 */
@Controller
class CustomerController {

	private static final String VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM = "customers/createOrUpdateCustomerForm";

	private final CustomerRepository customers;

	public CustomerController(CustomerRepository customers) {
		this.customers = customers;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("customer")
	public Customer findCustomer(@PathVariable(name = "customerId", required = false) Integer customerId) {
		return customerId == null ? new Customer()
				: this.customers.findById(customerId)
					.orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId
							+ ". Please ensure the ID is correct " + "and the customer exists in the database."));
	}

	@GetMapping("/customers/new")
	public String initCreationForm() {
		return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/customers/new")
	public String processCreationForm(@Valid Customer customer, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "There was an error in creating the customer.");
			return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
		}

		this.customers.save(customer);
		redirectAttributes.addFlashAttribute("message", "New Customer Created");
		return "redirect:/customers/" + customer.getId();
	}

	@GetMapping("/customers/find")
	public String initFindForm() {
		return "customers/findCustomers";
	}

	@GetMapping("/customers")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Customer customer, BindingResult result,
			Model model) {
		String lastName = customer.getLastName();
		if (lastName == null) {
			lastName = "";
		}

		Page<Customer> customersResults = findPaginatedForCustomersLastName(page, lastName);
		if (customersResults.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return "customers/findCustomers";
		}

		if (customersResults.getTotalElements() == 1) {
			customer = customersResults.iterator().next();
			return "redirect:/customers/" + customer.getId();
		}

		return addPaginationModel(page, model, customersResults);
	}

	private String addPaginationModel(int page, Model model, Page<Customer> paginated) {
		List<Customer> listCustomers = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listCustomers", listCustomers);
		return "customers/customersList";
	}

	private Page<Customer> findPaginatedForCustomersLastName(int page, String lastname) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return customers.findByLastNameStartingWith(lastname, pageable);
	}

	@GetMapping("/customers/{customerId}/edit")
	public String initUpdateCustomerForm() {
		return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/customers/{customerId}/edit")
	public String processUpdateCustomerForm(@Valid Customer customer, BindingResult result,
			@PathVariable("customerId") int customerId, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "There was an error in updating the customer.");
			return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
		}

		if (!Objects.equals(customer.getId(), customerId)) {
			result.rejectValue("id", "mismatch", "The customer ID in the form does not match the URL.");
			redirectAttributes.addFlashAttribute("error", "Customer ID mismatch. Please try again.");
			return "redirect:/customers/{customerId}/edit";
		}

		customer.setId(customerId);
		this.customers.save(customer);
		redirectAttributes.addFlashAttribute("message", "Customer Values Updated");
		return "redirect:/customers/{customerId}";
	}

	@GetMapping("/customers/{customerId}")
	public ModelAndView showCustomer(@PathVariable("customerId") int customerId) {
		ModelAndView mav = new ModelAndView("customers/customerDetails");
		Optional<Customer> optionalCustomer = this.customers.findById(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new IllegalArgumentException(
				"Customer not found with id: " + customerId + ". Please ensure the ID is correct "));
		mav.addObject(customer);
		return mav;
	}

}
