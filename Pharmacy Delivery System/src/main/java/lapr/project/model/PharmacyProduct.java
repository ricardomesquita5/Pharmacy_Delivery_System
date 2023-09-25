package lapr.project.model;

import java.util.Objects;

/**
 * The type Pharmacy product.
 */
public class PharmacyProduct {

    /**
     * The PharmacyProduct's reference.
     */
    private int reference;
    /**
     * The PharmacyProduct's pharEmail.
     */
    private String pharEmail;
    /**
     * The PharmacyProduct's amount.
     */
    private int amount;

    /**
     * Instantiates a new PharmacyProduct.
     *
     * @param reference the reference
     * @param pharEmail the pharEmail
     * @param amount    the amount
     */
    public PharmacyProduct(String reference, String pharEmail, String amount) {
        if ((reference == null) || (pharEmail == null) || (amount == null) || (reference.isEmpty()) ||
                (pharEmail.isEmpty()) || (amount.isEmpty()) || (Integer.parseInt(reference) < 0) ||
                (Integer.parseInt(amount) < 0)) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }
        this.setReference(reference);
        this.pharEmail = pharEmail;
        this.setAmount(amount);
    }

    /**
     * Instantiates a new PharmacyProduct.
     *
     * @param reference the reference
     * @param pharEmail the pharEmail
     * @param amount    the amount
     */
    public PharmacyProduct(int reference, String pharEmail, int amount) {
        this.reference = reference;
        this.pharEmail = pharEmail;
        this.amount = amount;
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
     * Gets pharEmail.
     *
     * @return the pharEmail
     */
    public String getPharEmail() {
        return this.pharEmail;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return this.amount;
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
                if (aux >= 0) {
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
     * Sets pharEmail.
     *
     * @param pharEmail the pharEmail
     */
    public void setPharEmail(String pharEmail) {
        if (pharEmail != null && !pharEmail.trim().isEmpty()) {
            this.pharEmail = pharEmail;
        } else {
            throw new IllegalArgumentException("Invalid pharmacy email!");
        }
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(String amount) {
        if (amount != null && !amount.trim().isEmpty()) {
            try {
                int aux = Integer.parseInt(amount);
                if (aux >= 0) {
                    this.amount = aux;
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid amount!");
            }
        } else {
            throw new IllegalArgumentException("Invalid amount!");
        }
    }

    /**
     * Add amount.
     *
     * @param amount the amount
     */
    public void addAmount(String amount) {
        if (amount != null && !amount.trim().isEmpty()) {
            try {
                int aux = Integer.parseInt(amount);
                if (aux > 0) {
                    this.amount += aux;
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid amount!");
            }
        } else {
            throw new IllegalArgumentException("Invalid amount!");
        }
    }

    /**
     * Check if two Pharmacy Products are equals
     *
     * @param o PharmacyProduct
     * @return true if are equals or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PharmacyProduct)) return false;
        PharmacyProduct pharmacyProduct = (PharmacyProduct) o;
        return getReference() == pharmacyProduct.getReference();
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
     * Textual Description of PharmacyProduct.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return String.format("|| Product %d || Pharmacy Email: %s || Amount: %d ||",
                reference, pharEmail, amount);
    }
}
