package lapr.project.model;

import lapr.project.data.ScooterDB;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Scooter.
 */
public class Scooter extends Vehicle {

    /**
     * Instantiates a new Scooter.
     *
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param weight     the weight
     */
    public Scooter(String capacity, String power, String maxBattery, String weight) {
        super(capacity, power, maxBattery, weight);
    }

    /**
     * Instantiates a new Scooter.
     *
     * @param id         the id
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param battery    the battery
     * @param weight     the weight
     */
    public Scooter(int id, Double capacity, Double power, Double maxBattery, int battery, Double weight) {
        super(id, capacity, power, maxBattery, battery, weight);
    }

    /**
     * Save Object in Data Base.
     *
     * @param pharEmail the pharmacy email
     * @throws SQLException the sql exception
     */
    public void save(String pharEmail) throws SQLException {
        new ScooterDB().addScooter(this, pharEmail);
    }

    /**
     * Remove Object in Data Base.
     *
     * @throws SQLException the sql exception
     */
    public void remove() throws SQLException {
        new ScooterDB().removeScooter(this.getId());
    }

    /**
     * Update Object in Data Base.
     *
     * @throws SQLException the sql exception
     */
    public void update() throws SQLException {
        new ScooterDB().updateScooter(this);
    }

    /**
     * Check if two Scooters are equals
     *
     * @param o Scooter
     * @return true if are equals or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scooter)) return false;
        Scooter scooter = (Scooter) o;
        return getId() == scooter.getId();
    }

    /**
     * Hash Code
     *
     * @return code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
