package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatalogPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By preview = By.xpath("//a[normalize-space()='Preview']");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickpreview() {
        wait.until(ExpectedConditions.elementToBeClickable(preview)).click();
    }
}
