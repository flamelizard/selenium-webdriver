package com.selenium.principal.fotolab.pageobjects;

import com.selenium.principal.fotolab.FotolabException;
import com.selenium.principal.fotolab.pageobjects.templates.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

import static com.selenium.principal.fotolab.common.Utils.*;


public class ProductEditor extends PageObject<ProductEditor> {
    @FindBy(id = "centerStage_content.image0")
    private WebElement imageOutline;

    @FindBy(linkText = "Vybrat fotografie")
    private WebElement choosePhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_uploadForm")
    private WebElement formUploadPhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_file-1")
    private WebElement inUploadPhoto;

    @FindBy(id = "galleryAndInboxBrowser_uplDlg1_fileList")
    private WebElement ulUploadedFiles;

    @FindBy(linkText = "Nahrát vybrané fotografie")
    private WebElement btnUploadSubmit;

    @FindBy(css = "div.htmlUploadDone a")
    private WebElement btnUploadDone;

    @FindBy(xpath = "//a[contains(@onclick,'Přidat')]")
    private WebElement btnAddToCart;

    @FindBy(id = "waiter")
    private WebElement loadingIcon;

    @FindBy(css = "#shoppingCartButton>a")
    private WebElement btnAddToCartPreview;

    @FindBy(xpath = "//div[contains(@onclick,'galleryAndInboxBrowser_uplDlg1')]")
    private WebElement divFileUpload;

    private WebDriverWait wait = new WebDriverWait(driver, 3);

    public ProductEditor(WebDriver driver) {
        super(driver);
        get();
    }

    @Override
    protected void isLoaded() throws Error {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(imageOutline));
    }

    public ProductEditor uploadBackgroundPhoto(String photo) throws FotolabException {
        if (!isFile(photo))
            throw new FotolabException("Photo file not found <" + photo + ">");

        imageOutline.click();
        new FluentWait<>(driver)
                .ignoring(StaleElementReferenceException.class)
                .withTimeout(Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(formUploadPhoto));
        uploadViaInputElem(photo);
        sleep(1);
        return this;
    }

    public ShoppingCart addOrderToCart() {
        btnAddToCart.click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(btnAddToCartPreview));
        btnAddToCartPreview.click();
        return new ShoppingCart(driver);
    }

    private void uploadViaFileDialog(String photo) throws FotolabException {
        divFileUpload.click();
        sleep(1);
        selectFileViaDialog(photo);
        verifyPhotoUpload(photo);
        wait.until(ExpectedConditions.visibilityOf(btnUploadDone))
                .click();
    }

    private void uploadViaInputElem(String photo)
            throws FotolabException {
        inUploadPhoto.sendKeys(photo);
        verifyPhotoUpload(photo);
        btnUploadSubmit.click();
        handleAlertIfShown(driver, 2);
        wait.until(ExpectedConditions.invisibilityOf(loadingIcon));
    }

    private void verifyPhotoUpload(String photo) throws FotolabException {
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
    }
}
