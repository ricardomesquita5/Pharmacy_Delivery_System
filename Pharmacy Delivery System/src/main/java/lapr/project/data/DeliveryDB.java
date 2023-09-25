package lapr.project.data;


import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Delivery db.
 */
public class DeliveryDB extends DataHandler {

    /**
     * Add delivery.
     */
    public void addDelivery() {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addDelivery() }")) {

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets last delivery id.
     *
     * @return the last delivery id
     */
    public int getLastDeliveryId() {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getLastDelivery() }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

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
        throw new IllegalArgumentException();
    }

    /**
     * Remove delivery.
     *
     * @param id the id
     */
    public void removeDelivery(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeDelivery(?) }")) {

            callStmt.setInt(1, id);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}
