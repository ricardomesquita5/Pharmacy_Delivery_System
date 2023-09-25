package lapr.project.model;


import lapr.project.data.*;
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
 * The type Client test.
 */
class ClientTest {

    /**
     * The Client.
     */
    private Client client;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * The User.
     */
    private final User user;

    /**
     * The Date.
     */
    private final Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");

    /**
     * Instantiates a new Client test.
     *
     * @throws ParseException the parse exception
     */
    public ClientTest() throws ParseException {
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

        this.address = new Address("04040404,04040404", "Rua ISEP", "4460-123", 123, "São João", 21);
        this.user = new User("testeCliente", "testeClient@gmail.com", "123");

        this.client = new Client("testeCliente", "testeClient@gmail.com", "123", address, 1234500890123456L, d, 123);
    }

    /**
     * User Product Test.
     */
    @Test
    void ClientConstructor() {
        System.out.println("ClientConstructor() Test");

        this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "123");
        Assertions.assertEquals(1234567890123456L, this.client.getCreditCardNumber());
        this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "100");
        Assertions.assertEquals(100, this.client.getCcv());
        this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "999");
        Assertions.assertEquals(999, this.client.getCcv());
        this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "123");
        Assertions.assertEquals(123, this.client.getCcv());


        try {
            this.client = new Client(null, null, null, null, null, null, null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(address, this.client.getAddress());
        }
        try {
            this.client = new Client("", "", "", address, "", "", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(address, this.client.getAddress());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "", "20/01/2222", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1234567890123456L, this.client.getCreditCardNumber());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, null, "20/01/2222", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1234567890123456L, this.client.getCreditCardNumber());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "12345678901234567", "20/01/2222", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1234567890123456L, this.client.getCreditCardNumber());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "123456789012345", "20/01/2222", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1234567890123456L, this.client.getCreditCardNumber());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "", "123");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", null, "123");
        } catch (NullPointerException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/011221/2009", "123");
        } catch (IllegalArgumentException ia) {
            this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "123");
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2009", "123");
        } catch (IllegalArgumentException ia) {
            this.client = new Client("Pedro", "dasda@gmail.com", "23", address, "1234567890123456", "20/01/2222", "123");
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", "1000");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", "2000");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", "99");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
        try {
            this.client = new Client("Jose", "jose@gmail.com", "123", address, "1234567890123456", "20/01/2222", "10");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(123, this.client.getCcv());
        }
    }


    /**
     * Gets email.
     */
    @Test
    void getEmail() {
        System.out.println("Client getEmail() Test");
        Assertions.assertEquals("testeClient@gmail.com", this.client.getEmail());
    }

    /**
     * Gets credit card number.
     */
    @Test
    void getCreditCardNumber() {
        System.out.println("Client getCreditCardNumber() Test");
        Assertions.assertEquals(1234500890123456L, this.client.getCreditCardNumber());
    }

    /**
     * Gets validity date.
     */
    @Test
    void getValidityDate() {
        System.out.println("Client getValidityDate() Test");
        Assertions.assertEquals(d, this.client.getValidityDate());
    }

    /**
     * Gets ccv.
     */
    @Test
    void getCcv() {
        System.out.println("Client getCcv() Test");
        Assertions.assertEquals(123, this.client.getCcv());
    }

    /**
     * Gets address.
     */
    @Test
    void getAddress() {
        System.out.println("Client getAddress() Test");
        Assertions.assertEquals(address, this.client.getAddress());

    }

    /**
     * Gets credits.
     */
    @Test
    void getCredits() {
        System.out.println("Client getCredits() Test");
        Assertions.assertEquals(0, this.client.getCredits());
        this.client.addCredits(24);
        Assertions.assertEquals(24, this.client.getCredits());
        this.client.addCredits(0);
    }

    /**
     * Sets address.
     */
    @Test
    void setAddress() {
        System.out.println("Client setAddress() Test");
        Address ad = new Address("33.777, 8.888", "3", "3333-666", "3", "3", "22");
        client.setAddress(ad);
        Assertions.assertEquals(client.getAddress(), ad);
    }

    /**
     * Sets credit card number.
     */
    @Test
    void setCreditCardNumber() {
        System.out.println("Client setCreditCardNumber() Test");
        String ccn = "1029384756483745";
        client.setCreditCardNumber(ccn);
        Assertions.assertEquals(String.valueOf(client.getCreditCardNumber()), ccn);

        try {
            this.client.setCreditCardNumber(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1029384756483745L, this.client.getCreditCardNumber(), 0);
        }
        try {
            this.client.setCreditCardNumber("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1029384756483745L, this.client.getCreditCardNumber(), 0);
        }
        try {
            this.client.setCreditCardNumber("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1029384756483745L, this.client.getCreditCardNumber(), 0);
        }
        try {
            this.client.setCreditCardNumber("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1029384756483745L, this.client.getCreditCardNumber(), 0);
        }
        try {
            this.client.setCreditCardNumber("comiamendoasesen");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1029384756483745L, this.client.getCreditCardNumber(), 0);
        }
    }

    /**
     * Sets validity date.
     *
     * @throws ParseException the parse exception
     */
    @Test
    void setValidityDate() throws ParseException {
        System.out.println("Client setValidityDate() Test");
        String date = "20/03/2222";
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        client.setValidityDate("20/03/2222");
        Assertions.assertEquals(client.getValidityDate(), d);
        try {
            this.client.setValidityDate(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client.setValidityDate("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client.setValidityDate("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client.setValidityDate("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client.setValidityDate("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
        try {
            this.client.setValidityDate("20/10/2010");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(d, this.client.getValidityDate());
        }
    }

    /**
     * Sets ccv.
     */
    @Test
    void setCcv() {
        System.out.println("Client setCcv() Test");
        String newCcv = "999";
        client.setCcv(newCcv);
        Assertions.assertEquals(String.valueOf(client.getCcv()), newCcv);
        newCcv = "100";
        client.setCcv(newCcv);
        Assertions.assertEquals(String.valueOf(client.getCcv()), newCcv);
        newCcv = "736";
        client.setCcv(newCcv);
        Assertions.assertEquals(String.valueOf(client.getCcv()), newCcv);
        try {
            this.client.setCcv(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("1001");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("1000");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("99");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
        try {
            this.client.setCcv("10");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(736, this.client.getCcv(), 0);
        }
    }

    /**
     * Add credits.
     */
    @Test
    void addCredits() {
        System.out.println("Client addCredits() Test");
        double price = 25.6;
        client.addCredits(price);
        Assertions.assertEquals(25, client.getCredits());
    }

    /**
     * Remove credits.
     */
    @Test
    void removeCredits() {
        System.out.println("Client removeCredits() Test");
        int price = 2;
        client.removeCredits(price);
        Assertions.assertEquals(-2, client.getCredits());
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Client save() Test");
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.user.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new ClientDB().addClient(this.client);
        new ClientDB().isClient(this.client.getEmail());
        this.client.save();
        Assertions.assertTrue(new ClientDB().isClient(this.client.getEmail()));
        try {
            this.client.save();
        } catch (IllegalArgumentException iae){
            System.out.println(iae.toString());
        }
        try {
            this.client.setEmail("Teste");
            this.client.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testeClient@gmail.com", this.user.getEmail());
        }
        try {
            this.client.setEmail("Teste@i");
            this.client.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testeClient@gmail.com", this.user.getEmail());
        }
        try {
            this.client.setEmail("Teste.pt");
            this.client.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testeClient@gmail.com", this.user.getEmail());
        }
        try {
            this.client.setEmail("Teste.com");
            this.client.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testeClient@gmail.com", this.user.getEmail());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.user.getEmail());
        Assertions.assertFalse(new ClientDB().isClient(this.client.getEmail()));
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Remove client.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeClient() throws SQLException {
        System.out.println("Client remove() Test");
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.user.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new ClientDB().addClient(this.client);
        this.client.save();
        Assertions.assertTrue(new ClientDB().isClient(this.client.getEmail()));
        this.client.removeClient();
        Assertions.assertFalse(new ClientDB().isClient(this.client.getEmail()));
        try {
            this.client.removeClient();
        } catch (IllegalArgumentException ia) {
            Assertions.assertFalse(new ClientDB().isClient(this.client.getEmail()));
        }
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new UserDB().removeUser(this.client.getEmail());
    }

    /**
     * Equals.
     */
    @Test
    public void equals() {
        System.out.println("Client equals() Test");

        User u = new User("jose", "jose@isep.pt", "jose123");
        Client c1 = new Client("jose", "testeClient@gmail.com", "jose123", address, 1234567890123456L, d, 123);
        Client c2 = new Client("pedro", "das@das.com", "jose123", address, 1234567890123456L, d, 123);

        Assertions.assertEquals(this.client, this.client);

        Assertions.assertNotEquals(this.client, c2);

        Assertions.assertNotEquals(this.client, u);

        Assertions.assertEquals(this.client, c1);

    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest() {
        System.out.println("Client toString() Test");
        String s = "Client{" + "email='" + client.getEmail() + '\'' +
                ", name=" + client.getName() +
                ", password=" + client.getPassword() +
                ", address=" + address +
                ", creditCardNumber='" + client.getCreditCardNumber() + '\'' +
                ", validityDate=" + client.getValidityDate() +
                ", ccv=" + client.getCcv() +
                ", credits=" + client.getCredits() +
                '}';
        Assertions.assertEquals(s, this.client.toString());
    }

    /**
     * Hash code test.
     */
    @Test
    public void hashCodeTest() {
        System.out.println("Client hashCodeTest() Test");

        Client c1 = new Client("testeCliente", "testeClient@gmail.com", "123", address, 1234500890123456L, d, 123);
        Client c2 = new Client("jose", "pedro@gmail.pt", "jo3", address, 1234567890567566L, d, 175);
        Assertions.assertEquals(this.client.hashCode(), c1.hashCode());
        Assertions.assertNotEquals(this.client.hashCode(), c2.hashCode());
    }
}