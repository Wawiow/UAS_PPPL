package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PreviewPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By Download_btn = By.xpath("//button[normalize-space()='Minta Akses Full Text']");
    private By Preview_Doc = By.xpath("//h2[normalize-space()='Preview Dokumen']");

    public PreviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickacsess() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.visibilityOfElementLocated(Preview_Doc));
        js.executeScript("window.scrollTo(0, 700);");

        wait.until(ExpectedConditions.elementToBeClickable(Download_btn)).click();
    }
}
