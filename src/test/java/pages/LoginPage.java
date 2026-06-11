package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
	private final WebDriver driver;
	private final WebDriverWait wait;
	private final WebDriverWait shortWait;
	private final String loginUrl = "https://elibrary.sanskuy.space/login";
	private final String homeUrl  = "https://elibrary.sanskuy.space/";

	public LoginPage(WebDriver driver) {
		this.driver    = driver;
		this.wait      = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
	}

	public void openLoginPage() {
		driver.get(loginUrl);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@placeholder='Email']")));
	}

	public void openHomePage() {
		driver.get(homeUrl);
	}

	public void enterEmail(String email) {
		WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@placeholder='Email']")));
		field.clear();
		field.sendKeys(email);
	}

	public void enterPassword(String password) {
		WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@placeholder='Password']")));
		field.clear();
		field.sendKeys(password);
	}

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("button[type='submit']"))).click();
	}

	public boolean isAtHomePage() {
		try {
			wait.withTimeout(Duration.ofSeconds(5))
					.until(d -> !d.getCurrentUrl().contains("/login"));
		} catch (Exception ignored) {}
		return !driver.getCurrentUrl().contains("/login");
	}

	public String getFlashMessage() {
		try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
		By[] selectors = {
				By.xpath("//*[@id='app']//form//div[contains(normalize-space(.), 'Invalid credentials') or contains(., '401')]"),
				By.xpath("//*[@id='app']//div[contains(@class,'text-red-700')]")
		};
		for (By sel : selectors) {
			try {
				String txt = shortWait.until(ExpectedConditions
						.visibilityOfElementLocated(sel)).getText();
				if (txt != null && !txt.trim().isEmpty()) return txt.trim();
			} catch (Exception ignored) {}
		}
		return "";
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSourceSnippet() {
		try {
			String src = driver.getPageSource();
			return src == null ? "" : (src.length() > 1000 ? src.substring(0, 1000) : src);
		} catch (Exception e) {
			return "";
		}
	}
}