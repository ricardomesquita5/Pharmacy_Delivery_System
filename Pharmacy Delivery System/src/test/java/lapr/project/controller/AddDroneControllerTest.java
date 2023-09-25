package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Add Drone Controller Test.
 */
public class AddDroneControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Add Drone Controller.
     */
    private final AddDroneController controller;

    /**
     * Instantiates a new Add Drone Controller Test.
     */
    public AddDroneControllerTest() {
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

        this.controller = new AddDroneController();

        this.address = new Address("11,11", "Rua ISEP", "4460-123", 123, "São João",3);
        this.pharmacy = new Pharmacy("testeAddDroneController@gmail.com", this.address, "Pharmacy Teste");
    }

    /**
     * Test get pharmacies list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testGetPharmaciesList() throws SQLException {
        System.out.println("AddDroneController getPharmaciesList() Test");
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
        System.out.println("AddDroneController getPharmacyByEmail() Test");
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
     * New Drone Test.
     */
    @Test
    public void testNewDrone() {
        System.out.println("AddDroneController testNewDrone() Test");

        Assertions.assertTrue(this.controller.newDrone("111", "111", "111","30"));
        try {
            this.controller.newDrone("asd", "111", "111","30");
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
    }

    /**
     * Add Drone Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testAddDrone() throws SQLException {
        System.out.println("AddDroneController addDrone() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.newDrone("111", "111", "111","30");
        Park p = new Park("Drone", "1", "150");
        Spot s = new Spot(1, "no");
        new ParkDB().addPark(p, this.pharmacy.getEmail());
        int idPark = new ParkDB().getLastPark(this.pharmacy.getEmail());
        new SpotDB().addSpot(s, idPark);
        Assertions.assertTrue(this.controller.addDrone());

        try {
            this.controller.addDrone();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, new DroneDB().getAllDrones(this.pharmacy.getEmail()).size());
        }

        new SpotDB().removeSpot(s.getId(), idPark);
        new ParkDB().removePark(idPark);
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * To String Drone Test.
     */
    @Test
    public void testGetDroneString() {
        System.out.println("AddDroneController testGetDroneString() Test");
        Drone s = new Drone("111", "111", "111","30");
        this.controller.newDrone("111", "111", "111","30");
        Assertions.assertEquals(s.toString2(), this.controller.getDroneString());
    }
}
