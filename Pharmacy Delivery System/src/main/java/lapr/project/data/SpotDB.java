package lapr.project.data;

import lapr.project.model.Spot;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Spot db.
 */
public class SpotDB extends DataHandler {

    /**
     * Gets spot.
     *
     * @param id     the id
     * @param idPark the id park
     * @return the spot
     */
    public Spot getSpot(int id, int idPark) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getSpot(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, id);
            callStmt.setInt(3, idPark);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int spotID = rSet.getInt(1);
                String chargingSpot = rSet.getString(3);
                int idPark2 = rSet.getInt(2);
                return new Spot(spotID, chargingSpot, idPark2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Spot with ID: " + id + " and ParkID: " + idPark);
    }

    /**
     * Add spot.
     *
     * @param spot   the spot
     * @param idPark the id park
     */
    public void addSpot(Spot spot, int idPark) {
        addSpot(spot.getId(), spot.getChargingSpot(), idPark);
    }

    /**
     * Add spot.
     *
     * @param id           the id
     * @param chargingSpot the chargingSpot
     * @param idPark       the idPark
     */
    private void addSpot(int id, String chargingSpot, int idPark) {
        try (
                CallableStatement
                        callStmt = getConnection().prepareCall("{ call addSpot(?,?,?) }")) {

            callStmt.setInt(1, id);
            callStmt.setString(2, chargingSpot);
            callStmt.setInt(3, idPark);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove spot.
     *
     * @param id     the id
     * @param idPark the id park
     */
    public void removeSpot(int id, int idPark) {

        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call removeSpot(?,?) }")) {

            callStmt.setInt(1, id);
            callStmt.setInt(2, idPark);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all spots.
     *
     * @param idPark the id park
     * @return the all spots
     */
    public List<Spot> getAllSpots(int idPark) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getAllSpots(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setInt(2, idPark);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Spot> spotsList = new ArrayList<>();
            while (rSet.next()) {
                Spot s = new Spot(rSet.getInt(1), rSet.getString(3));
                spotsList.add(s);
            }
            return spotsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Spot associated to the park with the id:" + idPark);
    }

    /**
     * Gets available spots.
     *
     * @param email       the email
     * @param designation the designation
     * @return the available spots
     */
    public List<Spot> getAvailableSpots(String email, String designation) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getAvailableSpots(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, email);
            callStmt.setString(3, designation);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Spot> spotsList = new ArrayList<>();
            while (rSet.next()) {
                Spot s = new Spot(rSet.getInt(1), rSet.getString(3), rSet.getInt(2));
                spotsList.add(s);
            }
            return spotsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Spots in the pharmacy with the email:" + email);
    }

    /**
     * Update spot.
     *
     * @param id        the id
     * @param idPark    the id park
     * @param idScooter the id scooter
     */
    public void updateSpot(int id, int idPark, Integer idScooter) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call updateSpot(?,?,?) }")) {
            callStmt.setInt(1, id);
            callStmt.setInt(2, idPark);
            callStmt.setInt(3, idScooter);
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Update spot 2.
     *
     * @param id     the id
     * @param idPark the id park
     */
    public void updateSpot2(int id, int idPark) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call updateSpot2(?,?) }")) {
            callStmt.setInt(1, id);
            callStmt.setInt(2, idPark);
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Update spot 3.
     *
     * @param id      the id
     * @param idPark  the id park
     * @param idDrone the id drone
     */
    public void updateSpot3(int id, int idPark, Integer idDrone) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call updateSpot3(?,?,?) }")) {
            callStmt.setInt(1, id);
            callStmt.setInt(2, idPark);
            callStmt.setInt(3, idDrone);
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets spot by scooter id.
     *
     * @param scooterId   the scooter id
     * @param designation the designation
     * @return the spot by scooter id
     */
    public Spot getSpotByScooterId(int scooterId, String designation) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getSpotByScooterId(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, scooterId);
            callStmt.setString(3, designation);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int spotID = rSet.getInt(1);
                int parkId = rSet.getInt(2);
                String charging = rSet.getString(3);
                return new Spot(spotID, charging, parkId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Spot with a scooter with ID: " + scooterId);
    }

    /**
     * Gets spot by drone id.
     *
     * @param droneId     the drone id
     * @param designation the designation
     * @return the spot by drone id
     */
    public Spot getSpotByDroneId(int droneId, String designation) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getSpotByDroneId(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, droneId);
            callStmt.setString(3, designation);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int spotID = rSet.getInt(1);
                int parkId = rSet.getInt(2);
                String charging = rSet.getString(3);
                return new Spot(spotID, charging, parkId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Spot with a drone with ID: " + droneId);
    }
}
