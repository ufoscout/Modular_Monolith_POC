package com.modular.exchangerate;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.modular.Application;

/**
 * Base class for integration tests that require a spring context
 *
 * @author Francesco Cina
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestBase extends UnitTestBase {

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

}
