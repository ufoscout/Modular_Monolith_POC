/*******************************************************************************
 * Copyright 2015 Francesco Cina'
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
package com.modular.exchangerate.core.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.modular.exchangerate.IntegrationTestBase;
import com.modular.exchangerate.core.dao.ExchangeRateRepository;
import com.modular.exchangerate.core.dao.entity.ExchangeRate;
import com.modular.exchangerate.core.service.ExchangeRateService;
import com.modular.exchangerate.core.service.dto.ExchangeRateDTO;

@Transactional
public class ExchangeRateServiceImplIT extends IntegrationTestBase {

	@Autowired
	private ExchangeRateService exchangeRateService;
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Test
	public void shouldNotBeNull() {
		assertNotNull(exchangeRateService);
	}

	@Test
	public void shouldConvertBetweenCurrencies() {
		String from = UUID.randomUUID().toString().substring(0, 6);
		String to = UUID.randomUUID().toString().substring(0, 6);

		ExchangeRate newExchangeRate_1 = new ExchangeRate();
		newExchangeRate_1.setFromCurrency(from);
		newExchangeRate_1.setToCurrency(to);
		newExchangeRate_1.setExchangeRate(new BigDecimal(5));
		newExchangeRate_1 = exchangeRateRepository.save(newExchangeRate_1);

		assertEquals( BigDecimal.valueOf(50), exchangeRateService.convert(from, to, BigDecimal.valueOf(10)));
		assertEquals( new BigDecimal("250000000000000000.0000000000000000000000025"), exchangeRateService.convert(from, to, new BigDecimal("50000000000000000.0000000000000000000000005")));

		System.out.println("100 USD in EUR: " + exchangeRateService.convert("USD", "EUR", BigDecimal.valueOf(100)));

	}

	@Test
	public void shouldReturnCurrentExchangeRate() {
		String currencyId = UUID.randomUUID().toString().substring(0, 6);

		ExchangeRate newExchangeRate_1 = new ExchangeRate();
		newExchangeRate_1.setFromCurrency(currencyId);
		newExchangeRate_1.setToCurrency(UUID.randomUUID().toString().substring(0, 6));
		newExchangeRate_1.setExchangeRate(new BigDecimal(100));
		newExchangeRate_1 = exchangeRateRepository.save(newExchangeRate_1);

		ExchangeRate newExchangeRate_2 = new ExchangeRate();
		newExchangeRate_2.setFromCurrency(currencyId);
		newExchangeRate_2.setToCurrency(UUID.randomUUID().toString().substring(0, 6));
		newExchangeRate_2.setExchangeRate(new BigDecimal(100));
		newExchangeRate_2 = exchangeRateRepository.save(newExchangeRate_2);

		List<ExchangeRateDTO> exchangeRates = exchangeRateService.getExchangeRate(currencyId);

		assertNotNull(exchangeRates);
		assertEquals(2, exchangeRates.size());
		assertTrue( contains(exchangeRates, currencyId, newExchangeRate_1.getToCurrency()) );
		assertTrue( contains(exchangeRates, currencyId, newExchangeRate_2.getToCurrency()) );

	}

	@Test
	public void shouldReturnCurrentExchangeRateForAListOfCurrencies() {
		String currencyId_1 = UUID.randomUUID().toString().substring(0, 6);
		String currencyId_2 = UUID.randomUUID().toString().substring(0, 6);

		ExchangeRate newExchangeRate_1 = new ExchangeRate();
		newExchangeRate_1.setFromCurrency(currencyId_1);
		newExchangeRate_1.setToCurrency(UUID.randomUUID().toString().substring(0, 6));
		newExchangeRate_1.setExchangeRate(new BigDecimal(100));
		newExchangeRate_1 = exchangeRateRepository.save(newExchangeRate_1);

		ExchangeRate newExchangeRate_2 = new ExchangeRate();
		newExchangeRate_2.setFromCurrency(currencyId_1);
		newExchangeRate_2.setToCurrency(UUID.randomUUID().toString().substring(0, 6));
		newExchangeRate_2.setExchangeRate(new BigDecimal(100));
		newExchangeRate_2 = exchangeRateRepository.save(newExchangeRate_2);

		ExchangeRate newExchangeRate_3 = new ExchangeRate();
		newExchangeRate_3.setFromCurrency(currencyId_2);
		newExchangeRate_3.setToCurrency(UUID.randomUUID().toString().substring(0, 6));
		newExchangeRate_3.setExchangeRate(new BigDecimal(100));
		newExchangeRate_3 = exchangeRateRepository.save(newExchangeRate_3);

		List<ExchangeRateDTO> exchangeRates = exchangeRateService.getExchangeRate(Arrays.asList(currencyId_1, currencyId_2));

		assertNotNull(exchangeRates);
		assertEquals(3, exchangeRates.size());
		assertTrue( contains(exchangeRates, currencyId_1, newExchangeRate_1.getToCurrency()) );
		assertTrue( contains(exchangeRates, currencyId_1, newExchangeRate_2.getToCurrency()) );
		assertTrue( contains(exchangeRates, currencyId_2, newExchangeRate_3.getToCurrency()) );

		assertFalse(exchangeRateService.getExchangeRate(Arrays.asList()).isEmpty());

	}

	private boolean contains(List<ExchangeRateDTO> rates, String from, String to) {
		for (ExchangeRateDTO er : rates) {
			if (from.equals(er.getFrom()) && to.equals(er.getTo()) ) {
				return true;
			}
		}
		return false;
	}

}
