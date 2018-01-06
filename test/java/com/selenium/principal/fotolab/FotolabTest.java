package com.selenium.principal.fotolab;

import com.selenium.principal.fotolab.pageobjects.Fotolab;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@FixMethodOrder(MethodSorters.JVM)
public class FotolabTest {
    public static String configFile = "config/fotolab.properties";
    public static Properties config;
    private static Browser browser;

    @BeforeClass()
    public static void setup() throws IOException, FotolabException {
        config = Utils.getConfig(configFile);
        browser = new Browser(config.getProperty("webdriver.path"));
        browser.getDriver().get(config.getProperty("fotolab.url"));
    }

    @AfterClass
    public static void cleanup() {
        boolean shouldClose = Boolean.valueOf(
                config.getProperty("testDone.browser.close"));
        if (shouldClose)
            browser.close();
    }

    @Test
    public void testScenarioForRFI() throws Exception {
        List<String> expected = Arrays.asList(
                config.getProperty("fotoobrazy.expectedTypes").split(","));

        new Fotolab(browser.getDriver())
                .selectFotoobrazy()
                .checkAvailableTypes(expected)
                .selectFotoNaPlatno()
                .makeOrder()
                .uploadPhotoBackground(config.getProperty("user.photo.path"))
                .addOrderToCart()
                .proceedToRegistration();
    }
}