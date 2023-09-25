package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Product order test.
 */
class ProductOrderTest {

    /**
     * The ProductOrder.
     */
    private final ProductOrder productOrder;

    /**
     * Instantiates a new Product order test.
     */
    public ProductOrderTest() {
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

        this.productOrder = new ProductOrder(123,123,4);
    }

    /**
     * Gets reference.
     */
    @Test
    void getReference() {
        System.out.println("ProductOrder getReference() Test");
        Assertions.assertEquals(123, this.productOrder.getReference());
    }

    /**
     * Gets id order.
     */
    @Test
    void getIdOrder() {
        System.out.println("ProductOrder getIdOrder() Test");
        Assertions.assertEquals(123, this.productOrder.getIdOrder());
    }

    /**
     * Gets amount.
     */
    @Test
    void getAmount() {
        System.out.println("ProductOrder getAmount() Test");
        Assertions.assertEquals(4,this.productOrder.getAmount());
    }


    /**
     * Sets reference.
     */
    @Test
    void setReference() {
        System.out.println("ProductOrder setReference() Test");
        double fP = 444;
        productOrder.setReference(444);
        Assertions.assertEquals(productOrder.getReference(), fP);

    }

    /**
     * Sets id order.
     */
    @Test
    void setIdOrder() {
        System.out.println("ProductOrder setIdOrder() Test");
        int id = 456;
        productOrder.setIdOrder(456);
        Assertions.assertEquals(productOrder.getIdOrder(), id);
    }
}