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
package com.modular.currency.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modular.currency.core.service.CurrencyService;
import com.modular.currency.core.service.dto.CurrencyDTO;

@RestController
@RequestMapping("/rest/currency")
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	@RequestMapping("/currencies")
	public List<CurrencyDTO> home(@RequestParam(value = "filter", defaultValue="") String filter) {
		return currencyService.getAllCurrencies(filter);
	}

}
