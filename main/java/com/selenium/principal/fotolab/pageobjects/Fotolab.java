package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.selenium.principal.fotolab.Utils.jsClick;
import static com.selenium.principal.fotolab.Utils.scrollInto;
import static org.junit.Assert.assertTrue;


public class Fotolab extends PageObject<Fotolab> {
    private static String URL;
    private String TITLE = "FOTOLAB";
    @FindBy(linkText = "Fotoprodukty")
    private WebElement dropdownProdukty;

    @FindBy(xpath = "//a[contains(@href,'fotoobrazy')]")
    private WebElement linkFotoobrazy;


    public Fotolab(WebDriver driver, String startUrl) {
        super(driver);
        URL = startUrl;
        get();
    }

    @Override
    public void load() {
        driver.get(URL);
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(TITLE));
    }

    //    TODO inject driver to jsClick through DI
    public Fotoobrazy selectFotoobrazy() {
        scrollInto(dropdownProdukty, driver);
        jsClick(dropdownProdukty, driver);
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(linkFotoobrazy));
        jsClick(linkFotoobrazy, driver);
        return new Fotoobrazy(driver);
    }
}
