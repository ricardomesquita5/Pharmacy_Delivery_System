package lapr.project.data;

import lapr.project.model.Courier;
import lapr.project.utils.authorization.model.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Courier db.
 */
public class CourierDB extends DataHandler {
    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Gets courier.
     *
     * @param userEmail the user email
     * @return the courier
     */
    public Courier getCourier(String userEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourier(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, userEmail);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int nif = rSet.getInt(1);
                String email = rSet.getString(3);
                int phoneNumber = rSet.getInt(4);
                double socialSecurityNumber = rSet.getDouble(5);
                String status = rSet.getString(6);
                User u = new UserDB().getUser(email);

                return new Courier(nif, u.getName(), email, phoneNumber, socialSecurityNumber, status, u.getPassword());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Courier with email:" + userEmail);
    }

    /**
     * Gets email pharmacy by email courier.
     *
     * @param userEmail the user email
     * @return the email pharmacy by email courier
     */
    public String getEmailPharmacyByEmailCourier(String userEmail) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getEmailPharmacyByEmailCourier(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, userEmail);
            callStmt.execute();

            return callStmt.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Courier with email:" + userEmail);
    }

    /**
     * Add courier.
     *
     * @param courier   the courier
     * @param pharEmail the phar email
     * @throws SQLException the sql exception
     */
    public void addCourier(Courier courier, String pharEmail) throws SQLException {
        try {
            courier.getCourier(courier.getEmail());
            LOGGER.log(Level.WARNING, "The specified email is already associated to a courier!");
        } catch (IllegalArgumentException iae) {
            new UserDB().addUser(courier);
            addCourier(pharEmail, courier.getNif(), courier.getEmail(), courier.getPhoneNumber(), courier.getSocialSecurityNumber(),
                    courier.getStatus());
        }
    }

    /**
     * Add Courier.
     *
     * @param pharEmail            pharmacy Email
     * @param nif                  nif
     * @param email                email
     * @param phoneNumber          phone Number
     * @param socialSecurityNumber social Security Number
     * @param status               status
     */
    private void addCourier(String pharEmail, double nif, String email, double phoneNumber, double socialSecurityNumber, String status) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ call addCourier(?,?,?,?,?,?) }")) {

            callStmt.setString(1, pharEmail);
            callStmt.setDouble(2, nif);
            callStmt.setString(3, email);
            callStmt.setDouble(4, phoneNumber);
            callStmt.setDouble(5, socialSecurityNumber);
            callStmt.setString(6, status);

            callStmt.execute();

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                new UserDB().removeUser(email);
                throw new IllegalArgumentException("The NIF should be unique for each Courier!");
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Is courier boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isCourier(String email) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call isCourier(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, email);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    /**
     * Update courier.
     *
     * @param cour the cour
     */
    public void updateCourier(Courier cour) {
        updateCourier(cour.getStatus(), cour.getEmail());
    }

    /**
     * Update Courier.
     *
     * @param status status
     * @param email  email
     */
    private void updateCourier(String status, String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateCourier(?,?) }")) {

            callStmt.setString(1, status);
            callStmt.setString(2, email);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all couriers of pharmacy.
     *
     * @param email the email
     * @return the all couriers of pharmacy
     */
    public List<Courier> getAllCouriersOfPharmacy(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllCouriersOfPharmacy(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, email);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Courier> couriersList = new ArrayList<>();
            while (rSet.next()) {
                int nif = rSet.getInt(1);
                String email2 = rSet.getString(3);
                int phoneNumber = rSet.getInt(4);
                double socialSecurityNumber = rSet.getDouble(5);
                String status = rSet.getString(6);
                User u = new UserDB().getUser(email2);
                Courier c = new Courier(nif, u.getName(), email2, phoneNumber, socialSecurityNumber, status, u.getPassword());
                couriersList.add(c);
            }

            return couriersList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Couriers");
    }

    /**
     * Remove courier.
     *
     * @param email the email
     */
    public void removeCourier(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeCourier(?) }")) {
            callStmt.setString(1, email);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets available couriers.
     *
     * @param email the email
     * @return the available couriers
     */
    public List<Courier> getAvailableCouriers(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAvailableCouriers(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, email);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<Courier> couriersList = new ArrayList<>();
            while (rSet.next()) {
                int nif = rSet.getInt(1);
                String email2 = rSet.getString(3);
                int phoneNumber = rSet.getInt(4);
                double socialSecurityNumber = rSet.getDouble(5);
                String status = rSet.getString(6);
                User u = new UserDB().getUser(email2);
                Courier c = new Courier(nif, u.getName(), email2, phoneNumber, socialSecurityNumber, status, u.getPassword());
                couriersList.add(c);
            }

            return couriersList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Couriers");
    }
}
