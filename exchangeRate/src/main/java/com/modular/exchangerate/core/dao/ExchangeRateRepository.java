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

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.modular.exchangerate.core.dao.entity.ExchangeRate;
import com.modular.exchangerate.core.dao.entity.ExchangeRateId;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, ExchangeRateId>{

	@Query("Select er from ExchangeRate er where er.fromCurrency = :currencyId")
	List<ExchangeRate> findExchangeRates(@Param("currencyId") String currencyId);

	@Query("Select er from ExchangeRate er where er.fromCurrency in :currencyList")
	Iterable<ExchangeRate> findExchangeRates(@Param("currencyList") List<String> currencyList);

}
