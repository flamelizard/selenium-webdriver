package principal.rfi.fotolab.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import principal.rfi.fotolab.FotolabException;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static principal.rfi.fotolab.Utils.isFile;
import static principal.rfi.fotolab.Utils.jsClick;


public class ProductEditor extends PageObject<ProductEditor> {
    @FindBy(id = "centerStage_content.image0")
    private WebElement imageIcon;

    @FindBy(linkText = "Vybrat fotografie")
    private WebElement choosePhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_uploadForm")
    private WebElement formUploadPhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_file-1")
    private WebElement inputUploadPhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_fileList")
    private WebElement ulUploadedFiles;

    @FindBy(linkText = "Nahrát vybrané fotografie")
    private WebElement btnLoadPhoto;

    @FindBy(linkText = "Přidat do košíku")
    private WebElement btnAddToCart;

    @FindBy(id = "waiter")
    private WebElement loadingIcon;

    @FindBy(css = "#shoppingCartButton>a")
    private WebElement btnAddToCartPreview;

    public ProductEditor(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOf(imageIcon));
    }

    public void uploadPhotoBackground(String photo) throws FotolabException {
        if (!isFile(photo))
            throw new FotolabException("Photo file not found <" + photo + ">");

        jsClick(imageIcon, driver);
        new FluentWait<>(driver)
                .ignoring(StaleElementReferenceException.class)
                .withTimeout(5, TimeUnit.SECONDS)
                .until(ExpectedConditions.visibilityOf(formUploadPhoto));

        inputUploadPhoto.sendKeys(photo);
        String photoName = Paths.get(photo).getFileName().toString()
                .toLowerCase();
        boolean photoFound = ulUploadedFiles.findElements(
                By.className("filename"))
                .stream()
                .map(elem -> elem.getText().toLowerCase())
                .anyMatch(filename -> filename.equals(photoName));
        if (!photoFound) {
            throw new FotolabException("Photo did not upload successfully");
        }
        jsClick(btnLoadPhoto, driver);
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOf(loadingIcon));
    }

    public ShoppingCart addOrderToCart() {
        jsClick(btnAddToCart, driver);
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(btnAddToCartPreview));
        jsClick(btnAddToCartPreview, driver);
        return new ShoppingCart(driver);
    }
}
