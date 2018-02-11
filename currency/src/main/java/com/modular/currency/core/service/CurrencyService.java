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
package com.modular.currency.core.service;

import java.util.List;

import com.modular.currency.core.service.dto.CurrencyDTO;

public interface CurrencyService {

	/**
	 * Returns a list of available currencies
	 * @return
	 */
	List<CurrencyDTO> getAllCurrencies();

	/**
	 * Returns a list of available currencies whose id matches the filter
	 * @param filter
	 * @return
	 */
	List<CurrencyDTO> getAllCurrencies(String filter);

}
