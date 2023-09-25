package lapr.project.model;

/**
 * The type Coordinates elevation.
 */
public class CoordinatesElevation {

    /**
     * The gpsCoordinates.
     */
    private final String gpsCoordinates;

    /**
     * The elevation.
     */
    private final int elevation;

    /**
     * The street.
     */
    private final String street;

    /**
     * Instantiates a new Coordinates elevation.
     *
     * @param gpsCoordinates the gps coordinates
     * @param elevation      the elevation
     * @param street         the street
     */
    public CoordinatesElevation(String gpsCoordinates, int elevation, String street) {
        this.gpsCoordinates = gpsCoordinates;
        this.elevation = elevation;
        this.street = street;
    }

    /**
     * Gets elevation.
     *
     * @return the elevation
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * Gets gps coordinates.
     *
     * @return the gps coordinates
     */
    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    /**
     * Get street string.
     *
     * @return the string
     */
    public String getStreet() {
        return street;
    }
}
