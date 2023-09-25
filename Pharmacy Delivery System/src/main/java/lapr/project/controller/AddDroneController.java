package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.data.DroneDB;
import lapr.project.data.SpotDB;
import lapr.project.model.Pharmacy;
import lapr.project.model.Drone;
import lapr.project.model.Spot;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add Product Controller.
 */
public class AddDroneController {

    /**
     * Object Drone.
     */
    private Drone drone;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Add Drone Controller.
     */
    public AddDroneController() {
        //does nothing yet
    }

    /**
     * Gets Pharmacies List.
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
     * New drone.
     *
     * @param strCapacity the capacity
     * @param strPower    the power
     * @param strMaxPower the max power
     * @param strWeight   the str weight
     * @return true when creation without errors or false if creation have errors
     */
    public boolean newDrone(String strCapacity, String strPower, String strMaxPower, String strWeight) {
        try {
            this.drone = new Drone(strCapacity, strPower, strMaxPower, strWeight);
            return true;
        } catch (IllegalArgumentException ia) {
            this.drone = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Add Drone.
     *
     * @return true if add with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean addDrone() throws SQLException {
        if (new DroneDB().getAmountOfDrones(this.pharmacy.getEmail()) < (new PharmacyDB().getParkDroneLimit(this.pharmacy.getEmail()))) {
            this.drone.save(this.pharmacy.getEmail());
            Spot s = (new SpotDB().getAvailableSpots(this.pharmacy.getEmail(), "Drone")).get(0);
            new SpotDB().updateSpot3(s.getId(), s.getIdPark(), new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
            return true;
        } else {
            this.drone = null;
            throw new IllegalArgumentException("ERROR: Park Limit Reached!");
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
        return this.drone.toString2();
    }
}
