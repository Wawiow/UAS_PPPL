package steps;

import driver.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.ContributorPages;
import pages.ELibraryLoginPage;
import pages.LandingPage;

public class ReqContributorSteps {

    private final WebDriver driver = DriverFactory.getDriver();
    private final LandingPage landingPage = new LandingPage(driver);
    private final ELibraryLoginPage loginPage = new ELibraryLoginPage(driver);
    private final ContributorPages contributorPages = new ContributorPages(driver);

    @Given("user membuka halaman login")
    public void user_membuka_halaman_login() {
        // Hooks @Before sudah open landing page — tinggal klik tombol Login
        landingPage.clickLoginButton();
    }

    @When("user login dengan email {string} dan password {string}")
    public void user_login_dengan_email_dan_password(String email, String password) {
        loginPage.login(email, password);
    }

    @When("user klik menu jadi kontributor")
    public void user_klik_menu_jadi_kontributor() {
        contributorPages.clickJadiKontributorMenu();
    }

    @When("user klik tombol lanjut isi form")
    public void user_klik_tombol_lanjut_isi_form() {
        contributorPages.clickLanjutButton();
    }

    @When("user input alasan {string}")
    public void user_input_alasan(String alasan) {
        contributorPages.inputAlasan(alasan);
    }

    @When("user submit pengajuan")
    public void user_submit_pengajuan() {
        contributorPages.submitPengajuan();
    }

    @Then("pengajuan berhasil dikirim")
    public void pengajuan_berhasil_dikirim() {
        Assertions.assertTrue(contributorPages.isPengajuanBerhasil(),
            "Pengajuan kontributor harus berhasil dikirim");
    }

    @Given("admin membuka halaman login")
    public void admin_membuka_halaman_login() {
        landingPage.clickLoginButton();
    }

    @When("admin login dengan email {string} dan password {string}")
    public void admin_login_dengan_email_dan_password(String email, String password) {
        loginPage.login(email, password);
    }

    @When("admin klik menu contributor request")
    public void admin_klik_menu_contributor_request() {
        contributorPages.clickAdminContributorMenu();
    }

    @When("admin klik tombol approve")
    public void admin_klik_tombol_approve() {
        contributorPages.clickApprove();
    }

    @When("admin input notes")
    public void admin_input_notes() {
        contributorPages.inputNotes();
    }

    @Then("user berhasil jadi kontributor")
    public void user_berhasil_jadi_kontributor() {
        Assertions.assertTrue(contributorPages.isUserJadiKontributor(),
            "User harus berhasil menjadi kontributor setelah admin approve");
    }
}
