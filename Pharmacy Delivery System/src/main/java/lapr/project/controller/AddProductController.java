package lapr.project.controller;

import lapr.project.model.Product;

import java.sql.SQLException;

/**
 * The type Add product controller.
 */
public class AddProductController {

    /**
     * Object Product.
     */
    private Product product;

    /**
     * Instantiates a new Add product controller.
     */
    public AddProductController() {
        // Do nothing.
    }

    /**
     * New product boolean.
     *
     * @param reference   the reference
     * @param name        the name
     * @param description the description
     * @param price       the price
     * @param weight      the weight
     * @return the boolean
     */
    public boolean newProduct(String reference, String name, String description, String price, String weight) {
        try {
            this.product = new Product(reference, name, description, price, weight);
            return true;
        } catch (IllegalArgumentException ia) {
            this.product = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Add product boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean addProduct() throws SQLException {
        try {
            this.product.save();
            return true;
        } catch (SQLException | IllegalArgumentException iae) {
            throw new SQLException("Referecence " + this.product.getReference() + " already exists!");
        }
    }

    /**
     * Gets product string.
     *
     * @return the product string
     */
    public String getProductString() {
        return this.product.toString();
    }
}
