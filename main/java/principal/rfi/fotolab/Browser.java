package principal.rfi.fotolab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static principal.rfi.fotolab.Utils.isFile;


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

        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability("nativeEvents", false);
        ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability("enablePersistentHover", true);
        ieCapabilities.setCapability("ignoreZoomSetting", true);
        ieCapabilities.setCapability(
                InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);

        driver = new InternetExplorerDriver(ieCapabilities);
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        driver.close();
    }
}
