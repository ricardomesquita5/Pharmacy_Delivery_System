package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Address;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;
import lapr.project.model.Spot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The type Add park controller test.
 */
public class AddParkControllerTest {

    /**
     * The Controller.
     */
    private final AddParkController controller;

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * Instantiates a new Add Park Controller Test.
     */
    public AddParkControllerTest() {
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

        this.controller = new AddParkController();

        this.address = new Address("66,66", "Rua ISEP2", "4460-124", 124, "São João2",4);
        this.pharmacy = new Pharmacy("testeParkController@gmail.com", this.address, "Pharmacy Teste2");
    }

    /**
     * Test get pharmacies list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testGetPharmaciesList() throws SQLException {
        System.out.println("AddParkController getPharmaciesList() Test");
        for (Park park:
             new ParkDB().getAllParks(this.pharmacy.getEmail())) {
            for (Spot spot:
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        int size = new PharmacyDB().getAllPharmacies().size();
        new PharmacyDB().addPharmacy(this.pharmacy);
        Assertions.assertEquals(size + 1, this.controller.getPharmaciesList().size());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Test get pharmacy by email.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testGetPharmacyByEmail() throws SQLException {
        System.out.println("AddParkController testGetPharmacyByEmail() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(this.pharmacy.toString(), this.controller.getPharmacy().toString());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * New Park Test.
     */
    @Test
    public void testNewPark() {
        System.out.println("AddParkController testNewPark() Test");
        Park park = new Park("Desig1", "2", "100");
        this.controller.newPark("Desig1", "2", "100");
        Assertions.assertEquals(park.getDesignation(), this.controller.getPark().getDesignation());
        try {
            this.controller.newPark("Desig1", "Capacity", "Capacity");
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
    }

    /**
     * New Spots Test.
     */
    @Test
    public void testNewSpots() {
        System.out.println("AddParkController testNewSpots() Test");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Assertions.assertTrue(this.controller.newSpots(list));
        try {
            list = null;
            this.controller.newSpots(list);
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
    }

    /**
     * Add Park Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testAddPark() throws SQLException {
        System.out.println("AddParkController testAddPark() Test");
        for (Park park:
                new ParkDB().getAllParks(this.pharmacy.getEmail())) {
            for (Spot spot:
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.newPark("Desig1", "2", "100");
        Assertions.assertTrue(this.controller.addPark());
        for (Park park:
                new ParkDB().getAllParks(this.pharmacy.getEmail())) {
            for (Spot spot:
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}
