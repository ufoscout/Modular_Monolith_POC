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
package com.modular.build.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.modular.build.IntegrationTestBase;

public class WebResourcesControllerIT extends IntegrationTestBase {

	@Test
	public void should_return_the_home_page() {
		final TestRestTemplate restTemplate = new TestRestTemplate();
	    final ResponseEntity<String> response = restTemplate.getForEntity(serverUrl + "/index.html", String.class);
	    assertEquals( HttpStatus.OK, response.getStatusCode());

	    getLogger().info("Found page:\n{}", response.getBody());

	    assertTrue(response.getBody().contains("<body>"));
	}

	@Test
	public void html5_calls_should_return_the_home_page() {
		final TestRestTemplate restTemplate = new TestRestTemplate();

		final ResponseEntity<String> homePage = restTemplate.getForEntity(serverUrl + "/index.html", String.class);
	    assertEquals( HttpStatus.OK, homePage.getStatusCode());

	    final ResponseEntity<String> html5Calls = restTemplate.getForEntity(serverUrl + "/" + UUID.randomUUID(), String.class);
	    assertEquals( HttpStatus.OK, html5Calls.getStatusCode());

	    assertEquals(homePage.getBody(), html5Calls.getBody());
	}

}
