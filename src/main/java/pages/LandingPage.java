package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LandingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By catalogNavLink = By.xpath("//a[normalize-space()='Katalog']");
    private final By loginButton    = By.xpath(
            "//a[normalize-space()='Login' or normalize-space()='Masuk'] | " +
            "//button[normalize-space()='Login' or normalize-space()='Masuk']"
    );

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://elibrary.sanskuy.space/");
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickCatalogMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(catalogNavLink)).click();
    }

    public boolean isOnLandingPage() {
        return driver.getCurrentUrl().contains("elibrary.sanskuy.space");
    }
}
