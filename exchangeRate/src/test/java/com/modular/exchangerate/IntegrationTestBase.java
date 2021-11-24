package com.modular.exchangerate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import com.modular.Application;

/**
 * Base class for integration tests that require a spring context
 *
 * @author Francesco Cina
 *
 */
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestBase extends UnitTestBase {

	@Autowired
    protected WebApplicationContext server;
    @LocalServerPort
    protected int serverPort;
    protected String contextPath;
	protected String serverUrl;

	@BeforeEach
	public void setUpBeforeEachTest() {
		String contextPath = server.getServletContext().getContextPath();
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length());
		}
		serverUrl = "http://localhost:" + serverPort + contextPath;
	}

}
