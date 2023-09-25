package lapr.project.ui;

import lapr.project.controller.RemoveDroneController;
import lapr.project.model.Drone;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove Drone UI.
 */
public class RemoveDroneUI {

    /**
     * The Remove Drone Controller.
     */
    private final RemoveDroneController controller;

    /**
     * Instantiates a new Remove Drone UI.
     */
    public RemoveDroneUI() {
        this.controller = new RemoveDroneController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Remove Drone #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you want remove this drone? (Y/N)")) {
                    if (this.controller.removeDrone()) {
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
     * @return the Drone
     */

    private boolean enterData() throws SQLException {
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to update a drone to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                List<Drone> dronesList = this.controller.getDronesList();
                if (dronesList.isEmpty()) {
                    throw new IllegalArgumentException("ERROR: No Drones associated with the Pharmacy in the system!");
                } else {
                    Drone drone = (Drone) Utils.showsAndSelect(dronesList, "\nSelect the Drone you want update:");
                    if (drone != null) {
                        int droneID = drone.getId();
                        this.controller.getDroneByID(droneID);
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
        System.out.println("\nDrone:\n" + this.controller.getDroneString());
    }
}
