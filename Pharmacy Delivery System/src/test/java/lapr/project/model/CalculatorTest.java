package lapr.project.model;

import lapr.project.data.FileReader;
import lapr.project.graphbase.Graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.LinkedList;

/**
 * The type Calculator test.
 */
class CalculatorTest {

    /**
     * The Graph.
     */
    private final Graph<String, Integer> graph;

    /**
     * Instantiates a new Calculator test.
     *
     * @throws IOException the io exception
     */
    public CalculatorTest() throws IOException {
        FileReader fr = new FileReader();
        this.graph = fr.getGraph();
    }

    /**
     * Calculate autonomy.
     */
    @Test
    void calculateAutonomy() {
        double exp = 0.17867950795922036;
        double result = Calculator.calculateAutonomyForDrone(100,100,100, 100);
        Assertions.assertEquals(exp, result);
        exp = 6.999430023775303;
        result = Calculator.calculateAutonomy(100,100);
        Assertions.assertEquals(exp, result);
    }

    /**
     * Calculate distance.
     */
    @Test
    void calculateDistance() {
        double exp1 = 0;
        double exp = 3046.6120571611687;
        double exp2 = 3039.605898328144;
        double exp3 = 38.15614879828958;
        double result2 = Calculator.calculateDistance(36.204823, 138.252930, 36.204823, 104.195396);
        double result = Calculator.calculateDistance(36.204823, 138.252930, 35.861660, 104.195396);
        double result1 = Calculator.calculateDistance(36.204823, 138.252930, 36.204823, 138.252930);
        double result3 = Calculator.calculateDistance(36.204823, 138.252930, 35.861660, 138.252930);
        Assertions.assertEquals(exp, result);
        Assertions.assertEquals(exp1, result1);
        Assertions.assertEquals(exp2, result2);
        Assertions.assertEquals(exp3, result3);
    }

    /**
     * Calculate battery.
     *
     * @throws IOException the io exception
     */
    @Test
    void calculateBattery() throws IOException {
        double exp = -6376.0;
        int result = Calculator.calculateBattery(0.0, this.graph, new LinkedList<>(), 1000, 100, 200, 0.0);
        Assertions.assertEquals(exp, result);
        exp = -3341.0;
        result = Calculator.calculateBatteryForDrone(1.0, 1000, 100, 200, 0.0);
        Assertions.assertEquals(exp, result);
    }

    /**
     * Calculate battery wasted.
     *
     * @throws IOException the io exception
     */
    @Test
    void calculateBatteryWasted() throws IOException {
        double exp = 12953.544263888889;
        double result = Calculator.calculateBatteryWastedForScooter(0.0, this.graph, new LinkedList<>(), 1000, 100, 0.0);
        Assertions.assertEquals(exp, result, 0.1);
        exp = 6883.490277777779;
        result = Calculator.calculateBatteryWastedForDrone(1.0, 1000, 100, 0.0);
        Assertions.assertEquals(exp, result, 0.1);
    }

    /**
     * Calculate time.
     */
    @Test
    void calculateTime() {
        double exp = 20.0;
        long result = (long) Calculator.calculateTime(1000);
        Assertions.assertEquals(exp, result);
    }
}