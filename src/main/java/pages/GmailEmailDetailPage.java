package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailEmailDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Body email — class Gmail bisa bervariasi tergantung status baca/belum, fallback ke class dasar
    private final By emailBody = By.xpath("//div[contains(@class,'a3s')]");

    // Approve link — cari di seluruh halaman (bukan hanya div.a3s) agar tidak gagal jika strukturnya berbeda
    private final By approveButton = By.xpath(
            "//a[contains(@href,'approve') or contains(@href,'setuj') " +
            "or contains(@href,'accept') or contains(@href,'terima')]"
    );
    private final By rejectButton = By.xpath(
            "//a[contains(@href,'reject') or contains(@href,'tolak') or contains(@href,'deny')]"
    );
    // Semua link di halaman — untuk debug
    private final By allLinksInEmail = By.xpath("//div[contains(@class,'a3s')]//a");

    // Konfirmasi setelah approve — bisa berupa halaman eLibrary atau pesan di Gmail
    private final By approvalConfirmation = By.xpath(
            "//*[contains(text(),'berhasil disetujui') or contains(text(),'Approved') " +
            "or contains(text(),'approved') or contains(text(),'Permohonan Disetujui')]"
    );

    public GmailEmailDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isRequestDetailVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailBody));
            // Print isi email untuk membantu debug selanjutnya
            System.out.println("[DEBUG] Email body ditemukan. Teks email:");
            System.out.println(driver.findElement(emailBody).getText());
            return true;
        } catch (Exception e) {
            System.out.println("[DEBUG] Email body tidak ditemukan. URL saat ini: " + driver.getCurrentUrl());
            System.out.println("[DEBUG] Page title: " + driver.getTitle());
            return false;
        }
    }

    public void clickApprove() {
        // Cetak semua link di body email untuk debug
        System.out.println("[DEBUG] Semua link dalam email:");
        driver.findElements(allLinksInEmail).forEach(link ->
            System.out.println("  teks=[" + link.getText() + "]  \nhref=[" + link.getAttribute("href") + "]")
        );
        try {
            // Prioritas: cari approve link yang tepat setelah mailto Ahsani.fadhli
            By approveForAhsani = By.xpath(
                "//a[contains(@href,'Ahsani.fadhli') or contains(@href,'ahsani.fadhli')]" +
                "/following::a[contains(@href,'approve')][1]"
            );
            java.util.List<WebElement> targetLinks = driver.findElements(approveForAhsani);
            if (!targetLinks.isEmpty()) {
                String approveUrl = targetLinks.get(0).getAttribute("href");
                System.out.println("[DEBUG] Navigasi ke approve URL (Ahsani.fadhli): " + approveUrl);
                driver.get(approveUrl);
                return;
            }
            // Fallback: ambil link approve terakhir
            java.util.List<WebElement> approveLinks = driver.findElements(approveButton);
            if (approveLinks.isEmpty()) {
                throw new RuntimeException("Tidak ada link approve ditemukan di halaman");
            }
            String approveUrl = approveLinks.get(approveLinks.size() - 1).getAttribute("href");
            System.out.println("[DEBUG] Fallback - navigasi ke approve URL: " + approveUrl);
            driver.get(approveUrl);
        } catch (Exception e) {
            System.out.println("[DEBUG] Approve button tidak ditemukan: " + e.getMessage());
            throw e;
        }
    }

    public void clickReject() {
        wait.until(ExpectedConditions.elementToBeClickable(rejectButton)).click();
    }

    public boolean isApprovalConfirmed() {
        try {
            // Iterasi semua tab — cari yang ada URL eLibrary (approve redirect) atau teks konfirmasi
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                String url = driver.getCurrentUrl();
                System.out.println("[DEBUG] Tab - URL: " + url + " | Title: " + driver.getTitle());

                if (url.contains("elibrary") || url.contains("approve") || url.contains("setuj")) {
                    return true;
                }
            }
            // Fallback: cek teks konfirmasi di tab aktif saat ini
            wait.until(ExpectedConditions.visibilityOfElementLocated(approvalConfirmation));
            return true;
        } catch (Exception e) {
            System.out.println("[DEBUG] isApprovalConfirmed gagal. URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public boolean isApprovalEmailVisible() {
        try {
            // Coba beberapa selector body email
            String bodyText = "";
            By[] selectors = { emailBody,
                By.xpath("//div[contains(@class,'ii') and contains(@class,'gt')]"),
                By.cssSelector("div.a3s, div.ii.gt")
            };
            for (By sel : selectors) {
                try {
                    WebElement el = new WebDriverWait(driver, Duration.ofSeconds(8))
                            .until(ExpectedConditions.visibilityOfElementLocated(sel));
                    bodyText = el.getText().toLowerCase();
                    if (!bodyText.isEmpty()) break;
                } catch (Exception ignored) {}
            }
            // Fallback: ambil seluruh page text
            if (bodyText.isEmpty()) bodyText = driver.getPageSource().toLowerCase();
            System.out.println("[DEBUG] Email body preview: " + bodyText.substring(0, Math.min(bodyText.length(), 200)));
            return bodyText.contains("disetujui") || bodyText.contains("approved")
                    || bodyText.contains("elibrary") || bodyText.contains("sanskuy")
                    || bodyText.contains("permohonan");
        } catch (Exception e) {
            System.out.println("[DEBUG] isApprovalEmailVisible error: " + e.getMessage());
            return false;
        }
    }
}
