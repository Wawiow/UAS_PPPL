package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class Hooks {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before(order = 0)
    public void setUp() {
        if (driver == null) {
            try {
                driver = new EdgeDriver();
            } catch (Exception ignored) {
                io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }
    }

    @After(order = 0)
    public void resetSession() {
        if (driver != null) {
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "window.localStorage.clear(); window.sessionStorage.clear();");
            } catch (Exception ignored) {}
            // Navigate ke login page — ini memaksa Vue reload dari scratch
            try {
                driver.get("https://elibrary.sanskuy.space/login");
            } catch (Exception ignored) {}
        }
    }

    @io.cucumber.java.AfterAll
    public static void tearDownAll() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}