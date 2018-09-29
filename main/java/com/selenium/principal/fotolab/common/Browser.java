package com.selenium.principal.fotolab.common;

import com.selenium.principal.fotolab.FotolabException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import static com.selenium.principal.fotolab.common.Utils.isFile;


// TODO Browser class marked for removal or to be repurposed
public class Browser {
    private WebDriver driver;
    private String driverPath;

    public Browser(String driverPath) throws FotolabException {
        this.driverPath = driverPath;
        initIEDriver();
    }

    private void initIEDriver() throws FotolabException {
        if (!isFile(driverPath))
            throw new FotolabException(
                    "Webdriver exe file not found <" + driverPath + ">");
        System.setProperty("webdriver.ie.driver", driverPath);

        InternetExplorerOptions ieOptions = new InternetExplorerOptions()
                .disableNativeEvents()
                .ignoreZoomSettings()
                .introduceFlakinessByIgnoringSecurityDomains()
                .destructivelyEnsureCleanSession()
                .enablePersistentHovering()
                .requireWindowFocus();
        driver = new InternetExplorerDriver(ieOptions);
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        driver.close();
    }
}
