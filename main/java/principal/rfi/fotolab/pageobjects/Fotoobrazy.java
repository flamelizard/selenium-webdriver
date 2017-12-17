package principal.rfi.fotolab.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import principal.rfi.fotolab.FotolabException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static principal.rfi.fotolab.Utils.jsClick;


public class Fotoobrazy extends PageObject<Fotoobrazy> {
    private String title = "Fotoobrazy";

    @FindBy(id = "cw_subnav")
    private WebElement navigationPane;

    @FindBy(css = "#pip_ips_target > span")
    private WebElement nyniObjednat;

    @FindBy(linkText = "Foto na plátno")
    private WebElement fotoNaPlatno;

    public Fotoobrazy(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 2).until(
                ExpectedConditions.titleContains(title));
    }

    public List<WebElement> getFotoobrazyLinks() {
        return navigationPane.findElements(By
                .cssSelector("a[href*='fotoobrazy']"));
    }

    public void checkAvailableTypes(List<String> expectedTypes)
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
    }

    public FotoNaPlatno selectFotoNaPlatno() {
        jsClick(fotoNaPlatno, driver);
        return new FotoNaPlatno(driver);
    }
}
