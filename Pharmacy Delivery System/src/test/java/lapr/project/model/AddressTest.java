package lapr.project.model;

import lapr.project.data.AddressDB;
import lapr.project.data.DataHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Address test.
 */
class AddressTest {

    /**
     * The Address.
     */
    private final Address add;

    /**
     * Instantiates a new Address test.
     */
    public AddressTest() {
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

        this.add = new Address("114.412,159.231", "Rua do Isep", "4450-789", "24", "Porto", "19");
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Address save() Test");
        Address address = new Address("0404,0404", "Rua ", "4460-133", 122, "São Pedro", 20);
        new AddressDB().removeAddress(address.getGPSCoordinates());
        int size = new AddressDB().getAllAddresses().size();
        address.save();
        Assertions.assertEquals(size + 1, new AddressDB().getAllAddresses().size());
        try {
            address.save();
        } catch (IllegalArgumentException ie) {
            Assertions.assertEquals(size + 1, new AddressDB().getAllAddresses().size());
        }
        new AddressDB().removeAddress(address.getGPSCoordinates());
    }

    /**
     * Gets door number.
     */
    @Test
    void getDoorNumber() {
        System.out.println("Address getDoorNumber() Test");
        Assertions.assertEquals(24, this.add.getDoorNumber());
    }

    /**
     * Gets locality.
     */
    @Test
    void getLocality() {
        System.out.println("Address getLocality() Test");
        Assertions.assertEquals("Porto", this.add.getLocality());
    }

    /**
     * Gets gps coordinates.
     */
    @Test
    void getGPSCoordinates() {
        System.out.println("Address getGPSCoordinates() Test");
        Assertions.assertEquals("114.412,159.231", this.add.getGPSCoordinates());
    }

    /**
     * Gets street.
     */
    @Test
    void getStreet() {
        System.out.println("Address getStreet() Test");
        Assertions.assertEquals("Rua do Isep", this.add.getStreet());
    }

    /**
     * Gets postal code.
     */
    @Test
    void getPostalCode() {
        System.out.println("Address getPostalCode() Test");
        Assertions.assertEquals("4450-789", this.add.getPostalCode());
    }

    /**
     * Sets door number.
     */
    @Test
    void setDoorNumber() {
        System.out.println("Address setDoorNumber() Test");
        this.add.setDoorNumber("36");
        Assertions.assertEquals(36, this.add.getDoorNumber());
        try {
            this.add.setDoorNumber(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(36, this.add.getDoorNumber());
        }
        try {
            this.add.setDoorNumber("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(36, this.add.getDoorNumber());
        }
        try {
            this.add.setDoorNumber("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(36, this.add.getDoorNumber());
        }
        try {
            this.add.setDoorNumber("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(36, this.add.getDoorNumber());
        }
        try {
            this.add.setDoorNumber("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(36, this.add.getDoorNumber());
        }
    }

    /**
     * Sets postal code.
     */
    @Test
    void setPostalCode() {
        System.out.println("Address setPostalCode() Test");
        this.add.setPostalCode("4490-789");
        Assertions.assertEquals("4490-789", this.add.getPostalCode());
        try {
            this.add.setPostalCode(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("4490-789", this.add.getPostalCode());
        }
        try {
            this.add.setPostalCode("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("4490-789", this.add.getPostalCode());
        }
        try {
            this.add.setPostalCode("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("4490-789", this.add.getPostalCode());
        }
        try {
            this.add.setPostalCode("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("4490-789", this.add.getPostalCode());
        }
        try {
            this.add.setPostalCode("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("4490-789", this.add.getPostalCode());
        }
    }

    /**
     * Sets gps coordinates.
     */
    @Test
    void setGpsCoordinates() {
        System.out.println("Address setGpsCoordinates() Test");
        this.add.setGpsCoordinates("41.15926217793073, -8.630302754195354");
        Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        try {
            this.add.setGpsCoordinates(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
        try {
            this.add.setGpsCoordinates("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
        try {
            this.add.setGpsCoordinates("abc,efg");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
        try {
            this.add.setGpsCoordinates("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
        try {
            this.add.setGpsCoordinates("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
        try {
            this.add.setGpsCoordinates("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("41.15926217793073, -8.630302754195354", this.add.getGPSCoordinates());
        }
    }

    /**
     * Sets elevation.
     */
    @Test
    void setElevation() {
        System.out.println("Address setElevation() Test");
        this.add.setElevation("120");
        Assertions.assertEquals(120, this.add.getElevation());
        try {
            this.add.setElevation(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(120, this.add.getElevation());
        }
        try {
            this.add.setElevation("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(120, this.add.getElevation());
        }
        try {
            this.add.setElevation("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(120, this.add.getElevation());
        }
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        System.out.println("Address toString() Test");
        String s = "Address{" +
                "GPSCoordinates='" + add.getGPSCoordinates() + '\'' +
                ", street='" + add.getStreet() + '\'' +
                ", postalCode='" + add.getPostalCode() + '\'' +
                ", doorNumber=" + add.getDoorNumber() +
                ", locality='" + add.getLocality() + '\'' +
                '}';
        Assertions.assertEquals(s, this.add.toString());
    }
}