package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminContributorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AdminContributorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openContributorRequest() {

        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/contributor-requests')]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", menu);
    }

    public void clickApproveRequest() {

        WebElement approve = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//button[normalize-space()='Approve'])[1]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", approve);
    }

    public void inputNotes(String notes) {

        WebElement textarea = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea[@placeholder='Add any notes...']")
                ));

        textarea.clear();
        textarea.sendKeys(notes);
    }

    public void confirmApprove() {

        WebElement approveBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//textarea[@placeholder='Add any notes...']/ancestor::*[contains(@class,'fixed')]//button[contains(.,'Approve')]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", approveBtn);
    }

    public boolean isApproveSuccess() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'approved') or contains(text(),'Approved') or contains(text(),'completed')]")
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}