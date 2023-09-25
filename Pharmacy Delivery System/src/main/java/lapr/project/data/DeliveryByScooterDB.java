package lapr.project.data;

import lapr.project.model.DeliveryByScooter;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Delivery by scooter db.
 */
public class DeliveryByScooterDB extends DataHandler {

    /**
     * Add delivery by scooter.
     *
     * @param del          the del
     * @param emailCourier the email courier
     * @param scooterId    the scooter id
     */
    public void addDeliveryByScooter(DeliveryByScooter del, String emailCourier, int scooterId) {
        addDeliveryByScooter(del.getStatus(), emailCourier, scooterId);
    }

    /**
     * Add Delivery By Scooter.
     *
     * @param status       status
     * @param emailCourier email Courier
     * @param scooterId    scooter Id
     */
    private void addDeliveryByScooter(String status, String emailCourier, int scooterId) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addDeliveryByScooter(?,?,?,?) }")) {

            new DeliveryDB().addDelivery();
            int idDelivery = new DeliveryDB().getLastDeliveryId();
            callStmt.setString(1, status);
            callStmt.setString(2, emailCourier);
            callStmt.setInt(3, scooterId);
            callStmt.setInt(4, idDelivery);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Update delivery by scooter.
     *
     * @param del the del
     */
    public void updateDeliveryByScooter(DeliveryByScooter del) {
        updateDeliveryByScooter(del.getdeliveryByScooterId(), del.getStatus());
    }

    /**
     * Update Delivery By Scooter
     *
     * @param id     id
     * @param status status
     */
    private void updateDeliveryByScooter(int id, String status) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateDeliveryByScooter(?,?) }")) {

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
     * Gets delivery by scooter by email courier.
     *
     * @param status       the status
     * @param emailCourier the email courier
     * @return the delivery by scooter by email courier
     */
    public DeliveryByScooter getDeliveryByScooterByEmailCourier(String status, String emailCourier) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDelivery_Scooter(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, status);
            callStmt.setString(3, emailCourier);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int deliveryByScooterId = rSet.getInt(1);
                String status2 = rSet.getString(2);
                int deliveryId = rSet.getInt(4);

                return new DeliveryByScooter(deliveryByScooterId, status2, deliveryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return new DeliveryByScooter(0, null, 0);
    }

    /**
     * Gets last delivery by scooter id.
     *
     * @param id the id
     * @return the last delivery by scooter id
     */
    public String getLastDeliveryByScooterId(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getLastDeliveryByScooterId(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setInt(2, id);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {

                return rSet.getString(3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No order with Scooter ID:" + id);
    }

    /**
     * Remove delivery by scooter.
     *
     * @param idScooter    the id scooter
     * @param emailCourier the email courier
     */
    public void removeDeliveryByScooter(int idScooter, String emailCourier) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeDeliveryByScooter(?,?) }")) {
            callStmt.setInt(1, idScooter);
            callStmt.setString(2, emailCourier);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}
