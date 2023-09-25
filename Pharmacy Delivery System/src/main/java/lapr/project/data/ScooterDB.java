package lapr.project.data;

import lapr.project.model.Scooter;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Scooter Data Base.
 */
public class ScooterDB extends DataHandler {
    /**
     * The Warning.
     */
    String warning = "No Pharmacy with Email:";

    /**
     * Gets Scooter.
     *
     * @param id the scooter's id
     * @return the scooter
     */
    public Scooter getScooter(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, id);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int scooterID = rSet.getInt(1);
                double capacity = rSet.getDouble(3);
                double power = rSet.getDouble(4);
                double maxPower = rSet.getDouble(5);
                int battery = rSet.getInt(6);
                double weight = rSet.getDouble(7);
                return new Scooter(scooterID, capacity, power, maxPower, battery, weight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Scooter with ID:" + id);
    }

    /**
     * Add Scooter.
     *
     * @param scooter   the scooter
     * @param pharEmail the pharmacy email
     */
    public void addScooter(Scooter scooter, String pharEmail) {
        addScooter(scooter.getCapacity(), scooter.getPower(), scooter.getMaxBattery(), scooter.getBattery(), scooter.getWeight(), pharEmail);
    }

    /**
     * Add Scooter.
     *
     * @param capacity  capacity
     * @param power     power
     * @param maxPower  max Power
     * @param battery   battery
     * @param weight    weight
     * @param pharEmail pharmacy Email
     */
    private void addScooter(Double capacity, Double power, Double maxPower, Integer battery, Double weight, String pharEmail) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call addScooter(?,?,?,?,?,?) }")) {

            callStmt.setString(1, pharEmail);
            callStmt.setDouble(2, capacity);
            callStmt.setDouble(3, power);
            callStmt.setDouble(4, maxPower);
            callStmt.setInt(5, battery);
            callStmt.setDouble(6, weight);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove Scooter.
     *
     * @param id the scooter's id
     */
    public void removeScooter(int id) {

        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call removeScooter(?) }")) {

            callStmt.setInt(1, id);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all scooters.
     *
     * @param pharEmail the pharmacy email
     * @return the list scooters
     */
    public List<Scooter> getAllScooters(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllScooters(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, pharEmail);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Scooter> scootersList = new ArrayList<>();
            while (rSet.next()) {
                Scooter s = new Scooter(rSet.getInt(1), rSet.getDouble(3), rSet.getDouble(4), rSet.getDouble(5), rSet.getInt(6), rSet.getDouble(7));
                scootersList.add(s);
            }
            return scootersList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + pharEmail);
    }

    /**
     * Gets amount of scooters of one pharmacy.
     *
     * @param pharEmail the pharmacy email
     * @return the amount of scooters
     */
    public int getAmountOfScooters(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAmountOfScooters(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, pharEmail);

            callStmt.execute();
            return callStmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + pharEmail);
    }

    /**
     * Update Scooter.
     *
     * @param scooter the scooter
     */
    public void updateScooter(Scooter scooter) {
        updateScooter(scooter.getId(), scooter.getCapacity(), scooter.getPower(), scooter.getMaxBattery(), scooter.getWeight());
    }

    /**
     * Update Scooter.
     *
     * @param id       id
     * @param capacity capacity
     * @param power    power
     * @param maxPower max Power
     * @param weight   weight
     */
    private void updateScooter(Integer id, Double capacity, Double power, Double maxPower, Double weight) {

        try (
                CallableStatement
                        callStmt = getConnection().prepareCall("{ call updateScooter(?,?,?,?,?) }")) {

            callStmt.setInt(1, id);
            callStmt.setDouble(2, capacity);
            callStmt.setDouble(3, power);
            callStmt.setDouble(4, maxPower);
            callStmt.setDouble(5, weight);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Update scooter battery.
     *
     * @param battery the battery
     * @param id      the id
     */
    public void updateScooterBattery(int battery, int id) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call updateScooterBattery(?,?) }")) {

            callStmt.setInt(1, battery);
            callStmt.setInt(2, id);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets available scooters of one pharmacy.
     *
     * @param pharEmail the pharmacy email
     * @return the available scooters
     */
    public List<Scooter> getAvailableScooters(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAvailableScooters(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, pharEmail);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Scooter> scootersList = new ArrayList<>();
            while (rSet.next()) {
                Scooter s = new Scooter(rSet.getInt(1), rSet.getDouble(3), rSet.getDouble(4), rSet.getDouble(5), rSet.getInt(6), rSet.getDouble(7));
                scootersList.add(s);
            }
            return scootersList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + pharEmail);
    }

    /**
     * Gets scooter byemail courier on del.
     *
     * @param email the email
     * @return the scooter byemail courier on del
     */
    public Scooter getScooterByemailCourierOnDel(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterByemailCourierOnDel(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, email);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {
                return new Scooter(rSet.getInt(1), rSet.getDouble(3), rSet.getDouble(4), rSet.getDouble(5), rSet.getInt(6), rSet.getDouble(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + email);
    }
}
