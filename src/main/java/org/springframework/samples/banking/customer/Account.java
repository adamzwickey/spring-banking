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
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.banking.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

/**
 * Simple business object representing a bank account.
 */
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "opened_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate openedDate;

	@Column(precision = 19, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private AccountType type;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	@OrderBy("transaction_date ASC")
	private final Set<Transaction> transactions = new LinkedHashSet<>();

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public LocalDate getOpenedDate() {
		return this.openedDate;
	}

	public void setOpenedDate(LocalDate openedDate) {
		this.openedDate = openedDate;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountType getType() {
		return this.type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public Collection<Transaction> getTransactions() {
		return this.transactions;
	}

	public void addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
	}

	@Override
	public String toString() {
		return this.accountNumber;
	}

}
