package com.selenium.principal.fotolab.pageobjects.templates;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;


public abstract class PageObject<T extends PageObject<T>>
        extends LoadableComponent<T> {
    protected final WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
    }

    //    let client implement page navigation check
    @Override
    protected abstract void isLoaded() throws Error;
}
