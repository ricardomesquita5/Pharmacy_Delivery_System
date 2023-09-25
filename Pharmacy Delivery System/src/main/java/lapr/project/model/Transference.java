package lapr.project.model;

/**
 * The type Transference.
 */
public class Transference {

    /**
     * The Pharmacy in email.
     */
    private final String pharInEmail;
    /**
     * The Pharmacy out email.
     */
    private final String pharOutEmail;
    /**
     * The Product's reference.
     */
    private final int productReference;
    /**
     * The Product's amount.
     */
    private final int amount;

    /**
     * Instantiates a new Transference.
     *
     * @param pharInEmail      the pharmacy in email
     * @param pharOutEmail     the pharmacy out email
     * @param productReference the product reference
     * @param amount           the product amount
     */
    public Transference(String pharInEmail, String pharOutEmail, int productReference, int amount) {
        this.pharInEmail = pharInEmail;
        this.pharOutEmail = pharOutEmail;
        this.productReference = productReference;
        this.amount = amount;
    }

    /**
     * Gets pharmacy in email.
     *
     * @return the pharmacy in email
     */
    public String getPharInEmail() {
        return this.pharInEmail;
    }

    /**
     * Gets pharmacy out email.
     *
     * @return the pharmacy out email
     */
    public String getPharOutEmail() {
        return this.pharOutEmail;
    }

    /**
     * Gets product's reference.
     *
     * @return the product's reference
     */
    public int getProductReference() {
        return this.productReference;
    }

    /**
     * Gets product's amount.
     *
     * @return the product's amount
     */
    public int getAmount() {
        return this.amount;
    }
}
