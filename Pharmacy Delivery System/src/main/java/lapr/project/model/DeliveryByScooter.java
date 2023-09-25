package lapr.project.model;

import lapr.project.data.DeliveryByScooterDB;

/**
 * The type Delivery by scooter.
 */
public class DeliveryByScooter extends Delivery {

    /**
     * The deliveryByScooterId.
     */
    private int deliveryByScooterId;

    /**
     * The deliveryByScooterStatus.
     */
    private String deliveryByScooterStatus;

    /**
     * Instantiates a new Delivery by scooter.
     *
     * @param deliveryByScooterId     the delivery by scooter id
     * @param deliveryByScooterStatus the delivery by scooter status
     * @param deliveryId              the delivery id
     */
    public DeliveryByScooter(int deliveryByScooterId, String deliveryByScooterStatus, int deliveryId) {
        super(deliveryId);
        this.deliveryByScooterId = deliveryByScooterId;
        this.deliveryByScooterStatus = deliveryByScooterStatus;
    }

    /**
     * Instantiates a new Delivery by scooter.
     */
    public DeliveryByScooter() {
        this.deliveryByScooterStatus = "assigned";
    }

    /**
     * Getdelivery by scooter id int.
     *
     * @return the int
     */
    public int getdeliveryByScooterId() {
        return this.deliveryByScooterId;
    }

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus() {
        return this.deliveryByScooterStatus;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.deliveryByScooterStatus = status;
    }

    /**
     * Save.
     *
     * @param scooterId    the scooter id
     * @param emailCourier the email courier
     */
    public void save(int scooterId, String emailCourier) {
        new DeliveryByScooterDB().addDeliveryByScooter(this, emailCourier, scooterId);
    }

    /**
     * Update.
     */
    public void update() {
        new DeliveryByScooterDB().updateDeliveryByScooter(this);
    }
}
