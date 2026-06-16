package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By Email_form = By.xpath("//input[@placeholder='Email']");
    private By Password_form = By.xpath("//input[@placeholder='Password']");
    private By Login_btn = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Email_form)).sendKeys(email);
        driver.findElement(Password_form).sendKeys(password);
        driver.findElement(Login_btn).click();

    }

}
