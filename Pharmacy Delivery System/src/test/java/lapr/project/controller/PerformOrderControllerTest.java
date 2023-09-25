package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Add courier controller test.
 */
class PerformOrderControllerTest {

    /**
     * The Controller.
     */
    private final PerformOrderController controller;
    /**
     * The Order.
     */
    private final Order order;
    /**
     * The Product.
     */
    private final Product product;

    /**
     * Instantiates a new Add courier controller test.
     *
     * @throws IOException    the io exception
     * @throws SQLException   the sql exception
     * @throws ParseException the parse exception
     */
    public PerformOrderControllerTest() throws IOException, SQLException, ParseException {
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

        Address adress1 = new Address("01010155,01010155", "Rua sem ISEP", "4460-123", 123, "Rio Tinto", 2);
        Address adress2 = new Address("232.534,543.332", "Rua ISEP", "4460-122", 124, "São João", 6);
        Pharmacy pharmacy = new Pharmacy("addCourierControllerTest@gmail.com", adress1, "Teste");
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");
        Client client = new Client("clientPerformOrderController", "clientperformordercontrollertest@gmail.com", "123", adress2, 1234567890123456L, d, 123, 0);
        this.controller = new PerformOrderController("clientperformordercontrollertest@gmail.com");
        this.order =  new Order(0);
        this.product = new Product(159,"performOrderControllerTest","performOrderControllerTest",1.00,1.00);

    }

    /**
     * New Order.
     */
    @Test
    void newOrder() {
        Assertions.assertTrue(this.controller.newOrder());
    }

    /**
     * Gets product list.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getProductList() throws SQLException {
        new ProductDB().removeProduct(this.product.getReference());
        new ProductDB().addProduct(this.product);
        int size = new ProductDB().getAllProducts().size();
        Assertions.assertEquals(size, this.controller.getProductList().size());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

    /**
     * Gets product by reference.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getProductByReference() throws SQLException {
        new ProductDB().removeProduct(this.product.getReference());
        new ProductDB().addProduct(this.product);
        String number = String.valueOf(new ProductDB().getAllProducts().get(0).getReference());
        this.product.setReference(number);
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
        Assertions.assertEquals(this.product.getReference(), new ProductDB().getAllProducts().get(0).getReference());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

    /*@Test
    void addBasket(){
    }

    @Test
    void calculateOrderPrice() throws SQLException {
        Map<Product, Integer> basket = new LinkedHashMap<>();
        controller.addBasket(3);
        basket.put(this.product,3);
        Assertions.assertEquals(3.00,controller.calculateOrderPrice());
    }*/

    /**
     * New product order.
     */
    @Test
    void newProductOrder()  {
        new ProductOrderDB().removeProductOrder(this.product.getReference(),this.order.getIdOrder());
        new ProductOrderDB().addProductOrder(this.product.getReference(),this.order.getIdOrder(),1);
        Assertions.assertTrue(true);
        new ProductOrderDB().removeProductOrder(this.product.getReference(),this.order.getIdOrder());
    }

    /*
    @Test
    void checkProducts(){

    }

    @Test
    void getClosestPharmacy(){

    }

    @Test
    void removeFromBasket(){

    }

    @Test
    void getOrderString(){
        Map<Product, Integer> basket = new LinkedHashMap<>();
        basket.put(this.product,1);
        String order = String.format("Product : %s  Amount : %d Price(1/u) : %.2f € \n", this.product.getName(), basket.get(this.product), this.product.getPrice()) +
                String.format("Total Price: %.2f €\n", this.controller.calculateOrderPrice());
        Assertions.assertNull(controller.getOrderString());
    }

    @Test
    void getClientCredits(){
        Assertions.assertEquals(client.getCredits(),controller.getClientCredits());
    }

    @Test
    void removeCredits(){

    }

    @Test
    void addCredits(){

    }

    @Test
    void sendEmail(){


     */
    }