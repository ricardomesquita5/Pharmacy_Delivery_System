package lapr.project.model;

import lapr.project.data.AddressDB;
import lapr.project.data.DataHandler;
import lapr.project.data.PharmacyDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Pharmacy test.
 */
class PharmacyTest {

    /**
     * The Pharmacy phar.
     */
    private final Pharmacy phar;

    /**
     * The Pharmacy pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * Instantiates a new Pharmacy test.
     */
    public PharmacyTest() {
        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initial Database Setup
        new DataHandler();

        Address add = new Address();
        this.phar = new Pharmacy("phar@gmail.com", add, "Pharmacy");
        this.address = new Address("01330145,01330101", "Rua ISEP", "4460-123", 123, "São João", 31);
        this.pharmacy = new Pharmacy("PharmacyTest@gmail.com", this.address, "Teste2");
    }

    /**
     * Gets address.
     */
    @Test
    void getAddress() {
        System.out.println("Pharmacy getAddress() Test");
        Assertions.assertEquals(new Address().getGPSCoordinates(), this.phar.getAddress().getGPSCoordinates());
    }

    /**
     * Gets designation.
     */
    @Test
    void getDesignation() {
        System.out.println("Pharmacy getDesignation() Test");
        Assertions.assertEquals("Pharmacy", this.phar.getDesignation());
    }

    /**
     * Gets email.
     */
    @Test
    void getEmail() {
        System.out.println("Pharmacy getEmail() Test");
        Assertions.assertEquals("phar@gmail.com", this.phar.getEmail());
    }

    /**
     * Sets designation.
     */
    @Test
    void setDesignation() {
        System.out.println("Pharmacy setDesignation() Test");
        this.phar.setDesignation("Pharmacy One");
        Assertions.assertEquals("Pharmacy One", this.phar.getDesignation());
    }


    /**
     * Sets email.
     */
    @Test
    void setEmail() {
        System.out.println("Pharmacy setEmail() Test");
        this.phar.setEmail("PharmacyOne@gmail.pt");
        Assertions.assertEquals("PharmacyOne@gmail.pt", this.phar.getEmail());
        this.phar.setEmail("PharmacyOne@gmail.com");
        Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        try {
            this.phar.setEmail(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        }
        try {
            this.phar.setEmail("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        }
        try {
            this.phar.setEmail("PharmacyOne@gmail");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        }
        try {
            this.phar.setEmail("PharmacyOnegmail.com");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        }
        try {
            this.phar.setEmail("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("PharmacyOne@gmail.com", this.phar.getEmail());
        }

    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        System.out.println("Pharmacy toString() Test");
        String s = "Pharmacy{" +
                "email='" + phar.getEmail() + '\'' +
                ", address=" + phar.getAddress() +
                ", designation='" + phar.getDesignation() + '\'' +
                '}';
        Assertions.assertEquals(s, this.phar.toString());
    }

    /**
     * Gets pharmacy.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmacy() throws SQLException {
        System.out.println("Pharmacy getPharmacy() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        this.address.save();
        this.pharmacy.save();
        Pharmacy pharAux = this.pharmacy.getPharmacy(this.pharmacy.getEmail());
        Assertions.assertEquals(pharAux.getEmail(), this.pharmacy.getEmail());
        try {
            this.pharmacy.getPharmacy(null);
        } catch (IllegalArgumentException iae) {
            Assertions.assertEquals(pharAux.getEmail(), this.pharmacy.getEmail());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Pharmacy save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        int size = new PharmacyDB().getAllPharmacies().size();
        this.address.save();
        this.pharmacy.save();
        Assertions.assertEquals(size + 1, new PharmacyDB().getAllPharmacies().size());
        try {
            this.pharmacy.save();
        } catch (IllegalArgumentException ie) {
            Assertions.assertEquals(size + 1, new PharmacyDB().getAllPharmacies().size());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

}