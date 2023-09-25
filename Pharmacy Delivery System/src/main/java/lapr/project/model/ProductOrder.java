package lapr.project.model;

import lapr.project.data.ProductOrderDB;

import java.sql.SQLException;

/**
 * The type Product order.
 */
public class ProductOrder {

    /**
     * The ProductOrder's ID.
     */
    private int reference;
    /**
     * The ProductOrder's ID of Order.
     */
    private int idOrder;
    /**
     * The ProductOrder's amount.
     */
    private int amount;

    /**
     * Instantiates a new Product order.
     *
     * @param reference the reference
     * @param idOrder   the id order
     * @param amount    the amount
     */
    public ProductOrder(int reference, int idOrder, int amount) {
        this.setReference(reference);
        this.setIdOrder(idOrder);
        this.setAmount(amount);
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
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
     * Sets reference.
     *
     * @param reference the reference
     */
    public void setReference(int reference) {
        this.reference = reference;
    }

    /**
     * Get reference int.
     *
     * @return the int
     */
    public int getReference() {
        return reference;
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
     * Get amount int.
     *
     * @return the int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    public void save() throws SQLException {
        new ProductOrderDB().addProductOrder(this);
    }
}
