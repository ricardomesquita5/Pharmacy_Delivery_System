package lapr.project.controller;

import lapr.project.model.Address;
import lapr.project.model.Client;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type User register controller.
 */
public class UserRegisterController {

    /**
     * The Address.
     */
    private Address address;
    /**
     * The Client.
     */
    private Client client;
    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Instantiates a new User register controller.
     */
    public UserRegisterController() {
        //does nothing yet
    }

    /**
     * New client boolean.
     *
     * @param name             the name
     * @param email            the email
     * @param password         the password
     * @param street           the street
     * @param postalCode       the postal code
     * @param number           the number
     * @param locality         the locality
     * @param gpsCoordinates   the gps coordinates
     * @param elevation        the elevation
     * @param creditCardNumber the credit card number
     * @param validityDate     the validity date
     * @param ccv              the ccv
     * @return the boolean
     */
    public boolean newClient(String name, String email, String password, String street,
                             String postalCode, String number, String locality,
                             String gpsCoordinates, String elevation, String creditCardNumber,
                             String validityDate, String ccv) {
        try {
            this.address = new Address(gpsCoordinates, street, postalCode, number, locality, elevation);
            this.client = new Client(name, email, password, this.address, creditCardNumber, validityDate, ccv);
            return true;
        } catch (IllegalArgumentException ia) {
            LOGGER.log(Level.WARNING, ia.getMessage());
            this.client = null;
            return false;
        }
    }

    /**
     * Add client boolean.
     *
     * @return the boolean
     */
    public boolean addClient() {
        try {
            this.address.save2();
            this.client.save();
            return true;
        } catch (IllegalArgumentException | SQLException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
            return false;
        }
    }

    /**
     * Gets client string.
     *
     * @return the client string
     */
    public String getClientString() {
        return this.client.toString();
    }
}
