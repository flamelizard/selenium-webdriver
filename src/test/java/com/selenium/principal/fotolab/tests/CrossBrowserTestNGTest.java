package com.selenium.principal.fotolab.tests;

import com.selenium.principal.fotolab.Project;
import com.selenium.principal.fotolab.common.BrowserManager;
import com.selenium.principal.fotolab.common.BrowserType;
import com.selenium.principal.fotolab.common.Utils;
import com.selenium.principal.fotolab.pageobjects.Fotolab;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.selenium.principal.fotolab.common.BrowserType.FIREFOX;

public class CrossBrowserTestNGTest {
    private static Properties testConfig;
    private WebDriver driver;

    @BeforeClass
    public static void setup() throws IOException {
        testConfig = Utils.getConfig(Project.CONFIG_FILE);
    }

    @DataProvider(name = "browsers")
    public static Object[] driverTypes() {
        return new Object[][]{
//                {CHROME},
                {FIREFOX},
//                {EDGE}
        };
    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.isSuccess())
            driver.close();
    }

    @Test(invocationCount = 1, dataProvider = "browsers")
    public void makeProductOrder(BrowserType browserType) throws Exception {
        List<String> expectedTypes = Arrays.asList(testConfig.getProperty(
                "fotoobrazy.expectedTypes").split(","));

        driver = BrowserManager.getDriver(browserType);
        new Fotolab(driver, testConfig.getProperty("fotolab.url"))
                .selectFotoobrazy()
                .checkAvailableTypes(expectedTypes)
                .selectFotoNaPlatno()
                .makeOrder()
                .uploadBackgroundPhoto(
                        testConfig.getProperty("user.photo.path"))
                .addOrderToCart()
                .proceedToRegistration();
    }
}
