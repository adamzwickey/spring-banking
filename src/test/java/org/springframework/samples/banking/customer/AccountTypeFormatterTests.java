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
import static org.mockito.BDDMockito.given;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for {@link AccountTypeFormatter}
 */
@ExtendWith(MockitoExtension.class)
@DisabledInNativeImage
class AccountTypeFormatterTests {

	@Mock
	private AccountTypeRepository types;

	private AccountTypeFormatter accountTypeFormatter;

	@BeforeEach
	void setup() {
		this.accountTypeFormatter = new AccountTypeFormatter(types);
	}

	@Test
	void testPrint() {
		AccountType accountType = new AccountType();
		accountType.setName("Checking");
		String accountTypeName = this.accountTypeFormatter.print(accountType, Locale.ENGLISH);
		assertThat(accountTypeName).isEqualTo("Checking");
	}

	@Test
	void shouldParse() throws ParseException {
		given(types.findAccountTypes()).willReturn(makeAccountTypes());
		AccountType accountType = accountTypeFormatter.parse("Savings", Locale.ENGLISH);
		assertThat(accountType.getName()).isEqualTo("Savings");
	}

	@Test
	void shouldThrowParseException() {
		given(types.findAccountTypes()).willReturn(makeAccountTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			accountTypeFormatter.parse("Investment", Locale.ENGLISH);
		});
	}

	private List<AccountType> makeAccountTypes() {
		List<AccountType> accountTypes = new ArrayList<>();
		accountTypes.add(new AccountType() {
			{
				setName("Checking");
			}
		});
		accountTypes.add(new AccountType() {
			{
				setName("Savings");
			}
		});
		return accountTypes;
	}

}
