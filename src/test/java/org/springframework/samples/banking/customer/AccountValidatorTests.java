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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link AccountValidator}
 */
@ExtendWith(MockitoExtension.class)
@DisabledInNativeImage
class AccountValidatorTests {

	private AccountValidator accountValidator;

	private Account account;

	private AccountType accountType;

	private Errors errors;

	private static final String accountNumber = "CHK-10001";

	private static final String accountTypeName = "Checking";

	private static final LocalDate openedDate = LocalDate.of(2020, 1, 1);

	@BeforeEach
	void setUp() {
		accountValidator = new AccountValidator();
		account = new Account();
		accountType = new AccountType();
		errors = new MapBindingResult(new HashMap<>(), "account");
	}

	@Test
	void validate() {
		accountType.setName(accountTypeName);
		account.setAccountNumber(accountNumber);
		account.setType(accountType);
		account.setOpenedDate(openedDate);

		accountValidator.validate(account, errors);

		assertFalse(errors.hasErrors());
	}

	@Nested
	class ValidateHasErrors {

		@Test
		void validateWithInvalidAccountNumber() {
			accountType.setName(accountTypeName);
			account.setAccountNumber("");
			account.setType(accountType);
			account.setOpenedDate(openedDate);

			accountValidator.validate(account, errors);

			assertTrue(errors.hasFieldErrors("accountNumber"));
		}

		@Test
		void validateWithInvalidAccountType() {
			account.setAccountNumber(accountNumber);
			account.setType(null);
			account.setOpenedDate(openedDate);

			accountValidator.validate(account, errors);

			assertTrue(errors.hasFieldErrors("type"));
		}

		@Test
		void validateWithInvalidOpenedDate() {
			accountType.setName(accountTypeName);
			account.setAccountNumber(accountNumber);
			account.setType(accountType);
			account.setOpenedDate(null);

			accountValidator.validate(account, errors);

			assertTrue(errors.hasFieldErrors("openedDate"));
		}

	}

}
