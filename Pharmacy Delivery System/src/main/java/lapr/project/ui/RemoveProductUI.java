package lapr.project.ui;

import lapr.project.controller.RemoveProductController;
import lapr.project.data.PharmacyProductDB;
import lapr.project.data.ProductOrderDB;
import lapr.project.model.PharmacyProduct;
import lapr.project.model.Product;
import lapr.project.model.ProductOrder;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Remove product ui.
 */
public class RemoveProductUI {

    /**
     * The Controller.
     */
    private final RemoveProductController controller;

    /**
     * Instantiates a new Remove product ui.
     */
    public RemoveProductUI() {
        this.controller = new RemoveProductController();
    }

    /**
     * Run.
     *
     * @throws SQLException the sql exception
     */
    public void run() throws SQLException {
        System.out.println("\nRemove Product:");
        try {
            if (enterData()) {
                System.out.println("\nRemove with Success.");
            } else {
                System.out.println("An error has occurred. Operation canceled.");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("\n" + iae.getMessage());
        }

    }

    /**
     * Enter data.
     *
     * @throws SQLException the sql exception
     * @return the Product
     */

    private boolean enterData() throws SQLException {
        List<PharmacyProduct> pharmacyProductList = new PharmacyProductDB().getAllPharmacyProduct();
        List<ProductOrder> productOrderList = new ProductOrderDB().getAllProductOrder();
        List<Product> productsList = this.controller.getProductsList();
        if (productsList.isEmpty()) {
            System.out.println("ERROR: No Products in the system!");
            return false;
        } else {
            int productReference;
            Product product = (Product) Utils.showsAndSelect(productsList, "Select the Product you want remove:");
            if (product != null) {
                productReference = product.getReference();
                this.controller.getProductByReference(productReference);
                showData();
                if (Utils.confirm("Do you want remove this product? (Y/N)")) {
                    for (PharmacyProduct pp : new PharmacyProductDB().getAllPharmacyProduct()) {
                        if (pp.getReference() == productReference) {
                            new PharmacyProductDB().removePharmacyProduct(pp.getReference(), pp.getPharEmail());
                        }
                    }
                    for (ProductOrder po : new ProductOrderDB().getAllProductOrder()) {
                        if (po.getReference() == productReference) {
                            new ProductOrderDB().removeProductOrder(po.getReference(), po.getIdOrder());
                        }
                    }
                    if (!this.controller.removeProduct()) {
                        System.out.println("It was not possible to complete the remove successfully.");
                        for (PharmacyProduct pp : pharmacyProductList) {
                            if (!new PharmacyProductDB().getAllPharmacyProduct().contains(pp)) {
                                new PharmacyProductDB().addPharmacyProduct(pp);
                            }
                        }
                        for (ProductOrder po : productOrderList) {
                            if (!new ProductOrderDB().getAllProductOrder().contains(po)) {
                                new ProductOrderDB().addProductOrder(po);
                            }
                        }
                        return false;
                    }
                }
                return true;
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
