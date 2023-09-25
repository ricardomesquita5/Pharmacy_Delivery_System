package lapr.project.controller;

import lapr.project.data.CourierDB;
import lapr.project.data.PharmacyDB;
import lapr.project.data.UserDB;
import lapr.project.model.Courier;
import lapr.project.model.Pharmacy;
import lapr.project.utils.authorization.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove courier controller.
 */
public class RemoveCourierController {

    /**
     * The Courier.
     */
    private Courier courier;
    /**
     * The User.
     */
    private User user;

    /**
     * Instantiates a new Remove courier controller.
     */
    public RemoveCourierController() {
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
        new PharmacyDB().getPharmacy(pharmacyEmail);
    }

    /**
     * Gets email pharmacy.
     *
     * @param phar the phar
     * @return the email pharmacy
     */
    public String getEmailPharmacy(Pharmacy phar) {
        return phar.getEmail();
    }

    /**
     * Gets email courier.
     *
     * @param cour the cour
     * @return the email courier
     */
    public String getEmailCourier(Courier cour) {
        return cour.getEmail();
    }

    /**
     * Gets courier list.
     *
     * @param pharEmail the phar email
     * @return the courier list
     * @throws SQLException the sql exception
     */
    public List<Courier> getCourierList(String pharEmail) throws SQLException {
        return new CourierDB().getAllCouriersOfPharmacy(pharEmail);
    }


    /**
     * Gets courier.
     *
     * @param courierEmail the courier email
     */
    public void getCourier(String courierEmail) {
        this.courier = new CourierDB().getCourier(courierEmail);
    }

    /**
     * Remove courier boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean removeCourier() throws SQLException {
        this.courier.remove();
        return true;
    }

    /**
     * Gets user.
     *
     * @param emailUser the email user
     */
    public void getUser(String emailUser) {
        this.user = new UserDB().getUser(emailUser);
    }

    /**
     * Remove user boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean removeUser() throws SQLException {
        this.user.remove();
        return true;
    }

    /**
     * Gets pharmacy.
     *
     * @param pharmacyEmail the pharmacy email
     * @return the pharmacy
     * @throws SQLException the sql exception
     */
    public Pharmacy getPharmacy(String pharmacyEmail) throws SQLException {
        return new PharmacyDB().getPharmacy(pharmacyEmail);
    }

    /**
     * Gets courier string.
     *
     * @return the courier string
     */
    public String getCourierString() {
        return this.courier.toString();
    }

    /**
     * Gets user string.
     *
     * @return the user string
     */
    public String getUserString() {
        return this.user.toString();
    }
}

