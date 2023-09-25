package lapr.project.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Delivery test.
 */
class DeliveryTest {

    /**
     * The Delivery.
     */
    private final Delivery delivery;

    /**
     * Instantiates a new Delivery test.
     */
    public DeliveryTest() {
        this.delivery = new Delivery(111);
    }

    /**
     * Gets delivery id.
     */
    @Test
    void getDeliveryId() {
        System.out.println("Delivery getDeliveryId() Test");
        Assertions.assertEquals(111,this.delivery.getDeliveryId());
    }



}