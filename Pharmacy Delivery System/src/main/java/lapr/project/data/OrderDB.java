package lapr.project.data;

import lapr.project.model.Order;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Order db.
 */
public class OrderDB extends DataHandler {
    /**
     * The String Aux.
     */
    private static final String STRING_AUX = "No Order with ID:";

    /**
     * Gets order.
     *
     * @param idOrder the id order
     * @return the order
     */
    public Order getOrder(int idOrder) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getOrder(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getOrder".
            callStmt.setInt(2, idOrder);

            // Executa a invocação da função "getOrder".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {

                int orderId = rSet.getInt(1);
                double finalPrice = rSet.getDouble(2);

                return new Order(orderId, finalPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(STRING_AUX + idOrder);
    }

    /**
     * Gets all orders.
     *
     * @param pharEmail the phar email
     * @return the all orders
     */
    public List<Order> getAllOrders(String pharEmail) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllOrders(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getAllOrders".
            callStmt.setString(2, pharEmail);

            // Executa a invocação da função "getAllOrders".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Order> ordersList = new ArrayList<>();
            while (rSet.next()) {
                Order o = new Order(rSet.getInt(1), rSet.getInt(2));
                ordersList.add(o);
            }

            return ordersList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy with Email:" + pharEmail);
    }

    /**
     * Add order.
     *
     * @param order         the order
     * @param pharmacyEmail the pharmacy email
     * @param clientEmail   the client email
     */
    public void addOrder(Order order, String pharmacyEmail, String clientEmail) {
        addOrder(order.getFinalPrice(), pharmacyEmail, clientEmail);
    }

    /**
     * Add Order.
     *
     * @param finalPrice    final Price
     * @param pharmacyEmail pharmacy Email
     * @param clientEmail   client Email
     */
    private void addOrder(double finalPrice, String pharmacyEmail, String clientEmail) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call addOrder(?,?,?) }")) {

            callStmt.setDouble(1, finalPrice);
            callStmt.setString(2, clientEmail);
            callStmt.setString(3, pharmacyEmail);


            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets order address coordinates by client email.
     *
     * @param emailClient the email client
     * @return the order address coordinates by client email
     */
    public String getOrderAddressCoordinatesByClientEmail(String emailClient) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getOrderAddressCoordinatesByClientEmail(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.LONGVARCHAR);
            callStmt.setString(2, emailClient);
            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Client with email:" + emailClient);
    }

    /**
     * Gets email clientby order id.
     *
     * @param idOrder the id order
     * @return the email clientby order id
     */
    public String getEmailClientbyOrderId(int idOrder) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getEmailClientbyOrderId(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setInt(2, idOrder);
            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(STRING_AUX + idOrder);
    }

    /**
     * Gets by order id.
     *
     * @param idOrder the id order
     * @return the by order id
     */
    public double getweightByOrderId(int idOrder) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getweightByOrderId(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
            callStmt.setInt(2, idOrder);
            callStmt.execute();

            return callStmt.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(STRING_AUX + idOrder);
    }

    /**
     * Update order.
     *
     * @param ord        the ord
     * @param deliveryId the delivery id
     */
    public void updateOrder(Order ord, int deliveryId) {
        updateOrder(ord.getIdOrder(), deliveryId);
    }


    /**
     * Update order.
     *
     * @param id         the id
     * @param deliveryId the delivery id
     */
    public void updateOrder(int id, int deliveryId) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateOrder(?,?) }")) {
            callStmt.setInt(1, id);
            callStmt.setInt(2, deliveryId);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove order.
     *
     * @param idOrder the id order
     */
    public void removeOrder(int idOrder) {
        try (CallableStatement callStmt = getConnection().prepareCall("{call removeOrder(?) }")) {
            callStmt.setInt(1, idOrder);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }


    /**
     * Gets orders by delivery id.
     *
     * @param deliveryId the delivery id
     * @return the orders by delivery id
     */
    public LinkedList<Order> getOrdersByDeliveryId(int deliveryId) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getOrdersByDeliveryId(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, deliveryId);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            LinkedList<Order> ordersList = new LinkedList<>();
            while (rSet.next()) {
                Order o = new Order(rSet.getInt(1), rSet.getInt(2));
                ordersList.add(o);
            }

            return ordersList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No order with id: " + deliveryId);
    }

    /**
     * Gets last client order.
     *
     * @param clientEnail the client enail
     * @return the last client order
     */
    public int getLastClientOrder(String clientEnail) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getLastClientOrder(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, clientEnail);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {

                return rSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No client with email" + clientEnail);
    }
}