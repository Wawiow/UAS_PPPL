package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailLoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Fallback selectors — Google mengubah ID/struktur login antara versi
    private final By emailField        = By.xpath("//*[@id='identifierId'] | //input[@type='email'][@autocomplete='email' or @autocomplete='username'] | //input[@name='identifier']");
    private final By emailNextButton   = By.xpath("//*[@id='identifierNext'] | //button[@type='submit']");
    private final By passwordField     = By.cssSelector("input[type='password']");
    private final By passwordNextButton = By.xpath("//*[@id='passwordNext'] | //button[@type='submit']");

    public GmailLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        // Langsung ke halaman login — hindari account chooser jika sudah ada sesi
        driver.get("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&hl=id&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        try { Thread.sleep(2000); } catch (Exception ignored) {}
        System.out.println("[DEBUG] Gmail open URL: " + driver.getCurrentUrl() + " | title: " + driver.getTitle());
    }

    /**
     * Login ke Gmail dengan email dan password.
     * CATATAN: Gunakan App Password jika akun mengaktifkan 2-Factor Authentication.
     * Jika Google menampilkan halaman verifikasi tambahan, test ini akan gagal
     * dan perlu penanganan manual atau akun testing khusus tanpa 2FA.
     */
    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(emailNextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(passwordNextButton)).click();

        // Tunggu sampai URL berubah dari halaman password (bisa ke inbox, challenge, atau halaman lain)
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
            d -> !d.getCurrentUrl().contains("/signin/v2/") && !d.getCurrentUrl().contains("/signin/identifier")
        );

        // Tangani Google challenge/pwd — Google kadang minta verifikasi ulang password
        if (driver.getCurrentUrl().contains("challenge/pwd") || driver.getCurrentUrl().contains("challenge/ipp")) {
            System.out.println("[DEBUG] Halaman challenge terdeteksi: " + driver.getCurrentUrl());
            try {
                org.openqa.selenium.WebElement challengePwdField =
                    new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.visibilityOfElementLocated(passwordField));
                challengePwdField.clear();
                challengePwdField.sendKeys(password);
                try { Thread.sleep(800); } catch (Exception ignored) {}
                // Tekan Enter — lebih natural, bypass overlay button
                challengePwdField.sendKeys(Keys.RETURN);
                System.out.println("[DEBUG] Challenge: Enter dikirim");
            } catch (Exception e) {
                System.out.println("[DEBUG] Challenge handler gagal: " + e.getMessage());
            }
        }

        wait.until(ExpectedConditions.urlContains("mail.google.com/mail"));
        dismissChromeSystemTabs();
    }

    private void dismissChromeSystemTabs() {
        String gmailHandle = driver.getWindowHandle();
        for (String handle : new java.util.ArrayList<>(driver.getWindowHandles())) {
            if (handle.equals(gmailHandle)) continue;
            try {
                driver.switchTo().window(handle);
                String url = driver.getCurrentUrl();
                if (url.startsWith("chrome://") || url.startsWith("about:")) {
                    driver.close();
                }
            } catch (Exception ignored) {
                // Tab tidak bisa ditutup atau sudah tertutup — lanjut saja
            }
        }
        try {
            driver.switchTo().window(gmailHandle);
        } catch (Exception ignored) {}
    }
}
