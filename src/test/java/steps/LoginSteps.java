package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.UploadPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private LoginPage loginPage() {
        return new LoginPage(Hooks.getDriver());
    }

    private UploadPage uploadPage() {
        return new UploadPage(Hooks.getDriver());
    }

    @Given("pengguna berada di halaman login")
    public void pengguna_berada_di_halaman_login() {
        loginPage().openLoginPage();
    }

    @Given("pengguna sudah memiliki akun dengan email {string} dan password {string}")
    public void pengguna_sudah_memiliki_akun(String email, String password) {
        // tidak perlu aksi
    }

    @When("pengguna memasukkan email {string} ke dalam field email")
    public void pengguna_memasukkan_email(String email) {
        loginPage().enterEmail(email);
    }

    @And("pengguna memasukkan password {string} ke dalam field password")
    public void pengguna_memasukkan_password(String password) {
        loginPage().enterPassword(password);
    }

    @And("pengguna menekan tombol {string}")
    public void pengguna_menekan_tombol(String tombol) {
        switch (tombol) {
            case "Login":
                loginPage().clickLogin();
                break;
            case "Dashboard Kontributor":
                uploadPage().clickDashboardContributor();
                break;
            case "Unggah Dokumen":
                uploadPage().clickUnggahDokumen();
                break;
            case "Lanjut":
                uploadPage().clickLanjut();
                break;
            case "Upload Dokumen":
                uploadPage().clickUploadDokumenFinal();
                break;
            default:
                throw new IllegalArgumentException("Tombol tidak dikenal: " + tombol);
        }
    }

    @Then("pengguna diarahkan ke halaman dashboard")
    public void pengguna_diarahkan_ke_halaman_dashboard() {
        assertTrue(loginPage().isAtHomePage(), "Diharapkan diarahkan ke dashboard setelah login");
    }

    @Then("pengguna tetap berada di halaman login")
    public void pengguna_tetap_berada_di_halaman_login() {
        assertFalse(loginPage().isAtHomePage(), "Diharapkan tetap di halaman login");
    }

    @Then("muncul pesan kesalahan {string}")
    public void muncul_pesan_kesalahan(String expected) {
        String msg = loginPage().getFlashMessage();
        if (msg != null && msg.contains(expected)) return;
        fail("Diharapkan: '" + expected + "' tetapi ditemukan: '" + msg + "'"
                + "\nURL: " + loginPage().getCurrentUrl()
                + "\nPage source:\n" + loginPage().getPageSourceSnippet());
    }
}