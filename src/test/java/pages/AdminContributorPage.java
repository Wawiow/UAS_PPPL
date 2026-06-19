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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openContributorRequest() {

        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/contributor-requests')]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", menu);
    }

    // ================= APPROVE =================

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
                        By.xpath("(//button[normalize-space()='Approve'])[2]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", approveBtn);
    }

    public boolean isApproveSuccess() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Approved') or contains(text(),'approved')]")
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // ================= REJECT =================

    public void clickRejectRequest() {

        System.out.println("Klik reject pertama");

        WebElement reject = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//button[normalize-space()='Reject'])[1]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", reject);

        System.out.println("Modal reject terbuka");
    }

    public void inputRejectReason(String reason) {

        WebElement textarea = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea[@placeholder='Please provide a reason for rejection...']")
                ));

        textarea.clear();
        textarea.sendKeys(reason);

        // Trigger event Vue
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                textarea
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                textarea
        );

        System.out.println("Isi textarea = " + textarea.getAttribute("value"));
    }

    public void confirmReject() {

        WebElement rejectBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[@class='flex-1 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 disabled:bg-gray-400 transition']")
                ));

        wait.until(driver -> rejectBtn.isEnabled());

        System.out.println("Button enabled = " + rejectBtn.isEnabled());

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);",
                rejectBtn
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                rejectBtn
        );

        System.out.println("Reject clicked");
    }

    public boolean isRejectSuccess() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Rejected') or contains(text(),'rejected')]")
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRejectButtonDisabled() {

        WebElement rejectBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//button[normalize-space()='Reject'])[2]")
                ));

        return !rejectBtn.isEnabled();
    }
}