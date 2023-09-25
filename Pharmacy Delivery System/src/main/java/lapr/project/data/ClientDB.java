package lapr.project.data;

import lapr.project.model.*;
import lapr.project.utils.authorization.model.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Client db.
 */
public class ClientDB extends DataHandler {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Gets client.
     *
     * @param userEmail the user email
     * @return the client
     */
    public Client getClient(String userEmail) {

        /* Objeto "callStmt" para invocar a função "getClient" armazenada na BD.
         *
         * FUNCTION getClient(email VARCHAR(200)) RETURN pkgClients.ref_cursor
         */
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClient(?) }")) {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            // Especifica o parâmetro de entrada da função "getClient".
            callStmt.setString(2, userEmail);

            // Executa a invocação da função "getClient".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String clientEmail = rSet.getString(1);
                String gpsCoordinates = rSet.getString(2);
                Address clientAddress = Address.getAddress(gpsCoordinates);
                long clientCreditCardNumber = rSet.getLong(3);
                Date clientValidityDate = new Date(rSet.getDate(4).getTime());
                int clientCcv = rSet.getInt(5);
                int creds = rSet.getInt(6);
                User u = new UserDB().getUser(clientEmail);

                return new Client(u.getName(), clientEmail, u.getPassword(), clientAddress, clientCreditCardNumber, clientValidityDate, clientCcv, creds);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Client with email:" + userEmail);
    }

    /**
     * Add client.
     *
     * @param client the client
     */
    public void addClient(Client client) {
        try {
            client.getClient(client.getEmail());
            LOGGER.log(Level.WARNING, "The specified email is already associated to a client!");
        } catch (IllegalArgumentException iae) {
            new UserDB().addUser(client);
            addClient(client.getEmail(), client.getAddress().getGPSCoordinates(), client.getCreditCardNumber(), client.getValidityDate(), client.getCcv(), client.getCredits());
        }
    }

    /**
     * Add Client.
     *
     * @param email            email
     * @param gpsCoordinates   gps Coordinates
     * @param creditCardNumber credit Card Number
     * @param validityDate     validity Date
     * @param ccv              ccv
     * @param credits          credits
     */
    private void addClient(String email, String gpsCoordinates, long creditCardNumber, Date validityDate, int ccv, int credits) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?,?,?) }")) {
            callStmt.setString(1, email);
            callStmt.setString(2, gpsCoordinates);
            callStmt.setLong(3, creditCardNumber);
            java.sql.Date sql = new java.sql.Date(validityDate.getTime());
            callStmt.setDate(4, sql);
            callStmt.setLong(5, ccv);
            callStmt.setInt(6, credits);
            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Is client boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isClient(String email) {

        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id VARCHAR(1000)) RETURN pkgScooters.ref_cursor
         */
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call isClient(?) }")) {
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
     * Remove client.
     *
     * @param email the email
     */
    public void removeClient(String email) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeClient(?) }")) {

            callStmt.setString(1, email);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Udpate client.
     *
     * @param email the email
     * @param creds the creds
     */
    public void udpateClient(String email, int creds) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateClient(?,?) }")) {

            callStmt.setString(1, email);
            callStmt.setInt(2, creds);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

}

