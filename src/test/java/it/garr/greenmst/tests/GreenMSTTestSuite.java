package it.garr.greenmst.tests;

import it.garr.greenmst.GreenMSTTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Suite.class)
@SuiteClasses({ GreenMSTTest.class, GreenMSTAlgorithmSuite.class })
public class GreenMSTTestSuite {
	private static final Logger LOGGER = LoggerFactory.getLogger(GreenMSTTestSuite.class);

    @BeforeClass 
    public static void setUpClass() {
    	LOGGER.info("Test suite setup.");

    }

    @AfterClass
    public static void tearDownClass() {
    	LOGGER.info("Test suite end.");
    }

}