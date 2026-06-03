package steps;

import driver.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.*;

public class DownloadRequestSteps {

    private final WebDriver driver = DriverFactory.getDriver();

    private final LandingPage landingPage                    = new LandingPage(driver);
    private final ELibraryLoginPage elibraryLoginPage        = new ELibraryLoginPage(driver);
    private final ELibraryDashboardPage elibraryDashboard    = new ELibraryDashboardPage(driver);
    private final CatalogPage catalogPage                    = new CatalogPage(driver);
    private final DownloadRequestPopupPage popupPage         = new DownloadRequestPopupPage(driver);
    private final GmailLoginPage gmailLoginPage              = new GmailLoginPage(driver);
    private final GmailInboxPage gmailInboxPage              = new GmailInboxPage(driver);
    private final GmailEmailDetailPage gmailDetailPage       = new GmailEmailDetailPage(driver);

    // ── Background ───────────────────────────────────────────────────────────

    @Given("user membuka halaman landing page eLibrary")
    public void user_membuka_landing_page() {
        landingPage.open();
    }

    // ── Login eLibrary & Dashboard ───────────────────────────────────────────

    @When("user klik tombol Login di landing page")
    public void user_klik_tombol_login() {
        landingPage.clickLoginButton();
    }

    @And("user login ke eLibrary dengan email {string} dan password {string}")
    public void user_login_elibrary(String email, String password) {
        elibraryLoginPage.login(email, password);
    }

    @And("user klik Beranda di dashboard")
    public void user_klik_beranda() {
        elibraryDashboard.clickBeranda();
    }

    // ── Navigasi ke Katalog ──────────────────────────────────────────────────

    @When("user klik menu Katalog di navbar")
    public void user_klik_menu_katalog() {
        landingPage.clickCatalogMenu();
    }

    @Then("user berada di halaman katalog")
    public void user_berada_di_halaman_katalog() {
        Assertions.assertTrue(catalogPage.isOnCatalogPage(),
                "URL harus mengandung /catalog setelah klik menu Katalog");
    }

    // ── Interaksi popup Download ─────────────────────────────────────────────

    @When("user klik tombol Download pada dokumen")
    public void user_klik_tombol_download() {
        catalogPage.clickDownloadButton();
    }

    @Then("popup permohonan unduh ditampilkan")
    public void popup_permohonan_unduh_ditampilkan() {
        Assertions.assertTrue(popupPage.isVisible(),
                "Popup permohonan unduh harus muncul setelah klik tombol Download");
    }

    @When("user mengisi nama {string}")
    public void user_mengisi_nama(String nama) {
        popupPage.inputNama(nama);
    }

    @When("user mengisi email {string}")
    public void user_mengisi_email(String email) {
        popupPage.inputEmail(email);
    }

    @And("user mengisi instansi {string}")
    public void user_mengisi_instansi(String instansi) {
        popupPage.inputInstansi(instansi);
    }

    @And("user mengisi keperluan {string}")
    public void user_mengisi_keperluan(String keperluan) {
        popupPage.inputKeperluan(keperluan);
    }

    @When("user klik tombol Kirim Permohonan")
    public void user_klik_kirim_permohonan() {
        popupPage.clickKirimPermohonan();
    }

    // ── Hasil submit form ────────────────────────────────────────────────────

    @Then("permohonan berhasil dikirim dengan pesan sukses")
    public void permohonan_berhasil_dikirim() {
        Assertions.assertTrue(popupPage.isSuccessMessageDisplayed(),
                "Pesan sukses harus ditampilkan setelah permohonan berhasil dikirim");
    }

    @Then("muncul pesan validasi error")
    public void muncul_pesan_validasi_error() {
        Assertions.assertTrue(popupPage.isValidationErrorDisplayed(),
                "Pesan error validasi harus ditampilkan saat data tidak valid");
    }

    @Then("sistem menampilkan respons permohonan")
    public void sistem_menampilkan_respons_permohonan() {
        boolean adaRespons = popupPage.isSuccessMessageDisplayed()
                || popupPage.isValidationErrorDisplayed();
        Assertions.assertTrue(adaRespons,
                "Sistem harus menampilkan respons (sukses atau error) setelah submit");
    }

    // ── Gmail: pemilik dokumen ───────────────────────────────────────────────

    @Given("pemilik dokumen membuka halaman Gmail")
    public void pemilik_membuka_gmail() {
        gmailLoginPage.open();
    }

    @When("pemilik dokumen login dengan email {string} dan password {string}")
    public void pemilik_login_gmail(String email, String password) {
        gmailLoginPage.login(email, password);
    }

    @When("pemilik dokumen membuka email permohonan unduh terbaru")
    public void pemilik_membuka_email_permohonan() {
        gmailInboxPage.openLatestElibraryRequestEmail();
    }

    @Then("detail permohonan ditampilkan dalam email")
    public void detail_permohonan_ditampilkan() {
        Assertions.assertTrue(gmailDetailPage.isRequestDetailVisible(),
                "Body email permohonan harus terlihat");
    }

    @When("pemilik dokumen klik tombol Approve dalam email")
    public void pemilik_klik_approve() {
        gmailDetailPage.clickApprove();
    }

    @Then("halaman konfirmasi persetujuan ditampilkan")
    public void halaman_konfirmasi_ditampilkan() {
        Assertions.assertTrue(gmailDetailPage.isApprovalConfirmed(),
                "Konfirmasi persetujuan harus ditampilkan setelah approve");
    }

    // ── Gmail: pemohon (requester) ───────────────────────────────────────────

    @Given("pemohon membuka halaman Gmail")
    public void pemohon_membuka_gmail() {
        gmailLoginPage.open();
    }

    @When("pemohon login dengan email {string} dan password {string}")
    public void pemohon_login_gmail(String email, String password) {
        gmailLoginPage.login(email, password);
    }

    @When("pemohon membuka email notifikasi persetujuan dari eLibrary")
    public void pemohon_membuka_email_notifikasi() {
        gmailInboxPage.openLatestApprovalNotificationEmail();
    }

    @Then("email konfirmasi unduh ditampilkan")
    public void email_konfirmasi_unduh_ditampilkan() {
        Assertions.assertTrue(gmailDetailPage.isApprovalEmailVisible(),
                "Email notifikasi persetujuan unduh harus ditampilkan");
    }
}
