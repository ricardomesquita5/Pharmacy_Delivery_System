package lapr.project.ui;

import lapr.project.controller.AddCourierController;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add courier ui.
 */
public class AddCourierUI {

    /**
     * the controller
     */
    private final AddCourierController controller;

    /**
     * Instantiates a new Add courier ui.
     */
    public AddCourierUI() {
        this.controller = new AddCourierController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\nRegister Courier:");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                    if (this.controller.addCourier()) {
                        System.out.println("\nAdd Successful!");
                    } else {
                        System.out.println("It was not possible to complete the registration successfully...");
                    }
                }
            } else {
                System.out.println("An error has occurred... Operation canceled!");
            }
        } catch (IllegalArgumentException | SQLException ia) {
            System.out.println(ia.getMessage());
        }
    }

    /**
     * enterData.
     *
     * @return new Courier
     * @throws SQLException the exception
     */
    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("\nERROR: No Pharmacies in the System!!!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "Select the Pharmacy you want to add a courier to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                String nif = Utils.readLineFromConsole("NIF (9 digits): ");
                String name = Utils.readLineFromConsole("Name: ");
                String email = Utils.readLineFromConsole("Email: ");
                String phoneNumber = Utils.readLineFromConsole("Phone Number (9 digits): ");
                String socialSecurityNumber = Utils.readLineFromConsole("Social Security Number (11 digits): ");
                String pwd = Utils.readLineFromConsole("Password: ");

                return this.controller.newCourier(nif, name, email, phoneNumber, socialSecurityNumber, pwd);
            } else {
                return false;
            }
        }
    }

    /**
     * showData.
     */
    private void showData() {
        System.out.println("\nPharmacy:\n" + this.controller.getCourierString());
    }
}
