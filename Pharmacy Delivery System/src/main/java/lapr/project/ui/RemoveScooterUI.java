package lapr.project.ui;

import lapr.project.controller.RemoveScooterController;
import lapr.project.model.Pharmacy;
import lapr.project.model.Scooter;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove Scooter UI.
 */
public class RemoveScooterUI {

    /**
     * The Controller.
     */
    private final RemoveScooterController controller;

    /**
     * Instantiates a new Remove Scooter UI.
     */
    public RemoveScooterUI() {
        this.controller = new RemoveScooterController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Remove Scooter #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you want remove this scooter? (Y/N)")) {
                    if (this.controller.removeScooter()) {
                        System.out.println("\nRemove with Success.");
                    } else {
                        System.out.println("It was not possible to complete the remove successfully.");
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
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to remove a scooter to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                List<Scooter> scootersList = this.controller.getScootersList();
                if (scootersList.isEmpty()) {
                    throw new IllegalArgumentException("ERROR: No Scooters associated with the Pharmacy in the system!");
                } else {
                    Scooter scooter = (Scooter) Utils.showsAndSelect(scootersList, "\nSelect the Scooter you want remove:");
                    if (scooter != null) {
                        int scooterID = scooter.getId();
                        this.controller.getScooterByID(scooterID);
                        return true;
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
