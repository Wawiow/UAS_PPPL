package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.ReqContributorPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReqContributorSteps {

    private ReqContributorPage contributorPage () {
        return new ReqContributorPage(Hooks.getDriver());
    }

    @And("pengguna menekan tombol jadi kontributor")
    public void penggunaMenekanTombolJadiKontributor() {
        contributorPage().clickJadiKontributor();
    }

    @And("pengguna membuka form pengajuan kontributor")
    public void penggunaMembukaFormPengajuanKontributor() {
        contributorPage().clickLanjutIsiForm();
    }

    @And("pengguna mengisi alasan kontributor {string}")
    public void penggunaMengisiAlasanKontributor(String alasan) {
        contributorPage().inputAlasan(alasan);
    }

    @And("pengguna mengajukan permintaan kontributor")
    public void penggunaMengajukanPermintaanKontributor() {
        contributorPage().clickAjukanPermintaan();
    }

    @Then("permintaan kontributor berhasil dikirim")
    public void permintaanKontributorBerhasilDikirim() {
        assertTrue(
                contributorPage().isRequestSuccess(),
                "Pesan sukses pengajuan contributor tidak ditemukan"
        );
    }

    @Then("tombol ajukan permintaan tidak aktif")
    public void tombolAjukanPermintaanTidakAktif() {

        assertTrue(contributorPage().isSubmitButtonDisabled(), "Tombol submit seharusnya tidak aktif");
    }
}