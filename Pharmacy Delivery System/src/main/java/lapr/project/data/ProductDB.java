package lapr.project.data;

import lapr.project.model.Product;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Product db.
 */
public class ProductDB extends DataHandler {
    /**
     * Gets product.
     *
     * @param reference the reference
     * @return the product
     */
    public Product getProduct(int reference) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProduct(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setInt(2, reference);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int productReference = rSet.getInt(1);
                String name = rSet.getString(2);
                String description = rSet.getString(3);
                double price = rSet.getDouble(4);
                double weight = rSet.getDouble(5);
                return new Product(productReference, name, description, price, weight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Product with ID:" + reference);
    }

    /**
     * Add product.
     *
     * @param product the product
     */
    public void addProduct(Product product) {
        addProduct(product.getReference(), product.getName(), product.getDescription(), product.getPrice(), product.getWeight());
    }

    /**
     * Add product.
     *
     * @param reference the reference
     * @param name the name
     * @param description the description
     * @param price the price
     * @param weight the weight
     */
    private void addProduct(int reference, String name, String description, Double price, Double weight) {
        try (

                CallableStatement callStmt = getConnection().prepareCall("{ call addProduct(?,?,?,?,?) }")) {

            callStmt.setInt(1, reference);
            callStmt.setString(2, name);
            callStmt.setString(3, description);
            callStmt.setDouble(4, price);
            callStmt.setDouble(5, weight);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove product.
     *
     * @param reference the reference
     */
    public void removeProduct(int reference) {
        try (
                CallableStatement
                        callStmt = getConnection().prepareCall("{ call removeProduct(?) }")) {

            callStmt.setInt(1, reference);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public List<Product> getAllProducts() {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getAllProducts() }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Product> productsList = new ArrayList<>();
            while (rSet.next()) {
                Product p = new Product(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getLong(4), rSet.getDouble(5));
                productsList.add(p);
            }
            return productsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Products in the system!");
    }

    /**
     * Update product.
     *
     * @param product the product
     */
    public void updateProduct(Product product) {
        updateProduct(product.getReference(), product.getName(), product.getDescription(), product.getPrice(), product.getWeight());
    }

    /**
     * Update Product.
     *
     * @param reference   the reference
     * @param name        the name
     * @param description the description
     * @param price       the price
     * @param weight      the weight
     */
    private void updateProduct(int reference, String name, String description, double price, double weight) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateProduct(?,?,?,?,?) }")) {

            callStmt.setInt(1, reference);
            callStmt.setString(2, name);
            callStmt.setString(3, description);
            callStmt.setDouble(4, price);
            callStmt.setDouble(5, weight);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}