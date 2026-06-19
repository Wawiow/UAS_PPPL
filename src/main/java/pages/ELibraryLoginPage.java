package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ELibraryLoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField    = By.cssSelector("input[type='email'], input[name='email'], input[id='email']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By submitButton  = By.cssSelector("button[type='submit']");

    public ELibraryLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String email, String password) {
        System.out.println("[DEBUG] eLibrary login URL: " + driver.getCurrentUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        // Tunggu redirect ke dashboard setelah login berhasil
        wait.until(ExpectedConditions.urlContains("elibrary.sanskuy.space"));
        wait.until(d -> !d.getCurrentUrl().contains("/login"));
        System.out.println("[DEBUG] Setelah login URL: " + driver.getCurrentUrl());
    }
}
