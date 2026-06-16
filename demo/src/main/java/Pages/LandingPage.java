package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage {
    
    private WebDriver driver;
    private WebDriverWait wait;

    private By Login = By.xpath("//a[@href='/login']");
    
    public LandingPage (WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(Login)).click();
    }
}
