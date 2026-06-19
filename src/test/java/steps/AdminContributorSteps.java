package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.AdminContributorPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminContributorSteps {

    private AdminContributorPage adminPage() {
        return new AdminContributorPage(Hooks.getDriver());
    }

    @And("admin membuka halaman contributor request")
    public void adminMembukaHalamanContributorRequest() {
        adminPage().openContributorRequest();
    }

    @And("admin memilih approve")
    public void adminMemilihApprove() {
        adminPage().clickApproveRequest();
    }

    @And("admin mengisi notes {string}")
    public void adminMengisiNotes(String notes) {
        adminPage().inputNotes(notes);
    }

    @And("admin mengkonfirmasi approve")
    public void adminMengkonfirmasiApprove() {
        adminPage().confirmApprove();
    }

    @Then("request contributor berhasil disetujui")
    public void requestContributorBerhasilDisetujui() {
        assertTrue(
                adminPage().isApproveSuccess(),
                "Request contributor tidak berhasil disetujui"
        );
    }

    @And("admin memilih reject")
    public void adminMemilihReject() {
        adminPage().clickRejectRequest();
    }

    @And("admin mengisi alasan reject {string}")
    public void adminMengisiAlasanReject(String alasan) {
        adminPage().inputRejectReason(alasan);
    }

    @And("admin mengkonfirmasi reject")
    public void adminMengkonfirmasiReject() {
        adminPage().confirmReject();
    }

    @Then("request contributor berhasil ditolak")
    public void requestContributorBerhasilDitolak() {
        assertTrue(
                adminPage().isRejectSuccess(),
                "Request contributor tidak berhasil ditolak"
        );
    }

    @Then("tombol reject tidak aktif")
    public void tombolRejectTidakAktif() {
        assertTrue(
                adminPage().isRejectButtonDisabled(),
                "Tombol reject seharusnya tidak aktif"
        );
    }
}