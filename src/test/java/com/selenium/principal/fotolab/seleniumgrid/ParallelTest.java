package com.selenium.principal.fotolab.seleniumgrid;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/*
How to setup and run tests
Start grid either through selenium grid jar or docker compose file
Then distribute tests on the grid with testng or another framework capable of
parallel execution

Test hangs on creating driver session
If driver instance not destroyed with drive.quit(), then the session will
remain in grid. To resolve it, delete session from the grid through node
console AND restart grid.

Headless mode not possible
Grid seems to ignore headless flag in Capabilities set on a node or passed
to remote driver
 */
public class ParallelTest {
    private RemoteWebDriver driver;
    //    private String gridUrl = "http://192.168.99.1:4444/wd/hub";
    private String gridUrl = "http://192.168.99.100:4444/wd/hub";

    @Parameters({"browser", "version"})
    @BeforeTest
    public void createDriver(String browser, String version)
            throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(browser);
        cap.setVersion(version);
        driver = new RemoteWebDriver(new URL(gridUrl), cap);
    }

    @AfterTest
    public void cleanUp() {
//        driver.close() will only close window but not the driver
        driver.quit();
    }

    @Test
    public void testFotolabTrivia() {
        System.out.println(driver.getCapabilities());
        System.out.println(driver);
        driver.get("http://www.fotolab.cz");
        assertThat(driver.getTitle(), containsString("FOTOLAB"));
    }
}
