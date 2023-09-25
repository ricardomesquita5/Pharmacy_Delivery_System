package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Add Product Controller Test.
 */
public class AddProductControllerTest {

    /**
     * The Controller.
     */
    private final AddProductController controller;

    /**
     * Instantiates a new Add Product Controller Test.
     */
    public AddProductControllerTest() {
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

        this.controller = new AddProductController();
    }

    /**
     * New Product Test.
     */
    @Test
    public void testNewProduct() {
        System.out.println("AddProductController testNewProduct() Test");

        Assertions.assertTrue(this.controller.newProduct("1","Name", "Description", "10", "1"));
        try {
            this.controller.newProduct("1","Name", "Description", "Price", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertTrue(true);
        }
    }

    /**
     * Add Product Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testAddProduct() throws SQLException {
        System.out.println("AddProductController addProduct() Test");
        while (new ProductDB().getAllProducts().size() > 0){
            new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
        }
        this.controller.newProduct("1","N", "D", "10", "10");
        Assertions.assertTrue(this.controller.addProduct());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

    /**
     * To String Product Test.
     */
    @Test
    public void testGetProductString() {
        System.out.println("AddProductControllerTest testGetScooterString() Test");
        Product s = new Product("1","Name", "Description", "10", "1");
        this.controller.newProduct("1","Name", "Description", "10", "1");
        Assertions.assertEquals(s.toString(), this.controller.getProductString());
    }
}