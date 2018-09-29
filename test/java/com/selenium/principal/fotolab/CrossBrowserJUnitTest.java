package com.selenium.principal.fotolab;

import com.selenium.principal.fotolab.common.BrowserManager;
import com.selenium.principal.fotolab.common.BrowserType;
import com.selenium.principal.fotolab.common.Utils;
import com.selenium.principal.fotolab.pageobjects.Fotolab;
import com.selenium.principal.fotolab.pageobjects.wrappers.TestSuiteTempl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static com.selenium.principal.fotolab.common.BrowserType.FIREFOX;

/*
TODO run on headless
TODO support driver version selection, run on older Chrome
 */
@RunWith(Parameterized.class)
public class CrossBrowserJUnitTest {
    private static Properties testConfig;
    @Parameter(0)
    public BrowserType browserType;
    private WebDriver driver;

    @BeforeClass
    public static void setup() throws IOException {
        testConfig = Utils.getConfig(TestSuiteTempl.CONFIG_FILE);
    }

    @Parameters
    public static Iterable<Object[]> browserTypes() {
        return Arrays.asList(new Object[][]{
//                {CHROME},
                {FIREFOX},
//                {EDGE}
        });
    }

    @After
    public void cleanup() {
        if (driver != null) driver.close();
    }

    @Test
    public void makeProductOrder() throws Exception {
        String[] expectedTypes = testConfig.getProperty(
                "fotoobrazy.expectedTypes").split(",");

        driver = BrowserManager.getDriver(browserType);
        new Fotolab(driver, testConfig.getProperty("fotolab.url"))
                .selectFotoobrazy()
                .checkAvailableTypes(Arrays.asList(expectedTypes))
                .selectFotoNaPlatno()
                .makeOrder()
                .uploadBackgroundPhoto(
                        testConfig.getProperty("user.photo.path"))
                .addOrderToCart()
                .proceedToRegistration();
    }
}
