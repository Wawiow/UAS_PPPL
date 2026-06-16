package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class UploadPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;

    public UploadPage(WebDriver driver) {
        this.driver    = driver;
        this.wait      = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }
    private void waitForToastToDisappear() {
        try {
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'pointer-events-auto')]")));
        } catch (Exception ignored) {}
    }

    public void clickDashboardContributor() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[normalize-space()='Dashboard Kontributor']")));
        waitForToastToDisappear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public boolean isAtContributorDashboard() {
        try {
            wait.withTimeout(Duration.ofSeconds(5))
                    .until(d -> d.getCurrentUrl().contains("/contributor-dashboard"));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void clickUnggahDokumen() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[normalize-space()='Unggah Dokumen']")));
        waitForToastToDisappear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public boolean isUploadModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[normalize-space()='Upload Dokumen Baru']"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    public void fillTitle(String title) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Masukkan judul dokumen']")));
        input.clear();
        input.sendKeys(title);
    }

    public void clearTitle() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Masukkan judul dokumen']")));
        input.clear();
    }

    public void selectLanguage(String value) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//select[.//option[@value='id']]"))))
                .selectByValue(value);
    }

    public void selectFirstDocumentType() {
        By selectBy = By.xpath("//select[.//option[contains(.,'pilih jenis')]]");
        wait.until(d -> {
            try {
                Select s = new Select(d.findElement(selectBy));
                return s.getOptions().size() > 1;
            } catch (Exception e) {
                return false;
            }
        });
        new Select(driver.findElement(selectBy)).selectByIndex(1);
    }

    public void fillAuthor(String author) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nama penulis']")));
        input.clear();
        input.sendKeys(author);
    }

    public void fillYear(String year) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='2024']")));
        input.clear();
        input.sendKeys(year);
    }

    public void selectFirstSubject() {
        wait.until(d -> {
            try {
                Select s = new Select(d.findElement(By.id("subject")));
                return s.getOptions().size() > 1;
            } catch (Exception e) {
                return false;
            }
        });
        new Select(driver.findElement(By.id("subject"))).selectByIndex(1);
    }

    public void selectFirstUniversity() {
        wait.until(d -> {
            try {
                Select s = new Select(d.findElement(By.id("university")));
                return s.getOptions().size() > 1;
            } catch (Exception e) {
                return false;
            }
        });
        new Select(driver.findElement(By.id("university"))).selectByIndex(1);
    }

    public void fillValidMetadata() {
        fillTitle("Dokumen Test Validasi");
        selectLanguage("id");
        selectFirstDocumentType();
        fillAuthor("Test Author");
        fillYear("2025");
        selectFirstSubject();
        selectFirstUniversity();
    }



    public void clickLanjut() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[normalize-space()='Lanjut']")));
        waitForToastToDisappear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void clickUploadDokumenFinal() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[normalize-space()='Upload Dokumen']")));
        waitForToastToDisappear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }



    public void uploadPdf(String fileName) {
        String path = Paths.get(System.getProperty("user.dir"),
                        "src", "test", "resources", "files", fileName)
                .toAbsolutePath().toString();

        WebElement fileInput = null;
        try {
            fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='file' and @accept='.pdf' and not(@multiple)]")));
        } catch (Exception ignored) {
            fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='file' and @accept='.pdf']")));
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].removeAttribute('class');",
                fileInput);
        fileInput.sendKeys(path);
    }



    public void agreeAllStatements() {
        List<WebElement> checkboxes = driver.findElements(
                By.xpath("//div[contains(@class,'space-y-3')]//input[@type='checkbox']"));
        for (WebElement cb : checkboxes) {
            if (!cb.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cb);
            }
        }
    }
    public boolean isUploadSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[normalize-space()='Upload Berhasil!']"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStatusPendingReview() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[normalize-space()='Pending Review']"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getValidationMessage() {
        try {
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class,'text-red-500')]"))).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}