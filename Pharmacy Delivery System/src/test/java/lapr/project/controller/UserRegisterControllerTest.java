package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Address;
import lapr.project.model.Client;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * The type User register controller test.
 */
class UserRegisterControllerTest {

    /**
     * The Controller.
     */
    private final UserRegisterController controller;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The User.
     */
    private final User user;
    /**
     * The Client.
     */
    private Client client;

    /**
     * Instantiates a new User register controller test.
     *
     * @throws ParseException the parse exception
     */
    public UserRegisterControllerTest() throws ParseException {

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

        this.controller = new UserRegisterController();

        this.address = new Address("04040404,04040404", "Rua ISEP", "4460-123", 123, "São João", 14);
        this.user = new User("testeCliente", "userRegisterControllerTest@gmail.com", "123");
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");
        this.client = new Client("testeCliente", "userRegisterControllerTest@gmail.com", "123", this.address, 1234567890123456L, d, 123);
    }

    /**
     * New client.
     */
    @Test
    void newClient() {
        System.out.println("newClient() Test");

        Assertions.assertTrue(this.controller.newClient("Jose", "jose@gmail.com", "111", "sao", "4443-444", "12",
                "Matosinhos", "123.432,3124.213", "15", "1234567890123456", "13/05/2022", "123"));
        try {
            this.controller.newClient("Jose", "jose@gmail.com", "111", "sao", "4443-444", "12",
                    "Matosinhos", "123.432,3124.213", "15", "1234567890123456", "13/05/2022", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
        try {
            this.controller.newClient(null, null, null, null, null, null,
                    null, null, null, null, null, null);
        } catch (IllegalArgumentException ia) {
            Assertions.fail();
        }
    }

    /**
     * Gets client string.
     */
    @Test
    void getClientString() {
        System.out.println("getClientString() Test");
        Address address = new Address("234112,324123", "12", "1234-234", "12", "12", "16");
        Client c = new Client("Jose", "jose@gmail.com", "111", address, "1234567890123456", "13/05/2022", "123");
        this.controller.newClient("Jose", "jose@gmail.com", "111", "12", "1234-234", "12", "12", "234112,324123",
                "17", "1234567890123456", "13/05/2022", "123");
        Assertions.assertEquals(c.toString(), this.controller.getClientString());
    }

    /**
     * Add client.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void addClient() throws SQLException {
        System.out.println("Controller AddClient() Test");
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.user.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().removeAddress("234112,324123");
        new AddressDB().addAddress(this.address);
        this.controller.newClient("testeCliente", "userRegisterControllerTest@gmail.com", "111", "12", "1234-234", "12", "12", "234112,324123",
                "18", "1234567890123456", "13/05/2022", "123");

        Assertions.assertTrue(this.controller.addClient());

        try {
            this.controller.addClient();
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }

        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.user.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().removeAddress("234112,324123");
    }
}