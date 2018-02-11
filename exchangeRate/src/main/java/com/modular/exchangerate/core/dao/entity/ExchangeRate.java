/*******************************************************************************
 * Copyright 2018 Francesco Cina'
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.modular.exchangerate.core.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ExchangeRateId.class)
@Table(name="CURRENCY_EXCHANGE_RATE")
public class ExchangeRate {

	@Id
	private String fromCurrency;
	@Id
	private String toCurrency;
	@Column(name="EXCHANGE_RATE")
	private BigDecimal exchangeRate;

	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String from) {
		fromCurrency = from;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String to) {
		toCurrency = to;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}
