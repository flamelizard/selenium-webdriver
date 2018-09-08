package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Registration extends PageObject<Registration> {
    public Registration(WebDriver driver) {
        super(driver);
        get();
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 5).until(ExpectedConditions.
                presenceOfElementLocated(By.linkText("Registrace")));
    }
}
