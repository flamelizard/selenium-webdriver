package com.selenium.principal.fotolab.pageobjects.wrappers;

import com.selenium.principal.fotolab.Browser;
import com.selenium.principal.fotolab.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

/*
Recommended practice to have class for driver lifecycle
No need to close driver explicitly
 */
public class TestSuiteTempl {
    public static String CONFIG_FILE = "config/fotolab.properties";
    protected static Properties testConfig;
    protected WebDriver driver;

    @BeforeClass
    public static void init() throws IOException {
        testConfig = Utils.getConfig(CONFIG_FILE);
    }


    @Before
    public void setUp() throws Exception {
        driver = new Browser(testConfig.getProperty("webdriver.path")).
                getDriver();
    }

    @After
    public void cleanUp() {
        boolean shouldClose = Boolean.valueOf(
                testConfig.getProperty("testDone.browser.close"));
        if (shouldClose)
            driver.close();
    }
}
