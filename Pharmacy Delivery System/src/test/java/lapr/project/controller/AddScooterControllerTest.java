package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
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
 * The type Add Scooter Controller Test.
 */
public class AddScooterControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Add Scooter Controller.
     */
    private final AddScooterController controller;

    /**
     * Instantiates a new Add Scooter Controller Test.
     */
    public AddScooterControllerTest() {
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

        this.controller = new AddScooterController();

        this.address = new Address("01010101.01,01010101.01", "Rua ISEP", "4460-123", 123, "São João",5);
        this.pharmacy = new Pharmacy("testeAddScooterController@gmail.com", this.address, "Pharmacy Teste");
    }

    /**
     * Test get pharmacies list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testGetPharmaciesList() throws SQLException {
        System.out.println("AddScooterController getPharmaciesList() Test");
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
        System.out.println("AddScooterController getPharmacyByEmail() Test");
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
     * New Scooter Test.
     */
    @Test
    public void testNewScooter() {
        System.out.println("AddScooterController testNewScooter() Test");

        Assertions.assertTrue(this.controller.newScooter("111", "111", "111","30"));
        try {
            this.controller.newScooter("asd", "111", "111","30");
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
    }

    /**
     * Add Scooter Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testAddScooter() throws SQLException {
        System.out.println("AddScooterController addScooter() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.newScooter("111", "111", "111","30");
        Park p = new Park("Scooter", "1", "150");
        Spot s = new Spot(1, "no");
        new ParkDB().addPark(p, this.pharmacy.getEmail());
        int idPark = new ParkDB().getLastPark(this.pharmacy.getEmail());
        new SpotDB().addSpot(s, idPark);
        Assertions.assertTrue(this.controller.addScooter());

        try {
            this.controller.addScooter();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size());
        }

        new SpotDB().removeSpot(s.getId(), idPark);
        new ParkDB().removePark(idPark);
        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Get Last Scooter Test.
     */
    @Test
    void testGetLastScooter(){
        System.out.println("AddScooterController getLastScooter() Test");
        List<Scooter> listScooter = new ArrayList<>();
        Scooter s1 = new Scooter(1,10.0,100.0,100.0,100,20.0);
        listScooter.add(s1);
        Scooter s2 = new Scooter(2,10.0,100.0,100.0,100,20.0);
        listScooter.add(s2);
        Scooter s3 = new Scooter(3,10.0,100.0,100.0,100,20.0);
        listScooter.add(s3);
        Scooter s4 = new Scooter(4,10.0,100.0,100.0,100,20.0);
        listScooter.add(s4);
        Assertions.assertEquals(4,this.controller.getLastScooter(listScooter));
    }

    /**
     * To String Scooter Test.
     */
    @Test
    public void testGetScooterString() {
        System.out.println("AddScooterController testGetScooterString() Test");
        Scooter s = new Scooter("111", "111", "111","30");
        this.controller.newScooter("111", "111", "111","30");
        Assertions.assertEquals(s.toString2(), this.controller.getScooterString());
    }
}
