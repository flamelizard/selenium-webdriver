package com.selenium.principal.fotolab.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;


public class Utils {
    private static JavascriptExecutor executor;

    public static void scrollInto(WebElement elem, WebDriver driver) {
        executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", elem);
    }

    public static void jsClick(WebElement elem, WebDriver driver) {
        executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elem);
    }

    public static boolean isFile(String filepath) {
        File file = Paths.get(filepath).toFile();
        return file.isFile() && file.canRead();
    }

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    Classloader.getSystemResource treats path slightly differently
    public static Properties getConfig(String config) throws IOException {
        InputStream in = ClassLoader.getSystemResourceAsStream(config);
        Properties prop = new Properties();
        prop.load(in);
        return prop;
    }

    public static void handleAlertIfShown(WebDriver driver, Integer timeout) {
        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.alertIsPresent())
                    .accept();
            System.out.println("Warning: alert has occurred");
        } catch (TimeoutException ex) {
//            WebDriverWait cannot ignore timeout exception
        }
    }

    public static void acceptCookieAgreement(WebDriver driver) {
        String divId = "cewe-accept-cookies-icon";
        try {
            WebElement banner = new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.
                            elementToBeClickable(By.id(divId)));
//            MS Edge generates error on standard .click()
            jsClick(banner, driver);
        } catch (TimeoutException ex) {
        }
        sleep(1);
    }

    //    prone to stealing dialog focus when running locally and using mouse
    public static void selectFileViaDialog(String filepath) {
        StringSelection path = new StringSelection(filepath);
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(path, null);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Error: file dialog handler failed");
            return;
        }
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        sleep(1);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        sleep(1);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(1);
    }
}
