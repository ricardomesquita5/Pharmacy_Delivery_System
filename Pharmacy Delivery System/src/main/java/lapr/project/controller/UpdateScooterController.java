package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.data.ScooterDB;
import lapr.project.model.Pharmacy;
import lapr.project.model.Scooter;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update Scooter Controller.
 */
public class UpdateScooterController {

    /**
     * Object Scooter.
     */
    private Scooter scooter;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Instantiates a new Update Scooter Controller.
     */
    public UpdateScooterController() {
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
     * Change scooter.
     *
     * @param strCapacity the capacity
     * @param strPower    the power
     * @param strMaxPower the max power
     * @param strWeight   the str weight
     * @return true if change with success or false if not
     */
    public boolean changeScooter(String strCapacity, String strPower, String strMaxPower, String strWeight) {
        try {
            this.scooter.setCapacity(strCapacity);
            this.scooter.setPower(strPower);
            this.scooter.setMaxBattery(strMaxPower);
            this.scooter.setWeight(strWeight);
            return true;
        } catch (IllegalArgumentException ia) {
            this.scooter = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update scooter.
     *
     * @return true if update with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean updateScooter() throws SQLException {
        if (new ScooterDB().getAvailableScooters(this.pharmacy.getEmail()).contains(this.scooter)) {
            this.scooter.update();
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
