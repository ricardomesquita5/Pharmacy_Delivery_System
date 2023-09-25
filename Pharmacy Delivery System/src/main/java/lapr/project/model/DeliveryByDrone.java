package lapr.project.model;

import lapr.project.data.DeliveryByDroneDB;

/**
 * The type Delivery by drone.
 */
public class DeliveryByDrone extends Delivery {

    /**
     * The deliveryByDroneId.
     */
    private int deliveryByDroneId;

    /**
     * The deliveryByDroneStatus.
     */
    private String deliveryByDroneStatus;

    /**
     * Instantiates a new Delivery by drone.
     *
     * @param deliveryByDroneId     the delivery by drone id
     * @param deliveryByDroneStatus the delivery by drone status
     * @param deliveryId            the delivery id
     */
    public DeliveryByDrone(int deliveryByDroneId, String deliveryByDroneStatus, int deliveryId) {
        super(deliveryId);
        this.deliveryByDroneId = deliveryByDroneId;
        this.deliveryByDroneStatus = deliveryByDroneStatus;
    }

    /**
     * Instantiates a new Delivery by drone.
     */
    public DeliveryByDrone() {
        this.deliveryByDroneStatus = "assigned";
    }

    /**
     * Getdelivery by drone id int.
     *
     * @return the int
     */
    public int getdeliveryByDroneId() {
        return this.deliveryByDroneId;
    }

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus() {
        return this.deliveryByDroneStatus;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.deliveryByDroneStatus = status;
    }

    /**
     * Save.
     *
     * @param droneId the drone id
     */
    public void save(int droneId) {
        new DeliveryByDroneDB().addDeliveryByDrone(this, droneId);
    }

    /**
     * Update.
     */
    public void update() {
        new DeliveryByDroneDB().updateDeliveryByDrone(this);
    }

}
