package lapr.project.model;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;

import java.io.IOException;
import java.util.*;

/**
 * The type Delivery creation.
 */
public class DeliveryCreation {

    /**
     * Instantiates a new Delivery creation.
     */
    private DeliveryCreation() {
        //nothing
    }

    /**
     * Delivery data map.
     *
     * @param orderList the order list
     * @param graph     the graph
     * @param phar      the phar
     * @return the map
     * @throws IOException the io exception
     */
    public static Map<Scooter, List<String>> deliveryData(List<Order> orderList, Graph<String, Integer> graph, Pharmacy phar) throws IOException {
        Map<Scooter, List<String>> map = new LinkedHashMap<>();
        LinkedList<String> pathList = new LinkedList<>();
        pathList.add(phar.getAddress().getGPSCoordinates());
        List<Scooter> scootersList = new ScooterDB().getAvailableScooters(phar.getEmail());
        getPaths(orderList, pathList);
        LinkedList<String> pathFinal = new LinkedList<>();
        if (isPossible(pathFinal, pathList, orderList, graph, scootersList) && !scootersList.isEmpty()) {
            map.put(scootersList.get(0), pathFinal);
            return map;
        }
        return map;
    }

    /**
     * Delivery drone data map.
     *
     * @param orderList the order list
     * @param graph     the graph
     * @param phar      the phar
     * @return the map
     * @throws IOException the io exception
     */
    public static Map<Drone, List<String>> deliveryDroneData(List<Order> orderList, Graph<String, Integer> graph, Pharmacy phar) throws IOException {
        Map<Drone, List<String>> map = new LinkedHashMap<>();
        LinkedList<String> pathList = new LinkedList<>();
        pathList.add(phar.getAddress().getGPSCoordinates());
        List<Drone> dronesList = new DroneDB().getAvailableDrones(phar.getEmail());
        getPaths(orderList, pathList);
        LinkedList<String> pathFinal = new LinkedList<>();
        if (isPossibleForDrones(pathFinal, pathList, orderList, graph, dronesList) && !dronesList.isEmpty()) {
            map.put(dronesList.get(0), pathFinal);
            return map;
        }
        return map;
    }

    /**
     * Is possible boolean.
     *
     * @param finalPath       the final path
     * @param pathList        the path list
     * @param ordersToDeliver the orders to deliver
     * @param graph           the graph
     * @param listScooters    the list scooters
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean isPossible(List<String> finalPath, List<String> pathList, List<Order> ordersToDeliver, Graph<String, Integer> graph, List<Scooter> listScooters) throws IOException {
        Map<Double, LinkedList<String>> map = calculateDistanceOfPaths(pathList, graph);
        finalPath.addAll(map.get(map.keySet().iterator().next()));
        double distance = map.keySet().iterator().next();
        double weight = getWeight(ordersToDeliver);
        List<Scooter> scooterList = new ArrayList<>(listScooters);
        for (int l = 0; l < listScooters.size(); l++) {
            if (distance >= Calculator.calculateAutonomy(weight, graph, map.get(map.keySet().iterator().next()), listScooters.get(l).getBattery(), listScooters.get(l).getMaxBattery(), listScooters.get(l).getWeight()) || weight > listScooters.get(l).getCapacity()) {
                listScooters.remove(listScooters.get(l));
                l--;
            }
        }
        if (listScooters.isEmpty()) {
            List<Pharmacy> pharmacyList = new PharmacyDB().getAllPharmacies();
            listScooters.addAll(scooterList);
            for (int i = 0; i < listScooters.size(); i++) {
                if (isPossibleWithStops(finalPath, listScooters.get(i), pharmacyList, weight, graph)) {
                    listScooters.remove(listScooters.get(i));
                    i--;
                }
            }
        }
        return !listScooters.isEmpty();
    }

    /**
     * IsPossibleWithStops boolean.
     *
     * @param finalPath    the final path
     * @param scooter      the scooter
     * @param pharmacyList the pharmacy list
     * @param weight       the weight
     * @param graph        the graph
     * @return the boolean
     * @throws IOException IOException
     */
    private static boolean isPossibleWithStops(List<String> finalPath, Scooter scooter, List<Pharmacy> pharmacyList, double weight, Graph<String, Integer> graph) throws IOException {
        double distance = 0;
        LinkedList<String> pathToPharmacy = new LinkedList<>();
        for (int i = 0; i < finalPath.size() - 1; i++) {
            Pharmacy p = ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(pharmacyList, finalPath.get(i));
            pathToPharmacy.add(finalPath.get(i));
            if (p != null && i != 0) {
                if (!(distance < Calculator.calculateAutonomy(weight, graph, pathToPharmacy, scooter.getBattery(), scooter.getMaxBattery(), scooter.getWeight()) && weight < scooter.getCapacity())) {
                    return false;
                } else {
                    scooter.setBattery(100);
                    distance = 0;
                    pathToPharmacy = new LinkedList<>();
                    pathToPharmacy.add(finalPath.get(i));
                }
            }
            distance = distance + graph.getEdge(finalPath.get(i), finalPath.get(i + 1)).getWeight();
        }
        return (!(distance < Calculator.calculateAutonomy(weight, graph, pathToPharmacy, scooter.getBattery(), scooter.getMaxBattery(), scooter.getWeight()) && weight < scooter.getCapacity()));
    }

    /**
     * IsPossibleWithStopsForDrone boolean.
     *
     * @param finalPath    the final path
     * @param drone        the drone
     * @param pharmacyList the pharmacy list
     * @param weight       the weight
     * @param graph        the graph
     * @return the boolean
     * @throws IOException IOException
     */
    private static boolean isPossibleWithStopsForDrone(List<String> finalPath, Drone drone, List<Pharmacy> pharmacyList, double weight, Graph<String, Integer> graph) throws IOException {
        double distance = 0;
        LinkedList<String> pathToPharmacy = new LinkedList<>();
        for (int i = 0; i < finalPath.size() - 1; i++) {
            Pharmacy p = ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(pharmacyList, finalPath.get(i));
            pathToPharmacy.add(finalPath.get(i));
            if (p != null && i != 0) {
                if (!(distance < Calculator.calculateAutonomy(weight, graph, pathToPharmacy, drone.getBattery(), drone.getMaxBattery(), drone.getWeight()) && weight < drone.getCapacity())) {
                    return false;
                } else {
                    drone.setBattery(100);
                    distance = 0;
                    pathToPharmacy = new LinkedList<>();
                    pathToPharmacy.add(finalPath.get(i));
                }
            }
            distance = distance + graph.getEdge(finalPath.get(i), finalPath.get(i + 1)).getWeight();
        }
        return (!(distance < Calculator.calculateAutonomy(weight, graph, pathToPharmacy, drone.getBattery(), drone.getMaxBattery(), drone.getWeight()) && weight < drone.getCapacity()));
    }

    /**
     * Calculate distance of paths map.
     *
     * @param pathList the path list
     * @param graph    the graph
     * @return the map
     */
    public static Map<Double, LinkedList<String>> calculateDistanceOfPaths(List<String> pathList, Graph<String, Integer> graph) {
        double distance = 0;
        LinkedList<String> listOfPaths = new LinkedList<>();
        Map<Double, LinkedList<String>> mapReturn = new TreeMap<>();

        for (int z = 0; z < pathList.size() - 1; z++) {
            LinkedList<String> listPath = new LinkedList<>();
            distance = distance + GraphAlgorithms.shortestPath(graph, pathList.get(z), pathList.get(z + 1), listPath);
            for (int i = 0; i < listPath.size(); i++) {
                if (i > 0 || z == 0) {
                    listOfPaths.add(listPath.get(i));
                }
            }
        }

        LinkedList<String> listOfPaths1 = new LinkedList<>();
        distance = distance + GraphAlgorithms.shortestPath(graph, pathList.get(pathList.size() - 1), pathList.get(0), listOfPaths1);
        for (int i = 0; i < listOfPaths1.size(); i++) {
            if (i > 0) {
                listOfPaths.add(listOfPaths1.get(i));
            }
        }
        mapReturn.put(distance, listOfPaths);
        return mapReturn;
    }

    /**
     * Is possible for drones boolean.
     *
     * @param finalPath       the final path
     * @param pathList        the path list
     * @param ordersToDeliver the orders to deliver
     * @param graph           the graph
     * @param dronesList      the drones list
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean isPossibleForDrones(List<String> finalPath, List<String> pathList, List<Order> ordersToDeliver, Graph<String, Integer> graph, List<Drone> dronesList) throws IOException {
        Map<Double, LinkedList<String>> map = calculateDistanceOfPaths(pathList, graph);
        finalPath.addAll(map.get(map.keySet().iterator().next()));
        double distance = map.keySet().iterator().next();
        double weight = getWeight(ordersToDeliver);
        List<Drone> listDrones = new ArrayList<>(dronesList);
        for (int l = 0; l < dronesList.size(); l++) {
            if (distance >= Calculator.calculateAutonomyForDrone(weight, dronesList.get(l).getBattery(), dronesList.get(l).getMaxBattery(), dronesList.get(l).getWeight()) - 0.28 || weight > dronesList.get(l).getCapacity()) {
                dronesList.remove(dronesList.get(l));
                l--;
            }
        }
        if (dronesList.isEmpty()) {
            List<Pharmacy> pharmacyList = new PharmacyDB().getAllPharmacies();
            dronesList.addAll(listDrones);
            for (int i = 0; i < dronesList.size(); i++) {
                if (isPossibleWithStopsForDrone(finalPath, dronesList.get(i), pharmacyList, weight, graph)) {
                    dronesList.remove(dronesList.get(i));
                    i--;
                }
            }
        }
        return !dronesList.isEmpty();
    }

    /**
     * Gets weight.
     *
     * @param ordersToDeliver the orders to deliver
     * @return the weight
     */
    public static double getWeight(List<Order> ordersToDeliver) {
        double weight = 0;
        for (Order order : ordersToDeliver) {
            weight = weight + new OrderDB().getweightByOrderId(order.getIdOrder());
        }
        return weight;
    }

    /**
     * Gets paths.
     *
     * @param orderList the order list
     * @param pathList  the path list
     */
    public static void getPaths(List<Order> orderList, List<String> pathList) {
        for (Order order : orderList) {
            String coordinates = new OrderDB().getOrderAddressCoordinatesByClientEmail(new OrderDB().getEmailClientbyOrderId(order.getIdOrder()));
            if (!pathList.contains(coordinates)) {
                pathList.add(coordinates);
            }
        }
    }
}
