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
package com.modular.currency.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.modular.IntegrationTestBase;
import com.modular.currency.core.dao.CurrencyReporitory;
import com.modular.currency.core.dao.entity.Currency;

@Transactional
public class CurrencyReporitoryTest extends IntegrationTestBase {

	@Autowired
	private CurrencyReporitory currencyReporitory;

	@Test
	public void repositoryNotNull() {
		assertNotNull(currencyReporitory);
	}

	@Test
	public void currencyShouldBePresentInTheRepository() {
		assertTrue(currencyReporitory.exists("USD"));
	}

	@Test
	public void countExisting() {
		int count = currencyReporitory.countExisting();

		String randomCurrency = UUID.randomUUID().toString().substring(0, 6);
		assertFalse(currencyReporitory.exists(randomCurrency));

		Currency newCurrency = new Currency();
		newCurrency.setId(randomCurrency);
		newCurrency.setName(randomCurrency);

		assertNotNull(currencyReporitory.save(newCurrency));

		assertEquals( count+1, currencyReporitory.countExisting() );

	}

}
