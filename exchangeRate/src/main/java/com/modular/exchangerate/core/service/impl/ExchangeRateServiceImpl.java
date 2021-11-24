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
package com.modular.exchangerate.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.modular.exchangerate.core.dao.ExchangeRateRepository;
import com.modular.exchangerate.core.dao.entity.ExchangeRate;
import com.modular.exchangerate.core.dao.entity.ExchangeRateId;
import com.modular.exchangerate.core.service.ExchangeRateService;
import com.modular.exchangerate.core.service.dto.ExchangeRateDTO;
import com.modular.exchangerate.core.service.dto.ExchangeRateDTOExchangeRateConverter;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	private final ExchangeRateRepository exchangeRateRepository;

	public ExchangeRateServiceImpl( ExchangeRateRepository exchangeRateRepository ) {
		this.exchangeRateRepository = exchangeRateRepository;
	}

	@Override
	@Cacheable(value = "EXCHANGE_RATE", key = "{#root.targetClass,#root.methodName,#currencyId}")
	public List<ExchangeRateDTO> getExchangeRate(String currencyId) {
		LOGGER.info("Return exchange rates for [{}]", currencyId);
		final List<ExchangeRateDTO> result = new ArrayList<>();
		for (final ExchangeRate er : exchangeRateRepository.findExchangeRates(currencyId)) {
			result.add(ExchangeRateDTOExchangeRateConverter.convert(er));
		}
		return result;
	}

	@Override
	public BigDecimal convert(String from, String to, BigDecimal valueOf) {
		LOGGER.info("Convert [{}} from {} to {}", valueOf, from, to);
		final ExchangeRate er = exchangeRateRepository.findById(new ExchangeRateId(from, to)).get();
		return valueOf.multiply(er.getExchangeRate());
	}

	@Override
	public List<ExchangeRateDTO> getExchangeRate(List<String> currencyList) {
		LOGGER.info("Return exchange rates for [{}]", currencyList);
		Iterable<ExchangeRate> iterable = null;
		if (currencyList.isEmpty()) {
			iterable = exchangeRateRepository.findAll();
		} else {
			iterable = exchangeRateRepository.findExchangeRates(currencyList);
		}

		final List<ExchangeRateDTO> result = new ArrayList<>();
		for (final ExchangeRate er : iterable) {
			result.add(ExchangeRateDTOExchangeRateConverter.convert(er));
		}
		return result;
	}

}
