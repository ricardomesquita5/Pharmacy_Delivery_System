package lapr.project.data;

import lapr.project.model.Address;
import lapr.project.model.Pharmacy;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Pharmacy db.
 */
public class PharmacyDB extends DataHandler {

    /**
     * Gets pharmacy.
     *
     * @param emailPharmacy the email pharmacy
     * @return the pharmacy
     */
    public Pharmacy getPharmacy(String emailPharmacy) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacy(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getPharmacy".
            callStmt.setString(2, emailPharmacy);

            // Executa a invocação da função "getPharmacy".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String email = rSet.getString(1);
                String gpsCoordinates = rSet.getString(2);
                Address address = Address.getAddress(gpsCoordinates);
                String designation = rSet.getString(3);
                return new Pharmacy(email, address, designation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy with email:" + emailPharmacy);
    }

    /**
     * Add pharmacy.
     *
     * @param pharmacy the pharmacy
     */
    public void addPharmacy(Pharmacy pharmacy) {
        addPharmacy(pharmacy.getEmail(), pharmacy.getAddress().getGPSCoordinates(), pharmacy.getDesignation());
    }

    /**
     * Add Phamaacy.
     *
     * @param email       email
     * @param coordinates coordinates
     * @param designation designation
     */
    private void addPharmacy(String email, String coordinates, String designation) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addPharmacy(?,?,?) }")) {

            callStmt.setString(1, email);
            callStmt.setString(2, coordinates);
            callStmt.setString(3, designation);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all pharmacies.
     *
     * @return the all pharmacies
     */
    public List<Pharmacy> getAllPharmacies() {
        try (
                PreparedStatement callStmt = getConnection().prepareStatement("SELECT * FROM PHARMACY")) {

            // Guarda o cursor retornado num objeto "ResultSet".
            try (ResultSet rSet = callStmt.executeQuery()) {
                List<Pharmacy> pharmaciesList = new ArrayList<>();
                while (rSet.next()) {
                    Address a = Address.getAddress(rSet.getString(2));
                    Pharmacy s = new Pharmacy(rSet.getString(1), a, rSet.getString(3));
                    pharmaciesList.add(s);
                }
                return pharmaciesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacies");
    }

    /**
     * Gets pharmacy address coordinates by courier email.
     *
     * @param emailCourier the email courier
     * @return the pharmacy address coordinates by courier email
     */
    public String getPharmacyAddressCoordinatesByCourierEmail(String emailCourier) {
        try (
                CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyAddressCoordinatesByCourierEmail(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, emailCourier);
            callStmt.execute();

            return callStmt.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Client with email:" + emailCourier);
    }

    /**
     * Remove Pharmacy.
     *
     * @param email the Pharmacy's email
     */
    public void removePharmacy(String email) {

        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call removePharmacy(?) }")) {

            callStmt.setString(1, email);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Get Park Scooter Limit of one Pharmacy.
     *
     * @param emailPharmacy email pharmacy
     * @return limit park scooter limit
     */
    public int getParkScooterLimit(String emailPharmacy) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getParkScooterLimit(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, emailPharmacy);
            callStmt.execute();
            return callStmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy with email:" + emailPharmacy);
    }

    /**
     * Get Park Drone Limit of one Pharmacy.
     *
     * @param emailPharmacy email pharmacy
     * @return limit park drone limit
     */
    public int getParkDroneLimit(String emailPharmacy) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getParkDroneLimit(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, emailPharmacy);
            callStmt.execute();

            return callStmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy with email:" + emailPharmacy);
    }
}
