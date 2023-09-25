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
 * The type Spot test.
 */
class SpotTest {

    /**
     * The Spot.
     */
    private Spot spot;
    /**
     * The Park.
     */
    private final Park park;
    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;

    /**
     * Instantiates a new Spot test.
     */
    public SpotTest() {
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

        this.address = new Address("133.334,144.543", "Rua Quato", "4460-110", 12, "Porto", 38);
        this.pharmacy = new Pharmacy("testeSpot@gmail.com", this.address, "Spot Teste");
        this.park = new Park(5,"Desig",10,350);
        this.spot = new Spot(1,"no");
        this.spot = new Spot(1, "no", 5);
    }

    /**
     * Gets id.
     */
    @Test
    void getId() {
        System.out.println("Spot getId() Test");
        Assertions.assertEquals(1, this.spot.getId());
    }

    /**
     * Gets id park.
     */
    @Test
    void getIdPark() {
        System.out.println("Spot getIdPark() Test");
        Assertions.assertEquals(5, this.spot.getIdPark());
    }

    /**
     * Gets charging spot.
     */
    @Test
    void getChargingSpot() {
        System.out.println("Spot getChargingSpot() Test");
        Assertions.assertEquals("no", this.spot.getChargingSpot());
    }


    /**
     * Sets id.
     */
    @Test
    void setId() {
        System.out.println("Spot setId() Test");
        this.spot.setId("8");
        Assertions.assertEquals(8, this.spot.getId());
        try {
            this.spot.setId(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(8, this.spot.getId());
        }
        try {
            this.spot.setId("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(8, this.spot.getId());
        }
        try {
            this.spot.setId("abcde");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(8, this.spot.getId());
        }
    }

    /**
     * Sets charging spot.
     */
    @Test
    void setChargingSpot() {
        System.out.println("Spot setChargingSpot() Test");
        this.spot.setChargingSpot("no");
        Assertions.assertEquals("no", this.spot.getChargingSpot());
    }


    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Spot save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ParkDB().addPark(this.park,this.pharmacy.getEmail());
        int idLastPark = new ParkDB().getLastPark(this.pharmacy.getEmail());
        int size = new SpotDB().getAllSpots(idLastPark).size();
        this.spot.save(idLastPark);
        Assertions.assertEquals(size + 1, new SpotDB().getAllSpots(idLastPark).size());
        new SpotDB().removeSpot(this.spot.getId(),idLastPark);
        new ParkDB().removePark(idLastPark);
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * To string test.
     */
    @Test
    void toStringTest() {
        System.out.println("Spot toString() Test");
        String s = String.format("ID: %d - Charging Spot: %s - Id Park: %d ", 1,"no",5);
        Assertions.assertEquals(s, this.spot.toString());
    }
}