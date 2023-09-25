package lapr.project.model;

import lapr.project.data.DroneDB;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Drone.
 */
public class Drone extends Vehicle {

    /**
     * Instantiates a new Drone.
     *
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param weight     the weight
     */
    public Drone(String capacity, String power, String maxBattery, String weight) {
        super(capacity, power, maxBattery, weight);
    }

    /**
     * Instantiates a new Drone.
     *
     * @param id         the id
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param battery    the battery
     * @param weight     the weight
     */
    public Drone(int id, Double capacity, Double power, Double maxBattery, int battery, Double weight) {
        super(id, capacity, power, maxBattery, battery, weight);
    }

    /**
     * Save Object in Data Base.
     *
     * @param pharEmail the pharmacy email
     * @throws SQLException the sql exception
     */
    public void save(String pharEmail) throws SQLException {
        new DroneDB().addDrone(this, pharEmail);
    }

    /**
     * Remove Object in Data Base.
     *
     * @throws SQLException the sql exception
     */
    public void remove() throws SQLException {
        new DroneDB().removeDrone(this.getId());
    }

    /**
     * Update Object in Data Base.
     *
     * @throws SQLException the sql exception
     */
    public void update() throws SQLException {
        new DroneDB().updateDrone(this);
    }

    /**
     * Check if two Drones are equals
     *
     * @param o Drone
     * @return true if are equals or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        Drone drone = (Drone) o;
        return getId() == drone.getId();
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
