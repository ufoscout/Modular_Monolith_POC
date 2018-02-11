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
package com.modular.exchangerate.web.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.modular.config.Globals;
import com.modular.exchangerate.IntegrationTestBase;
import com.modular.exchangerate.core.service.ExchangeRateService;
import com.modular.exchangerate.core.service.dto.ExchangeRateDTO;
import com.modular.json.JsonSerializerService;

public class ExchangeRateControllerIT extends IntegrationTestBase {

	@Autowired
	protected JsonSerializerService json;
	@Autowired
	private ExchangeRateService exchangeRateService;

    @Test
    public void testGetExchangeRate() throws Exception {
		final TestRestTemplate restTemplate = new TestRestTemplate();
	    final ResponseEntity<String> response = restTemplate.getForEntity(serverUrl + Globals.BASE_REST_PATH + "/exchangeRate/rates?currencies=USD,EUR", String.class);
	    assertEquals( HttpStatus.OK, response.getStatusCode());

	    getLogger().info("Received: [{}]", response.getBody());

	    final List<ExchangeRateDTO> responseBody = json.fromJson(response.getBody(), List.class, ExchangeRateDTO.class);
	    assertFalse(responseBody.isEmpty());
    }

    @Test
    public void testGetConvert() throws Exception {
		final TestRestTemplate restTemplate = new TestRestTemplate();
	    final ResponseEntity<BigDecimal> response = restTemplate.getForEntity(serverUrl + Globals.BASE_REST_PATH + "/exchangeRate/convert/USD/EUR/100", BigDecimal.class);
	    assertEquals( HttpStatus.OK, response.getStatusCode());

	    getLogger().info("Received: [{}]", response.getBody());

	    assertEquals(exchangeRateService.convert("USD", "EUR", BigDecimal.valueOf(100)), response.getBody());
    }

}
