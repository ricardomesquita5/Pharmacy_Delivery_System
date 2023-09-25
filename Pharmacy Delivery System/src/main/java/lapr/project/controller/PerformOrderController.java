package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;
import lapr.project.model.*;
import lapr.project.utils.controller.ApplicationPOT;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * The type Perform order controller.
 */
public class PerformOrderController {

    /**
     * The Product.
     */
    private Product product;
    /**
     * The Pharmacy.
     */
    private Pharmacy pharmacy;
    /**
     * The Order.
     */
    private Order order;
    /**
     * The Client.
     */
    private Client client;
    /**
     * The Email Client.
     */
    private final String emailClient;
    /**
     * The Basket.
     */
    private final Map<Product, Integer> basket;
    /**
     * The Final Price.
     */
    private double finalPrice;

    /**
     * Instantiates a new Perform order controller.
     *
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public PerformOrderController() throws IOException, SQLException {
        this.basket = new LinkedHashMap<>();
        this.emailClient = ApplicationPOT.getInstance().getCurrentSession().getUserEmail();
        this.client = new ClientDB().getClient(this.emailClient);
        this.finalPrice = -1;
        getClosestPharmacy();
    }

    /**
     * Instantiates a new Perform order controller.
     *
     * @param emailClient the email client
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public PerformOrderController(String emailClient) throws IOException, SQLException {
        this.basket = new LinkedHashMap<>();
        this.emailClient = emailClient;
        this.finalPrice = -1;
    }

    /**
     * Gets product list.
     *
     * @return the product list
     */
    public List<Product> getProductList() {
        return new ProductDB().getAllProducts();
    }

    /**
     * Gets product by reference.
     *
     * @param reference the reference
     * @throws SQLException the sql exception
     */
    public void getProductByReference(int reference) throws SQLException {
        this.product = new ProductDB().getProduct(reference);
    }

    /**
     * Add basket.
     *
     * @param amount the amount
     */
    public void addBasket(int amount) {
        if (amount > 0) {
            if (basket.containsKey(this.product)) {
                amount += this.basket.get(this.product);
            }
            int amountProductAllPharmacies = new PharmacyProductDB().getTotalAmountOfProduct(this.product.getReference());
            if (amount <= amountProductAllPharmacies) {
                this.basket.put(this.product, amount);
            } else {
                throw new IllegalArgumentException("Error: the pharmacies dont have enough amount of products to support your order!");
            }
        } else {
            throw new IllegalArgumentException("Error: You need to enter a valid amount of a Product!!");
        }
    }

    /**
     * New order boolean.
     *
     * @return the boolean
     */
    public boolean newOrder() {
        this.order = new Order(this.calculateOrderPrice());
        return true;
    }

    /**
     * Calculate order price double.
     *
     * @return the double
     */
    public double calculateOrderPrice() {
        double orderPrice = 0;
        for (Product po : this.basket.keySet()) {
            orderPrice += po.getPrice() * this.basket.get(po);
        }
        return orderPrice;
    }

    /**
     * Save order boolean.
     *
     * @return the boolean
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public boolean saveOrder() throws SQLException, IOException {

        this.order.save(this.pharmacy.getEmail(), this.emailClient);
        int idOrder = new OrderDB().getLastClientOrder(this.emailClient);
        for (Product po : this.basket.keySet()) {
            this.newProductOrder(idOrder, po.getReference(), this.basket.get(po));
            this.updateDB(po, this.basket.get(po));
        }
        return true;
    }

    /**
     * Update order boolean.
     *
     * @param po the Product
     * @param integer the integer
     */
    private void updateDB(Product po, Integer integer) {
        PharmacyProduct pp = new PharmacyProductDB().getPharmacyProduct(po.getReference(), this.pharmacy.getEmail());
        int amount = pp.getAmount();
        amount -= integer;
        pp.setAmount("" + amount);
        new PharmacyProductDB().updatePharmacyProduct(pp);
    }

    /**
     * New product order.
     *
     * @param idOrder   the id order
     * @param reference the reference
     * @param amount    the amount
     * @throws SQLException the sql exception
     */
    public void newProductOrder(int idOrder, int reference, int amount) throws SQLException {
        ProductOrder productOrder = new ProductOrder(reference, idOrder, amount);
        productOrder.save();
    }

    /**
     * Check products map.
     *
     * @return the map
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public Map<Product, Integer> checkProducts() throws SQLException, IOException {
        Map<Product, Integer> listAux = new LinkedHashMap<>();
        for (Product po : basket.keySet()) {
            PharmacyProduct pp = null;
            try {
                pp = new PharmacyProductDB().getPharmacyProduct(po.getReference(), this.pharmacy.getEmail());
            } catch (IllegalArgumentException iae) {
                listAux.put(po, this.basket.get(po));
            }
            if (pp != null && pp.getAmount() < this.basket.get(po)) {
                int qt = this.basket.get(po) - pp.getAmount();
                listAux.put(po, qt);
            }
        }

        Map<Product, Integer> finalMap = new LinkedHashMap<>();
        for (Product p : listAux.keySet()) {
            Map<Pharmacy, Integer> map = ProductInNearbyPharmaciesAlgorithm.checkNearbyPharmacies(this.pharmacy, p, listAux.get(p));
            if (!map.isEmpty()) {
                int amount = 0;
                for (Pharmacy ph : map.keySet()) {
                    amount += map.get(ph);
                }
                int required = listAux.get(p);
                if (required - amount > 0) {
                    finalMap.put(p, required - amount);
                }
            }
        }
        return finalMap;
    }

    /**
     * Gets closest pharmacy.
     *
     * @throws IOException the io exception
     */
    public void getClosestPharmacy() throws IOException {
        Graph<String, Integer> g = new FileReader().getGraph();
        List<Pharmacy> pharmacyList = new PharmacyDB().getAllPharmacies();
        double minDist = Double.MAX_VALUE;
        Pharmacy closestPharmacy = null;
        for (String s : g.vertices()) {
            Pharmacy p = ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(pharmacyList, s);
            if (!s.equalsIgnoreCase(this.client.getAddress().getGPSCoordinates()) && p != null) {
                double dist = GraphAlgorithms.shortestPath(g, this.client.getAddress().getGPSCoordinates(), s, new LinkedList<>());
                if (dist < minDist) {
                    minDist = dist;
                    closestPharmacy = p;
                }
            }
            this.pharmacy = closestPharmacy;
        }
    }

    /**
     * Remove from basket.
     *
     * @param p      the p
     * @param amount the amount
     */
    public void removeFromBasket(Product p, int amount) {
        int x = this.basket.get(p);
        x -= amount;
        this.basket.put(p, x);
    }

    /**
     * Gets order string.
     *
     * @return the order string
     */
    public String getOrderString() {
        StringBuilder order = new StringBuilder();
        for (Product p : this.basket.keySet()) {
            order.append(String.format("Product : %s  Amount : %d Price(1/u) : %.2f € \n", p.getName(), basket.get(p), p.getPrice()));
        }

        return order.toString();
    }

    /**
     * Gets client credits.
     *
     * @return the client credits
     */
    public int getClientCredits() {
        return this.client.getCredits();
    }

    /**
     * Remove credits.
     *
     * @param creds the creds
     * @throws SQLException the sql exception
     */
    public void removeCredits(int creds) throws SQLException {
        this.client.removeCredits(creds);
        new ClientDB().udpateClient(this.emailClient, this.client.getCredits());
    }

    /**
     * Add credits.
     *
     * @throws SQLException the sql exception
     */
    public void addCredits() throws SQLException {
        this.client.addCredits(this.order.getFinalPrice());
        new ClientDB().udpateClient(this.emailClient, this.client.getCredits());
    }

    /**
     * Send email.
     *
     * @throws IOException the io exception
     */
    public void sendEmail() throws IOException {
        double price = this.finalPrice;
        if (this.finalPrice == -1) {
            price = calculateOrderPrice();
        }
        String content = getOrderString() + String.format("Total Price: %.2f €\nBest Regards,\nBerkelios Company", price);
        new EmailHandler().sendEmail(this.client.getEmail(), "Order Completed with Success", content);

    }

    /**
     * Sets final price.
     *
     * @param price the price
     */
    public void setFinalPrice(double price) {
        this.finalPrice = price;
    }
}
