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
package com.modular.exchangerate.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.modular.exchangerate.core.service.dto.ExchangeRateDTO;

public interface ExchangeRateService {

	/**
	 * Returns the current exchange rates for a specific currency
	 * @param currencyId the id of the currency
	 * @return a list of available currency Rates
	 */
	List<ExchangeRateDTO> getExchangeRate(String currencyId);

	/**
	 * Returns the current exchange rates for a list of currencies
	 * @param currencyList the id of the currencies
	 * @return a list of available currency Rates
	 */
	List<ExchangeRateDTO> getExchangeRate(List<String> currencyList);

	/**
	 * Converts an amount between two currencies
	 * @param from the current currency
	 * @param to the currency to which the amount has to be converted
	 * @param valueOf the amount to convert
	 * @return the amount in the converted currency
	 */
	BigDecimal convert(String from, String to, BigDecimal valueOf);

}
