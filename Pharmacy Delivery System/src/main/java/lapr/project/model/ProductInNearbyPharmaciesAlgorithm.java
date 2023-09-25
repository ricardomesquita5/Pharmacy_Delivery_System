package lapr.project.model;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Product In Nearby Pharmacies Algorithm.
 */
public class ProductInNearbyPharmaciesAlgorithm {

    /**
     * Create new ProductInNearbyPharmaciesAlgorithm
     */
    private ProductInNearbyPharmaciesAlgorithm() {
        //Nothing
    }

    /**
     * Check if nearby pharmacies have the products.
     *
     * @param pharIn  the pharmacy
     * @param product the product
     * @param amout   the amout necessary
     * @return map with the nearby pharmacies and the amount that they have
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public static Map<Pharmacy, Integer> checkNearbyPharmacies(Pharmacy pharIn, Product product, int amout) throws SQLException, IOException {
        Graph<String, Integer> pharmaciesGraph = new FileReader().getGraph();
        List<Pharmacy> listPharmacy = getAllPharmacies();
        Map<Pharmacy, Double> nearbyPharmacies = getNearbyPharmacies(pharIn, pharmaciesGraph, listPharmacy, product);
        Map<Pharmacy, Double> pharmaciesNearbyWithProducts = checkNearbyProducts(nearbyPharmacies, product);
        Map<Pharmacy, Integer> pharmaciesAndProductAmount = new LinkedHashMap<>();
        if (!pharmaciesNearbyWithProducts.isEmpty()) {
            //ordenar por proximidade
            Map<Pharmacy, Double> map = pharmaciesNearbyWithProducts.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect
                    (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, content) -> content, LinkedHashMap::new));
            pharmaciesAndProductAmount = checkAmounts(map, product, amout);
            makeTransferences(pharIn, pharmaciesAndProductAmount, product);
            return pharmaciesAndProductAmount;
        }
        return pharmaciesAndProductAmount;
    }

    /**
     * Make transferences.
     *
     * @param pharIn                     the pharmacy that need the product
     * @param pharmaciesAndProductAmount the pharmacies and product amounts of the product need
     * @param product                    the product need
     * @throws IOException the io exception
     */
    public static void makeTransferences(Pharmacy pharIn, Map<Pharmacy, Integer> pharmaciesAndProductAmount, Product product) throws IOException {
        for (Pharmacy pharOut : pharmaciesAndProductAmount.keySet()) {
            updateProductsInDataBase(pharIn, pharOut, product, pharmaciesAndProductAmount.get(pharOut));
            new TransferenceDB().addTransference(pharIn.getEmail(), pharOut.getEmail(), product.getReference(), pharmaciesAndProductAmount.get(pharOut));
            new EmailHandler().sendEmail(pharOut.getEmail(), "Transference of One Product", String.format("Hello,\nYou have to transfer the product with the reference: %d, %d unities to %s.\nBest Regards,\nBerkelios Company", product.getReference(), pharmaciesAndProductAmount.get(pharOut), pharIn.getDesignation()));
        }
    }

    /**
     * Update products in data base.
     *
     * @param pharIn  the phamarcy that will recive the product
     * @param pharOut the pharmacy that will give the product
     * @param product the product
     * @param amount  the product's amount
     */
    public static void updateProductsInDataBase(Pharmacy pharIn, Pharmacy pharOut, Product product, int amount) {
        PharmacyProduct productOut = new PharmacyProductDB().getPharmacyProduct(product.getReference(), pharOut.getEmail());
        int amountOut = productOut.getAmount();
        amountOut = amountOut - amount;
        productOut.setAmount("" + amountOut);
        new PharmacyProductDB().updatePharmacyProduct(productOut);
        PharmacyProduct productIn = null;
        try {
            productIn = new PharmacyProductDB().getPharmacyProduct(product.getReference(), pharIn.getEmail());
        } catch (IllegalArgumentException ia) {
            PharmacyProduct p = new PharmacyProduct(product.getReference(), pharIn.getEmail(), amount);
            new PharmacyProductDB().addPharmacyProduct(p);
        }
        if (productIn != null) {
            int amountIn = productIn.getAmount();
            amountIn = amountIn + amount;
            productIn.setAmount("" + amountIn);
            new PharmacyProductDB().updatePharmacyProduct(productIn);
        }
    }

    /**
     * Check amounts in nearby pharmacies.
     *
     * @param pharmaciesNearbyWithProducts the map with the pharmacies nearby with products
     * @param product                      the product
     * @param amout                        the product's amout necessary
     * @return the map with the nearbies pharmacies and the amount that it have
     */
    public static Map<Pharmacy, Integer> checkAmounts(Map<Pharmacy, Double> pharmaciesNearbyWithProducts, Product product, int amout) {
        Map<Pharmacy, Integer> pharmaciesAndProductAmount = new LinkedHashMap<>();
        int amountTotal = 0;
        int lastAmount = 0;
        for (Pharmacy p : pharmaciesNearbyWithProducts.keySet()) {
            int amountInPharmacy = new PharmacyProductDB().getPharmacyProduct(product.getReference(), p.getEmail()).getAmount();
            amountTotal = amountTotal + amountInPharmacy;
            if (amountTotal > amout) {
                amountInPharmacy = (amountTotal - (amountTotal - amout)) - lastAmount;
            }
            pharmaciesAndProductAmount.put(p, amountInPharmacy);
            if (amountTotal >= amout) {
                break;
            }
            lastAmount = lastAmount + amountInPharmacy;
        }
        return pharmaciesAndProductAmount;
    }

    /**
     * Get All Pharmacies.
     *
     * @return the pharmacies
     * @throws SQLException the sql exception
     */
    public static List<Pharmacy> getAllPharmacies() throws SQLException {
        return new PharmacyDB().getAllPharmacies();
    }

    /**
     * Gets nearby pharmacies.
     *
     * @param pharmacy        the pharmacy
     * @param pharmaciesGraph the pharmacies graph
     * @param listPharmacy    the list pharmacy
     * @param product         the product
     * @return the nearby pharmacies
     * @throws IOException the io exception
     */
    public static Map<Pharmacy, Double> getNearbyPharmacies(Pharmacy pharmacy, Graph<String, Integer> pharmaciesGraph, List<Pharmacy> listPharmacy, Product product) throws IOException {
        List<Scooter> scooters = getAllScooters(pharmacy);
        List<Drone> drones = getAllDrones(pharmacy);
        Map<Pharmacy, Double> map = new LinkedHashMap<>();
        double bestDrone = getBestDrone(drones, product);
        // o que anda mais em linha reta é o que consegue andar mais a subir e a descer
        Scooter bestScooter = getBestScooter(scooters);

        for (String coordinates : pharmaciesGraph.vertices()) {
            Pharmacy p = isPharmacyCoordinates(listPharmacy, coordinates);
            if (!coordinates.equals(pharmacy.getAddress().getGPSCoordinates()) && p != null) {
                LinkedList<String> pathGoing = new LinkedList<>();
                LinkedList<String> pathReturn = new LinkedList<>();
                double scooterDistanceGoing = GraphAlgorithms.shortestPath(pharmaciesGraph, pharmacy.getAddress().getGPSCoordinates(), coordinates, pathGoing);
                double scooterDistanceReturn = GraphAlgorithms.shortestPath(pharmaciesGraph, coordinates, pharmacy.getAddress().getGPSCoordinates(), pathReturn);
                // o ultimo sitio do pathGoing vai ser igual ao primeiro sitio do pathReturn
                pathReturn.removeFirst();
                pathGoing.addAll(pathReturn);
                // distancia em linha reta
                double droneDistance = Calculator.calculateDistance(Double.parseDouble(GraphAlgorithms.splitCoordinates(pharmacy.getAddress().getGPSCoordinates())[0]), Double.parseDouble(GraphAlgorithms.splitCoordinates(pharmacy.getAddress().getGPSCoordinates())[1]),
                        Double.parseDouble(GraphAlgorithms.splitCoordinates(coordinates)[0]), Double.parseDouble(GraphAlgorithms.splitCoordinates(coordinates)[1])) * 2;
                // distancia que a melhor scooter consegue percorrer no percurso em questão, tendo em conta as elevações de estrada e outros fatores
                double bestScooterDistance = Calculator.calculateAutonomy(product.getWeight(), pharmaciesGraph, pathGoing, 100, bestScooter.getMaxBattery(), bestScooter.getWeight());
                if ((scooterDistanceGoing + scooterDistanceReturn) < bestScooterDistance || droneDistance < bestDrone) {
                    // caso seja considerado em proximidade, ou seja, uma scooter ou um drone consegue chegar lá, a farmacia é guardada
                    // e a distancia mais pequena para uma posterior ordenação
                    map.put(p, Math.min((scooterDistanceGoing + scooterDistanceReturn), droneDistance));
                }
            }
        }
        return map;
    }

    /**
     * Is pharmacy coordinates.
     *
     * @param pharmacyList the pharmacy list
     * @param coordinates  the coordinates
     * @return the pharmacy
     */
    public static Pharmacy isPharmacyCoordinates(List<Pharmacy> pharmacyList, String coordinates) {
        for (Pharmacy p : pharmacyList) {
            if (p.getAddress().getGPSCoordinates().equalsIgnoreCase(coordinates)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Gets all scooters.
     *
     * @param pharmacy the pharmacy
     * @return the all scooters
     */
    public static List<Scooter> getAllScooters(Pharmacy pharmacy) {
        return new ScooterDB().getAllScooters(pharmacy.getEmail());
    }

    /**
     * Gets all drones.
     *
     * @param pharmacy the pharmacy
     * @return the all drones
     */
    public static List<Drone> getAllDrones(Pharmacy pharmacy) {
        return new DroneDB().getAllDrones(pharmacy.getEmail());
    }

    /**
     * Gets best scooter.
     *
     * @param scooters the scooters
     * @return the best scooter
     */
    public static Scooter getBestScooter(List<Scooter> scooters) {
        double maxDistance = 0;
        Scooter bestScooter = scooters.get(0);
        for (Scooter s : scooters) {
            double distance = Calculator.calculateAutonomy(100, s.getMaxBattery());
            if (distance > maxDistance) {
                maxDistance = distance;
                bestScooter = s;
            }
        }
        return bestScooter;
    }

    /**
     * Gets best drone.
     *
     * @param drones  the drones
     * @param product the product
     * @return the best drone
     */
    public static double getBestDrone(List<Drone> drones, Product product) {
        double maxDistance = 0;
        for (Drone d : drones) {
            double distance = Calculator.calculateAutonomyForDrone(product.getWeight(), 100, d.getMaxBattery(), d.getWeight());
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }

    /**
     * Check the nearby pharmacies with the necessary product.
     *
     * @param nearbyPharmacies the nearby pharmacies list
     * @param product          the product
     * @return the map with the pharmacy that have the product
     * @throws SQLException the sql exception
     */
    public static Map<Pharmacy, Double> checkNearbyProducts(Map<Pharmacy, Double> nearbyPharmacies, Product product) throws SQLException {
        Map<Pharmacy, Double> pharmaciesNearbyWithProducts = new LinkedHashMap<>();
        for (Pharmacy pharmacy : nearbyPharmacies.keySet()) {
            if (checkProducts(pharmacy, product)) {
                pharmaciesNearbyWithProducts.put(pharmacy, nearbyPharmacies.get(pharmacy));
            }
        }
        return pharmaciesNearbyWithProducts;
    }

    /**
     * Check if one pharmacy have the necessary product.
     *
     * @param pharmacy the pharmacy
     * @param product  the product
     * @return true if the pharmacy have the product or false if not
     * @throws SQLException the sql exception
     */
    public static boolean checkProducts(Pharmacy pharmacy, Product product) throws SQLException {
        PharmacyProduct pp = getPharmacyProduct(pharmacy, product);
        return ((pp != null) && (pp.getAmount() > 0));
    }

    /**
     * Get Pharmacy Product.
     *
     * @param pharmacy the pharmacy
     * @param product  the product
     * @return the pharmacy product if the pharmacy have the product or null
     */
    public static PharmacyProduct getPharmacyProduct(Pharmacy pharmacy, Product product) {
        try {
            return new PharmacyProductDB().getPharmacyProduct(product.getReference(), pharmacy.getEmail());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }
}
