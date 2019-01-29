package com.selenium.principal.fotolab.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import static com.selenium.principal.fotolab.common.Utils.isFile;

public class BrowserManager {
    public static WebDriver getDriver(BrowserType type, String version) {
        WebDriver driver = null;
        switch (type) {
            case CHROME:
                if (version.equals("")) {
                    WebDriverManager.chromedriver().setup();
                } else {
                    WebDriverManager.chromedriver().version(version).setup();
                }
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver getDriver(BrowserType type) {
        return getDriver(type, "");
    }

    @Deprecated
    public static WebDriver getWebDriverIE(String driverPath)
            throws FotolabException {
        if (!isFile(driverPath))
            throw new FotolabException(
                    "File does not exists <" + driverPath + ">");
        System.setProperty("webdriver.ie.driver", driverPath);

        InternetExplorerOptions ieOptions = new InternetExplorerOptions()
                .disableNativeEvents()
                .ignoreZoomSettings()
                .introduceFlakinessByIgnoringSecurityDomains()
                .destructivelyEnsureCleanSession()
                .enablePersistentHovering()
                .requireWindowFocus();
        WebDriver driver = new InternetExplorerDriver(ieOptions);
        driver.manage().window().maximize();
        return driver;
    }
}
