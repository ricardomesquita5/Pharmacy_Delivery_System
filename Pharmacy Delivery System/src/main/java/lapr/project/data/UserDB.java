package lapr.project.data;

import lapr.project.utils.authorization.model.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User db.
 */
public class UserDB extends DataHandler {

    /**
     * Find user user.
     *
     * @param email    the email
     * @param passWord the pass word
     * @return the user
     */
    public User findUser(String email, String passWord) {

        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id VARCHAR(1000)) RETURN pkgScooters.ref_cursor
         */
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call findUser(?,?) }")) {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getScooter".
            callStmt.setString(2, email);
            callStmt.setString(3, passWord);

            // Executa a invocação da função "getScooter".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String userName = rSet.getString(1);
                String userEmail = rSet.getString(2);
                String userPassword = rSet.getString(3);
                return new User(userName, userEmail, userPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    /**
     * Gets user.
     *
     * @param emailU the email u
     * @return the user
     */
    public User getUser(String emailU) {

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUser(?) }")) {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, emailU);

            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String name = rSet.getString(1);
                String email = rSet.getString(2);
                String pwd = rSet.getString(3);
                return new User(name, email, pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No User with email:" + emailU);
    }

    /**
     * Add user.
     *
     * @param u the u
     */
    public void addUser(User u) {
        try (CallableStatement

                     callStmt = getConnection().prepareCall("{ call addUser(?,?,?) }")) {

            callStmt.setString(1, u.getName());
            callStmt.setString(2, u.getEmail());
            callStmt.setString(3, u.getPassword());

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove user.
     *
     * @param email the email
     */
    public void removeUser(String email) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ call removeUser(?) }")) {
            callStmt.setString(1, email);
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}
