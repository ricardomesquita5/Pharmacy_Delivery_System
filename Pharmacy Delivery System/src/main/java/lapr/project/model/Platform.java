package lapr.project.model;

import lapr.project.utils.authorization.FacadeAuthorization;

import java.util.Objects;

/**
 * The type Platform.
 *
 * @author Berkelios
 */
public class Platform {

    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization authorization;
    /**
     * The Platform's Designation.
     */
    private final String designation;

    /**
     * Instantiates a new Platform.
     *
     * @param strDesignation the Designation
     */
    public Platform(String strDesignation) {
        this.designation = strDesignation;
        this.authorization = new FacadeAuthorization();
    }

    /**
     * Gets Facade Authorization.
     *
     * @return the Facade Authorization
     */
    public FacadeAuthorization getFacadeAuthorization() {
        return this.authorization;
    }

    /**
     * Check if two objects are equals
     *
     * @param o Platform
     * @return true if two objects are equal or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform)) return false;
        Platform platform = (Platform) o;
        return designation.equals(platform.designation);
    }

    /**
     * Hash Code.
     *
     * @return code
     */
    @Override
    public int hashCode() {
        return Objects.hash(designation);
    }
}

