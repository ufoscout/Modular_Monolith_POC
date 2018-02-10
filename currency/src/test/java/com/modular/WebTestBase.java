package com.modular;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for testing the rest services
 * @author Francesco Cina
 *
 */
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
public abstract class WebTestBase extends IntegrationTestBase {

	final ObjectMapper mapper = new ObjectMapper();

	@Autowired
    protected EmbeddedWebApplicationContext server;
    @Value("${local.server.port}")
    protected int serverPort;
    protected String contextPath;
	protected String serverUrl;

	@Before
	public void setUpBeforeAllTests() {
		String contextPath = server.getServletContext().getContextPath();
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length());
		}
		serverUrl = "http://localhost:" + serverPort + contextPath;
	}

	protected <T> T fromJson(final String json, final Class<T> clazz, final Class<?>... genericsArgs) {
		try {
			if (genericsArgs.length > 0) {
				JavaType type = mapper.getTypeFactory().constructParametricType(clazz, genericsArgs);
				return mapper.readValue(json.toString(), type);
			}
			return mapper.readValue(json.toString(), clazz);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
