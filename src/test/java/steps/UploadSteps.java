package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.UploadPage;

import static org.junit.jupiter.api.Assertions.*;

public class UploadSteps {

    private LoginPage loginPage() {
        return new LoginPage(Hooks.getDriver());
    }

    private UploadPage uploadPage() {
        return new UploadPage(Hooks.getDriver());
    }

    @Given("pengguna sudah login menggunakan email {string} dan password {string}")
    public void pengguna_sudah_login(String email, String password) {
        loginPage().openLoginPage();
        loginPage().enterEmail(email);
        loginPage().enterPassword(password);
        loginPage().clickLogin();
        assertTrue(loginPage().isAtHomePage(), "Login gagal untuk: " + email);
    }

    @Given("pengguna berada di halaman beranda")
    public void pengguna_berada_di_halaman_beranda() {
        // sudah di beranda setelah login
    }

    @Then("pengguna diarahkan ke halaman dashboard contributor")
    public void pengguna_diarahkan_ke_dashboard_contributor() {
        assertTrue(uploadPage().isAtContributorDashboard(), "Gagal masuk ke dashboard kontributor");
    }

    @When("pengguna membuka form upload dokumen")
    public void pengguna_membuka_form_upload_dokumen() {
        uploadPage().clickUnggahDokumen();
    }

    @Then("form upload dokumen ditampilkan")
    public void form_upload_dokumen_ditampilkan() {
        assertTrue(uploadPage().isUploadModalDisplayed(), "Modal upload tidak muncul");
    }

    @When("pengguna mengisi judul dokumen {string}")
    public void pengguna_mengisi_judul(String title) {
        uploadPage().fillTitle(title);
    }

    @When("pengguna mengosongkan judul dokumen")
    public void pengguna_mengosongkan_judul() {
        uploadPage().clearTitle();
    }

    @And("pengguna memilih bahasa {string}")
    public void pengguna_memilih_bahasa(String value) {
        uploadPage().selectLanguage(value);
    }

    @And("pengguna memilih jenis dokumen")
    public void pengguna_memilih_jenis_dokumen() {
        uploadPage().selectFirstDocumentType();
    }

    @And("pengguna mengisi penulis {string}")
    public void pengguna_mengisi_penulis(String author) {
        uploadPage().fillAuthor(author);
    }

    @And("pengguna mengisi tahun terbit {string}")
    public void pengguna_mengisi_tahun(String year) {
        uploadPage().fillYear(year);
    }

    @And("pengguna memilih subjek")
    public void pengguna_memilih_subjek() {
        uploadPage().selectFirstSubject();
    }

    @And("pengguna mengisi metadata dengan data valid")
    public void pengguna_mengisi_metadata_valid() {
        uploadPage().fillValidMetadata();
    }

    @And("pengguna mengisi seluruh data upload dengan benar")
    public void pengguna_mengisi_seluruh_data() {
        uploadPage().fillValidMetadata();
        uploadPage().clickLanjut(); // ke step 2 ringkasan
        uploadPage().clickLanjut(); // ke step 3 upload file
        uploadPage().uploadPdf("Selenium Cucumber.pdf");
        uploadPage().clickLanjut(); // ke step 4 lisensi
    }

    @And("pengguna menekan tombol \"Lanjut\" pada ringkasan metadata")
    public void pengguna_menekan_lanjut_ringkasan() {
        uploadPage().clickLanjut();
    }

    @And("pengguna mengunggah file PDF {string}")
    public void pengguna_mengunggah_pdf(String fileName) {
        uploadPage().uploadPdf(fileName);
    }

    @And("pengguna memilih lisensi {string}")
    public void pengguna_memilih_lisensi(String license) {
        // default sudah ARR
    }

    @And("pengguna menyetujui seluruh pernyataan")
    public void pengguna_menyetujui_pernyataan() {
        uploadPage().agreeAllStatements();
    }

    @And("pengguna tidak mencentang pernyataan persetujuan")
    public void pengguna_tidak_mencentang_pernyataan() {
        // sengaja kosong
    }

    @Then("upload dokumen berhasil")
    public void upload_dokumen_berhasil() {
        assertTrue(uploadPage().isUploadSuccessful(), "Pesan 'Upload Berhasil!' tidak ditemukan");
    }

    @And("status dokumen adalah {string}")
    public void status_dokumen_adalah(String status) {
        assertTrue(uploadPage().isStatusPendingReview(), "Status '" + status + "' tidak ditemukan");
    }

    @Then("muncul pesan validasi {string}")
    public void muncul_pesan_validasi(String expected) {
        String msg = uploadPage().getValidationMessage();
        assertTrue(msg.contains(expected),
                "Diharapkan: '" + expected + "' tetapi ditemukan: '" + msg + "'");
    }
}