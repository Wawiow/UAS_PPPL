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
}