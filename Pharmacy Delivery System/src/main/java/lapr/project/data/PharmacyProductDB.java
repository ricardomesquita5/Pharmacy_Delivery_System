package lapr.project.data;

import lapr.project.model.PharmacyProduct;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Pharmacy product db.
 */
public class PharmacyProductDB extends DataHandler {

    /**
     * Gets pharmacy product.
     *
     * @param reference the reference
     * @param pharEmail the phar email
     * @return the pharmacy product
     */
    public PharmacyProduct getPharmacyProduct(int reference, String pharEmail) {
        try (
                CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyProduct(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setInt(2, reference);
            callStmt.setString(3, pharEmail);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int productReference = rSet.getInt(1);
                String pharmacyEmail = rSet.getString(2);
                int amount = rSet.getInt(3);
                return new PharmacyProduct(productReference, pharmacyEmail, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy Product with ID: " + reference + " and Pharmacy Email: " + pharEmail);
    }


    /**
     * Add pharmacy product.
     *
     * @param pharProduct the phar product
     */
    public void addPharmacyProduct(PharmacyProduct pharProduct) {
        addPharmacyProduct(pharProduct.getReference(), pharProduct.getPharEmail(), pharProduct.getAmount());
    }

    /**
     * Gets total amount of product.
     *
     * @param reference the reference
     * @param pharEmail the pharEmail
     * @param amount the amount
     */
    private void addPharmacyProduct(int reference, String pharEmail, int amount) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addPharmacyProduct(?,?,?) }")) {

            callStmt.setInt(1, reference);
            callStmt.setString(2, pharEmail);
            callStmt.setInt(3, amount);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Remove pharmacy product.
     *
     * @param reference the reference
     * @param pharEmail the phar email
     */
    public void removePharmacyProduct(int reference, String pharEmail) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removePharmacyProduct(?,?) }")) {

            callStmt.setInt(1, reference);
            callStmt.setString(2, pharEmail);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }


    /**
     * Gets all pharmacy product.
     *
     * @return the all pharmacy product
     */
    public List<PharmacyProduct> getAllPharmacyProduct() {
        try (
                CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllPharmacyProduct() }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<PharmacyProduct> pharProductsList = new ArrayList<>();
            while (rSet.next()) {
                PharmacyProduct p = new PharmacyProduct(rSet.getInt(1), rSet.getString(2), rSet.getInt(3));
                pharProductsList.add(p);
            }
            return pharProductsList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy Product in the system!");
    }

    /**
     * Gets all pharmacy product email.
     *
     * @param pharEmail the phar email
     * @return the all pharmacy product email
     */
    public List<PharmacyProduct> getAllPharmacyProductEmail(String pharEmail) {
        try (
                CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllPharmacyProductEmail(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, pharEmail);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            List<PharmacyProduct> pharProductsList = new ArrayList<>();
            while (rSet.next()) {
                PharmacyProduct p = new PharmacyProduct(rSet.getInt(1), rSet.getString(2), rSet.getInt(3));
                pharProductsList.add(p);
            }
            return pharProductsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy Product in the system!");
    }

    /**
     * Update pharmacy product.
     *
     * @param pharProduct the phar product
     */
    public void updatePharmacyProduct(PharmacyProduct pharProduct) {
        updatePharmacyProduct(pharProduct.getReference(), pharProduct.getPharEmail(), pharProduct.getAmount());
    }

    /**
     * Gets update PharmacyProduct.
     *
     * @param reference the reference
     * @param pharEmail the pharEmail
     * @param amount    the amount
     */
    private void updatePharmacyProduct(int reference, String pharEmail, int amount) {
        try (
                CallableStatement callStmt = getConnection().prepareCall("{ call updatePharmacyProduct(?,?,?) }")) {

            callStmt.setInt(1, reference);
            callStmt.setString(2, pharEmail);
            callStmt.setInt(3, amount);

            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Gets total amount of product.
     *
     * @param ref the ref
     * @return the total amount of product
     */
    public int getTotalAmountOfProduct(int ref) {
        try (CallableStatement
                     callStmt = getConnection().prepareCall("{ ? = call getTotalAmountOfProduct(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, ref);

            callStmt.execute();

            return callStmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Pharmacy Product with said reference!");
    }
}
