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
 * The type Remove product controller test.
 */
public class RemoveProductControllerTest {
    /**
     * The Remove Product Controler.
     */
    private final RemoveProductController controller;
    /**
     * The Product.
     */
    private final Product product;

    /**
     * Instantiates a new Remove product controller test.
     */
    public RemoveProductControllerTest() {
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

        this.controller = new RemoveProductController();

        this.product = new Product("1","Name1","Desc1", "999", "999");
    }

    /**
     * Gets Products List Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getProductsList() throws SQLException {
        System.out.println("RemoveProductController getProductsList() Test");
        while (new ProductDB().getAllProducts().size() > 0){
            new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
        }
        int size = new ProductDB().getAllProducts().size();
        new ProductDB().addProduct(this.product);
        Assertions.assertEquals(size + 1, this.controller.getProductsList().size());
        while (new ProductDB().getAllProducts().size() > 0){
            new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
        }
    }

    /**
     * Gets Product By Reference Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getProductByReference() throws SQLException {
        System.out.println("RemoveProductController getProductByReference() Test");
        new ProductDB().addProduct(this.product);
        String number = String.valueOf(new ProductDB().getAllProducts().get(0).getReference());
        this.product.setReference(number);
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
        Assertions.assertEquals(this.product.toString(), this.controller.getProductString());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

    /**
     * Remove Product Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeProduct() throws SQLException {
        System.out.println("RemoveProductController removeProduct() Test");
        while (new ProductDB().getAllProducts().size() > 0){
            new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
        }
        new ProductDB().addProduct(this.product);
        int size = new ProductDB().getAllProducts().size();
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
        Assertions.assertTrue(this.controller.removeProduct());
        Assertions.assertEquals(size - 1, new ProductDB().getAllProducts().size());
    }

    /**
     * To String Product Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getProductString() throws SQLException {
        System.out.println("RemoveProductController getProductString() Test");
        while (new ProductDB().getAllProducts().size() > 0){
            new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
        }
        new ProductDB().addProduct(this.product);
        String number = String.valueOf(new ProductDB().getAllProducts().get(0).getReference());
        this.product.setReference(number);
        Assertions.assertEquals(this.product.toString(), new ProductDB().getAllProducts().get(0).toString());
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
    }
}
