package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Transference Test.
 */
class TransferenceTest {

    /**
     * The Transference.
     */
    public Transference transference;

    /**
     * Instantiates a new Transference Test.
     */
    public TransferenceTest(){
        this.transference = new Transference("p1@gmail.com","p2@gmail.com",123,5);
    }

    /**
     * Gets pharmacy in email Test.
     */
    @Test
    void getPharInEmail() {
        System.out.println("Transference getPharInEmail() Test");
        Assertions.assertEquals("p1@gmail.com",this.transference.getPharInEmail());
    }

    /**
     * Gets pharmacy out email Test.
     */
    @Test
    void getPharOutEmail() {
        System.out.println("Transference getPharOutEmail() Test");
        Assertions.assertEquals("p2@gmail.com",this.transference.getPharOutEmail());
    }

    /**
     * Gets product's reference Test.
     */
    @Test
    void getProductReference() {
        System.out.println("Transference getProductReference() Test");
        Assertions.assertEquals(123,this.transference.getProductReference());
    }

    /**
     * Gets product's amount Test.
     */
    @Test
    void getAmount() {
        System.out.println("Transference getAmount() Test");
        Assertions.assertEquals(5,this.transference.getAmount());
    }
}