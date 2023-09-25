package lapr.project.ui;

import lapr.project.controller.UpdateDroneController;
import lapr.project.model.Pharmacy;
import lapr.project.model.Drone;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update Drone UI.
 */
public class UpdateDroneUI {

    /**
     * The Controller.
     */
    private final UpdateDroneController controller;

    /**
     * Instantiates a new Update Drone UI.
     */
    public UpdateDroneUI() {
        this.controller = new UpdateDroneController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Update Drone #");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you want update this drone? (Y/N)")) {
                    if (this.controller.updateDrone()) {
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
                        String strCapacity = Utils.readLineFromConsole("Capacity (KG): ");
                        String strPower = Utils.readLineFromConsole("Power (W): ");
                        String strMaxPower = Utils.readLineFromConsole("Max Battery (W/H): ");
                        String strWeight = Utils.readLineFromConsole("Weight (Kg): ");
                        return this.controller.changeDrone(strCapacity, strPower, strMaxPower, strWeight);
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
