package lapr.project.data;

import lapr.project.model.Transference;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Transference Data Base.
 */
public class TransferenceDB extends DataHandler {

    /**
     * Add transference.
     *
     * @param pharEmail1       the pharmacy email 1
     * @param pharEmail2       the pharmacy email 2
     * @param productReference the product reference
     * @param amount           the amount
     */
    public void addTransference(String pharEmail1, String pharEmail2, int productReference, int amount) {
        try (CallableStatement

                     callStmt = getConnection().prepareCall("{ call addTransference(?,?,?,?) }")) {

            callStmt.setString(1, pharEmail1);
            callStmt.setString(2, pharEmail2);
            callStmt.setInt(3, productReference);
            callStmt.setInt(4, amount);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove transference.
     *
     * @param id the id
     */
    public void removeTransference(int id) {
        try (
                CallableStatement
                        callStmt = getConnection().prepareCall("{ call removeTransference(?) }")) {

            callStmt.setInt(1, id);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets all transferences.
     *
     * @return the all transferences
     */
    public List<Transference> getAllTransferences() {
        try (PreparedStatement
                     callStmt = getConnection().prepareStatement("SELECT * FROM TRANSFERENCE")) {

            // Guarda o cursor retornado num objeto "ResultSet".
            try (ResultSet rSet = callStmt.executeQuery()) {
                List<Transference> transferenceList = new ArrayList<>();
                while (rSet.next()) {
                    Transference t = new Transference(rSet.getString(2), rSet.getString(3), rSet.getInt(4), rSet.getInt(5));
                    transferenceList.add(t);
                }
                return transferenceList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("ERROR: No Transferences!");
    }

    /**
     * Gets last transference.
     *
     * @param emailPharIn the email phar in
     * @return the last transference
     */
    public int getLastTransference(String emailPharIn) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getLastTransference(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, emailPharIn);

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
        throw new IllegalArgumentException("ERROR!");
    }
}
