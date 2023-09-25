package lapr.project.model;

import lapr.project.data.*;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Drone Test.
 */
class DroneTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Drone.
     */
    private Drone drone;

    /**
     * Instantiates a new Drone Test.
     */
    public DroneTest() {
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

        this.address = new Address("00000000,00000000", "Rua ISEP", "4460-123", 123, "São João", 28);
        this.pharmacy = new Pharmacy("testeDrone@gmail.com", this.address, "Pharmacy Teste");

        this.drone = new Drone("10.0", "134.0", "35.0", "20.0");
        this.drone = new Drone(1, 10.0, 134.0, 35.0, 100, 20.0);
    }

    /**
     * Save Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void save() throws SQLException {
        System.out.println("Drone save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new DroneDB().getAllDrones(this.pharmacy.getEmail()).size();
        this.drone.save(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, new DroneDB().getAllDrones(this.pharmacy.getEmail()).size());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Remove Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void remove() throws SQLException {
        System.out.println("Drone remove() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.drone.save(this.pharmacy.getEmail());
        int size = new DroneDB().getAllDrones(this.pharmacy.getEmail()).size();
        Drone d = new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0);
        d.remove();
        Assertions.assertEquals(size - 1, new DroneDB().getAllDrones(this.pharmacy.getEmail()).size());

        try {
            d.remove();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(size, new DroneDB().getAllDrones(this.pharmacy.getEmail()).size());
        }

        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Update Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void update() throws SQLException {
        System.out.println("Drone update() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.drone.save(this.pharmacy.getEmail());
        Drone d = new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0);
        d.setCapacity("100");
        d.update();
        Assertions.assertEquals(d.getCapacity(), new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getCapacity());
        new DroneDB().removeDrone(d.getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Equals Test.
     */
    @Test
    public void equals() {
        System.out.println("Drone equals() Test");

        User u = new User("jose", "testEqualsDrone@isep.pt", "jose123");
        Drone d1 = new Drone(2, 10.0, 134.0, 35.0, 100, 20.0);
        Drone d2 = new Drone(1, 134.0, 154.0, 135.0, 2, 20.0);

        //objetos iguais
        Assertions.assertEquals(this.drone, this.drone);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.drone, u);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.drone, d1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.drone, d2);
    }

    /**
     * Hash Code Test.
     */
    @Test
    public void HashCodeTest() {
        System.out.println("Drone hashCode() Test");
        Drone d1 = new Drone(1, 10.0, 134.0, 35.0, 100,20.0);
        Drone d2 = new Drone(2, 134.0, 154.0, 135.0, 2, 20.0);
        Assertions.assertEquals(this.drone.hashCode(), d1.hashCode());
        Assertions.assertNotEquals(this.drone.hashCode(), d2.hashCode());
    }
}