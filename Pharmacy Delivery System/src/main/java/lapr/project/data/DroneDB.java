package lapr.project.data;

import lapr.project.model.Drone;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Drone Data Base.
 */
public class DroneDB extends DataHandler {
    /**
     * The Warning.
     */
    String warning = "No Pharmacy with Email:";

    /**
     * Gets Drone.
     *
     * @param id the drone's id
     * @return the drone
     */
    public Drone getDrone(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDrone(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, id);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int droneID = rSet.getInt(1);
                double capacity = rSet.getDouble(3);
                double power = rSet.getDouble(4);
                double maxPower = rSet.getDouble(5);
                int battery = rSet.getInt(6);
                double weight = rSet.getDouble(7);

                return new Drone(droneID, capacity, power, maxPower, battery, weight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Drone with ID:" + id);
    }

    /**
     * Add Drone.
     *
     * @param drone     the drone
     * @param pharEmail the pharmacy email
     */
    public void addDrone(Drone drone, String pharEmail) {
        addDrone(drone.getCapacity(), drone.getPower(), drone.getMaxBattery(), drone.getBattery(), drone.getWeight(), pharEmail);
    }

    /**
     * Add Drone.
     *
     * @param capacity  capacity
     * @param power     power
     * @param maxPower  max Power
     * @param battery   battery
     * @param weight    weight
     * @param pharEmail pharmacy Email
     */
    private void addDrone(Double capacity, Double power, Double maxPower, Integer battery, Double weight, String pharEmail) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addDrone(?,?,?,?,?,?) }")) {

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
     * Remove Drone.
     *
     * @param id the drone's id
     */
    public void removeDrone(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeDrone(?) }")) {

            callStmt.setInt(1, id);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all drones.
     *
     * @param pharEmail the pharmacy email
     * @return the list drones
     */
    public List<Drone> getAllDrones(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllDrones(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, pharEmail);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Drone> dronesList = new ArrayList<>();
            while (rSet.next()) {
                Drone s = new Drone(rSet.getInt(1), rSet.getDouble(3), rSet.getDouble(4), rSet.getDouble(5), rSet.getInt(6), rSet.getDouble(7));
                dronesList.add(s);
            }

            return dronesList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + pharEmail);
    }

    /**
     * Gets amount of drones of one pharmacy.
     *
     * @param pharEmail the pharmacy email
     * @return the amount of drones
     */
    public int getAmountOfDrones(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAmountOfDrones(?) }")) {
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
     * Update Drone.
     *
     * @param drone the drone
     */
    public void updateDrone(Drone drone) {
        updateDrone(drone.getId(), drone.getCapacity(), drone.getPower(), drone.getMaxBattery(), drone.getWeight());
    }

    /**
     * Udpate drone.
     *
     * @param id the id
     * @param capacity the capacity
     * @param power the power
     * @param maxPower the max power
     * @param weight the weight
     */

    private void updateDrone(Integer id, Double capacity, Double power, Double maxPower, Double weight) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateDrone(?,?,?,?,?) }")) {

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
     * Update Drone.
     *
     * @param drone the drone
     */
    public void updateDrone2(Drone drone) {
        updateDrone2(drone.getId(), drone.getBattery());
    }

    /**
     * Udpate drone2.
     *
     * @param id the id
     * @param battery the battery
     */

    private void updateDrone2(Integer id, int battery) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateDrone2(?,?) }")) {

            callStmt.setInt(1, id);
            callStmt.setDouble(2, battery);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets available drones of one pharmacy.
     *
     * @param pharEmail the pharmacy email
     * @return the available drones
     */
    public List<Drone> getAvailableDrones(String pharEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAvailableDrones(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, pharEmail);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Drone> dronesList = new ArrayList<>();
            while (rSet.next()) {
                Drone s = new Drone(rSet.getInt(1), rSet.getDouble(3), rSet.getDouble(4), rSet.getDouble(5), rSet.getInt(6), rSet.getDouble(7));
                dronesList.add(s);
            }

            return dronesList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException(warning + pharEmail);
    }
}
