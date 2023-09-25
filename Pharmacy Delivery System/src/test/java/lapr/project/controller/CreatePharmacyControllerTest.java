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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The type Create pharmacy controller test.
 */
class CreatePharmacyControllerTest {

    /**
     * The Controller.
     */
    private final CreatePharmacyController controller;
    /**
     * The Address.
     */
    private final Address add;
    /**
     * The Pharmacy.
     */
    private final Pharmacy phar;
    /**
     * The list.
     */
    private final List<Integer> list = new ArrayList<>();

    /**
     * Instantiates a new Create pharmacy controller test.
     */
    public CreatePharmacyControllerTest() {
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

        this.controller = new CreatePharmacyController();

        this.add = new Address("232.534,543.332", "Rua ISEP", "4460-122", 124, "São João",6);
        this.phar = new Pharmacy("createPharmacyControllerTest@gmail.com", this.add, "Teste1");
    }

    /**
     * New pharmacy.
     */
    @Test
    void newPharmacy() {
        System.out.println("CreatePharmacyController newPharmacy() Test");
        Assertions.assertTrue(this.controller.newPharmacy("desig", "p@gmail.com", "Rua do ISEP",
                "4450-009", "24", "Porto", "43.444,56.563", "6", "scooter","3",
                "22",list));
        try {
            Assertions.assertFalse(this.controller.newPharmacy("desig", "email", "Rua do ISEP",
                    "4450-009", "24", "Porto", "43.444,56.563", "6", "scooter","3",
                    "22",list));
        } catch (IllegalArgumentException ia) {
            Assertions.assertFalse(false);
        }
    }

    /**
     * Add pharmacy.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void addPharmacy() throws SQLException {
        System.out.println("CreatePharmacyController addPharmacy() Test");
        for (Park park:
             new ParkDB().getAllParks(this.phar.getEmail())) {
            for (Spot spot:
                 new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        new PharmacyDB().removePharmacy(this.phar.getEmail());
        new AddressDB().removeAddress(this.add.getGPSCoordinates());
        this.controller.newPharmacy(this.phar.getDesignation(), this.phar.getEmail(), this.phar.getAddress().getStreet(),
                this.phar.getAddress().getPostalCode(), "24", this.phar.getAddress().getLocality(),
                this.phar.getAddress().getGPSCoordinates(), "7", "scooter","3",
                "22",list);
        Assertions.assertTrue(this.controller.addPharmacy());
        for (Park park:
                new ParkDB().getAllParks(this.phar.getEmail())) {
            for (Spot spot:
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        new PharmacyDB().removePharmacy(this.phar.getEmail());
        new AddressDB().removeAddress(this.add.getGPSCoordinates());
    }

    /**
     * Gets pharmacy string.
     */
    @Test
    void getPharmacyString()  {
        System.out.println("CreatePharmacyController getPharmacyString() Test");
        new PharmacyDB().removePharmacy(this.phar.getEmail());
        new AddressDB().removeAddress(this.add.getGPSCoordinates());
        Address a = new Address("43.444,56.563", "Rua do ISEP", "4450-009", "24", "Porto", "8");
        Pharmacy p = new Pharmacy("p@gmail.com", a, "desig");
        this.controller.newPharmacy("desig", "p@gmail.com", "Rua do ISEP",
                "4450-009", "24", "Porto", "43.444,56.563", "8", "scooter","5",
                "23",list);

        Assertions.assertEquals(p.toString(), this.controller.getPharmacyString());
        new PharmacyDB().removePharmacy(this.phar.getEmail());
        new AddressDB().removeAddress(this.add.getGPSCoordinates());
    }
}