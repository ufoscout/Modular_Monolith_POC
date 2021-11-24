package com.modular.build;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for unit tests
 * @author Francesco Cina
 *
 */
public abstract class UnitTestBase {

	private Date startTime;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeEach
	public void setUpBeforeTest(TestInfo testInfo) {
		startTime = new Date();
		logger.info("==================================================================="); //$NON-NLS-1$
		logger.info("BEGIN TEST " + testInfo.getDisplayName()); //$NON-NLS-1$
		logger.info("==================================================================="); //$NON-NLS-1$
	}

	@AfterEach
	public void tearDownAfterTest(TestInfo testInfo) {
		final String time = new BigDecimal( new Date().getTime() - startTime.getTime() ).divide(new BigDecimal(1000)).toString();
		logger.info("==================================================================="); //$NON-NLS-1$
		logger.info("END TEST " + testInfo.getDisplayName()); //$NON-NLS-1$
		logger.info("Execution time: " + time + " seconds"); //$NON-NLS-1$ //$NON-NLS-2$
		logger.info("==================================================================="); //$NON-NLS-1$
	}

	protected Logger getLogger() {
		return logger;
	}

}
