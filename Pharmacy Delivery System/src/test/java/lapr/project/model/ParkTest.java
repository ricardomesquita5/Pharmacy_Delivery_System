package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Park test.
 */
class ParkTest {

    /**
     * The Park p.
     */
    private final Park p;

    /**
     * The Park park.
     */
    private final Park park;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * Instantiates a new Park test.
     */
    public ParkTest() {
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

        this.address = new Address("123.334,124.543", "Rua Tres", "4460-111", 13, "Porto", 30);
        this.pharmacy = new Pharmacy("testePark@gmail.com", this.address, "Park Teste");

        this.park = new Park(1,"Desig",5,300);
        this.p = new Park("Designation","10","220");
    }

    /**
     * Sets power capacity.
     */
    @Test
    void setPowerCapacity() {
        System.out.println("Park setPowerCapacity() Test");
        this.p.setPowerCapacity("230");
        Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        try {
            this.p.setPowerCapacity(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        }
        try {
            this.p.setPowerCapacity("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        }
        try {
            this.p.setPowerCapacity("-1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        }
        try {
            this.p.setPowerCapacity("0");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        }
        try {
            this.p.setPowerCapacity("abc");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(230.0, this.p.getPowerCapacity(), 0);
        }
    }

    /**
     * Sets spots capacity.
     */
    @Test
    void setSpotsCapacity() {
        System.out.println("Park setSpotsCapacity() Test");
        this.p.setSpotsCapacity("23");
        Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        try {
            this.p.setSpotsCapacity(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        }
        try {
            this.p.setSpotsCapacity("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        }
        try {
            this.p.setSpotsCapacity("-1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        }
        try {
            this.p.setSpotsCapacity("0");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        }
        try {
            this.p.setSpotsCapacity("abc");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(23.0, this.p.getSpotsCapacity(), 0);
        }
    }

    /**
     * Gets id.
     */
    @Test
    void getId() {
        System.out.println("Park getId() Test");
        Assertions.assertEquals(1, this.park.getId());
    }

    /**
     * Gets designation.
     */
    @Test
    void getDesignation() {
        System.out.println("Park getDesignation() Test");
        Assertions.assertEquals("Designation", this.p.getDesignation());
    }

    /**
     * Gets spots capacity.
     */
    @Test
    void getSpotsCapacity() {
        System.out.println("Park getSpotsCapacity() Test");
        Assertions.assertEquals(10, this.p.getSpotsCapacity());
    }

    /**
     * Gets power capacity.
     */
    @Test
    void getPowerCapacity() {
        System.out.println("Park getPowerCapacity() Test");
        Assertions.assertEquals(220.0, this.p.getPowerCapacity());
    }

    /**
     * Get charging scooters.
     */
    @Test
    void getChargingScooters() {
        System.out.println("Park getChargingScooters() Test");
        Assertions.assertEquals(0, this.park.getChargingScooters());
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Park save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new ParkDB().getAllParks(this.pharmacy.getEmail()).size();
        this.park.save(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, new ParkDB().getAllParks(this.pharmacy.getEmail()).size());
        new ParkDB().removePark(new ParkDB().getLastPark(this.pharmacy.getEmail()));
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        System.out.println("Park toString() Test");
        String pk="Park{" +
                "designation='" + p.getDesignation() + '\'' +
                ", spotsCapacity=" + p.getSpotsCapacity() +
                ", powerCapacity=" + p.getPowerCapacity() +
                '}';
        Assertions.assertEquals(pk,this.p.toString());
    }
}