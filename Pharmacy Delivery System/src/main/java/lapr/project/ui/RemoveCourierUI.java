package lapr.project.ui;

import lapr.project.controller.RemoveCourierController;
import lapr.project.model.Courier;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove courier ui.
 */
public class RemoveCourierUI {

    /**
     * The Controller.
     */
    private final RemoveCourierController controller;

    /**
     * Instantiates a new Remove courier ui.
     */
    public RemoveCourierUI() {
        this.controller = new RemoveCourierController();
    }

    /**
     * Run.
     *
     * @throws SQLException the sql exception
     */
    public void run() throws SQLException {
        System.out.println("\nRemove Courier:");

        if (enterData()) {
            showData();

            if (Utils.confirm("Do you want to remove this courier? (Y/N)")) {
                if (this.controller.removeCourier() && this.controller.removeUser()) {
                    System.out.println("Removed with Success.");
                } else {
                    System.out.println("It was not possible to complete the remove successfully.");
                }
            }
        } else {
            System.out.println("An error has occurred. Operation canceled.");
        }
    }

    /**
     * Enter data.
     *
     * @throws SQLException the sql exception
     * @return the Courier
     */

    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            System.out.println("ERROR: No Pharmacies in the system!");
            return false;
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "Select the Pharmacy you want to remove a Courier of:");
            if (phar != null) {
                String pharEmail = this.controller.getEmailPharmacy(phar);
                List<Courier> courierList = this.controller.getCourierList(pharEmail);

                if (courierList.isEmpty()) {
                    System.out.println("ERROR: No Couriers associated with the Pharmacie!");
                    return false;
                } else {
                    String emailCourier;
                    Courier courier = (Courier) Utils.showsAndSelect(courierList, "Select the Courier you want to remove:");
                    if (courier != null && !courier.getStatus().equals("Unavailable")) {
                        emailCourier = this.controller.getEmailCourier(courier);
                        this.controller.getCourier(emailCourier);
                        this.controller.getUser(emailCourier);
                        return true;
                    } else {
                        assert courier != null;
                        System.out.println("The Courier with the following data is currently on deliver " + courier.toString());
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Show Data.
     */
    private void showData() {
        System.out.println("Courier:\n" + this.controller.getCourierString());
    }
}
