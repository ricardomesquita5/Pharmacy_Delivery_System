package lapr.project.model;

/**
 * The type Delivery.
 */
public class Delivery {

    /**
     * The deliveryId.
     */
    private int deliveryId;
    
    /**
     * Instantiates a new Delivery.
     *
     * @param deliveryId the delivery id
     */
    public Delivery(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * Instantiates a new Delivery.
     */
    public Delivery() {
    }

    /**
     * Get delivery id int.
     *
     * @return the int
     */
    public int getDeliveryId() {
        return this.deliveryId;
    }
}
