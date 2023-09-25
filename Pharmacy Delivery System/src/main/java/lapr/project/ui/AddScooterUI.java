package lapr.project.ui;

import lapr.project.controller.AddScooterController;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add scooter UI.
 */
public class AddScooterUI {

    /**
     * The Controller.
     */
    private final AddScooterController controller;

    /**
     * Instantiates a new Add Scooter UI.
     */
    public AddScooterUI() {
        this.controller = new AddScooterController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Add Scooter #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                    if (this.controller.addScooter()) {
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
     * @return new Scooter
     * @throws SQLException the exception
     */
    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to add a scooter to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                String strCapacity = Utils.readLineFromConsole("Capacity (KG): ");
                String strPower = Utils.readLineFromConsole("Power (W): ");
                String strMaxPower = Utils.readLineFromConsole("Max Battery (W/H): ");
                String strWeight = Utils.readLineFromConsole("Weight (KG): ");
                return this.controller.newScooter(strCapacity, strPower, strMaxPower, strWeight);
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
