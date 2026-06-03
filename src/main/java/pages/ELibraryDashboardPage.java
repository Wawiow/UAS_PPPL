package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ELibraryDashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Tombol "Beranda" di navbar dashboard — dari HTML snippet yang diberikan
    private final By berandaButton = By.xpath(
            "//button[.//span[normalize-space()='Beranda']] | " +
            "//a[normalize-space()='Beranda']"
    );

    public ELibraryDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickBeranda() {
        System.out.println("[DEBUG] Dashboard URL sebelum klik Beranda: " + driver.getCurrentUrl());
        wait.until(ExpectedConditions.elementToBeClickable(berandaButton)).click();
        // Tunggu kembali ke landing page
        wait.until(ExpectedConditions.urlToBe("https://elibrary.sanskuy.space/"));
        System.out.println("[DEBUG] URL setelah klik Beranda: " + driver.getCurrentUrl());
    }
}
