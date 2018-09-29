package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ShoppingCart extends PageObject<ShoppingCart> {
    @FindBy(linkText = "Nákupní košík")
    private WebElement pageHeader;

    @FindBy(id = "nextCommercialActionButtonShoppingCart")
    private WebElement nextStep;

    public ShoppingCart(WebDriver driver) {
        super(driver);
        get();
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(nextStep));
    }

    public Registration proceedToRegistration() {
        nextStep.click();
        return new Registration(driver);
    }
}
