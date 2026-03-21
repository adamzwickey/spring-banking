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
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link CustomerController}
 */
@WebMvcTest(CustomerController.class)
@DisabledInNativeImage
@DisabledInAotMode
class CustomerControllerTests {

	private static final int TEST_CUSTOMER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CustomerRepository customers;

	private Customer george() {
		Customer george = new Customer();
		george.setId(TEST_CUSTOMER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setEmail("george@email.com");
		george.setPhone("6085551023");
		george.setAddress("110 W. Liberty St.");
		Account account = new Account();
		AccountType checking = new AccountType();
		checking.setName("Checking");
		account.setType(checking);
		account.setAccountNumber("CHK-10001");
		account.setOpenedDate(LocalDate.now());
		account.setBalance(new BigDecimal("5250.00"));
		george.addAccount(account);
		account.setId(1);
		return george;
	}

	@BeforeEach
	void setup() {

		Customer george = george();
		given(this.customers.findByLastNameStartingWith(eq("Franklin"), any(Pageable.class)))
			.willReturn(new PageImpl<>(List.of(george)));

		given(this.customers.findById(TEST_CUSTOMER_ID)).willReturn(Optional.of(george));
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setTransactionType("DEPOSIT");
		george.getAccounts().get(0).getTransactions().add(transaction);

	}

	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/customers/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("customer"))
			.andExpect(view().name("customers/createOrUpdateCustomerForm"));
	}

	@Test
	void processCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/customers/new").param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("email", "joe@email.com")
				.param("phone", "1316761638")
				.param("address", "123 Caramel Street"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	void processCreationFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/customers/new").param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("email", "joe@email.com"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("customer"))
			.andExpect(model().attributeHasFieldErrors("customer", "phone"))
			.andExpect(model().attributeHasFieldErrors("customer", "address"))
			.andExpect(view().name("customers/createOrUpdateCustomerForm"));
	}

	@Test
	void initFindForm() throws Exception {
		mockMvc.perform(get("/customers/find"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("customer"))
			.andExpect(view().name("customers/findCustomers"));
	}

	@Test
	void processFindFormSuccess() throws Exception {
		Page<Customer> tasks = new PageImpl<>(List.of(george(), new Customer()));
		when(this.customers.findByLastNameStartingWith(anyString(), any(Pageable.class))).thenReturn(tasks);
		mockMvc.perform(get("/customers?page=1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customers/customersList"));
	}

	@Test
	void processFindFormByLastName() throws Exception {
		Page<Customer> tasks = new PageImpl<>(List.of(george()));
		when(this.customers.findByLastNameStartingWith(eq("Franklin"), any(Pageable.class))).thenReturn(tasks);
		mockMvc.perform(get("/customers?page=1").param("lastName", "Franklin"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/" + TEST_CUSTOMER_ID));
	}

	@Test
	void processFindFormNoCustomersFound() throws Exception {
		Page<Customer> tasks = new PageImpl<>(List.of());
		when(this.customers.findByLastNameStartingWith(eq("Unknown Surname"), any(Pageable.class))).thenReturn(tasks);
		mockMvc.perform(get("/customers?page=1").param("lastName", "Unknown Surname"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasFieldErrors("customer", "lastName"))
			.andExpect(model().attributeHasFieldErrorCode("customer", "lastName", "notFound"))
			.andExpect(view().name("customers/findCustomers"));

	}

	@Test
	void initUpdateCustomerForm() throws Exception {
		mockMvc.perform(get("/customers/{customerId}/edit", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("customer"))
			.andExpect(model().attribute("customer", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("customer", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("customer", hasProperty("email", is("george@email.com"))))
			.andExpect(model().attribute("customer", hasProperty("phone", is("6085551023"))))
			.andExpect(model().attribute("customer", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(view().name("customers/createOrUpdateCustomerForm"));
	}

	@Test
	void processUpdateCustomerFormSuccess() throws Exception {
		mockMvc
			.perform(post("/customers/{customerId}/edit", TEST_CUSTOMER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("email", "joe@email.com")
				.param("phone", "1616291589")
				.param("address", "123 Caramel Street"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/{customerId}"));
	}

	@Test
	void processUpdateCustomerFormUnchangedSuccess() throws Exception {
		mockMvc.perform(post("/customers/{customerId}/edit", TEST_CUSTOMER_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers/{customerId}"));
	}

	@Test
	void processUpdateCustomerFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/customers/{customerId}/edit", TEST_CUSTOMER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("email", "")
				.param("phone", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("customer"))
			.andExpect(model().attributeHasFieldErrors("customer", "email"))
			.andExpect(model().attributeHasFieldErrors("customer", "phone"))
			.andExpect(view().name("customers/createOrUpdateCustomerForm"));
	}

	@Test
	void showCustomer() throws Exception {
		mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attribute("customer", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("customer", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("customer", hasProperty("email", is("george@email.com"))))
			.andExpect(model().attribute("customer", hasProperty("phone", is("6085551023"))))
			.andExpect(model().attribute("customer", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("customer", hasProperty("accounts", not(empty()))))
			.andExpect(model().attribute("customer",
					hasProperty("accounts", hasItem(hasProperty("transactions", hasSize(greaterThan(0)))))))
			.andExpect(view().name("customers/customerDetails"));
	}

	@Test
	void processUpdateCustomerFormWithIdMismatch() throws Exception {
		int pathCustomerId = 1;

		Customer customer = new Customer();
		customer.setId(2);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setEmail("john@email.com");
		customer.setPhone("0123456789");
		customer.setAddress("Center Street");

		when(customers.findById(pathCustomerId)).thenReturn(Optional.of(customer));

		mockMvc
			.perform(MockMvcRequestBuilders.post("/customers/{customerId}/edit", pathCustomerId)
				.flashAttr("customer", customer))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/customers/" + pathCustomerId + "/edit"))
			.andExpect(flash().attributeExists("error"));
	}

}
