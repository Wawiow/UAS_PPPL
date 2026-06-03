package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailInboxPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GmailInboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openLatestElibraryRequestEmail() {
        driver.get("https://mail.google.com/mail/u/0/#inbox");
        try { Thread.sleep(3000); } catch (Exception ignored) {}
        System.out.println("[DEBUG] Title inbox owner: " + driver.getTitle());

        System.out.println("[DEBUG] Email di inbox saat ini:");
        try {
            driver.findElements(By.xpath("//tr[contains(@class,'zA')]")).forEach(row -> {
                String text = "";
                try { text = row.getText().replaceAll("\\s+", " ").trim(); } catch (Exception ignored) {}
                System.out.println("  [row] " + text.substring(0, Math.min(text.length(), 120)));
            });
        } catch (Exception ignored) {}

        Object result = ((JavascriptExecutor) driver).executeScript(
            "var rows = Array.from(document.querySelectorAll('tr.zA'));" +
            "var target = rows.find(r => {" +
            "  var t = r.innerText.toLowerCase();" +
            "  return t.includes('permohonan') || t.includes('permintaan') ||" +
            "         t.includes('elibrary') || t.includes('e-library');" +
            "});" +
            "if (target) { target.click(); return 'klik: ' + target.innerText.substring(0,100); }" +
            "return 'email permohonan tidak ditemukan';"
        );
        System.out.println("[DEBUG] JS click owner: " + result);

        if (result.toString().startsWith("email permohonan tidak ditemukan")) {
            throw new RuntimeException("Email permohonan download tidak ditemukan di inbox owner: " + result);
        }

        try {
            wait.until(d -> d.getCurrentUrl().contains("#inbox/") || d.getCurrentUrl().contains("#all/"));
            System.out.println("[DEBUG] URL setelah klik email owner: " + driver.getCurrentUrl());
        } catch (Exception ignored) {}

        try { Thread.sleep(2000); } catch (Exception ignored) {}

        // Scroll ke bawah agar thread panjang ter-load
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        By[] bodySelectors = {
            By.xpath("//div[contains(@class,'a3s') and string-length(normalize-space(.))>5]"),
            By.xpath("//div[contains(@class,'ii') and contains(@class,'gt')]"),
            By.cssSelector("div.a3s"),
            By.cssSelector("div.ii.gt")
        };
        for (By sel : bodySelectors) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(sel));
                System.out.println("[DEBUG] Owner email body ditemukan: " + sel);
                return;
            } catch (Exception ignored) {}
        }
        throw new RuntimeException("Body email permohonan (owner) tidak ditemukan setelah klik thread");
    }

    public void openLatestApprovalNotificationEmail() {
        driver.get("https://mail.google.com/mail/u/0/#inbox");
        try { Thread.sleep(3000); } catch (Exception ignored) {}
        System.out.println("[DEBUG] Title setelah buka inbox: " + driver.getTitle());

        Object result = ((JavascriptExecutor) driver).executeScript(
            "var rows = Array.from(document.querySelectorAll('tr.zA'));" +
            "var target = rows.find(r => {" +
            "  var t = r.innerText.toLowerCase();" +
            "  return t.includes('elibrary') || t.includes('e-library') || t.includes('permohonan') || t.includes('disetujui') || t.includes('sanskuy');" +
            "});" +
            "if (target) { target.click(); return 'klik: ' + target.innerText.substring(0,100); }" +
            "return 'email notifikasi tidak ditemukan di inbox';"
        );
        System.out.println("[DEBUG] JS click result: " + result);

        if (result.toString().startsWith("email notifikasi tidak ditemukan")) {
            throw new RuntimeException("Email notifikasi approval dari eLibrary tidak ditemukan di inbox: " + result);
        }

        try {
            wait.until(d -> d.getCurrentUrl().contains("#inbox/") || d.getCurrentUrl().contains("#all/") || d.getCurrentUrl().contains("search/"));
            System.out.println("[DEBUG] URL setelah klik email: " + driver.getCurrentUrl());
        } catch (Exception ignored) {}

        try { Thread.sleep(2000); } catch (Exception ignored) {}

        By[] bodySelectors = {
            By.xpath("//div[contains(@class,'a3s') and string-length(normalize-space(.))>5]"),
            By.xpath("//div[@class='ii gt']"),
            By.xpath("//div[contains(@class,'nH') and string-length(normalize-space(.))>20]"),
            By.cssSelector("div.a3s, div.ii.gt, div[data-message-id]")
        };
        for (By sel : bodySelectors) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(8)).until(ExpectedConditions.visibilityOfElementLocated(sel));
                System.out.println("[DEBUG] Body email ditemukan dengan selector: " + sel);
                return;
            } catch (Exception ignored) {}
        }
        throw new RuntimeException("Body email tidak ditemukan setelah klik email eLibrary");
    }
}
