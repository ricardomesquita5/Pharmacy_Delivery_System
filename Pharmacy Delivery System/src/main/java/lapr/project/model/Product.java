package lapr.project.model;

import lapr.project.data.ProductDB;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Product.
 */
public class Product {

    /**
     * The Product's reference.
     */
    private int reference;
    /**
     * The Product's name.
     */
    private String name;
    /**
     * The Product's description.
     */
    private String description;
    /**
     * The Product's price.
     */
    private double price;
    /**
     * The Product's weight.
     */
    private double weight;

    /**
     * Instantiates a new Product.
     *
     * @param reference   the reference
     * @param name        the name
     * @param description the description
     * @param price       the price
     * @param weight      the weight
     */
    public Product(String reference, String name, String description, String price, String weight) {
        if ((name == null) || (description == null) || (price == null) || (weight == null) || (name.isEmpty()) ||
                (description.isEmpty()) || (price.isEmpty() || (weight.isEmpty()) || (Double.parseDouble(price) < 0) ||
                (Double.parseDouble(weight) < 0))) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }
        this.setReference(reference);
        this.name = name;
        this.description = description;
        this.setPrice(price);
        this.setWeight(weight);
    }

    /**
     * Instantiates a new Product.
     *
     * @param reference   the reference
     * @param name        the name
     * @param description the description
     * @param price       the price
     * @param weight      the weight
     */
    public Product(int reference, String name, String description, double price, double weight) {
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    public int getReference() {
        return this.reference;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    public void setReference(String reference) {
        if (reference != null && !reference.trim().isEmpty()) {
            try {
                int aux = Integer.parseInt(reference);
                if (aux > 0) {
                    this.reference = aux;
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid reference!");
            }
        } else {
            throw new IllegalArgumentException("Invalid reference!");
        }
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid name!");
        }
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Invalid description!");
        }
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        if (price != null && !price.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(price);
                if (aux > 0) {
                    this.price = aux;
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid price!");
            }
        } else {
            throw new IllegalArgumentException("Invalid price!");
        }
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(String weight) {
        if (weight != null && !weight.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(weight);
                if (aux > 0) {
                    this.weight = aux;
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid weight!");
            }
        } else {
            throw new IllegalArgumentException("Invalid weight!");
        }
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    public void save() throws SQLException {
        new ProductDB().addProduct(this);
    }

    /**
     * Update.
     *
     * @throws SQLException the sql exception
     */
    public void update() throws SQLException {
        new ProductDB().updateProduct(this);
    }

    /**
     * Check if two Products are equals
     *
     * @param o Product
     * @return true if are equals or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getReference() == product.getReference();
    }

    /**
     * Hash Code
     *
     * @return code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getReference());
    }

    /**
     * Remove.
     *
     * @throws SQLException the sql exception
     */
    public void remove() throws SQLException {
        new ProductDB().removeProduct(this.getReference());
    }

    /**
     * Textual Description of Product.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return String.format("|| Product %d || Name: %s || Description: %s || Price: %.2f || Weigh: %.2f ||",
                reference, name, description, price, weight);
    }
}
