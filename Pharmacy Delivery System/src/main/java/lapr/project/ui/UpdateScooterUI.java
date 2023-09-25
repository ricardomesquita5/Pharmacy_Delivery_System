package lapr.project.ui;

import lapr.project.controller.UpdateScooterController;
import lapr.project.model.Pharmacy;
import lapr.project.model.Scooter;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update Scooter UI.
 */
public class UpdateScooterUI {

    /**
     * The controller.
     */
    private final UpdateScooterController controller;

    /**
     * Instantiates a new Update Scooter UI.
     */
    public UpdateScooterUI() {
        this.controller = new UpdateScooterController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Update Scooter #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you want update this scooter? (Y/N)")) {
                    if (this.controller.updateScooter()) {
                        System.out.println("\nUpdate with Success.");
                    } else {
                        System.out.println("It was not possible to complete the update successfully.");
                    }
                }
            } else {
                System.out.println("An error has occurred. Operation canceled.");
            }
        } catch (IllegalArgumentException | SQLException ia) {
            System.out.println("\n" + ia.getMessage());
        }
    }

    /**
     * Enter data.
     *
     * @throws SQLException the sql exception
     * @return the Scooter
     */

    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to update a scooter to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                List<Scooter> scootersList = this.controller.getScootersList();
                if (scootersList.isEmpty()) {
                    throw new IllegalArgumentException("ERROR: No Scooters associated with the Pharmacy in the system!");
                } else {
                    Scooter scooter = (Scooter) Utils.showsAndSelect(scootersList, "\nSelect the Scooter you want update:");
                    if (scooter != null) {
                        int scooterID = scooter.getId();
                        this.controller.getScooterByID(scooterID);
                        String strCapacity = Utils.readLineFromConsole("Capacity (KG): ");
                        String strPower = Utils.readLineFromConsole("Power (W): ");
                        String strMaxPower = Utils.readLineFromConsole("Max Battery (W/H): ");
                        String strWeight = Utils.readLineFromConsole("Weight (KG): ");
                        return this.controller.changeScooter(strCapacity, strPower, strMaxPower, strWeight);
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Textual Description.
     */
    private void showData() {
        System.out.println("\nScooter:\n" + this.controller.getScooterString());
    }
}
