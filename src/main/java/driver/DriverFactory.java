package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;

public class DriverFactory {

    public static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            // Menyembunyikan tanda automation agar tidak diblokir Google
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void quitDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}