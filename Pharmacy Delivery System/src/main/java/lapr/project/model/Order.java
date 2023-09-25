package lapr.project.model;

import lapr.project.data.OrderDB;

import java.sql.SQLException;

/**
 * The type Order.
 */
public class Order {

    /**
     * The idOrder.
     */
    private int idOrder;

    /**
     * The finalPrice.
     */
    private double finalPrice;

    /**
     * Instantiates a new Order.
     *
     * @param idOrder    the id order
     * @param finalPrice the final price
     */
    public Order(int idOrder, double finalPrice) {
        this.setIdOrder(idOrder);
        this.setFinalPrice(finalPrice);
    }

    /**
     * Instantiates a new Order.
     *
     * @param finalPrice the final price
     */
    public Order(double finalPrice) {
        this.setFinalPrice(finalPrice);

    }


    /**
     * Get final price double.
     *
     * @return the double
     */
    public double getFinalPrice() {
        return finalPrice;
    }

    /**
     * Get id order int.
     *
     * @return the int
     */
    public int getIdOrder() {
        return idOrder;
    }

    /**
     * Sets final price.
     *
     * @param finalPrice the final price
     */
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    /**
     * Sets id order.
     *
     * @param idOrder the id order
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * Save.
     *
     * @param pharmacyEmail the pharmacy email
     * @param clientEmail   the client email
     * @throws SQLException the sql exception
     */
    public void save(String pharmacyEmail, String clientEmail) throws SQLException {
        new OrderDB().addOrder(this, pharmacyEmail, clientEmail);
    }

    /**
     * ToString method.
     *
     * @return the String
     */
    @Override
    public String toString() {
        return "Order{" +
                "Order ID = '" + idOrder + '\'' +
                ", finalPrice = " + finalPrice +
                '}';
    }
}
