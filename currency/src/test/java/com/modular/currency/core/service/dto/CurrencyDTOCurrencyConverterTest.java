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
package com.modular.currency.core.service.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.modular.currency.UnitTestBase;
import com.modular.currency.core.dao.entity.Currency;
import com.modular.currency.core.service.dto.CurrencyDTO;
import com.modular.currency.core.service.dto.CurrencyDTOCurrencyConverter;

public class CurrencyDTOCurrencyConverterTest extends UnitTestBase {

	@Test
	public void currencyTOCurrencyDTOConverter() {
		Currency currency = new Currency();
		currency.setId(UUID.randomUUID().toString());
		currency.setName(UUID.randomUUID().toString());

		CurrencyDTO currencyDTO = CurrencyDTOCurrencyConverter.convert(currency);

		assertNotNull(currencyDTO);
		assertEquals(currency.getId(),currencyDTO.getId());
		assertEquals(currency.getName(),currencyDTO.getName());
	}

}
