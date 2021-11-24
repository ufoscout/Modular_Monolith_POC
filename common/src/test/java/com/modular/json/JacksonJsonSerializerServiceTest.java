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
package com.modular.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.modular.UnitTestBase;

public class JacksonJsonSerializerServiceTest extends UnitTestBase {

	private final JsonSerializerService jsonSerializerService = new JacksonJsonSerializerService();

	@Test
	public void testJson() {
		final SerializerBean message = new SerializerBean();
		message.setId(new SecureRandom().nextLong());
		message.setName(UUID.randomUUID().toString());
		message.setDate(LocalDate.now());

		final String json = jsonSerializerService.toJson(message);
		assertNotNull(json);
		assertTrue(json.contains( "" + message.getId() ));

		getLogger().info("JSON content: /n[{}]", json);

		final SerializerBean fromJson = jsonSerializerService.fromJson(json, SerializerBean.class);
		assertNotNull(fromJson);
		assertEquals( message.getId(), fromJson.getId() );
		assertEquals( message.getDate(), fromJson.getDate() );
		assertEquals( message.getName(), fromJson.getName() );

	}

	@Test
	public void testJsonOutputStream() throws UnsupportedEncodingException {
		final SerializerBean message = new SerializerBean();
		message.setId(new SecureRandom().nextLong());
		message.setName(UUID.randomUUID().toString());
		message.setDate(LocalDate.now());

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		jsonSerializerService.toPrettyPrintedJson(message, baos);

		final String json = baos.toString(StandardCharsets.UTF_8.name());

		assertNotNull(json);
		assertTrue(json.contains( "" + message.getId() ));

		getLogger().info("JSON content: /n[{}]", json);

		final SerializerBean fromJson = jsonSerializerService.fromJson(json, SerializerBean.class);
		assertNotNull(fromJson);
		assertEquals( message.getId(), fromJson.getId() );
		assertEquals( message.getDate(), fromJson.getDate() );
		assertEquals( message.getName(), fromJson.getName() );

	}

	@Test
	public void testJsonWithGenerics() {
		final List<SerializerBean> originalList = new ArrayList<>();

		originalList.add( new SerializerBean( new Random().nextLong() , UUID.randomUUID().toString() , LocalDate.now() ) );
		originalList.add( new SerializerBean( new Random().nextLong() , UUID.randomUUID().toString() , LocalDate.now() ) );
		originalList.add( new SerializerBean( new Random().nextLong() , UUID.randomUUID().toString() , LocalDate.now() ) );
		originalList.add( new SerializerBean( new Random().nextLong() , UUID.randomUUID().toString() , LocalDate.now() ) );

		final String json = jsonSerializerService.toJson(originalList);
		assertNotNull(json);
		assertTrue(json.contains( "" + originalList.get(0).getId() ));

		getLogger().info("JSON content: /n[{}]", json);

		final List<SerializerBean> fromJsonList = jsonSerializerService.fromJson(json, ArrayList.class, SerializerBean.class);
		assertNotNull(fromJsonList);

		assertEquals(originalList.size(), fromJsonList.size());

		for (int i = 0; i < originalList.size(); i++) {
			final SerializerBean message = originalList.get(i);
			final SerializerBean fromJson = fromJsonList.get(i);

			assertEquals( message.getId(), fromJson.getId() );
			assertEquals( message.getDate(), fromJson.getDate() );
			assertEquals( message.getName(), fromJson.getName() );

		}

	}
}
