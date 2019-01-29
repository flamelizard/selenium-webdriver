package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.common.FotolabException;
import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.selenium.principal.fotolab.common.Utils.acceptCookieAgreement;
import static com.selenium.principal.fotolab.common.Utils.jsClick;


public class Fotoobrazy extends PageObject<Fotoobrazy> {
    private String title = "Fotoobrazy";

    @FindBy(id = "cw_leftnav")
    private WebElement navigationPane;

    @FindBy(css = "#pip_ips_target > span")
    private WebElement nyniObjednat;

    @FindBy(linkText = "Foto na plátno")
    private WebElement fotoNaPlatno;

    private WebDriverWait wait = new WebDriverWait(driver, 3);

    public Fotoobrazy(WebDriver driver) {
        super(driver);
        get();
        acceptCookieAgreement(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 3).until(
                ExpectedConditions.elementToBeClickable(fotoNaPlatno));
    }

    private List<WebElement> getFotoobrazyLinks() {
        return navigationPane
                .findElements(By
                        .cssSelector("a[href*='fotoobrazy']"));
    }

    public Fotoobrazy checkAvailableTypes(List<String> expectedTypes)
            throws FotolabException {
        List<String> allTypesFound = getFotoobrazyLinks().stream()
                .map(elem -> elem.getAttribute("title").toLowerCase())
                .collect(Collectors.toList());

        List<String> notFound = new LinkedList<>();
        for (String expected : expectedTypes) {
            if (!allTypesFound.contains(expected.toLowerCase()))
                notFound.add(expected);
        }
        if (notFound.size() > 0)
            throw new FotolabException(
                    "Missing fotobrazy types: " + notFound);
        return this;
    }

    public FotoNaPlatno selectFotoNaPlatno() {
        jsClick(fotoNaPlatno, driver);
        return new FotoNaPlatno(driver);
    }
}
