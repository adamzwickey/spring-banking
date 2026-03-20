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

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'AccountType'.
 */
@Component
public class AccountTypeFormatter implements Formatter<AccountType> {

	private final AccountTypeRepository types;

	public AccountTypeFormatter(AccountTypeRepository types) {
		this.types = types;
	}

	@Override
	public String print(AccountType accountType, Locale locale) {
		String name = accountType.getName();
		return name != null ? name : "<null>";
	}

	@Override
	public AccountType parse(String text, Locale locale) throws ParseException {
		Collection<AccountType> findAccountTypes = this.types.findAccountTypes();
		for (AccountType type : findAccountTypes) {
			if (Objects.equals(type.getName(), text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
