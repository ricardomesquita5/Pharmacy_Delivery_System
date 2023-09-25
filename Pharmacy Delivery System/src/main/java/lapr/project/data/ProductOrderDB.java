package lapr.project.data;

import lapr.project.model.ProductOrder;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Product order db.
 */
public class ProductOrderDB extends DataHandler {

    /**
     * Gets product order.
     *
     * @param reference the reference
     * @param idOrder   the id order
     * @return the product order
     */
    public ProductOrder getProductOrder(int reference, int idOrder) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getProductOrder(?,?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getProductOrder".
            callStmt.setInt(2, idOrder);
            callStmt.setInt(3, reference);

            // Executa a invocação da função "getProductOrder".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {
                int orderId = rSet.getInt(1);
                int ref = rSet.getInt(2);
                int amount = rSet.getInt(3);
                return new ProductOrder(ref, orderId, amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No ProductOrder with ID:" + idOrder + "and Reference:" + reference);

    }

    /**
     * Add product order.
     *
     * @param productOrder the product order
     */
    public void addProductOrder(ProductOrder productOrder) {
        addProductOrder(productOrder.getIdOrder(), productOrder.getReference(), productOrder.getAmount());
    }

    /**
     * Add product order.
     *
     * @param idOrder   the id order
     * @param reference the reference
     * @param amount    the amount
     */
    public void addProductOrder(int idOrder, int reference, int amount) {
        try (
                CallableStatement
                        callStmt = getConnection().prepareCall("{ call addProductOrder(?,?,?) }")) {

            callStmt.setInt(1, idOrder);
            callStmt.setInt(2, reference);
            callStmt.setInt(3, amount);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

    }

    /**
     * Remove product order.
     *
     * @param reference the reference
     * @param idOrder   the id order
     */
    public void removeProductOrder(int reference, int idOrder) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{call removeProductOrder(?,?)}")) {

            callStmt.setInt(1, reference);
            callStmt.setInt(2, idOrder);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all product order.
     *
     * @return the all product order
     */
    public List<ProductOrder> getAllProductOrder() {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getAllProductOrders() }")) {

            callStmt.registerOutParameter(1, oracle.jdbc.internal.OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<ProductOrder> productOrdersList = new ArrayList<>();
            while (rSet.next()) {
                ProductOrder p = new ProductOrder(rSet.getInt(2), rSet.getInt(3), rSet.getInt(1));
                productOrdersList.add(p);
            }
            return productOrdersList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Product Orders in the system!");
    }


}
