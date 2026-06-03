package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DownloadRequestPopupPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Selector berdasarkan DOM aktual dari popup permohonan unduh
    private final By namaField       = By.cssSelector("input[placeholder='Nama Anda']");
    private final By emailField      = By.cssSelector("input[type='email'][placeholder='email@contoh.com']");
    private final By instansiField   = By.cssSelector("input[placeholder='Universitas / Lembaga']");
    private final By keperluanField  = By.cssSelector("textarea[placeholder*='Jelaskan tujuan']");
    // Checkbox di dalam form popup — lebih spesifik agar tidak menangkap checkbox sidebar filter katalog
    private final By termsCheckbox   = By.xpath("//form[.//button[@type='submit']]//input[@type='checkbox']");
    private final By kirimButton     = By.xpath("//button[@type='submit' and contains(.,'Kirim Permohonan')]");
    private final By successMessage  = By.xpath(
            "//*[contains(text(),'berhasil') or contains(text(),'sukses') or contains(text(),'terkirim') " +
            "or contains(text(),'Terima kasih') or contains(text(),'dikirim') or contains(text(),'diterima') " +
            "or contains(text(),'Submitted') or contains(text(),'submitted')] | " +
            "//*[contains(@class,'alert-success') or contains(@class,'bg-green') or contains(@class,'text-green')]"
    );
    // Error dari framework UI (class Bootstrap/Tailwind) atau HTML5 native validation
    private final By errorMessage    = By.xpath(
            "//*[contains(@class,'text-red') or contains(@class,'error') or contains(@class,'alert-danger')] | " +
            "//input[@aria-invalid='true'] | " +
            "//textarea[@aria-invalid='true']"
    );

    public DownloadRequestPopupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(kirimButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void inputNama(String nama) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(namaField));
        field.clear();
        field.sendKeys(nama);
    }

    public void inputEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        field.clear();
        field.sendKeys(email);
    }

    public void inputInstansi(String instansi) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(instansiField));
        field.clear();
        field.sendKeys(instansi);
    }

    public void inputKeperluan(String keperluan) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(keperluanField));
        field.clear();
        field.sendKeys(keperluan);
    }

    public void clickKirimPermohonan() {
        // Centang checkbox persetujuan syarat via JavaScript agar tidak diblokir overlay modal
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(termsCheckbox));
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", checkbox);
        }
        wait.until(ExpectedConditions.elementToBeClickable(kirimButton)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            // Fallback: jika popup/form menutup setelah submit → indikasi submit berhasil
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.invisibilityOfElementLocated(kirimButton));
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public boolean isValidationErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return true;
        } catch (Exception e) {
            // Fallback: cek HTML5 browser-native validation (input:invalid)
            return !driver.findElements(By.cssSelector("input:invalid, textarea:invalid")).isEmpty();
        }
    }
}
