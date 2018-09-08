package com.selenium.principal.fotolab;

import com.selenium.principal.fotolab.pageobjects.Fotolab;
import com.selenium.principal.fotolab.pageobjects.wrappers.TestSuiteTempl;
import org.junit.Test;

import java.util.Arrays;

public class FotolabTest extends TestSuiteTempl {

    @Test
    public void makeProductOrder() throws Exception {
        String[] expectedTypes = testConfig.getProperty(
                "fotoobrazy.expectedTypes").split(",");

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

    //    TODO cross-browser testing, run same test on Chrome and FF

}
