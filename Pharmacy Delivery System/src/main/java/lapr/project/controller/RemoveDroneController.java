package lapr.project.controller;

import lapr.project.data.DroneDB;
import lapr.project.data.PharmacyDB;
import lapr.project.data.SpotDB;
import lapr.project.model.Drone;
import lapr.project.model.Pharmacy;
import lapr.project.model.Spot;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove Drone Controller.
 */
public class RemoveDroneController {

    /**
     * Object Drone.
     */
    private Drone drone;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Remove Drone Controller.
     */
    public RemoveDroneController() {
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
     * Gets Drone list.
     *
     * @return the Drone list
     */
    public List<Drone> getDronesList() {
        return new DroneDB().getAllDrones(this.pharmacy.getEmail());
    }

    /**
     * Gets Drone by id.
     *
     * @param droneID the Drone id
     */
    public void getDroneByID(int droneID) {
        this.drone = new DroneDB().getDrone(droneID);
    }

    /**
     * Remove Drone.
     *
     * @return true if remove with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean removeDrone() throws SQLException {
        if (new DroneDB().getAvailableDrones(this.pharmacy.getEmail()).contains(this.drone)) {
            Spot s = new SpotDB().getSpotByDroneId(this.drone.getId(), "Drone");
            new SpotDB().updateSpot2(s.getId(), s.getIdPark());
            this.drone.remove();
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
     * @return the Drone description
     */
    public String getDroneString() {
        return this.drone.toString();
    }
}
