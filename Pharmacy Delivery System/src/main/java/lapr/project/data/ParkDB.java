package lapr.project.data;

import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Park db.
 */
public class ParkDB extends DataHandler {

    /**
     * Gets park.
     *
     * @param id the id
     * @return the park
     */
    public Park getPark(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPark(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, id);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int parkID = rSet.getInt(1);
                String designation = rSet.getString(3);
                int spotsCapacity = rSet.getInt(4);
                double powerCapacity = rSet.getDouble(5);
                return new Park(parkID, designation, spotsCapacity, powerCapacity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Park with ID:" + id);
    }

    /**
     * Add park.
     *
     * @param park      the park
     * @param pharEmail the phar email
     */
    public void addPark(Park park, String pharEmail) {
        addPark(park.getDesignation(), park.getSpotsCapacity(), park.getPowerCapacity(), pharEmail);
    }

    /**
     * Add Park.
     *
     * @param designation   designation
     * @param spotsCapacity spots Capacity
     * @param powerCapacity power Capacity
     * @param pharEmail     pharmacy Email
     */
    private void addPark(String designation, int spotsCapacity, Double powerCapacity, String pharEmail) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call addPark(?,?,?,?) }")) {

            callStmt.setString(1, pharEmail);
            callStmt.setString(2, designation);
            callStmt.setInt(3, spotsCapacity);
            callStmt.setDouble(4, powerCapacity);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets last park.
     *
     * @param email the email
     * @return the last park
     */
    public int getLastPark(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getLastPark(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, email);

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
        throw new IllegalArgumentException("No Park associated to the pharmacy with the email:" + email);
    }

    /**
     * Remove park.
     *
     * @param id the id
     */
    public void removePark(int id) {

        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call removePark(?) }")) {

            callStmt.setInt(1, id);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all parks.
     *
     * @param pharEmail the phar email
     * @return the all parks
     */
    public List<Park> getAllParks(String pharEmail) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllParks(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, pharEmail);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Park> parksList = new ArrayList<>();
            while (rSet.next()) {
                Park p = new Park(rSet.getInt(1), rSet.getString(3), rSet.getInt(4), rSet.getDouble(5));
                parksList.add(p);
            }
            return parksList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Park associated to the pharmacy with the email:" + pharEmail);
    }
}
