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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.banking.model.Person;
import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Simple JavaBean domain object representing a customer.
 */
@Entity
@Table(name = "customers")
public class Customer extends Person {

	@Column
	@NotBlank
	private String email;

	@Column
	@NotBlank
	private String phone;

	@Column
	@NotBlank
	private String address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	@OrderBy("account_number")
	private final List<Account> accounts = new ArrayList<>();

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void addAccount(Account account) {
		if (account.isNew()) {
			getAccounts().add(account);
		}
	}

	public Account getAccount(Integer id) {
		for (Account account : getAccounts()) {
			if (!account.isNew()) {
				Integer compId = account.getId();
				if (Objects.equals(compId, id)) {
					return account;
				}
			}
		}
		return null;
	}

	public Account getAccount(String accountNumber) {
		return getAccount(accountNumber, false);
	}

	public Account getAccount(String accountNumber, boolean ignoreNew) {
		for (Account account : getAccounts()) {
			String compNumber = account.getAccountNumber();
			if (compNumber != null && compNumber.equalsIgnoreCase(accountNumber)) {
				if (!ignoreNew || !account.isNew()) {
					return account;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("email", this.email)
			.append("phone", this.phone)
			.append("address", this.address)
			.toString();
	}

	public void addTransaction(Integer accountId, Transaction transaction) {
		Assert.notNull(accountId, "Account identifier must not be null!");
		Assert.notNull(transaction, "Transaction must not be null!");

		Account account = getAccount(accountId);

		Assert.notNull(account, "Invalid Account identifier!");

		account.addTransaction(transaction);
	}

}
