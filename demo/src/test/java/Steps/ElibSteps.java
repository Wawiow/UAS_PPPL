package Steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Pages.CatalogPage;
import Pages.HomePage;
import Pages.LandingPage;
import Pages.LoginPage;
import Pages.PreviewPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ElibSteps {
    private WebDriver driver;

    private LandingPage landingPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private CatalogPage catalogPage;
    private PreviewPage previewPage;

    @Given("the user is on the E-Library homepage")
    public void userIsOnHomepage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://elibrary.sanskuy.space/");
        
        landingPage = new LandingPage(driver);
    }

    @When("the user navigates to the login page")
    public void userNavigatesToLoginPage() {
        landingPage.clickLogin();
        loginPage = new LoginPage(driver);
    }

    @And("logs in with email {string} and password {string}")
    public void logsInWithCredentials(String email, String password) {
        loginPage.login(email, password);
        homePage = new HomePage(driver);
    }

    @And("navigates to the homepage")
    public void navigatesBackToHomepage() {
        homePage.clickhomepage();
    }

    @And("opens the catalog page")
    public void opensTheCatalogPage() {
        homePage.clickcatalog();
        catalogPage = new CatalogPage(driver);
    }

    @And("the user clicks the preview button on a book")
    public void clicksThePreview() {
        catalogPage.clickpreview();
        previewPage = new PreviewPage(driver);
    }

    @Then("the use clicks the access full text button")
    public void clickfulltext() {
        previewPage.clickacsess();
        
        if (driver != null) {
            driver.quit();
        }
    }

    @Then("the login fails")
    public void loginfail() {
        driver.quit();
    }
}