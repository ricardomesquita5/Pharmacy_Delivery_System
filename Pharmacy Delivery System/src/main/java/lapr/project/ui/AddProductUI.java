package lapr.project.ui;

import lapr.project.controller.AddProductController;
import lapr.project.data.PharmacyDB;
import lapr.project.data.PharmacyProductDB;
import lapr.project.data.ProductDB;
import lapr.project.model.Pharmacy;
import lapr.project.model.PharmacyProduct;
import lapr.project.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Add product ui.
 */
public class AddProductUI {

    /**
     * The Controller.
     */
    private final AddProductController controller;

    /**
     * Instantiates a new Add product ui.
     */
    public AddProductUI() {
        this.controller = new AddProductController();
    }

    /**
     * Run.
     *
     * @throws SQLException the sql exception
     */
    public void run() throws SQLException {
        System.out.println("\n\n# Add Product #");
        try {
            if (Utils.confirm("Do you wish to add a new product? (Y/N)")) {
                if (enterData()) {
                    showData();
                    if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                        if (this.controller.addProduct()) {
                            System.out.println("Registration successful.");
                        } else {
                            System.out.println("It was not possible to complete the registration successfully.");
                        }
                    }
                } else {
                    System.out.println("An error has occurred. Operation canceled.");
                }
            }
            enterData2();

            if (Utils.confirm("Do you wish to add an amount to an existing product? (Y/N)")) {
                enterData3();
            }
        } catch (IllegalArgumentException | SQLException ia) {
            System.out.println("\n" + ia.getMessage());
        }
    }

    /**
     * Show Data.
     */
    private void showData() {
        System.out.println("\nProduct:\n" + this.controller.getProductString());
    }

    /**
     * enterData.
     *
     * @return new Product
     * @throws SQLException the exception
     */
    private boolean enterData() throws SQLException {
        boolean vf = true;
        boolean vfAux = true;
        String strReference = null;
        String strName = null;
        String strDescription = null;
        String strPrice = null;
        String strWeight = null;
        while (vf) {
            strReference = Utils.readLineFromConsole("Reference: ");
            for (Product product : new ProductDB().getAllProducts()) {
                assert strReference != null;
                if (product.getReference() == Integer.parseInt(strReference)) {
                    System.out.println("\nReference already in use!\n");
                    vfAux = false;
                    break;
                }
            }
            if (vfAux) {
                strName = Utils.readLineFromConsole("Name: ");
                strDescription = Utils.readLineFromConsole("Description: ");
                strPrice = Utils.readLineFromConsole("Price(€): ");
                strWeight = Utils.readLineFromConsole("Weight(KG): ");
                vf = false;
            } else {
                vfAux = true;
            }
        }
        return this.controller.newProduct(strReference, strName, strDescription, strPrice, strWeight);
    }

    /**
     * enterData2.
     *
     * @throws SQLException the exception
     */
    private void enterData2() throws SQLException {
        boolean vf = true;
        List<Pharmacy> pharList = new PharmacyDB().getAllPharmacies();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            while (Utils.confirm("Do you wish to add a new product to a pharmacy? (Y/N)") && vf) {
                Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to add a product to:");
                if (phar != null) {
                    List<Product> proList = new ProductDB().getAllProducts();
                    if (proList.isEmpty()) {
                        throw new IllegalArgumentException("ERROR: No Products in the system!");
                    } else {
                        for (PharmacyProduct pp : new PharmacyProductDB().getAllPharmacyProductEmail(phar.getEmail())) {
                            proList.removeIf(p -> p.getReference() == pp.getReference());
                        }
                        if (proList.isEmpty()) {
                            throw new IllegalArgumentException("ERROR: No productsavailable!");
                        }
                        Product pro = (Product) Utils.showsAndSelect(proList, "\nSelect the product you want to add:");
                        if (pro != null) {
                            String strAmount = Utils.readLineFromConsole("Amount: ");
                            PharmacyProduct pp = new PharmacyProduct(String.valueOf(pro.getReference()), phar.getEmail(), strAmount);
                            try {
                                new PharmacyProductDB().addPharmacyProduct(pp);
                                System.out.println("Registration successful.");
                            } catch (IllegalArgumentException sqle) {
                                throw new IllegalArgumentException("It was not possible to complete the registration successfully!");
                            }
                        } else {
                            vf = false;
                        }
                    }
                } else {
                    vf = false;
                }
            }
        }
    }

    /**
     * enterData3.
     *
     * @throws SQLException the exception
     */
    private void enterData3() throws SQLException {
        List<PharmacyProduct> ppList = new PharmacyProductDB().getAllPharmacyProduct();
        if (ppList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacy Products in the system!");
        } else {
            PharmacyProduct pp = (PharmacyProduct) Utils.showsAndSelect(ppList, "\nSelect the Product you want to add an amount to:");
            if (pp != null) {
                String strAmount = Utils.readLineFromConsole("Amount: ");
                pp.addAmount(strAmount);
                new PharmacyProductDB().updatePharmacyProduct(pp);
                System.out.println("Registration successful.");
            }
        }
    }
}
