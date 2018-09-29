package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.selenium.principal.fotolab.common.Utils.acceptCookieAgreement;
import static com.selenium.principal.fotolab.common.Utils.jsClick;


public class FotoNaPlatno extends PageObject<FotoNaPlatno> {
    private final String title = "obrazy na plátno";

    @FindBy(linkText = "Nyní objednat")
    private WebElement orderNow;

    public FotoNaPlatno(WebDriver driver) {
        super(driver);
        get();
        acceptCookieAgreement(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 2).until(
                ExpectedConditions.titleContains(title));
    }

    public ProductEditor makeOrder() {
        jsClick(orderNow, driver);
        return new ProductEditor(driver);
    }
}
