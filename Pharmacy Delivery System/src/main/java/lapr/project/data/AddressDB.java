package lapr.project.data;

import lapr.project.model.Address;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Address db.
 */
public class AddressDB extends DataHandler {

    /**
     * Gets address.
     *
     * @param coordinates the coordinates
     * @return the address
     */
    public Address getAddress(String coordinates) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAddress(?) }")) {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getAddress".
            callStmt.setString(2, coordinates);

            // Executa a invocação da função "getAddress".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String gpsCoordinates = rSet.getString(1);
                String street = rSet.getString(2);
                String postalCode = rSet.getString(3);
                int doorNumber = rSet.getInt(4);
                String locality = rSet.getString(5);
                int elevation = rSet.getInt(6);
                return new Address(gpsCoordinates, street, postalCode, doorNumber, locality, elevation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Address with coordinates:" + coordinates);

    }

    /**
     * Add address.
     *
     * @param address the address
     */
    public void addAddress(Address address) {
        addAddress(address.getGPSCoordinates(), address.getStreet(), address.getPostalCode(), address.getDoorNumber(),
                address.getLocality(), address.getElevation());
    }

    /**
     * Add address.
     *
     * @param gpsCoordinates gps Coordinates
     * @param street         street
     * @param postalCode     postal Code
     * @param doorNumber     door Number
     * @param locality       locality
     * @param elevation      elevation
     */
    private void addAddress(String gpsCoordinates, String street, String postalCode, int doorNumber, String locality, int elevation) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addAddress(?,?,?,?,?,?) }")) {

            callStmt.setString(1, gpsCoordinates);
            callStmt.setString(2, street);
            callStmt.setString(3, postalCode);
            callStmt.setInt(4, doorNumber);
            callStmt.setString(5, locality);
            callStmt.setInt(6, elevation);
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

    }

    /**
     * Gets all addresses.
     *
     * @return the all addresses
     */
    public List<Address> getAllAddresses() {
        try (PreparedStatement callStmt = getConnection().prepareStatement("SELECT * FROM ADDRESS")) {
            // Guarda o cursor retornado num objeto "ResultSet".
            try (ResultSet rSet = callStmt.executeQuery()) {
                LinkedList<Address> addressesList = new LinkedList<>();
                while (rSet.next()) {
                    Address a = new Address(rSet.getString(1), rSet.getString(2), rSet.getString(3),
                            rSet.getInt(4), rSet.getString(5), rSet.getInt(6));
                    addressesList.add(a);
                }
                return addressesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Addresses");
    }

    /**
     * Remove address.
     *
     * @param gpsCoordinates the gps coordinates
     */
    public void removeAddress(String gpsCoordinates) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeAddress(?) }")) {

            callStmt.setString(1, gpsCoordinates);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}
