package lapr.project.model;

import lapr.project.data.ParkDB;

import java.sql.SQLException;

/**
 * The type Park.
 */
public class Park {

    /**
     * The Park's id.
     */
    private int id;
    /**
     * The Park's designation.
     */
    private final String designation;
    /**
     * The Park's spotsCapacity.
     */
    private int spotsCapacity;
    /**
     * The Park's powerCapacity.
     */
    private double powerCapacity;
    /**
     * The Park's chargingVehicles.
     */
    private final int chargingVehicles;

    /**
     * Instantiates a new Park.
     *
     * @param id            the id
     * @param designation   the designation
     * @param spotsCapacity the spots capacity
     * @param powerCapacity the power capacity
     */
    public Park(int id, String designation, int spotsCapacity, double powerCapacity) {
        this.id = id;
        this.designation = designation;
        this.spotsCapacity = spotsCapacity;
        this.powerCapacity = powerCapacity;
        this.chargingVehicles = 0;
    }

    /**
     * Instantiates a new Park.
     *
     * @param designation   the designation
     * @param spotsCapacity the spots capacity
     * @param powerCapacity the power capacity
     */
    public Park(String designation, String spotsCapacity, String powerCapacity) {
        this.designation = designation;
        this.setSpotsCapacity(spotsCapacity);
        this.setPowerCapacity(powerCapacity);
        this.chargingVehicles = 0;
    }

    /**
     * Sets power capacity.
     *
     * @param powerCapacity the power capacity
     */
    public void setPowerCapacity(String powerCapacity) {
        if (powerCapacity != null && !powerCapacity.trim().isEmpty()) {
            try {
                if (Double.parseDouble(powerCapacity) > 0) {
                    this.powerCapacity = Double.parseDouble(powerCapacity);
                } else {
                    throw new IllegalArgumentException("The Power Capacity should be 1 or higher!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Power Capacity!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Power Capacity!");
        }
    }

    /**
     * Sets spots capacity.
     *
     * @param spotsCapacity the spots capacity
     */
    public void setSpotsCapacity(String spotsCapacity) {
        if (spotsCapacity != null && !spotsCapacity.trim().isEmpty()) {
            try {
                if (Integer.parseInt(spotsCapacity) > 0) {
                    this.spotsCapacity = Integer.parseInt(spotsCapacity);
                } else {
                    throw new IllegalArgumentException("The Spots Capacity should be 1 or higher!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Spots Capacity!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Spots Capacity!");
        }
    }

    /**
     * Save.
     *
     * @param pharEmail the phar email
     * @throws SQLException the sql exception
     */
    public void save(String pharEmail) throws SQLException {
        new ParkDB().addPark(this, pharEmail);
    }

    /**
     * Gets charging scooters.
     *
     * @return the charging scooters
     */
    public int getChargingScooters() {
        return chargingVehicles;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets designation.
     *
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Gets spots capacity.
     *
     * @return the spots capacity
     */
    public int getSpotsCapacity() {
        return spotsCapacity;
    }

    /**
     * Gets power capacity.
     *
     * @return the power capacity
     */
    public double getPowerCapacity() {
        return powerCapacity;
    }

    /**
     * Textual Description of Park.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return "Park{" +
                "designation='" + designation + '\'' +
                ", spotsCapacity=" + spotsCapacity +
                ", powerCapacity=" + powerCapacity +
                '}';
    }
}
