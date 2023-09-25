package lapr.project.model;

import lapr.project.data.FileReader;
import lapr.project.graphbase.Graph;

import java.io.IOException;
import java.util.LinkedList;


/**
 * The type Calculator.
 */
public class Calculator {

    /**
     * The File Reader
     */
    private static final FileReader fr = new FileReader();
    /**
     * Weight of the scooter considered as being 17 kg and courier weight considered to be 75 kg
     */
    private static final int TOTAL_WEIGHT = 92;

    /**
     * The Weight Courier considered to be 80 kg.
     */
    private static final int WEIGHT_COURIER = 80;

    /**
     * Mean velocity considered to be of 30km/h for the scooter
     */
    private static final int MEAN_VELOCITY = 30;

    /**
     * Eficiency of 100%
     */
    private static final double EFFICIENCY = 1;

    /**
     * Density of the air considered to be 1,275 kg/m^3
     */
    private static final double AIR_DENSITY = 1.275;

    /**
     * Drag coefficient
     */
    private static final double DRAG_COEFFICIENT = 1.1;

    /**
     * Front area of the scooter
     */
    private static final double FRONT_AREA = 0.3;

    /**
     * Acceleration due to gravity
     */
    private static final int GRAV_ACC = 10;

    /**
     * Coefficient of resistance
     */
    private static final double RESISTANCE_COEFFICIENT = 0.04;

    /**
     * Conversion of (1kg/m^3) * (1 km/h)^2 * (1 square meter) to Newtons
     * 0.278 is the conversion of km/h to m/s the SI unit
     */
    private static final double UNIT_CONVERSION_DRAG_FORCE = 1 * 0.278 * 0.278 * 1;

    /**
     * Calculation of dragForce in Newtons
     */
    private static final double DRAG_FORCE = DRAG_COEFFICIENT * FRONT_AREA * AIR_DENSITY * MEAN_VELOCITY * MEAN_VELOCITY * (0.5 * UNIT_CONVERSION_DRAG_FORCE);

    /**
     * Conversion of (1 m/second^2) * (1 kg) to Newtons
     */
    private static final double UNIT_CONVERSION_ROLLING_RESISTANCE = 1;

    /**
     * Calculation of rollingResistance in Newtons
     */
    private static final double ROLLING_RESISTANCE = GRAV_ACC * RESISTANCE_COEFFICIENT * TOTAL_WEIGHT * UNIT_CONVERSION_ROLLING_RESISTANCE;

    /**
     * ADVERSE FORCE
     */
    private static final double ADVERSE_FORCE = ROLLING_RESISTANCE + DRAG_FORCE;

    /**
     * Conversion of (1 killowatt hour)/(1 Newton) to km
     */
    private static final double UNIT_CONVERSION_RANGE = 3600;

    /**
     * Drag coefficient of the drone
     */
    private static final double DRAG_COEFFICIENT_DRONE = 0.3;

    /**
     * Front area of the drone
     */
    private static final double FRONT_AREA_DRONE = 0.4;

    /**
     * Mean velocity of the drone considered to be 50 km/h
     */
    private static final double MEAN_DRONE_VELOCITY = 50.0;

    /**
     * Calculation of dragForce of Drone in Newtons
     */
    private static final double DRAG_FORCE_DRONE = DRAG_COEFFICIENT_DRONE * FRONT_AREA_DRONE * AIR_DENSITY * MEAN_DRONE_VELOCITY * MEAN_DRONE_VELOCITY * (0.5 * UNIT_CONVERSION_DRAG_FORCE);

    /**
     * Creates a new Object Calculator.
     */
    private Calculator() {
        //Nothing
    }

    /**
     * Calculate autonomy double.
     *
     * @param battery    the battery
     * @param maxBattery the max battery
     * @return the double
     */
    public static double calculateAutonomy(int battery, double maxBattery) {

        //calculates the battery at the moment in kw*h
        double batMoment = battery * 0.01 * maxBattery * 0.001;

        //calculation of the range of the scooter
        return (batMoment / ADVERSE_FORCE * UNIT_CONVERSION_RANGE) * EFFICIENCY;
    }

    /**
     * Calculate autonomy double.
     *
     * @param weight        the weight
     * @param graph         the graph
     * @param listOfPaths   the list of paths
     * @param battery       the battery
     * @param maxBattery    the max battery
     * @param weightScooter the weight scooter
     * @return the double
     * @throws IOException the io exception
     */
    public static double calculateAutonomy(double weight, Graph<String, Integer> graph, LinkedList<String> listOfPaths, int battery, double maxBattery, double weightScooter) throws IOException {

        double totalWeight = WEIGHT_COURIER + weight + weightScooter;

        double elevationForce = calculateElevationForce(graph, listOfPaths, totalWeight);

        //calculates the battery at the moment in kw*h
        double batMoment = battery * 0.01 * maxBattery * 0.001;

        //calculation of rollingResistance in Newtons
        double rolling_resistance = GRAV_ACC * RESISTANCE_COEFFICIENT * (totalWeight) * UNIT_CONVERSION_ROLLING_RESISTANCE;

        //calculation of the range of the scooter
        return (batMoment / (DRAG_FORCE + elevationForce + rolling_resistance) * UNIT_CONVERSION_RANGE) * EFFICIENCY;
    }

    /**
     * CalculateElevationForce
     *
     * @param graph the graph
     * @param listOfPaths the list of paths
     * @param totalWeight the total weight
     * @return the double
     * @throws IOException IOException
     */
    private static double calculateElevationForce(Graph<String, Integer> graph, LinkedList<String> listOfPaths, double totalWeight) throws IOException {
        double elevationForce = 0.0;
        fr.saveCoordinatesElevation("Coordinates.txt");
        for (int i = 0; i < listOfPaths.size() - 1; i++) {
            int elevation1 = fr.getElevationByCoordinates(listOfPaths.get(i));
            int elevation2 = fr.getElevationByCoordinates(listOfPaths.get(i + 1));

            double distanceVertex = graph.getEdge(listOfPaths.get(i), listOfPaths.get(i + 1)).getWeight();

            int differenceInElevation = elevation1 - elevation2;

            if (differenceInElevation > 0) {
                double angle = Math.toRadians(Math.atan(differenceInElevation / (distanceVertex * 1000)));

                elevationForce = elevationForce + totalWeight * GRAV_ACC * Math.sin(angle);
            }
        }
        return elevationForce;
    }

    /**
     * Calculate autonomy for drone double.
     *
     * @param weight      the weight
     * @param battery     the battery
     * @param maxBattery  the max battery
     * @param weightDrone the weight drone
     * @return the double
     */
    public static double calculateAutonomyForDrone(double weight, int battery, double maxBattery, double weightDrone) {

        //calculates the battery at the moment in kw*h
        double batMoment = battery * 0.01 * maxBattery * 0.001;

        double weightForce = (weightDrone + weight) * GRAV_ACC;

        //calculation of the range of the scooter
        return (batMoment / (DRAG_FORCE_DRONE + weightForce) * UNIT_CONVERSION_RANGE) * EFFICIENCY;
    }

    /**
     * Calculate distance double.
     *
     * @param lat1 the lat 1
     * @param lon1 the lon 1
     * @param lat2 the lat 2
     * @param lon2 the lon 2
     * @return the double
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    /**
     * Calculate battery int.
     *
     * @param weight        the weight
     * @param graph         the graph
     * @param listOfPaths   the list of paths
     * @param distance      the distance
     * @param battery       the battery
     * @param maxBattery    the max battery
     * @param weightScooter the weight scooter
     * @return the int
     * @throws IOException the io exception
     */
    public static int calculateBattery(double weight, Graph<String, Integer> graph, LinkedList<String> listOfPaths, double distance, int battery, double maxBattery, double weightScooter) throws IOException {


        double totalWeight = weightScooter + WEIGHT_COURIER + weight;

        double elevationForce = calculateElevationForce(graph, listOfPaths, totalWeight);

        //calculation of rollingResistance in Newtons
        double rolling_resistance = GRAV_ACC * RESISTANCE_COEFFICIENT * (totalWeight) * UNIT_CONVERSION_ROLLING_RESISTANCE;

        double batMoment = distance * (DRAG_FORCE + rolling_resistance + elevationForce) / (UNIT_CONVERSION_RANGE * EFFICIENCY);

        return (int) (battery - (batMoment / (0.01 * maxBattery * 0.001)));
    }

    /**
     * Calculate battery for drone int.
     *
     * @param weight      the weight
     * @param distance    the distance
     * @param battery     the battery
     * @param maxBattery  the max battery
     * @param weightDrone the weight drone
     * @return the int
     */
    public static int calculateBatteryForDrone(double weight, double distance, int battery, double maxBattery, double weightDrone) {

        double weightForce = (weightDrone + weight) * GRAV_ACC;

        double batMoment = distance * (DRAG_FORCE_DRONE + weightForce) / (UNIT_CONVERSION_RANGE * EFFICIENCY);

        return (int) (battery - (batMoment / (0.01 * maxBattery * 0.001)));
    }

    /**
     * Calculate battery wasted for scooter double.
     *
     * @param weight        the weight
     * @param graph         the graph
     * @param listOfPaths   the list of paths
     * @param distance      the distance
     * @param maxBattery    the max battery
     * @param weightScooter the weight scooter
     * @return the double
     * @throws IOException the io exception
     */
    public static double calculateBatteryWastedForScooter(double weight, Graph<String, Integer> graph, LinkedList<String> listOfPaths, double distance, double maxBattery, double weightScooter) throws IOException {
        double totalWeight = weightScooter + WEIGHT_COURIER + weight;
        double elevationForce = calculateElevationForce(graph, listOfPaths, totalWeight);
        //calculation of rollingResistance in Newtons
        double rolling_resistance = GRAV_ACC * RESISTANCE_COEFFICIENT * (totalWeight) * UNIT_CONVERSION_ROLLING_RESISTANCE;
        return (distance * (DRAG_FORCE + rolling_resistance + elevationForce) / (UNIT_CONVERSION_RANGE * EFFICIENCY)) / (0.01 * maxBattery * 0.001);
    }

    /**
     * Calculate battery wasted for drone double.
     *
     * @param weight      the weight
     * @param distance    the distance
     * @param maxBattery  the max battery
     * @param weightDrone the weight drone
     * @return the double
     */
    public static double calculateBatteryWastedForDrone(double weight, double distance, double maxBattery, double weightDrone) {
        double weightForce = (weight + weightDrone) * GRAV_ACC;
        return (distance * (DRAG_FORCE_DRONE + weightForce) / (UNIT_CONVERSION_RANGE * EFFICIENCY)) / (0.01 * maxBattery * 0.001);
    }

    /**
     * Calculate time double.
     *
     * @param distance the distance
     * @return the double
     */
    public static double calculateTime(double distance) {
        return distance / MEAN_DRONE_VELOCITY;
    }
}
