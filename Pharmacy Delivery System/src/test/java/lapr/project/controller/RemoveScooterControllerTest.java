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
 * The type Remove Scooter Controller Test.
 */
class RemoveScooterControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Remove Scooter Controler.
     */
    private final RemoveScooterController controller;
    /**
     * The Scooter.
     */
    private final Scooter scooter;

    /**
     * Instantiates a new Remove Scooter Controller Test.
     */
    public RemoveScooterControllerTest() {
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

        this.controller = new RemoveScooterController();

        this.address = new Address("02020202.02,02020202.02", "Rua ISEP", "4460-123", 123, "São João", 12);
        this.pharmacy = new Pharmacy("testeRemoveScooterController@gmail.com", this.address, "Pharmacy Teste");
        this.scooter = new Scooter("100", "100", "100", "30");
    }

    /**
     * Gets Pharmacies List Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmaciesList() throws SQLException {
        System.out.println("RemoveScooterController getPharmaciesList() Test");
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
     * Gets Pharmacy by Email Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmacyByEmail() throws SQLException {
        System.out.println("RemoveScooterController getPharmacyByEmail() Test");
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
     * Gets Scooters List Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getScootersList() throws SQLException {
        System.out.println("RemoveScooterController getScootersList() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size();
        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, this.controller.getScootersList().size());
        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets Scooter By ID Test.
     */
    @Test
    void getScooterByID() {
        System.out.println("RemoveScooterController getScooterByID() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        Scooter s = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0);
        this.controller.getScooterByID(s.getId());
        this.scooter.setID(s.getId());
        Assertions.assertEquals(this.scooter.toString(), this.controller.getScooterString());
        new ScooterDB().removeScooter(s.getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Remove Scooter Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeScooter() throws SQLException {
        System.out.println("RemoveScooterController removeScooter() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        int size = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size();

        Park p = new Park("Scooter", "1", "150");
        Spot s = new Spot(1, "no");
        new ParkDB().addPark(p, this.pharmacy.getEmail());
        int idPark = new ParkDB().getLastPark(this.pharmacy.getEmail());
        new SpotDB().addSpot(s, idPark);
        int idScooter = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId();
        new SpotDB().updateSpot(s.getId(), idPark, idScooter);

        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.getScooterByID(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());

        Assertions.assertTrue(this.controller.removeScooter());

        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        idScooter = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId();

        try {
            this.controller.removeScooter();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(size, new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size());
        }


        new SpotDB().removeSpot(s.getId(), idPark);
        new ParkDB().removePark(idPark);
        new ScooterDB().removeScooter(idScooter);
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}