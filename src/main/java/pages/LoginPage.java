package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriverWait wait;

    private final By emailField    = By.xpath("//input[@placeholder='Email']");
    private final By passwordField = By.xpath("//input[@placeholder='Password']");
    private final By loginButton   = By.xpath("//button[contains(text(),'Login')]");

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void inputEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    public void inputPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        try {
            wait.until(d -> !d.getCurrentUrl().contains("/login"));
        } catch (Exception ignored) {}
    }
}
