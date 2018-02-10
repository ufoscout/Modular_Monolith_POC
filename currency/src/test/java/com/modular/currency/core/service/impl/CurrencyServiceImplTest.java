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
package com.modular.currency.core.service.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.modular.IntegrationTestBase;
import com.modular.currency.core.dao.CurrencyReporitory;
import com.modular.currency.core.dao.entity.Currency;
import com.modular.currency.core.service.CurrencyService;
import com.modular.currency.core.service.dto.CurrencyDTO;

@Transactional
public class CurrencyServiceImplTest extends IntegrationTestBase {

	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private CurrencyReporitory currencyReporitory;

	@Test
	public void shouldReturnExistingCurrencies() {
		int count = currencyReporitory.countExisting();
		assertTrue(count>0);

		List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies();
		assertNotNull(allCurrencies);
		assertEquals(count, allCurrencies.size());

	}

	@Test
	public void shouldReturnFilteredCurrencies() {
		String currency_xxx = "xxx" + UUID.randomUUID().toString().substring(0, 6);
		String currency_yyy = "yyy" + UUID.randomUUID().toString().substring(0, 6);
		String currency_description = "description" + UUID.randomUUID().toString().substring(0, 25);

		assertNotNull(currencyReporitory.save(new Currency(currency_xxx, currency_description)));
		assertNotNull(currencyReporitory.save(new Currency(currency_yyy, currency_description)));

		assertFalse( currencyService.getAllCurrencies(currency_xxx).isEmpty() );
		assertEquals( 1, currencyService.getAllCurrencies(currency_xxx).size() );
		assertTrue( currency_xxx.equals( currencyService.getAllCurrencies(currency_xxx).get(0).getId() ) );
		assertTrue( currency_xxx.equals( currencyService.getAllCurrencies(currency_xxx.substring(0, 4)).get(0).getId() ) );

		assertFalse( currencyService.getAllCurrencies(currency_yyy).isEmpty() );
		assertEquals( 1, currencyService.getAllCurrencies(currency_yyy).size() );
		assertTrue( currency_yyy.equals( currencyService.getAllCurrencies(currency_yyy).get(0).getId() ) );
		assertTrue( currency_yyy.equals( currencyService.getAllCurrencies(currency_yyy.substring(0, 4)).get(0).getId() ) );

		assertFalse( currencyService.getAllCurrencies("").isEmpty() );

	}
}
