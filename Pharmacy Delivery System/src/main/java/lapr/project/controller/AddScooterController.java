package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.data.ScooterDB;
import lapr.project.data.SpotDB;
import lapr.project.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add Scooter Controller.
 */
public class AddScooterController {

    /**
     * Object Scooter.
     */
    private Scooter scooter;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Add Scooter Controller.
     */
    public AddScooterController() {
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
     * New scooter.
     *
     * @param strCapacity the capacity
     * @param strPower    the power
     * @param strMaxPower the max power
     * @param strWeight   the str weight
     * @return true when creation without errors or false if creation have errors
     */
    public boolean newScooter(String strCapacity, String strPower, String strMaxPower, String strWeight) {
        try {
            this.scooter = new Scooter(strCapacity, strPower, strMaxPower, strWeight);
            return true;
        } catch (IllegalArgumentException ia) {
            this.scooter = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Add Scooter.
     *
     * @return true if add with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean addScooter() throws SQLException {
        if (new ScooterDB().getAmountOfScooters(this.pharmacy.getEmail()) < (new PharmacyDB().getParkScooterLimit(this.pharmacy.getEmail()))) {
            this.scooter.save(this.pharmacy.getEmail());
            List<Scooter> listScooter = new ScooterDB().getAllScooters(this.pharmacy.getEmail());
            Spot s = (new SpotDB().getAvailableSpots(this.pharmacy.getEmail(), "Scooter")).get(0);
            new SpotDB().updateSpot(s.getId(), s.getIdPark(), getLastScooter(listScooter));
            return true;
        } else {
            this.scooter = null;
            throw new IllegalArgumentException("ERROR: Park Limit Reached!");
        }
    }

    /**
     * Gets Last Scooter.
     *
     * @param listScooter list of Scooters
     * @return the id of the last Scooter
     */
    public int getLastScooter(List<Scooter> listScooter) {
        int id = listScooter.get(0).getId();
        for (Scooter s : listScooter) {
            if (s.getId() > id) {
                id = s.getId();
            }
        }
        return id;
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
     * @return the scooter description
     */
    public String getScooterString() {
        return this.scooter.toString2();
    }
}
