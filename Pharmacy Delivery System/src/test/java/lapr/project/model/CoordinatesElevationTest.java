package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Coordinates elevation test.
 */
class CoordinatesElevationTest {

    /**
     * The CoordinatesElevation.
     */
    private CoordinatesElevation ce;

    /**
     * Instantiates a new Coordinates elevation test.
     */
    public CoordinatesElevationTest() {
        System.out.println("CoordinatesElevation CoordinatesElevationTest() Test");
        this.ce = new CoordinatesElevation("41.455, -8.776", 10, "Rua da Foz");
    }

    /**
     * Gets elevation.
     */
    @Test
    void getElevation() {
        System.out.println("CoordinatesElevation getElevation() Test");
        Assertions.assertEquals(10, this.ce.getElevation());
    }

    /**
     * Gets gps coordinates.
     */
    @Test
    void getGpsCoordinates() {
        System.out.println("CoordinatesElevation getGpsCoordinates() Test");
        Assertions.assertEquals("41.455, -8.776", this.ce.getGpsCoordinates());
    }

    /**
     * Gets street.
     */
    @Test
    void getStreet() {
        System.out.println("CoordinatesElevation getStreet() Test");
        Assertions.assertEquals("Rua da Foz", this.ce.getStreet());
    }
}