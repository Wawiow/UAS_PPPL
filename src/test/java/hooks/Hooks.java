package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import driver.DriverFactory;

public class Hooks {

    @Before
    public void setup() {
        DriverFactory.getDriver().get("https://elibrary.sanskuy.space/");
    }

    @AfterStep
    public void delayBetweenSteps(Scenario scenario) {
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
