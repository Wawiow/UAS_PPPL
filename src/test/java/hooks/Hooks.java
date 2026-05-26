package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import driver.DriverFactory;

public class Hooks {

    @Before
    public void setup() {
        DriverFactory.getDriver().get("https://elibrary.sanskuy.space/");
    }

    @After
    public void tearDown() {
        DriverFactory.driver.quit();
    }
}
