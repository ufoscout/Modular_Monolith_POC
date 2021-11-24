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
package com.modular.exchangerate.core.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.modular.exchangerate.IntegrationTestBase;
import com.modular.exchangerate.core.dao.ExchangeRateRepository;
import com.modular.exchangerate.core.dao.entity.ExchangeRate;

public class ExchangeRateRepositoryIT extends IntegrationTestBase {

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Test
	public void repositoryNotNull() {
		assertNotNull(exchangeRateRepository);
	}

	@Test
	public void exchangeRatePerCurrency() {

		ExchangeRate exchangeRate = exchangeRateRepository.findAll().iterator().next();
		String currencyId = exchangeRate.getFromCurrency();

		List<ExchangeRate> conversions = exchangeRateRepository.findExchangeRates(currencyId);
		assertNotNull(conversions);

		assertFalse(conversions.isEmpty());

		for (ExchangeRate er : conversions) {
			assertEquals(currencyId, er.getFromCurrency());
		}

	}

}
