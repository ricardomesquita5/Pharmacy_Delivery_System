package lapr.project.data;

import lapr.project.model.DeliveryByDrone;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Delivery by drone db.
 */
public class DeliveryByDroneDB extends DataHandler {

    /**
     * Add delivery by drone.
     *
     * @param del     the del
     * @param droneId the drone id
     */
    public void addDeliveryByDrone(DeliveryByDrone del, int droneId) {
        addDeliveryByDrone(del.getStatus(), droneId);
    }

    /**
     * Add Delivery By Drone.
     *
     * @param status  status
     * @param droneId droneId
     */
    private void addDeliveryByDrone(String status, int droneId) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addDeliveryByDrone(?,?,?) }")) {

            new DeliveryDB().addDelivery();
            int idDelivery = new DeliveryDB().getLastDeliveryId();
            callStmt.setString(1, status);
            callStmt.setInt(2, droneId);
            callStmt.setInt(3, idDelivery);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Update delivery by drone.
     *
     * @param del the del
     */
    public void updateDeliveryByDrone(DeliveryByDrone del) {
        updateDeliveryByDrone(del.getdeliveryByDroneId(), del.getStatus());
    }

    /**
     * Update Delivery By Drone.
     *
     * @param id     id
     * @param status status
     */
    private void updateDeliveryByDrone(int id, String status) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateDeliveryByDrone(?,?) }")) {
            callStmt.setInt(1, id);
            callStmt.setString(2, status);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets delivery by drone by drone id.
     *
     * @param status  the status
     * @param droneId the drone id
     * @return the delivery by drone by drone id
     */
    public DeliveryByDrone getDeliveryByDroneByDroneId(String status, int droneId) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDeliveryByDrone(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, status);
            callStmt.setInt(3, droneId);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int deliveryByDroneId = rSet.getInt(1);
                String status2 = rSet.getString(2);
                int deliveryId = rSet.getInt(3);

                return new DeliveryByDrone(deliveryByDroneId, status2, deliveryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return new DeliveryByDrone(0, null, 0);
    }

    /**
     * Remove delivery by drone.
     *
     * @param id the id
     */
    public void removeDeliveryByDrone(int id) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeDeliveryByDrone(?) }")) {
            callStmt.setInt(1, id);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all deliveries by drone.
     *
     * @param droneId the drone id
     * @return the all deliveries by drone
     */
    public List<DeliveryByDrone> getAllDeliveriesByDrone(int droneId) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllDeliveriesByDrone(?) }")) {


            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, droneId);

            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<DeliveryByDrone> deliveryByDroneList = new ArrayList<>();
            while (rSet.next()) {
                int deliveryByDroneId = rSet.getInt(1);
                String status2 = rSet.getString(2);
                int deliveryId = rSet.getInt(3);
                DeliveryByDrone d = new DeliveryByDrone(deliveryByDroneId, status2, deliveryId);
                deliveryByDroneList.add(d);
            }

            return deliveryByDroneList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("warning " + droneId);
    }
}
