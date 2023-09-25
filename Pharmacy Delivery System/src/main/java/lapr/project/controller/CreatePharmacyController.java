package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Create pharmacy controller.
 */
public class CreatePharmacyController {

    /**
     * The Address.
     */
    private Address address;
    /**
     * The Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * Object Park.
     */
    private Park park;

    /**
     * The List.
     */
    private final List<Integer> list = new ArrayList<>();

    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Instantiates a new Create pharmacy controller.
     */
    public CreatePharmacyController() {
        //does nothing yet
    }

    /**
     * New pharmacy boolean.
     *
     * @param designation     the designation
     * @param email           the email
     * @param street          the street
     * @param postalCode      the postal code
     * @param number          the number
     * @param locality        the locality
     * @param gpsCoordinates  the gps coordinates
     * @param elevation       the elevation
     * @param designationPark the designation park
     * @param spotsCapacity   the spots capacity
     * @param powerCapacity   the power capacity
     * @param list            the list
     * @return the boolean
     */
    public boolean newPharmacy(String designation, String email, String street,
                               String postalCode, String number, String locality, String gpsCoordinates,
                               String elevation, String designationPark, String spotsCapacity,
                               String powerCapacity, List<Integer> list) {
        try {
            this.address = new Address(gpsCoordinates, street, postalCode, number, locality, elevation);
            this.pharmacy = new Pharmacy(email, this.address, designation);
            this.park = new Park(designationPark, spotsCapacity, powerCapacity);
            this.list.addAll(list);
            return true;
        } catch (IllegalArgumentException ia) {
            LOGGER.log(Level.WARNING, ia.getMessage());
            this.pharmacy = null;
            return false;
        }
    }

    /**
     * Add pharmacy boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean addPharmacy() throws SQLException {
        Spot spot;
        try {
            this.address.save();
            this.pharmacy.save();
            this.park.save(this.pharmacy.getEmail());
            int id = new ParkDB().getLastPark(this.pharmacy.getEmail());
            for (int i = 1; i <= this.park.getSpotsCapacity(); i++) {
                if (this.list.contains(i)) {
                    spot = new Spot(i, "no");
                } else {
                    spot = new Spot(i, "yes");
                }
                spot.save(id);
            }
            return true;
        } catch (IllegalArgumentException | SQLIntegrityConstraintViolationException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Gets pharmacy string.
     *
     * @return the pharmacy string
     */
    public String getPharmacyString() {
        return this.pharmacy.toString();
    }

}
