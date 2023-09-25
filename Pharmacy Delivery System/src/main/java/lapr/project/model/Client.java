package lapr.project.model;

import lapr.project.data.ClientDB;
import lapr.project.utils.authorization.model.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * The type Client.
 */
public class Client extends User {

    /**
     * The Client.
     */
    private Address address;

    /**
     * The creditCardNumber.
     */
    private long creditCardNumber;

    /**
     * The validityDate.
     */
    private Date validityDate;

    /**
     * The ccv.
     */
    private int ccv;

    /**
     * The credits.
     */
    private int credits;

    /**
     * Instantiates a new Client.
     *
     * @param name             the name
     * @param email            the email
     * @param psw              the psw
     * @param address          the address
     * @param creditCardNumber the credit card number
     * @param validityDate     the validity date
     * @param ccv              the ccv
     */
    public Client(String name, String email, String psw, Address address, String creditCardNumber, String validityDate, String ccv) {
        super(name, email, psw);
        this.setAddress(address);
        this.setCreditCardNumber(creditCardNumber);
        this.setValidityDate(validityDate);
        this.setCcv(ccv);
        this.credits = 0;
    }

    /**
     * Instantiates a new Client.
     *
     * @param name             the name
     * @param email            the email
     * @param psw              the psw
     * @param address          the address
     * @param creditCardNumber the credit card number
     * @param validityDate     the validity date
     * @param ccv              the ccv
     */
    public Client(String name, String email, String psw, Address address, long creditCardNumber, Date validityDate, int ccv) {
        super(name, email, psw);
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.validityDate = (Date) validityDate.clone();
        this.ccv = ccv;
        this.credits = 0;
    }

    /**
     * Instantiates a new Client.
     *
     * @param name             the name
     * @param email            the email
     * @param psw              the psw
     * @param address          the address
     * @param creditCardNumber the credit card number
     * @param validityDate     the validity date
     * @param ccv              the ccv
     * @param creds            the creds
     */
    public Client(String name, String email, String psw, Address address, long creditCardNumber, Date validityDate, int ccv, int creds) {
        super(name, email, psw);
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.validityDate = (Date) validityDate.clone();
        this.ccv = ccv;
        this.credits = creds;
    }


    /**
     * Gets credit card number.
     *
     * @return the credit card number
     */
    public Long getCreditCardNumber() {
        return this.creditCardNumber;
    }

    /**
     * Gets validity date.
     *
     * @return the validity date
     */
    public Date getValidityDate() {
        return (Date) this.validityDate.clone();
    }

    /**
     * Gets ccv.
     *
     * @return the ccv
     */
    public int getCcv() {
        return this.ccv;
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
     * Gets credits.
     *
     * @return the credits
     */
    public int getCredits() {
        return this.credits;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Sets credit card number.
     *
     * @param creditCardNumber the credit card number
     */
    public void setCreditCardNumber(String creditCardNumber) {
        if (creditCardNumber != null && !creditCardNumber.trim().isEmpty() && creditCardNumber.trim().length() == 16) {
            try {
                this.creditCardNumber = Long.parseLong(creditCardNumber);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid creditCardNumber!");
            }
        } else {
            throw new IllegalArgumentException("Invalid creditCardNumber!");
        }
    }

    /**
     * Sets validity date.
     *
     * @param validityDate the validity date
     */
    public void setValidityDate(String validityDate) {
        try {
            if (validityDate != null && !validityDate.trim().isEmpty()) {

                Date d = new SimpleDateFormat("dd/MM/yyyy").parse(validityDate);
                Date date = new Date();
                if (d.after(date)) {
                    this.validityDate = d;
                } else {
                    throw new IllegalArgumentException("Validity date must be greater than today's date!");
                }
            }
        } catch (StringIndexOutOfBoundsException | ParseException sione) {
            throw new IllegalArgumentException("Invalid date!");
        }

    }

    /**
     * Sets ccv.
     *
     * @param ccv the ccv
     */
    public void setCcv(String ccv) {
        if (ccv != null && !ccv.trim().isEmpty()) {
            try {
                int aux = Integer.parseInt(ccv);
                if (aux > 99 && aux < 1000) {
                    this.ccv = aux;
                } else {
                    throw new IllegalArgumentException("Ccv must be between 99 and 1000!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Ccv!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Ccv!");
        }

    }

    /**
     * Add credits.
     *
     * @param price the price
     */
    public void addCredits(double price) {
        int credits = this.credits;
        this.credits = credits + ((int) price);
    }

    /**
     * Remove credits.
     *
     * @param creds the creds
     */
    public void removeCredits(int creds) {
        int credits = this.credits;
        this.credits = credits - creds;
    }

    /**
     * Gets client.
     *
     * @param email the email
     */
    public void getClient(String email) {
        new ClientDB().getClient(email);
    }

    /**
     * Save Client.
     *
     * @throws SQLException SQLException
     */
    @Override
    public void save() throws SQLException {
        try {
            getClient(this.getEmail());
            throw new IllegalArgumentException("\nThe specified email is already associated to a client!");
        } catch (IllegalArgumentException ex) {
            new ClientDB().addClient(this);
        }
    }

    /**
     * Remove client.
     */
    public void removeClient() {
        new ClientDB().removeClient(this.getEmail());
    }

    /**
     * Hashcode method.
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getAddress(), getCreditCardNumber(), getValidityDate(), getCcv(), getCredits());
    }

    /**
     * ToString method.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Client{" +
                "email='" + super.getEmail() + '\'' +
                ", name=" + super.getName() +
                ", password=" + super.getPassword() +
                ", address=" + address +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", validityDate=" + validityDate +
                ", ccv=" + ccv +
                ", credits=" + credits +
                '}';
    }
}
