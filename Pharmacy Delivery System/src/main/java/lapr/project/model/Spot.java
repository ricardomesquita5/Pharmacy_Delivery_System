package lapr.project.model;

import lapr.project.data.SpotDB;

import java.sql.SQLException;

/**
 * The type Spot.
 */
public class Spot {

    /**
     * The Spot's ID.
     */
    private int id;
    /**
     * The Spot's Charging Spot.
     */
    private String chargingSpot;
    /**
     * The Spot's ID of Park.
     */
    private int idPark;

    /**
     * Instantiates a new Spot.
     *
     * @param id           the id
     * @param chargingSpot the charging spot
     */
    public Spot(int id, String chargingSpot) {
        this.id = id;
        this.chargingSpot = chargingSpot;
    }

    /**
     * Instantiates a new Spot.
     *
     * @param id           the id
     * @param chargingSpot the charging spot
     * @param idPark       the id park
     */
    public Spot(int id, String chargingSpot, int idPark) {
        this.id = id;
        this.chargingSpot = chargingSpot;
        this.idPark = idPark;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            try {
                this.id = Integer.parseInt(id);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid ID!");
            }
        } else {
            throw new IllegalArgumentException("Invalid ID!");
        }
    }


    /**
     * Get id park int.
     *
     * @return the int
     */
    public int getIdPark() {
        return this.idPark;
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
     * Gets charging spot.
     *
     * @return the charging spot
     */
    public String getChargingSpot() {
        return this.chargingSpot;
    }


    /**
     * Sets charging spot.
     *
     * @param chargingSpot the charging spot
     */
    public void setChargingSpot(String chargingSpot) {
        this.chargingSpot = chargingSpot;
    }


    /**
     * Save.
     *
     * @param idPark the id park
     * @throws SQLException the sql exception
     */
    public void save(int idPark) throws SQLException {
        new SpotDB().addSpot(this, idPark);
    }

    /**
     * Textual Description of Spot.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return String.format("ID: %d - Charging Spot: %s - Id Park: %d ", this.id, this.chargingSpot, this.idPark);
    }
}
