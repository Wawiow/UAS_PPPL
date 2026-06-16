package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReqContributorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ReqContributorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickJadiKontributor() {

        WebElement el = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Jadi Kontributor']")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", el);
    }

    public void clickLanjutIsiForm() {
        WebElement el = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(),'Lanjut Isi Form')]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", el);
    }

    public void inputAlasan(String alasan) {
        WebElement textarea = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea[@placeholder='Tulis alasan Anda ingin menjadi kontributor']")
                ));

        textarea.clear();
        textarea.sendKeys(alasan);
    }

    public void clickAjukanPermintaan() {
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@type='submit']")
                ));

        btn.click();
    }

    public boolean isRequestSuccess() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Request kontributor berhasil dikirim')]")
                    )
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSubmitButtonDisabled() {

        WebElement btn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[@type='submit']")
                ));

        return !btn.isEnabled();
    }
}