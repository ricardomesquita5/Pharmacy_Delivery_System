package lapr.project.model;

import lapr.project.data.*;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Product test.
 */
public class ProductTest {

    /**
     * The Product.
     */
    private Product product;

    /**
     * Instantiates a new Product Test.
     */
    public ProductTest() {
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

        this.product = new Product("1","Product1", "Description1", "10", "1");
        this.product = new Product(10, "Product2", "Description2", 20, 2);
    }

    /**
     * User Product Test.
     */
    @Test
    void ProductConstructor() {
        System.out.println("Product Constructor Test");
        try {
            this.product = new Product("10","", "", "", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Product2", this.product.getName());
        }
        try {
            this.product = new Product("10",null, null, null, null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Product2", this.product.getName());
        }
        try {
            this.product = new Product("10","", "Try", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Product2", this.product.getName());
        }
        try {
            this.product = new Product("10",null, "Try", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Product2", this.product.getName());
        }
        try {
            this.product = new Product("10","Try", "", "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Description2", this.product.getDescription());
        }
        try {
            this.product = new Product("10","Try", null, "1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("Description2", this.product.getDescription());
        }
        try {
            this.product = new Product("10","Try", "Try", "", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(20, this.product.getPrice());
        }
        try {
            this.product = new Product("10","Try", "Try", null, "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(20, this.product.getPrice());
        }
        try {
            this.product = new Product("10","Try", "Try", "-1", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(20, this.product.getPrice());
        }
        try {
            this.product = new Product("10","Try", "Try", "0", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(20, this.product.getPrice());
        }
        try {
            this.product = new Product("10","Try", "Try", "Try", "1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0.0, this.product.getPrice());
        }
        this.product = new Product("10","Try", "Try", "1", "2");
        Assertions.assertEquals(1, this.product.getPrice());
        try {
            this.product = new Product("10","Try", "Try", "1", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(2, this.product.getWeight());
        }
        try {
            this.product = new Product("10","Try", "Try", "1", null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(2, this.product.getWeight());
        }
        try {
            this.product = new Product("10","Try", "Try", "1", "-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(2, this.product.getWeight());
        }
        try {
            this.product = new Product("10","Try", "Try", "1", "0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(2, this.product.getWeight());
        }
        try {
            this.product = new Product("10","Try", "Try", "1", "Try");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0.0, this.product.getWeight());
        }
        this.product = new Product("10","Try", "Try", "1", "1");
        Assertions.assertEquals(1, this.product.getWeight());
        this.product = new Product(10, "Product2", "Description2", 20, 2);
    }

    /**
     * Gets Reference Test.
     */
    @Test
    public void getReference() {
        System.out.println("Product getReference() Test");
        Assertions.assertEquals(10, this.product.getReference());
    }

    /**
     * Gets Name Test.
     */
    @Test
    public void getName() {
        System.out.println("Product getName() Test");
        Assertions.assertEquals("Product2", this.product.getName());
    }

    /**
     * Gets Description Test.
     */
    @Test
    public void getDescription() {
        System.out.println("Product getDescription() Test");
        Assertions.assertEquals("Description2", this.product.getDescription());
    }

    /**
     * Gets Price Test.
     */
    @Test
    public void getPrice() {
        System.out.println("Product getPrice() Test");
        Assertions.assertEquals(20, this.product.getPrice(), 0);
    }

    /**
     * Gets Weight Test.
     */
    @Test
    public void getWeight() {
        System.out.println("Product getWeight() Test");
        Assertions.assertEquals(2, this.product.getWeight(), 0);
    }

    /**
     * Sets Reference Test.
     */
    @Test
    public void setReference() {
        System.out.println("Product setReference() Test");
        this.product.setReference("20");
        Assertions.assertEquals(20, this.product.getReference());
        try {
            this.product.setReference(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(20, this.product.getReference());
        }
        try {
            this.product.setReference("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(20, this.product.getReference());
        }
        try {
            this.product.setReference("-1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(20, this.product.getReference());
        }
        try {
            this.product.setReference("0");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(20, this.product.getReference());
        }
        try {
            this.product.setReference("asasad");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(20, this.product.getReference());
        }
    }

    /**
     * Sets Name Test.
     */
    @Test
    public void setName() {
        System.out.println("Product setName() Test");
        this.product.setName("P2");
        Assertions.assertEquals("P2", this.product.getName());
    }

    /**
     * Sets Description Test.
     */
    @Test
    public void setDescription() {
        System.out.println("Product setDescription() Test");
        this.product.setDescription("Desc2");
        Assertions.assertEquals("Desc2", this.product.getDescription());
    }

    /**
     * Sets Price Test.
     */
    @Test
    public void setPrice() {
        System.out.println("Product setPrice() Test");
        this.product.setPrice("20");
        Assertions.assertEquals(20, this.product.getPrice(), 0);
        this.product.setPrice("10");
        Assertions.assertEquals(10, this.product.getPrice(), 0);
        try {
            this.product.setPrice(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getPrice(), 0);
        }
        try {
            this.product.setPrice("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getPrice(), 0);
        }
        try {
            this.product.setPrice("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getPrice(), 0);
        }
        try {
            this.product.setPrice("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getPrice(), 0);
        }
        try {
            this.product.setPrice("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getPrice(), 0);
        }
    }

    /**
     * Sets Weight Test.
     */
    @Test
    public void setWeight() {
        System.out.println("Product setWeight() Test");
        this.product.setWeight("20");
        Assertions.assertEquals(20, this.product.getWeight(), 0);
        this.product.setWeight("10");
        Assertions.assertEquals(10, this.product.getWeight(), 0);
        try {
            this.product.setWeight(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getWeight(), 0);
        }
        try {
            this.product.setWeight("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getWeight(), 0);
        }
        try {
            this.product.setWeight("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getWeight(), 0);
        }
        try {
            this.product.setWeight("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getWeight(), 0);
        }
        try {
            this.product.setWeight("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(10, this.product.getWeight(), 0);
        }
    }

    /**
     * Save Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void save() throws SQLException {
        System.out.println("Product save() Test");
        int size = new ProductDB().getAllProducts().size();
        this.product.save();
        Assertions.assertEquals(size + 1, new ProductDB().getAllProducts().size());
        new ProductDB().removeProduct(new ProductDB().getAllProducts().get(0).getReference());
    }

//    /**
//     * Remove Test.
//     *
//     * @throws SQLException the sql exception
//     */
//    @Test
//    public void remove() throws SQLException {
//        System.out.println("Product remove() Test");
//        this.product.save();
//        int size = new ProductDB().getAllProducts().size();
//        Product p = new ProductDB().getAllProducts().get(0);
//        p.remove();
//        Assertions.assertEquals(size - 1, new ProductDB().getAllProducts().size());
//        try {
//            p.remove();
//        } catch (IllegalArgumentException ia) {
//            Assertions.assertTrue(true);
//        }
//    }

    /**
     * Update Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void update() throws SQLException {
        System.out.println("Product update() Test");
        for (Product pro:
                new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
        this.product.save();
        Product p = new ProductDB().getAllProducts().get(0);
        p.setWeight("100");
        p.update();
        Assertions.assertEquals(p.getWeight(), new ProductDB().getAllProducts().get(0).getWeight());
        for (Product pro:
             new ProductDB().getAllProducts()) {
            new ProductDB().removeProduct(pro.getReference());
        }
    }

    /**
     * Equals Test.
     */
    @Test
    public void equals() {
        System.out.println("Product equals() Test");

        User u = new User("Name22", "testEqualsProduct@isep.pt", "Password22");
        Product p1 = new Product(12, "Product3", "Description3", 20, 2);
        Product p2 = new Product(10, "Product4", "Description4", 20, 2);

        //objetos iguais
        Assertions.assertEquals(this.product, this.product);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.product, u);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.product, p1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.product, p2);
    }

    /**
     * Hash Code Test.
     */
    @Test
    public void HashCodeTest() {
        System.out.println("Product hashCode() Test");
        Product p1 = new Product(10, "Product3", "Description3", 20, 2);
        Product p2 = new Product(12, "Product4", "Description4", 20, 2);
        Assertions.assertEquals(this.product.hashCode(), p1.hashCode());
        Assertions.assertNotEquals(this.product.hashCode(), p2.hashCode());
    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest() {
        System.out.println("Product toString() Test");
        String s = String.format("|| Product %d || Name: %s || Description: %s || Price: %.2f || Weigh: %.2f ||",
                10, "Product2", "Description2", 20.0, 2.0);
        Assertions.assertEquals(s, this.product.toString());
    }
}