package lapr.project.ui;

import lapr.project.controller.UpdateProductController;
import lapr.project.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Update product ui.
 */
public class UpdateProductUI {

    /**
     * The controller.
     */
    private final UpdateProductController controller;

    /**
     * Instantiates a new Update product ui.
     */
    public UpdateProductUI() {
        this.controller = new UpdateProductController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\nUpdate Product:");
        try {
            if (enterData()) {
                showData();
                if (Utils.confirm("Do you want update this product? (Y/N)")) {
                    if (this.controller.updateProduct()) {
                        System.out.println("Update with Success.");
                    } else {
                        System.out.println("It was not possible to complete the update successfully.");
                    }
                }
            } else {
                System.out.println("An error has occurred. Operation canceled.");
            }
        } catch (IllegalArgumentException | SQLException ia) {
            System.out.println(ia.getMessage());
        }
    }

    /**
     * Enter data.
     *
     * @throws SQLException the sql exception
     * @return the Product
     */

    private boolean enterData() throws SQLException {
        List<Product> productsList = this.controller.getProductsList();
        if (productsList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Products in the system!");
        } else {
            Product product = (Product) Utils.showsAndSelect(productsList, "Select the product you want update:");
            if (product != null) {
                int productReference = product.getReference();
                this.controller.getProductByReference(productReference);
                String strName = Utils.readLineFromConsole("Name: ");
                String strDescription = Utils.readLineFromConsole("Description: ");
                String strPrice = Utils.readLineFromConsole("Price(€): ");
                String strWeight = Utils.readLineFromConsole("Weight(KG): ");
                return this.controller.changeProduct(strName, strDescription, strPrice, strWeight);
            } else {
                return false;
            }
        }
    }

    /**
     * Show Data.
     */
    private void showData() {
        System.out.println("Product:\n" + this.controller.getProductString());
    }
}
