package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.data.DroneDB;
import lapr.project.model.Pharmacy;
import lapr.project.model.Drone;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update Drone Controller.
 */
public class UpdateDroneController {

    /**
     * Object Drone.
     */
    private Drone drone;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Update Drone Controller.
     */
    public UpdateDroneController() {
        //does nothing yet
    }

    /**
     * Gets pharmacies list.
     *
     * @return the pharmacies list
     * @throws SQLException the sql exception
     */
    public List<Pharmacy> getPharmaciesList() throws SQLException {
        return new PharmacyDB().getAllPharmacies();
    }

    /**
     * Gets pharmacy by email.
     *
     * @param pharmacyEmail the pharmacy email
     * @throws SQLException the sql exception
     */
    public void getPharmacyByEmail(String pharmacyEmail) throws SQLException {
        this.pharmacy = new PharmacyDB().getPharmacy(pharmacyEmail);
    }

    /**
     * Gets drones list.
     *
     * @return the drones list
     */
    public List<Drone> getDronesList() {
        return new DroneDB().getAllDrones(this.pharmacy.getEmail());
    }

    /**
     * Gets drone by id.
     *
     * @param droneID the drone id
     */
    public void getDroneByID(int droneID) {
        this.drone = new DroneDB().getDrone(droneID);
    }

    /**
     * Change drone.
     *
     * @param strCapacity the capacity
     * @param strPower    the power
     * @param strMaxPower the max power
     * @param strWeight   the str weight
     * @return true if change with success or false if not
     */
    public boolean changeDrone(String strCapacity, String strPower, String strMaxPower, String strWeight) {
        try {
            this.drone.setCapacity(strCapacity);
            this.drone.setPower(strPower);
            this.drone.setMaxBattery(strMaxPower);
            this.drone.setWeight(strWeight);
            return true;
        } catch (IllegalArgumentException ia) {
            this.drone = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update drone.
     *
     * @return true if update with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean updateDrone() throws SQLException {
        if (new DroneDB().getAvailableDrones(this.pharmacy.getEmail()).contains(this.drone)) {
            this.drone.update();
            return true;
        } else {
            throw new IllegalArgumentException("ERROR: The drone you selected at this time is being used in a delivery, try again later!");
        }
    }

    /**
     * Get Pharmacy.
     *
     * @return the pharmacy
     */
    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    /**
     * Textual Description.
     *
     * @return the drone description
     */
    public String getDroneString() {
        return this.drone.toString();
    }
}
