package lapr.project.model;

import lapr.project.data.PharmacyDB;

import java.sql.SQLException;


/**
 * The type Pharmacy.
 */
public class Pharmacy {

    /**
     * The Pharmacy's email.
     */
    private String email;
    /**
     * The Pharmacy's address.
     */
    private final Address address;
    /**
     * The Pharmacy's designation.
     */
    private String designation;

    /**
     * Instantiates a new Pharmacy.
     *
     * @param email       the email
     * @param address     the address
     * @param designation the designation
     */
    public Pharmacy(String email, Address address, String designation) {
        this.setEmail(email);
        this.address = address;
        this.designation = designation;
    }

    /**
     * Gets pharmacy.
     *
     * @param email the email
     * @return the pharmacy
     */
    public Pharmacy getPharmacy(String email) {
        return new PharmacyDB().getPharmacy(email);
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    public void save() throws SQLException {
        try {
            getPharmacy(this.getEmail());
            throw new IllegalStateException();
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new PharmacyDB().addPharmacy(this);
        } catch (IllegalStateException is) {
            throw new IllegalArgumentException("\nThe specified email is already associated to one Pharmacy!");
        }
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Email!");
        }
        if (email.contains("@") && (email.contains(".pt") || email.contains(".com"))) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email with wrong format!");
        }
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets designation.
     *
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Sets designation.
     *
     * @param designation the designation
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Textual Description of Pharmacy.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return "Pharmacy{" +
                "email='" + email + '\'' +
                ", address=" + address +
                ", designation='" + designation + '\'' +
                '}';
    }
}

