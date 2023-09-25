package lapr.project.controller;

import lapr.project.data.ProductDB;
import lapr.project.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update product controller.
 */
public class UpdateProductController {

    /**
     * The Product.
     */
    private Product product;

    /**
     * Instantiates a new Update product controller.
     */
    public UpdateProductController() {
        //does nothing yet
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
     * Gets products list.
     *
     * @return the products list
     * @throws SQLException the sql exception
     */
    public List<Product> getProductsList() throws SQLException {
        return new ProductDB().getAllProducts();
    }

    /**
     * Change product boolean.
     *
     * @param strName        the str name
     * @param strDescription the str description
     * @param strPrice       the str price
     * @param strWeight      the str weight
     * @return the boolean
     */
    public boolean changeProduct(String strName, String strDescription, String strPrice, String strWeight) {
        try {
            this.product.setName(strName);
            this.product.setDescription(strDescription);
            this.product.setPrice(strPrice);
            this.product.setWeight(strWeight);
            return true;
        } catch (IllegalArgumentException ia) {
            //this.product = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update product boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean updateProduct() throws SQLException {
        this.product.update();
        return true;
    }

    /**
     * Get Product.
     *
     * @return the product
     */
    public Product getProduct() {
        return this.product;
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
