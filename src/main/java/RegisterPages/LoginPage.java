package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By emailField =
            By.xpath("//input[@placeholder='Email']");

    By passwordField =
            By.xpath("//input[@placeholder='Password']");

    By loginButton =
            By.xpath("//button[contains(text(),'Login')]");

    public LoginPage(WebDriver driver) {

        this.driver = driver;
    }

    public void inputEmail(String email) {

        driver.findElement(emailField).sendKeys(email);
    }

    public void inputPassword(String password) {

        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {

        driver.findElement(loginButton).click();
    }
}