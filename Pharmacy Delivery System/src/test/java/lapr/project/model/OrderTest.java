package lapr.project.model;

import lapr.project.data.*;
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
 * The type Order test.
 */
class OrderTest {

    /**
     * The Order.
     */
    private Order order;

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * The Client.
     */
    private final Client client;

    /**
     * Instantiates a new Order test.
     *
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public OrderTest() throws ParseException, IOException {
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
        new EmailHandler();

        this.order = new Order(1);
        Assertions.assertEquals(order.getIdOrder(), 0);
        this.order = new Order(2);
        Assertions.assertEquals(order.getIdOrder(), 0);
        this.order = new Order(1, 10.6);
        Assertions.assertEquals(order.getFinalPrice(), 10.6);
        this.order = new Order(1, 50.0);
        Assertions.assertEquals(order.getFinalPrice(), 50.0);
        this.order = new Order(1, 0);
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");
        this.address = new Address("123,321", "Rua ISEP", "4460-123", 123, "São João", 29);
        this.pharmacy = new Pharmacy("testOrder@gmail.com", this.address, "Pharmacy Teste");
        this.client = new Client("testeCliente", "testeOrder@gmail.com", "123", address, 1234500890123456L, d, 123);
    }

    /**
     * Gets final price.
     */
    @Test
    void getFinalPrice() {
        System.out.println("Order getFinalPrice() Test");
        Assertions.assertEquals(0, this.order.getFinalPrice());
    }

    /**
     * Gets id order.
     */
    @Test
    void getIdOrder() {
        System.out.println("Order getIdOrder() Test");
        Assertions.assertEquals(1, this.order.getIdOrder());
    }

    /**
     * Sets final price.
     */
    @Test
    void setFinalPrice() {
        System.out.println("Order setFinalPrice() Test");
        double fP = 50.0;
        order.setFinalPrice(50.0);
        Assertions.assertEquals(order.getFinalPrice(), fP);

    }

    /**
     * Sets id order.
     */
    @Test
    void setIdOrder() {
        System.out.println("Order setIdOrder() Test");
        int id = 123;
        order.setIdOrder(123);
        Assertions.assertEquals(order.getIdOrder(), id);
    }

//    @Test
//    void save() throws SQLException {
//        System.out.println("Order save() Test");
//        for (Order order :
//                new OrderDB().getAllOrders(this.pharmacy.getEmail())) {
//            new OrderDB().removeOrder(order.getIdOrder());
//        }
//        new ClientDB().removeClient(this.client.getEmail());
//        new UserDB().removeUser(this.client.getEmail());
//        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
//        new AddressDB().removeAddress(this.address.getGPSCoordinates());
//        new AddressDB().addAddress(this.address);
//        new PharmacyDB().addPharmacy(this.pharmacy);
//        new ClientDB().addClient(this.client);
//        int size = new OrderDB().getAllOrders(this.pharmacy.getEmail()).size();
//        this.order.save(this.pharmacy.getEmail(), this.client.getEmail());
//        this.order.save(this.pharmacy.getEmail(),this.client.getEmail());
//        Assertions.assertEquals(size + 1, new OrderDB().getAllOrders(this.pharmacy.getEmail()).size());
//        for (Order order :
//                new OrderDB().getAllOrders(this.pharmacy.getEmail())) {
//            new OrderDB().removeOrder(order.getIdOrder());
//        }
//        new ClientDB().removeClient(this.client.getEmail());
//        new UserDB().removeUser(this.client.getEmail());
//        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
//        new AddressDB().removeAddress(this.address.getGPSCoordinates());
//    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest() {
        System.out.println("Order toString() Test");
        String s = "Order{" +
                "Order ID = '" + order.getIdOrder() + '\'' +
                ", finalPrice = " + order.getFinalPrice() +
                '}';
        Assertions.assertEquals(s, this.order.toString());
    }
    

}