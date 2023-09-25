package lapr.project.model;

import lapr.project.data.DataHandler;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Pharmacy product test.
 */
public class PharmacyProductTest {

    /**
     * The PharmacyProduct.
     */
    private PharmacyProduct productPharmacy;

    /**
     * Instantiates a new Product Test.
     */
    public PharmacyProductTest() {
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

        this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","0");
    }

    /**
     * User PharmacyProduct Test.
     */
    @Test
    void PharmacyProductConstructor() {
        System.out.println("PharmacyProduct Constructor Test");
        try {
            this.productPharmacy = new PharmacyProduct("","","");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy = new PharmacyProduct(null,null,null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy = new PharmacyProduct("test","testePharmacyProduct@gmail.com","0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy = new PharmacyProduct("-1","testePharmacyProduct@gmail.com","0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy = new PharmacyProduct("","testePharmacyProduct@gmail.com","0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy = new PharmacyProduct(null,"testePharmacyProduct@gmail.com","0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        this.productPharmacy = new PharmacyProduct("0","testePharmacyProduct@gmail.com","0");
        Assertions.assertEquals(0, this.productPharmacy.getReference());
        this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","0");
        try {
            this.productPharmacy = new PharmacyProduct("1","","0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
        }
        try {
            this.productPharmacy = new PharmacyProduct("1",null,"0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
        }
        try {
            this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com",null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","test");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","1");
        Assertions.assertEquals(1, this.productPharmacy.getAmount());
        this.productPharmacy = new PharmacyProduct("1","testePharmacyProduct@gmail.com","0");
    }

    /**
     * Gets Reference Test.
     */
    @Test
    public void getReference() {
        System.out.println("Product getReference() Test");
        Assertions.assertEquals(1, this.productPharmacy.getReference());
    }

    /**
     * Gets PharEmail Test.
     */
    @Test
    public void getPharEmail() {
        System.out.println("Product getPharEmail() Test");
        Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
    }

    /**
     * Gets Amount Test.
     */
    @Test
    public void getAmount() {
        System.out.println("Product getAmount() Test");
        Assertions.assertEquals(0, this.productPharmacy.getAmount());
    }

    /**
     * Sets Reference Test.
     */
    @Test
    public void setReference() {
        System.out.println("Product setReference() Test");
        this.productPharmacy.setReference("0");
        Assertions.assertEquals(0, this.productPharmacy.getReference());
        this.productPharmacy.setReference("1");
        Assertions.assertEquals(1, this.productPharmacy.getReference());
        try {
            this.productPharmacy.setReference(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy.setReference("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy.setReference("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
        try {
            this.productPharmacy.setReference("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(1, this.productPharmacy.getReference());
        }
    }

    /**
     * Sets Name Test.
     */
    @Test
    public void setPharEmail() {
        System.out.println("Product setPharEmail() Test");
        this.productPharmacy.setPharEmail("testePharmacyProduct@gmail.com");
        Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
        try {
            this.productPharmacy.setPharEmail(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
        }
        try {
            this.productPharmacy.setPharEmail("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testePharmacyProduct@gmail.com", this.productPharmacy.getPharEmail());
        }
    }

    /**
     * Sets Amount Test.
     */
    @Test
    public void setAmount() {
        System.out.println("Product setAmount() Test");
        this.productPharmacy.setAmount("1");
        Assertions.assertEquals(1, this.productPharmacy.getAmount());
        this.productPharmacy.setAmount("0");
        Assertions.assertEquals(0, this.productPharmacy.getAmount());
        try {
            this.productPharmacy.setAmount(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.setAmount("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.setAmount("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.setAmount("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.setAmount("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
    }

    /**
     * Adds Amount Test.
     */
    @Test
    public void addAmount() {
        System.out.println("Product addAmount() Test");
        this.productPharmacy.addAmount("1");
        Assertions.assertEquals(1, this.productPharmacy.getAmount());
        this.productPharmacy.addAmount("0");
        Assertions.assertEquals(1, this.productPharmacy.getAmount());
        this.productPharmacy.setAmount("0");
        Assertions.assertEquals(0, this.productPharmacy.getAmount());
        try {
            this.productPharmacy.addAmount(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.addAmount("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.addAmount("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.addAmount("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
        try {
            this.productPharmacy.addAmount("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(0, this.productPharmacy.getAmount());
        }
    }

    /**
     * Equals Test.
     */
    @Test
    public void equals() {
        System.out.println("Product equals() Test");

        User u = new User("Name22", "testEqualsProduct@isep.pt", "Password22");
        PharmacyProduct p1 = new PharmacyProduct(12, "Product3",20);
        PharmacyProduct p2 = new PharmacyProduct(1, "Product4",20);

        //objetos iguais
        Assertions.assertEquals(this.productPharmacy, this.productPharmacy);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.productPharmacy, u);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.productPharmacy, p1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.productPharmacy, p2);
    }

    /**
     * Hash Code Test.
     */
    @Test
    public void HashCodeTest() {
        System.out.println("Product hashCode() Test");
        PharmacyProduct p1 = new PharmacyProduct(1, "Product3", 20);
        PharmacyProduct p2 = new PharmacyProduct(12, "Product4", 20);
        Assertions.assertEquals(this.productPharmacy.hashCode(), p1.hashCode());
        Assertions.assertNotEquals(this.productPharmacy.hashCode(), p2.hashCode());
    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest() {
        System.out.println("Product toString() Test");
        String s = String.format("|| Product %d || Pharmacy Email: %s || Amount: %d ||",
                1, "testePharmacyProduct@gmail.com", 0);
        Assertions.assertEquals(s, this.productPharmacy.toString());
    }
}
