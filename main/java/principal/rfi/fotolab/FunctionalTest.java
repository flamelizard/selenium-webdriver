package principal.rfi.fotolab;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/*
Recommended practice to have class for driver lifecycle
No need to close driver explicitly

Just extend the class to init and acquire driver
But how to inject driver path to set webdriver System property?
 */
public class FunctionalTest {
    protected static WebDriver driver;
    private static String driverPath =
            "D:\\projects\\fotolab\\src\\resources\\driver\\IEDriverServer.exe";

    @BeforeClass
    public static void setUp() {
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability("nativeEvents", false);
        ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability("enablePersistentHover", true);
        ieCapabilities.setCapability("ignoreZoomSetting", true);
        ieCapabilities.setCapability(
                InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);

        System.setProperty("webdriver.ie.driver", driverPath);
        driver = new InternetExplorerDriver(ieCapabilities);
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null)
            driver.close();
    }
}
