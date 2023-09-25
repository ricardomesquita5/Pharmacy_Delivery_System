package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Address;
import lapr.project.model.Courier;
import lapr.project.model.Pharmacy;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Remove courier controller test.
 */
class RemoveCourierControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Controller.
     */
    private final RemoveCourierController controller;
    /**
     * The Courier.
     */
    private final Courier c;

    /**
     * Instantiates a new Remove courier controller test.
     */
    public RemoveCourierControllerTest() {
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

        this.controller = new RemoveCourierController();
        this.c = new Courier("444444444", "nome", "email@gmail.com", "888888888", "11111111111", "pwd13231");
        this.address = new Address("01010101,01013333", "Rua ISEP", "4460-123", 123, "São João", 10);
        this.pharmacy = new Pharmacy("testeRemoveCourierController@gmail.com", this.address, "Pharmacy Teste");
    }

    /**
     * Gets pharmacies list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmaciesList() throws SQLException {
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
     * Gets pharmacy by email.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmacyByEmail() throws SQLException {
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(this.pharmacy.toString(), this.controller.getPharmacy(this.pharmacy.getEmail()).toString());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets email pharmacy.
     */
    @Test
    void getEmailPharmacy() {
        String email = this.controller.getEmailPharmacy(this.pharmacy);
        Assertions.assertEquals(this.pharmacy.getEmail(), email);
    }

    /**
     * Gets email courier.
     */
    @Test
    void getEmailCourier() {
        String email = this.controller.getEmailCourier(this.c);
        Assertions.assertEquals(this.c.getEmail(), email);
    }

    /**
     * Gets courier list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getCourierList() throws SQLException {
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new CourierDB().getAllCouriersOfPharmacy(this.pharmacy.getEmail()).size();
        new CourierDB().addCourier(this.c, this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, this.controller.getCourierList(this.pharmacy.getEmail()).size());
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets courier.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getCourier() throws SQLException {
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.c, this.pharmacy.getEmail());
        this.controller.getCourier(this.c.getEmail());
        Assertions.assertEquals(this.c.toString(), this.controller.getCourierString());
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }


    /**
     * Remove courier.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeCourier() throws SQLException {
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.c, this.pharmacy.getEmail());
        this.controller.getCourier(this.c.getEmail());
        Assertions.assertTrue(this.controller.removeCourier());
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets user.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getUser() throws SQLException {
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.c, this.pharmacy.getEmail());
        this.controller.getUser(this.c.getEmail());
        User u = new User(this.c.getName(), this.c.getEmail(), this.c.getPassword());
        Assertions.assertEquals(u.toString(), this.controller.getUserString());
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Remove user.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeUser() throws SQLException {
        new CourierDB().removeCourier(this.c.getEmail());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.c, this.pharmacy.getEmail());
        this.controller.getUser(this.c.getEmail());
        new CourierDB().removeCourier(this.c.getEmail());
        Assertions.assertTrue(this.controller.removeUser());
        new UserDB().removeUser(this.c.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}