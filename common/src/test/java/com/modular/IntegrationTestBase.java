package com.modular;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base class for integration tests that require a spring context
 *
 * @author Francesco Cina
 *
 */
@SpringBootTest(classes = {Application.class})
public abstract class IntegrationTestBase extends UnitTestBase {

}
