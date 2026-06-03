package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CatalogPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Cari title "Dokumen Penting" dulu, lalu ambil tombol Download TERDEKAT setelahnya di DOM
    private final By downloadButton = By.xpath(
            "(//*[contains(normalize-space(.),'Dokumen Penting') and (self::a or self::h1 or self::h2 or self::h3 or self::span or self::p)])" +
            "/following::button[contains(@class,'bg-blue-600') and contains(.,'Download')][1]"
    );

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public boolean isOnCatalogPage() {
        wait.until(ExpectedConditions.urlContains("/catalog"));
        // Tunggu konten katalog (setidaknya satu card dokumen) ter-render
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(normalize-space(.),'Dokumen Penting')]")));
        } catch (Exception ignored) {}
        return driver.getCurrentUrl().contains("/catalog");
    }

    public void clickDownloadButton() {
        org.openqa.selenium.WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(downloadButton));
        // Cetak konteks dokumen untuk membuktikan tombol yang diklik benar
        String cardText = "";
        try { cardText = btn.findElement(By.xpath("./ancestor::div[.//*[contains(text(),'Dokumen Penting')]][1]")).getText().replaceAll("\\s+", " ").trim(); } catch (Exception ignored) {}
        System.out.println("[DEBUG] Dokumen yang di-klik Download: " + cardText.substring(0, Math.min(cardText.length(), 150)));
        // Scroll ke elemen dan klik via JavaScript untuk bypass overlay
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", btn);
    }
}
