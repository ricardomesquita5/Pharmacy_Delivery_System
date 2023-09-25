package lapr.project.ui;

import lapr.project.controller.PerformOrderController;
import lapr.project.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Perform order ui.
 */
public class PerformOrderUI {
    /**
     * The Controller.
     */
    private final PerformOrderController controller;

    /**
     * Instantiates a new Add Scooter UI.
     *
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public PerformOrderUI() throws IOException, SQLException {
        this.controller = new PerformOrderController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Perform your order #");
        try {
            if (enterData() && this.controller.calculateOrderPrice() != 0) {
                showData();
                if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                    payment();
                    if (this.controller.saveOrder()) {
                        this.controller.sendEmail();
                        System.out.println("\nSuccessfuly ordered.");
                    } else {
                        System.out.println("It was not possible to complete the order successfully.");
                    }
                }
            } else {
                System.out.println("An error has occurred. Operation canceled.");
            }
        } catch (IllegalArgumentException | SQLException | IOException ia) {
            System.out.println("\n" + ia.getMessage());
        }
    }

    /**
     * payment.
     *
     * @throws SQLException the exception
     */
    private void payment() throws SQLException {
        int credits = this.controller.getClientCredits();
        System.out.println("\nOrder Price: " + this.controller.calculateOrderPrice() + "€");
        if (credits > 0) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!Warning!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("           1 credit is equivelant to 10 cents!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!Warning!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String confirmation = String.format("You have %d credits. Do you wish to discount them from the price? (Y/N)", credits);
            if (Utils.confirm(confirmation)) {
                int maxCredits = (int) (this.controller.calculateOrderPrice() / 0.1);
                if (credits >= maxCredits) {
                    this.controller.removeCredits(maxCredits);
                    System.out.printf("\nYour price is 0 € and you have left %d credits", credits - maxCredits);
                } else {
                    double price = controller.calculateOrderPrice() - 0.1 * credits;
                    this.controller.removeCredits(credits);
                    this.controller.setFinalPrice(price);
                    System.out.println("\nPrice with credits discounted:" + price);
                }
            } else {
                this.controller.addCredits();
            }
        } else {
            this.controller.addCredits();
        }
    }

    /**
     * enterData.
     *
     * @return Order
     * @throws SQLException the sqlException
     * @throws IOException  the ioException
     */
    private boolean enterData() throws SQLException, IOException {
        List<Product> productList = this.controller.getProductList();
        if (productList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Products in the system!");
        } else {
            do {
                try {
                    Product prod = (Product) Utils.showsAndSelect(productList, "\nSelect the Product you want to add to your basket:");
                    if (prod != null) {
                        int prodRef = prod.getReference();
                        this.controller.getProductByReference(prodRef);
                        int amount = Integer.parseInt(Objects.requireNonNull(Utils.readLineFromConsole("Amount: ")));
                        this.controller.addBasket(amount);
                    }
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }
            } while (Utils.confirm("Do you want to add more Products? (Y/N)"));
            checkProduct();
            return this.controller.newOrder();
        }
    }

    /**
     * checkProduct.
     *
     * @throws IOException  the ioException
     * @throws SQLException the sqlException
     */
    private void checkProduct() throws IOException, SQLException {
        Map<Product, Integer> map = controller.checkProducts();
        if (!map.isEmpty()) {
            System.out.println("The following products will be removed due to the fact that the pharmacies nearby and ourselves can´t complete your order.");
            for (Product p : map.keySet()) {
                this.controller.removeFromBasket(p, map.get(p));
                System.out.printf("Product : %s Amount : %d\n", p.getName(), map.get(p));
            }
        }
    }

    /**
     * Textual Description.
     */
    private void showData() {
        System.out.println("\nOrder :\n" + this.controller.getOrderString());
    }


}
