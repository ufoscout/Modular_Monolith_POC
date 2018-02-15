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
package com.modular.currency.core.service.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.mockito.Mockito;

import com.modular.currency.UnitTestBase;
import com.modular.currency.core.dao.CurrencyReporitory;
import com.modular.currency.core.dao.entity.Currency;

public class CurrencyServiceImplTest extends UnitTestBase {

	@Test
	public void shouldReturnFilteredCurrencies() {

		final String currency_xxx = "xxx" + UUID.randomUUID().toString().substring(0, 6);
		final String currency_yyy = "yyy" + UUID.randomUUID().toString().substring(0, 6);
		final String currency_description = "description" + UUID.randomUUID().toString().substring(0, 25);

		final CurrencyReporitory currencyReporitoryMock = Mockito.mock(CurrencyReporitory.class);
		Mockito.when(currencyReporitoryMock.findAllFiltered(currency_xxx)).thenReturn(Arrays.asList(new Currency(currency_xxx, currency_description)));
		Mockito.when(currencyReporitoryMock.findAllFiltered(currency_yyy)).thenReturn(Arrays.asList(new Currency(currency_yyy, currency_description)));

		final CurrencyServiceImpl currencyService = new CurrencyServiceImpl(currencyReporitoryMock);

		assertFalse( currencyService.getAllCurrencies(currency_xxx).isEmpty() );
		assertEquals( 1, currencyService.getAllCurrencies(currency_xxx).size() );
		assertTrue( currency_xxx.equals( currencyService.getAllCurrencies(currency_xxx).get(0).getId() ) );

		assertFalse( currencyService.getAllCurrencies(currency_yyy).isEmpty() );
		assertEquals( 1, currencyService.getAllCurrencies(currency_yyy).size() );
		assertTrue( currency_yyy.equals( currencyService.getAllCurrencies(currency_yyy).get(0).getId() ) );

	}
}
