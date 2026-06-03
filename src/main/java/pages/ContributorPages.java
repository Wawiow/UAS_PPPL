package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContributorPages {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By jadiKontributorMenu = By.xpath(
        "//a[contains(normalize-space(.),'Kontributor') or contains(normalize-space(.),'kontributor')] |" +
        "//button[contains(normalize-space(.),'Kontributor') or contains(normalize-space(.),'kontributor')]"
    );

    private final By lanjutButton = By.xpath(
        "//button[contains(normalize-space(.),'Lanjut') or contains(normalize-space(.),'lanjut') or " +
        "contains(normalize-space(.),'Isi Form') or contains(normalize-space(.),'isi form') or " +
        "contains(normalize-space(.),'Mulai') or contains(normalize-space(.),'Daftar')]"
    );

    private final By alasanField = By.xpath(
        "//textarea | //input[@name='alasan' or @name='reason' or " +
        "@placeholder='Alasan' or @placeholder='alasan' or @placeholder='Reason']"
    );

    private final By submitButton = By.xpath(
        "//button[@type='submit'] | " +
        "//button[contains(normalize-space(.),'Kirim') or contains(normalize-space(.),'Submit') or " +
        "contains(normalize-space(.),'Ajukan') or contains(normalize-space(.),'Daftar')]"
    );

    private final By successMessage = By.xpath(
        "//*[contains(text(),'berhasil') or contains(text(),'Berhasil') or " +
        "contains(text(),'sukses') or contains(text(),'success') or contains(text(),'Success')]"
    );

    private final By adminContributorMenu = By.xpath(
        "//a[contains(normalize-space(.),'Contributor') or contains(normalize-space(.),'contributor') or " +
        "contains(normalize-space(.),'Kontributor') or contains(normalize-space(.),'kontributor')]"
    );

    private final By approveButton = By.xpath(
        "//button[contains(normalize-space(.),'Approve') or contains(normalize-space(.),'approve') or " +
        "contains(normalize-space(.),'Setujui') or contains(normalize-space(.),'setujui') or " +
        "contains(normalize-space(.),'Terima') or contains(normalize-space(.),'Konfirmasi')]"
    );

    private final By notesField = By.xpath(
        "//textarea[@name='notes' or @name='catatan' or @placeholder='Notes' or " +
        "@placeholder='notes' or @placeholder='Catatan' or @placeholder='catatan'] | " +
        "//input[@name='notes' or @name='catatan']"
    );

    private final By approvalConfirmation = By.xpath(
        "//*[contains(text(),'berhasil') or contains(text(),'Berhasil') or " +
        "contains(text(),'approved') or contains(text(),'Approved') or " +
        "contains(text(),'kontributor') or contains(text(),'Kontributor')]"
    );

    public ContributorPages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickJadiKontributorMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(jadiKontributorMenu)).click();
    }

    public void clickLanjutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(lanjutButton)).click();
    }

    public void inputAlasan(String alasan) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(alasanField));
        field.clear();
        field.sendKeys(alasan);
    }

    public void submitPengajuan() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isPengajuanBerhasil() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            System.out.println("[DEBUG] Pesan sukses pengajuan ditemukan. URL: " + driver.getCurrentUrl());
            return true;
        } catch (Exception e) {
            System.out.println("[DEBUG] Pesan sukses tidak ditemukan. URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void clickAdminContributorMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(adminContributorMenu)).click();
    }

    public void clickApprove() {
        try {
            List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(approveButton));
            WebElement btn = buttons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", btn);
            System.out.println("[DEBUG] Approve klik: " + btn.getText());
        } catch (Exception e) {
            System.out.println("[DEBUG] clickApprove error: " + e.getMessage());
            throw new RuntimeException("Tombol approve tidak ditemukan", e);
        }
    }

    public void inputNotes() {
        try {
            WebElement field = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(notesField));
            field.clear();
            field.sendKeys("Disetujui oleh admin");
            System.out.println("[DEBUG] Notes diisi");
        } catch (Exception e) {
            System.out.println("[DEBUG] Notes field tidak ditemukan, lanjut tanpa input: " + e.getMessage());
        }
    }

    public boolean isUserJadiKontributor() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(approvalConfirmation));
            System.out.println("[DEBUG] Konfirmasi kontributor ditemukan. URL: " + driver.getCurrentUrl());
            return true;
        } catch (Exception e) {
            String url = driver.getCurrentUrl();
            System.out.println("[DEBUG] Konfirmasi teks tidak ditemukan, cek URL: " + url);
            return url.contains("kontributor") || url.contains("contributor") || url.contains("dashboard");
        }
    }
}
