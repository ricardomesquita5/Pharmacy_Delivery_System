package lapr.project.controller;

import lapr.project.data.ProductDB;
import lapr.project.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove product controller.
 */
public class RemoveProductController {

    /**
     * The Product.
     */
    private Product product;

    /**
     * Instantiates a new Remove product controller.
     */
    public RemoveProductController() {
        //does nothing yet
    }

    /**
     * Gets products list.
     *
     * @return the products list
     * @throws SQLException the sql exception
     */
    public List<Product> getProductsList() throws SQLException {
        return new ProductDB().getAllProducts();
    }

    /**
     * Gets product by reference.
     *
     * @param productReference the product reference
     * @throws SQLException the sql exception
     */
    public void getProductByReference(int productReference) throws SQLException {
        this.product = new ProductDB().getProduct(productReference);
    }

    /**
     * Remove product boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean removeProduct() throws SQLException {
        this.product.remove();
        return true;
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
