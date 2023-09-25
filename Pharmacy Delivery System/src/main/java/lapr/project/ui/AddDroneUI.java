package lapr.project.ui;

import lapr.project.controller.AddDroneController;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add Drone UI.
 */
public class AddDroneUI {

    /**
     * The Controller.
     */
    private final AddDroneController controller;

    /**
     * Instantiates a new Add Drone UI.
     */
    public AddDroneUI() {
        this.controller = new AddDroneController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Add Drone #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                    if (this.controller.addDrone()) {
                        System.out.println("\nAdd successful.");
                    } else {
                        System.out.println("It was not possible to complete the registration successfully.");
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
     * enterData.
     *
     * @return the drone
     * @throws SQLException the exception
     */
    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to add a drone to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                String strCapacity = Utils.readLineFromConsole("Capacity (KG): ");
                String strPower = Utils.readLineFromConsole("Power (W): ");
                String strMaxPower = Utils.readLineFromConsole("Max Battery (W/H): ");
                String strWeight = Utils.readLineFromConsole("Weight (Kg): ");
                return this.controller.newDrone(strCapacity, strPower, strMaxPower, strWeight);
            } else {
                return false;
            }
        }
    }

    /**
     * Textual Description.
     */
    private void showData() {
        System.out.println("\nDrone:\n" + this.controller.getDroneString());
    }
}
