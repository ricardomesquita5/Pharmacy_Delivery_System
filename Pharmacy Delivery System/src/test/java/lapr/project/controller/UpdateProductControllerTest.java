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
 * The type Update product controller test.
 */
public class UpdateProductControllerTest {
    /**
     * The Remove Product Controler.
     */
    private final UpdateProductController controller;
    /**
     * The Product.
     */
    private final Product product;

    /**
     * Instantiates a new Update product controller test.
     */
    public UpdateProductControllerTest() {
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

        this.controller = new UpdateProductController();

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
        new ProductDB().addProduct(this.product);
        int size = new ProductDB().getAllProducts().size();
        Assertions.assertEquals(size, this.controller.getProductsList().size());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

//    /**
//     * Gets Product By Reference Test.
//     *
//     * @throws SQLException the sql exception
//     */
//    @Test
//    void getProductByReference() throws SQLException {
//        System.out.println("RemoveProductController getProductByReference() Test");
//        new ProductDB().addProduct(this.product);
//        String number = String.valueOf(new ProductDB().getAllProducts().get(0).getReference());
//        this.product.setReference(number);
//        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
//        Assertions.assertEquals(this.product.toString(), this.controller.getProductString());
//        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
//    }

    /**
     * Change Product Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void changeProduct() throws SQLException {
        System.out.println("UpdateProductController changeProduct() Test");
        for (Product pro:
                new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
        new ProductDB().addProduct(this.product);
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
        Assertions.assertTrue(this.controller.changeProduct("Name1","Desc1", "999", "999"));
        try {
            this.controller.changeProduct("", "", "", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Name1", this.controller.getProduct().getName());
        }
        try {
            this.controller.changeProduct(null, null, null, null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Name1", this.controller.getProduct().getName());
        }
        try {
            this.controller.changeProduct("", "Try", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Name1", this.controller.getProduct().getName());
        }
        try {
            this.controller.changeProduct(null, "Try", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Name1", this.controller.getProduct().getName());
        }
        try {
            this.controller.changeProduct("Try", "", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Desc1", this.controller.getProduct().getDescription());
        }
        try {
            this.controller.changeProduct("Try", null, "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Desc1", this.controller.getProduct().getDescription());
        }
        try {
            this.controller.changeProduct("Try", "Try", "", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getPrice());
        }
        try {
            this.controller.changeProduct("Try", "Try", null, "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getPrice());
        }
        try {
            this.controller.changeProduct("Try", "Try", "-1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getPrice());
        }
        try {
            this.controller.changeProduct("Try", "Try", "0", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getPrice());
        }
        try {
            this.controller.changeProduct("Try", "Try", "Try", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getPrice());
        }
        this.controller.changeProduct("Try", "Try", "1", "999");
        Assertions.assertEquals(1, this.controller.getProduct().getPrice());
        try {
            this.controller.changeProduct("Try", "Try", "1", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getWeight());
        }
        try {
            this.controller.changeProduct("Try", "Try", "1", null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getWeight());
        }
        try {
            this.controller.changeProduct("Try", "Try", "1", "-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getWeight());
        }
        try {
            this.controller.changeProduct("Try", "Try", "1", "0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getWeight());
        }
        try {
            this.controller.changeProduct("Try", "Try", "1", "Try");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(999, this.controller.getProduct().getWeight());
        }
        this.controller.changeProduct("Try", "Try", "1", "1");
        Assertions.assertEquals(1, this.controller.getProduct().getWeight());
        this.controller.changeProduct("Name1","Desc1", "999", "999");

        for (Product pro:
             new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
    }

    /**
     * Update Product Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void updateProduct() throws SQLException {
        System.out.println("UpdateProductController updateProduct() Test");
        for (Product pro:
                new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
        new ProductDB().addProduct(this.product);
        this.controller.getProductByReference(new ProductDB().getAllProducts().get(0).getReference());
        Assertions.assertTrue(this.controller.updateProduct());
        for (Product pro:
                new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
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
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }
}
