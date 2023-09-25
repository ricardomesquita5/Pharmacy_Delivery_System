package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Address;
import lapr.project.model.Courier;
import lapr.project.model.Pharmacy;
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
 * The type Add courier controller test.
 */
class AddCourierControllerTest {

    /**
     * The Controller.
     */
    private final AddCourierController controller;
    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Courier.
     */
    private final Courier courier;

    /**
     * Instantiates a new Add courier controller test.
     */
    public AddCourierControllerTest() {
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

        this.controller = new AddCourierController();

        this.address = new Address("01010155,01010155", "Rua ISEP", "4460-123", 123, "São João",2);
        this.pharmacy = new Pharmacy("addCourierControllerTest@gmail.com", this.address, "Teste");
        this.courier = new Courier("000111333", "Courier", "addCourierControllerTest2@gmail.com", "123123123", "46894444444", "pwd");
    }

    /**
     * New courier.
     */
    @Test
    void newCourier() {
        System.out.println("AddCourierController newCourier() Test");
        Assertions.assertTrue(this.controller.newCourier("474444444", "Courier", "courier@gmail.com",
                "953333333", "44444444444", "pwd"));
        try {
            this.controller.newCourier("nif", "Courier", "courier@gmail.com",
                    "953333333", "44444444444", "pwd");
        } catch (IllegalArgumentException ia) {
            Assertions.assertFalse(false);
        }
        try {
            Assertions.assertTrue(this.controller.newCourier("474444444", "Courier", "courier@gmail.com",
                    "953333333", "44444444444", "pwd"));
        } catch (IllegalArgumentException ia) {
            Assertions.assertFalse(false);
        }
    }

    /**
     * Gets pharmacy by email.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmacyByEmail() throws SQLException {
        System.out.println("AddCourierController getPharmacyByEmail() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(this.pharmacy.toString(), this.controller.getPharmacy().toString());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Add courier.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void addCourier() throws SQLException {
        System.out.println("AddCourierController addCourier() Test");
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.newCourier("000111333", "Courier", "addCourierControllerTest2@gmail.com", "123123123", "46894444444", "pwd");
        Assertions.assertFalse(this.controller.addCourier());

        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets pharmacies list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmaciesList() throws SQLException {
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
     * Gets courier string.
     */
    @Test
    void getCourierString() {
        System.out.println("AddCourierController getCourierString() Test");
        Courier c = new Courier("474444444", "Courier", "courier@gmail.com",
                "953333333", "44444444444", "pwd");
        this.controller.newCourier("474444444", "Courier", "courier@gmail.com",
                "953333333", "44444444444", "pwd");
        Assertions.assertEquals(c.toString(), this.controller.getCourierString());
    }
}