package com.selenium.principal.fotolab;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
}
