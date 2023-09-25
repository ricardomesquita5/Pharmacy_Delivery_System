package lapr.project.model;

import lapr.project.data.CourierDB;
import lapr.project.utils.authorization.model.User;

import java.sql.SQLException;

/**
 * The type Courier.
 */
public class Courier extends User {

    /**
     * The nif.
     */
    private int nif;

    /**
     * The phoneNumber.
     */
    private int phoneNumber;

    /**
     * The socialSecurityNumber.
     */
    private double socialSecurityNumber;

    /**
     * The status.
     */
    private String status;

    /**
     * Instantiates a new Courier.
     *
     * @param nif                  the nif
     * @param name                 the name
     * @param email                the email
     * @param phoneNumber          the phone number
     * @param socialSecurityNumber the social security number
     * @param status               the status
     * @param pwd                  the pwd
     */
    public Courier(int nif, String name, String email, int phoneNumber, double socialSecurityNumber, String status, String pwd) {
        super(name, email, pwd);
        this.nif = nif;
        this.phoneNumber = phoneNumber;
        this.socialSecurityNumber = socialSecurityNumber;
        this.status = status;
    }

    /**
     * Instantiates a new Courier.
     *
     * @param nif                  the nif
     * @param name                 the name
     * @param email                the email
     * @param phoneNumber          the phone number
     * @param socialSecurityNumber the social security number
     * @param pwd                  the pwd
     */
    public Courier(String nif, String name, String email, String phoneNumber, String socialSecurityNumber, String pwd) {
        super(name, email, pwd);
        this.setNif(nif);
        this.setPhoneNumber(phoneNumber);
        this.setSocialSecurityNumber(socialSecurityNumber);
        this.status = "Available";
    }

    /**
     * Instantiates a new Courier.
     *
     * @param nif         the nif
     * @param name        the name
     * @param email       the email
     * @param phoneNumber the phone number
     * @param status      the status
     * @param pwd         the pwd
     */
    public Courier(int nif, String name, String email, int phoneNumber, String status, String pwd) {
        super(name, email, pwd);
        this.nif = nif;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    /**
     * Gets courier.
     *
     * @param email the email
     */
    public void getCourier(String email) {
        new CourierDB().getCourier(email);
    }

    /**
     * Save.
     *
     * @param pharEmail the phar email
     * @throws SQLException the sql exception
     */
    public void save(String pharEmail) throws SQLException {
        try {
            getCourier(this.getEmail());
            throw new IllegalStateException();
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new CourierDB().addCourier(this, pharEmail);
        } catch (IllegalStateException is) {
            throw new IllegalArgumentException("\nThe specified email is already associated to one Courier!");
        }
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        String message = "Invalid Phone Number!";
        if (phoneNumber != null && !phoneNumber.trim().isEmpty() && phoneNumber.length() == 9) {
            try {
                if (Integer.parseInt(phoneNumber) > 0) {
                    this.phoneNumber = Integer.parseInt(phoneNumber);
                } else {
                    throw new IllegalArgumentException(message);
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sets social security number.
     *
     * @param socialSecurityNumber the social security number
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        String message = "Invalid Social Security Number!";
        if (socialSecurityNumber != null && !socialSecurityNumber.trim().isEmpty() && socialSecurityNumber.length() == 11) {
            try {
                if (Double.parseDouble(socialSecurityNumber) > 0) {
                    this.socialSecurityNumber = Double.parseDouble(socialSecurityNumber);
                } else {
                    throw new IllegalArgumentException(message);
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sets nif.
     *
     * @param nif the nif
     */
    public void setNif(String nif) {
        String message = "Invalid NIF!";
        if (nif != null && !nif.trim().isEmpty() && nif.length() == 9) {
            try {
                if (Integer.parseInt(nif) > 0) {
                    this.nif = Integer.parseInt(nif);
                } else {
                    throw new IllegalArgumentException(message);
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Update.
     */
    public void update() {

        new CourierDB().updateCourier(this);
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets social security number.
     *
     * @return the social security number
     */
    public double getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * Gets nif.
     *
     * @return the nif
     */
    public int getNif() {
        return nif;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * ToString method.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Courier{" +
                super.toString() +
                ", nif=" + nif +
                ", phoneNumber=" + phoneNumber +
                ", socialSecurityNumber=" + socialSecurityNumber +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Remove.
     *
     * @throws SQLException SQLException
     */
    @Override
    public void remove() throws SQLException {
        new CourierDB().removeCourier(this.getEmail());
    }
}

