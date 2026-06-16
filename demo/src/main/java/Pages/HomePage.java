package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;

    private By Home_btn = By.xpath("//button[normalize-space()='Beranda']");
    private By Catalog = By.xpath("//a[@href='/catalog']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickhomepage() {
        wait.until(ExpectedConditions.elementToBeClickable(Home_btn)).click();
    }

    public void clickcatalog() {
        wait.until(ExpectedConditions.elementToBeClickable(Catalog)).click();
    }
}
