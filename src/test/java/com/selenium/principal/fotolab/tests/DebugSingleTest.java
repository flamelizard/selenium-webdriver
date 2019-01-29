package com.selenium.principal.fotolab.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class DebugSingleTest {
    private WebDriver driver;

    @BeforeTest
    public void createDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterTest
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void testFotolabTrivia() {
        driver.get("http://www.fotolab.cz");
        assertThat(driver.getTitle(), containsString("FOTOLAB"));
    }
}
