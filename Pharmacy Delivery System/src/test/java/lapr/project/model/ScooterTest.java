package lapr.project.model;

import lapr.project.data.AddressDB;
import lapr.project.data.DataHandler;
import lapr.project.data.PharmacyDB;
import lapr.project.data.ScooterDB;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Scooter Test.
 */
public class ScooterTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Scooter.
     */
    private Scooter scooter;

    /**
     * Instantiates a new Scooter Test.
     */
    public ScooterTest() {
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

        this.address = new Address("04040404.04,04040404.04", "Rua ISEP", "4460-123", 123, "São João", 37);
        this.pharmacy = new Pharmacy("testeScooter@gmail.com", this.address, "Pharmacy Teste");

        this.scooter = new Scooter("10.0", "134.0", "35.0", "20.0");
        this.scooter = new Scooter(1, 10.0, 134.0, 35.0, 100, 20.0);
    }

    /**
     * Save Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void save() throws SQLException {
        System.out.println("Scooter save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size();
        this.scooter.save(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size());
        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
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
        System.out.println("Scooter remove() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.scooter.save(this.pharmacy.getEmail());
        int size = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size();
        Scooter s = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0);
        s.remove();
        Assertions.assertEquals(size - 1, new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size());

        try {
            s.remove();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(size, new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size());
        }

        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Update Test.
     *
     * @throws SQLException the sql exception
     */
    @Test public void update() throws SQLException {
     System.out.println("Scooter update() Test");
     new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
     new AddressDB().removeAddress(this.address.getGPSCoordinates());
     new AddressDB().addAddress(this.address);
     new PharmacyDB().addPharmacy(this.pharmacy);
     this.scooter.save(this.pharmacy.getEmail());
     Scooter s = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0);
     s.setCapacity("100");
     s.update();
     Assertions.assertEquals(s.getCapacity(), new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getCapacity());
     new ScooterDB().removeScooter(s.getId());
     new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
     new AddressDB().removeAddress(this.address.getGPSCoordinates());
     }

    /**
     * Equals Test.
     */
    @Test
    public void equals() {
        System.out.println("Scooter equals() Test");

        User u = new User("jose", "testEqualsScooter@isep.pt", "jose123");
        Scooter s1 = new Scooter(2, 10.0, 134.0, 35.0, 100, 20.0);
        Scooter s2 = new Scooter(1, 134.0, 154.0, 135.0, 2, 20.0);

        //objetos iguais
        Assertions.assertEquals(this.scooter, this.scooter);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.scooter, u);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.scooter, s1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.scooter, s2);
    }

    /**
     * Hash Code Test.
     */
    @Test
    public void HashCodeTest() {
        System.out.println("Scooter hashCode() Test");
        Scooter s1 = new Scooter(1, 10.0, 134.0, 35.0, 100, 20.0);
        Scooter s2 = new Scooter(2, 134.0, 154.0, 135.0, 2, 20.0);
        Assertions.assertEquals(this.scooter.hashCode(), s1.hashCode());
        Assertions.assertNotEquals(this.scooter.hashCode(), s2.hashCode());
    }
}