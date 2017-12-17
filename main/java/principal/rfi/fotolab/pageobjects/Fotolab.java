package principal.rfi.fotolab.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static junit.framework.Assert.assertEquals;
import static principal.rfi.fotolab.Utils.jsClick;
import static principal.rfi.fotolab.Utils.scrollInto;


public class Fotolab extends PageObject<Fotolab> {
    private String title = "CEWE fotoslužby a fotoaparáty | FOTOLAB.cz";

    @FindBy(css = "div.cw_grid_2:nth-of-type(2) div ul li a[title='Fotoobrazy']")
    private WebElement fotoobrazy;

    public Fotolab(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        assertEquals(title, driver.getTitle());
    }

    public Fotoobrazy selectFotoobrazy() {
        scrollInto(fotoobrazy, driver);
        jsClick(fotoobrazy, driver);
        return new Fotoobrazy(driver);
    }
}
