package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.data.PharmacyDB;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;
import lapr.project.model.Spot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Add park controller.
 */
public class AddParkController {

    /**
     * Object Park.
     */
    private Park park;
    /**
     * Object Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * The spot List.
     */
    private List<Integer> list;

    /**
     * Instantiates a new AddParkController.
     */
    public AddParkController() {
        this.list = new ArrayList<>();
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
     * Get Pharmacy.
     *
     * @return the pharmacy
     */
    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }


    /**
     * New park boolean.
     *
     * @param designation   the designation
     * @param spotsCapacity the spots capacity
     * @param powerCapacity the power capacity
     * @return the boolean
     */
    public boolean newPark(String designation, String spotsCapacity, String powerCapacity) {
        try {
            this.park = new Park(designation, spotsCapacity, powerCapacity);
            return true;
        } catch (IllegalArgumentException ia) {
            this.park = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * New spots boolean.
     *
     * @param list the list
     * @return the boolean
     */
    public boolean newSpots(List<Integer> list) {
        try {
            this.list.addAll(list);
            return true;
        } catch (NullPointerException ia) {
            this.list = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Add Scooter.
     *
     * @return true if add with success or false if not
     * @throws SQLException the sql exception
     */
    public boolean addPark() throws SQLException {
        Spot spot;
        try {
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
        } catch (IllegalArgumentException | SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Get Park.
     *
     * @return the pharmacy
     */
    public Park getPark() {
        return this.park;
    }
}
