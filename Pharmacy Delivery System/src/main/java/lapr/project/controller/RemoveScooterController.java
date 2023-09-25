package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.data.ScooterDB;
import lapr.project.data.SpotDB;
import lapr.project.model.Pharmacy;
import lapr.project.model.Scooter;
import lapr.project.model.Spot;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove Scooter Controller.
 */
public class RemoveScooterController {

    /**
     * Object Scooter.
     */
    private Scooter scooter;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Remove Scooter Controller.
     */
    public RemoveScooterController() {
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
     * Gets scooters list.
     *
     * @return the scooters list
     */
    public List<Scooter> getScootersList() {
        return new ScooterDB().getAllScooters(this.pharmacy.getEmail());
    }

    /**
     * Gets scooter by id.
     *
     * @param scooterID the scooter id
     */
    public void getScooterByID(int scooterID) {
        this.scooter = new ScooterDB().getScooter(scooterID);
    }

    /**
     * Remove scooter.
     *
     * @return true if remove with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean removeScooter() throws SQLException {
        if (new ScooterDB().getAvailableScooters(this.pharmacy.getEmail()).contains(this.scooter)) {
            Spot s = new SpotDB().getSpotByScooterId(this.scooter.getId(), "Scooter");
            new SpotDB().updateSpot2(s.getId(), s.getIdPark());
            this.scooter.remove();
            return true;
        } else {
            throw new IllegalArgumentException("ERROR: The scooter you selected at this time is being used in a delivery, try again later!");
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
     * @return the scooter description
     */
    public String getScooterString() {
        return this.scooter.toString();
    }
}
