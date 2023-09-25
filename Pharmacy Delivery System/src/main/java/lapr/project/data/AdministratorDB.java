package lapr.project.data;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Administrator db.
 */
public class AdministratorDB extends DataHandler {

    /**
     * Is admin boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isAdmin(String email) {

        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id VARCHAR(1000)) RETURN pkgScooters.ref_cursor
         */
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call isAdmin(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, email);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            if (rSet.next()) {
                closeAll();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }
}
