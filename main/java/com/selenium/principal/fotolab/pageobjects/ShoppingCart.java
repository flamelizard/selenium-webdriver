package com.selenium.principal.fotolab.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.selenium.principal.fotolab.Utils.jsClick;


public class ShoppingCart extends PageObject<ShoppingCart> {
    private String header = "Nákupní košík";

    @FindBy(linkText = "Nákupní košík")
    private WebElement pageHeader;

    @FindBy(id = "nextCommercialActionButtonShoppingCart")
    private WebElement goToRegistration;

    public ShoppingCart(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOf(pageHeader));
    }

    public Registration proceedToRegistration() {
        jsClick(goToRegistration, driver);
        return new Registration(driver);
    }
}
