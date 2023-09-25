package lapr.project.controller;

import lapr.project.data.PharmacyDB;
import lapr.project.model.Courier;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Add courier controller.
 */
public class AddCourierController {

    /**
     * The Courier.
     */
    private Courier courier;
    /**
     * The Pharmacy.
     */
    private Pharmacy pharmacy;
    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Instantiates a new Add courier controller.
     */
    public AddCourierController() {
        //This constructor does nothing yet.
    }

    /**
     * New courier boolean.
     *
     * @param nif                  the nif
     * @param name                 the name
     * @param email                the email
     * @param phoneNumber          the phone number
     * @param socialSecurityNumber the social security number
     * @param pwd                  the pwd
     * @return the boolean
     */
    public boolean newCourier(String nif, String name, String email, String phoneNumber, String socialSecurityNumber, String pwd) {
        try {
            this.courier = new Courier(nif, name, email, phoneNumber, socialSecurityNumber, pwd);
            return true;
        } catch (IllegalArgumentException ia) {
            LOGGER.log(Level.WARNING, ia.getMessage());
            this.courier = null;
            return false;
        }
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
     * Add courier boolean.
     *
     * @return the boolean
     */
    public boolean addCourier() {
        try {
            this.courier.save(this.pharmacy.getEmail());
            return true;
        } catch (IllegalArgumentException | SQLException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
            return false;
        }
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
     * Gets pharmacy.
     *
     * @return the pharmacy
     */
    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    /**
     * Gets courier string.
     *
     * @return the courier string
     */
    public String getCourierString() {
        return this.courier.toString();
    }
}
