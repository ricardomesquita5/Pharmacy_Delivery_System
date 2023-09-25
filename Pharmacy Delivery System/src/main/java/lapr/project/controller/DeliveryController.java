package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;
import lapr.project.model.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The type Delivery controller.
 */
public class DeliveryController {

    /**
     * The Delivery By Scooter.
     */
    private DeliveryByScooter ds;
    /**
     * The Courier.
     */
    private Courier c;
    /**
     * The Scooter.
     */
    private Scooter s;
    /**
     * The Spot.
     */
    private Spot spot;
    /**
     * The Drone.
     */
    private Drone drone;
    /**
     * The Email Handler.
     */
    private final EmailHandler eH = new EmailHandler();
    /**
     * The Delivery By Drone.
     */
    private DeliveryByDrone dd;
    /**
     * The Graph.
     */
    private final Graph<String, Integer> graph;
    /**
     * The Graph 1.
     */
    private final Graph<String, Integer> graph1;

    /**
     * Instantiates a new Delivery controller.
     *
     * @throws IOException the io exception
     */
    public DeliveryController() throws IOException {
        FileReader fr = new FileReader();
        graph = fr.getGraph();
        graph1 = GraphAlgorithms.minDistGraph();
    }

    /**
     * Create delivery for scooter map.
     *
     * @param ordersList the orders list
     * @param phar       the phar
     * @return the map
     * @throws IOException the io exception
     */
    public Map<Scooter, List<String>> createDeliveryForScooter(List<Order> ordersList, Pharmacy phar) throws IOException {
        return DeliveryCreation.deliveryData(ordersList, graph, phar);
    }

    /**
     * Create delivery for drone map.
     *
     * @param ordersList the orders list
     * @param phar       the phar
     * @return the map
     * @throws IOException the io exception
     */
    public Map<Drone, List<String>> createDeliveryForDrone(List<Order> ordersList, Pharmacy phar) throws IOException {
        return DeliveryCreation.deliveryDroneData(ordersList, graph1, phar);
    }

    /**
     * Get drone drone.
     *
     * @param drone the drone
     */
    public void getDrone(Drone drone) {
        this.drone = drone;
    }

    /**
     * Get drone drone.
     *
     * @return the drone
     */
    public Drone getDronee() {
        return this.drone;
    }

    /**
     * Add delivery by scooter boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean addDeliveryByScooter(Scooter s) {
        try {
            this.ds = new DeliveryByScooter();
            this.ds.save(s.getId(), c.getEmail());
            return true;
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     * Gets orders from delivery.
     *
     * @return the orders from delivery
     */
    public List<Order> getOrdersFromDelivery() {
        return new OrderDB().getOrdersByDeliveryId(this.ds.getDeliveryId());
    }

    /**
     * Gets pharmacies list.
     *
     * @return the pharmacies list
     */
    public List<Pharmacy> getPharmaciesList() {
        return new PharmacyDB().getAllPharmacies();
    }

    /**
     * Gets all orders.
     *
     * @param pharEmail the phar email
     * @return the all orders
     */
    public List<Order> getAllOrders(String pharEmail) {
        return new OrderDB().getAllOrders(pharEmail);
    }

    /**
     * Change courier boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public boolean changeCourier(String status) {
        try {
            this.c.setStatus(status);
            return true;
        } catch (IllegalArgumentException ia) {
            this.c = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update courier.
     */
    public void updateCourier() {
        this.c.update();
    }

    /**
     * Change delivery by scooter.
     *
     * @param status the status
     */
    public void changeDeliveryByScooter(String status) {
        try {
            this.ds.setStatus(status);
        } catch (IllegalArgumentException ia) {
            this.ds = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update delivery by scooter.
     */
    public void updateDeliveryByScooter() {
        this.ds.update();
    }

    /**
     * Gets delivery by scooter.
     *
     * @param status the status
     * @return the delivery by scooter
     */
    public boolean getDeliveryByScooter(String status) {
        this.ds = new DeliveryByScooterDB().getDeliveryByScooterByEmailCourier(status, c.getEmail());
        return (new DeliveryByScooterDB().getDeliveryByScooterByEmailCourier(status, c.getEmail()).getStatus()) != null;
    }

    /**
     * Change delivery by drone.
     *
     * @param status the status
     */
    public void changeDeliveryByDrone(String status) {
        try {
            this.dd.setStatus(status);
        } catch (IllegalArgumentException ia) {
            this.dd = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update delivery by drone.
     */
    public void updateDeliveryByDrone() {
        this.dd.update();
    }

    /**
     * Gets courier by email.
     *
     * @param userEmail the user email
     */
    public void getCourierByEmail(String userEmail) {
        this.c = new CourierDB().getCourier(userEmail);
    }

    /**
     * Get email pharmacy string.
     *
     * @return the string
     */
    public String getEmailPharmacy() {
        return new CourierDB().getEmailPharmacyByEmailCourier(c.getEmail());
    }

    /**
     * Gets available spots.
     *
     * @param email the email
     * @return the available spots
     */
    public List<Spot> getAvailableSpots(String email) {
        return new SpotDB().getAvailableSpots(email, "Scooter");
    }

    /**
     * Get scooter scooter.
     *
     * @return the scooter
     */
    public Scooter getScooter() {
        return this.s = new ScooterDB().getScooterByemailCourierOnDel(this.c.getEmail());
    }

    /**
     * Get spot.
     *
     * @param id     the id
     * @param idPark the id park
     */
    public void getSpot(int id, int idPark) {
        this.spot = new SpotDB().getSpot(id, idPark);
    }

    /**
     * Get spot by scooter id.
     *
     * @param scooterId the scooter id
     */
    public void getSpotByScooterId(int scooterId) {
        this.spot = new SpotDB().getSpotByScooterId(scooterId, "Scooter");
    }

    /**
     * Update spot.
     *
     * @param flag    the flag
     * @param scooter the scooter
     */
    public void updateSpot(boolean flag, Scooter scooter) {
        if (flag) {
            new SpotDB().updateSpot(spot.getId(), spot.getIdPark(), scooter.getId());
        } else {
            new SpotDB().updateSpot2(spot.getId(), spot.getIdPark());
        }
    }

    /**
     * Update spot drone.
     *
     * @param flag the flag
     * @param dr   the dr
     */
    public void updateSpotDrone(boolean flag, Drone dr) {
        if (flag) {
            new SpotDB().updateSpot3(spot.getId(), spot.getIdPark(), dr.getId());
        } else {
            new SpotDB().updateSpot2(spot.getId(), spot.getIdPark());
        }
    }

    /**
     * Gets battery scooter.
     *
     * @return the battery scooter
     * @throws IOException the io exception
     */
    public int getBatteryScooter() throws IOException {
        LinkedList<Order> orderList = new OrderDB().getOrdersByDeliveryId(ds.getDeliveryId());
        LinkedList<String> path = new LinkedList<>();
        path.add(new PharmacyDB().getPharmacyAddressCoordinatesByCourierEmail(c.getEmail()));
        getPath(orderList, path);
        List<Pharmacy> pharmacyList = new PharmacyDB().getAllPharmacies();
        double distance = 0;
        boolean flag = true;
        Map<Double, LinkedList<String>> map = DeliveryCreation.calculateDistanceOfPaths(path, graph);
        for (int i = map.get(map.keySet().iterator().next()).size() - 2; i >= 0; i--) {
            distance = distance + graph.getEdge(map.get(map.keySet().iterator().next()).get(i), map.get(map.keySet().iterator().next()).get(i + 1)).getWeight();
            if (i != 0 && ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(pharmacyList, map.get(map.keySet().iterator().next()).get(i)) != null) {
                flag = false;
                break;
            }
        }
        double weight = DeliveryCreation.getWeight(orderList);
        if (flag) {
            return Calculator.calculateBattery(weight, graph, map.get(map.keySet().iterator().next()), distance, s.getBattery(), s.getMaxBattery(), s.getWeight());
        }
        return Calculator.calculateBattery(weight, graph, map.get(map.keySet().iterator().next()), distance, 100, s.getMaxBattery(), s.getWeight());
    }

    /**
     * Gets battery waste for scooter.
     *
     * @param orderList the order list
     * @param phar      the phar
     * @param scooter   the scooter
     * @return the battery waste for scooter
     * @throws IOException the io exception
     */
    public double getBatteryWasteForScooter(List<Order> orderList, Pharmacy phar, Scooter scooter) throws IOException {
        LinkedList<String> path = new LinkedList<>();
        path.add(phar.getAddress().getGPSCoordinates());
        getPath(orderList, path);
        Map<Double, LinkedList<String>> map = DeliveryCreation.calculateDistanceOfPaths(path, graph);
        double distance = map.keySet().iterator().next();
        double weight = DeliveryCreation.getWeight(orderList);
        return Calculator.calculateBatteryWastedForScooter(weight, graph, map.get(map.keySet().iterator().next()), distance, scooter.getMaxBattery(), scooter.getWeight());
    }

    /**
     * Change scooter boolean.
     *
     * @param battery the battery
     * @return the boolean
     */
    public boolean changeScooter(int battery) {
        try {
            this.s.setBattery(battery);
            return true;
        } catch (IllegalArgumentException ia) {
            this.s = null;
            throw new IllegalArgumentException(ia.getMessage());
        }

    }

    /**
     * Update scooter.
     */
    public void updateScooter() {
        new ScooterDB().updateScooterBattery(s.getBattery(), s.getId());
    }

    /**
     * Gets scooter by id.
     *
     * @param scooterID the scooter id
     */
    public void getScooterByID(int scooterID) {
        this.s = new ScooterDB().getScooter(scooterID);
    }

    /**
     * Gets battery waste for drone.
     *
     * @param orderList1 the order list 1
     * @param phar       the phar
     * @param drone      the drone
     * @return the battery waste for drone
     */
    public double getBatteryWasteForDrone(List<Order> orderList1, Pharmacy phar, Drone drone) {
        LinkedList<String> path = new LinkedList<>();
        path.add(phar.getAddress().getGPSCoordinates());
        getPath(orderList1, path);
        Map<Double, LinkedList<String>> map = DeliveryCreation.calculateDistanceOfPaths(path, graph1);
        double distance = map.keySet().iterator().next();
        double weight = DeliveryCreation.getWeight(orderList1);
        return Calculator.calculateBatteryWastedForDrone(weight, distance, drone.getMaxBattery(), drone.getWeight());
    }

    /**
     * Add delivery by drone boolean.
     *
     * @param d the d
     * @return the boolean
     */
    public boolean addDeliveryByDrone(Drone d) {
        try {
            this.dd = new DeliveryByDrone();
            this.dd.save(d.getId());
            return true;
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     * Gets delivery by drone.
     *
     * @param status the status
     * @param drone  the drone
     */
    public void getDeliveryByDrone(String status, Drone drone) {
        this.dd = new DeliveryByDroneDB().getDeliveryByDroneByDroneId(status, drone.getId());
    }

    /**
     * Update order drone.
     *
     * @param list the list
     */
    public void updateOrderDrone(List<Order> list) {
        for (Order order : list) {
            sendEmail(order);
            new OrderDB().updateOrder(order.getIdOrder(), dd.getDeliveryId());
        }
    }

    /**
     * Update order.
     *
     * @param list the list
     */
    public void updateOrder(List<Order> list) {
        for (Order order : list) {
            new OrderDB().updateOrder(order.getIdOrder(), ds.getDeliveryId());
        }
    }

    /**
     * Get spot by drone id.
     *
     * @param droneId the drone id
     */
    public void getSpotByDroneId(int droneId) {
        this.spot = new SpotDB().getSpotByDroneId(droneId, "Drone");
    }

    /**
     * Gets available couriers.
     *
     * @param email the email
     * @return the available couriers
     */
    public List<Courier> getAvailableCouriers(String email) {
        return new CourierDB().getAvailableCouriers(email);
    }

    /**
     * Gets time of delivery for drone.
     *
     * @param orderList1 the order list 1
     * @param phar       the phar
     * @return the time of delivery for drone
     */
    public double getTimeOfDeliveryForDrone(List<Order> orderList1, Pharmacy phar) {
        LinkedList<String> path = new LinkedList<>();
        path.add(phar.getAddress().getGPSCoordinates());
        getPath(orderList1, path);
        Map<Double, LinkedList<String>> map = DeliveryCreation.calculateDistanceOfPaths(path, graph1);
        double distance = map.keySet().iterator().next();
        return Calculator.calculateTime(distance);
    }

    /**
     * Gets battery drone.
     *
     * @param pharmacy the pharmacy
     * @param drone    the drone
     * @return the battery drone
     * @throws IOException the io exception
     */
    public int getBatteryDrone(Pharmacy pharmacy, Drone drone) throws IOException {
        LinkedList<Order> orderList = new OrderDB().getOrdersByDeliveryId(dd.getDeliveryId());
        LinkedList<String> path = new LinkedList<>();
        path.add(pharmacy.getAddress().getGPSCoordinates());
        getPath(orderList, path);
        List<Pharmacy> pharmacyList = new PharmacyDB().getAllPharmacies();
        double distance = 0;
        boolean flag = true;
        Map<Double, LinkedList<String>> map = DeliveryCreation.calculateDistanceOfPaths(path, graph1);
        for (int i = map.get(map.keySet().iterator().next()).size() - 2; i >= 0; i--) {
            distance = distance + graph1.getEdge(map.get(map.keySet().iterator().next()).get(i), map.get(map.keySet().iterator().next()).get(i + 1)).getWeight();
            if (i != 0 && ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(pharmacyList, map.get(map.keySet().iterator().next()).get(i)) != null) {
                flag = false;
                break;
            }
        }
        double weight = DeliveryCreation.getWeight(orderList);
        if (flag) {
            return Calculator.calculateBatteryForDrone(weight, distance, drone.getBattery(), drone.getMaxBattery(), drone.getWeight());
        }
        return Calculator.calculateBatteryForDrone(weight, distance, 100, drone.getMaxBattery(), drone.getWeight());
    }

    /**
     * Change drone boolean.
     *
     * @param battery the battery
     * @return the boolean
     */
    public boolean changeDrone(int battery) {
        try {
            this.drone.setBattery(battery);
            return true;
        } catch (IllegalArgumentException ia) {
            this.drone = null;
            throw new IllegalArgumentException(ia.getMessage());
        }
    }

    /**
     * Update drone.
     */
    public void updateDrone() {
        new DroneDB().updateDrone2(this.drone);
    }

    /**
     * Gets path.
     *
     * @param orderList the order list
     * @param path      the path
     * @return the path
     */
    public List<String> getPath(List<Order> orderList, List<String> path) {
        for (Order order : orderList) {
            String coordinates = new OrderDB().getOrderAddressCoordinatesByClientEmail(new OrderDB().getEmailClientbyOrderId(order.getIdOrder()));
            if (!path.contains(coordinates)) {
                path.add(coordinates);
            }
        }
        return path;
    }

    /**
     * Send email.
     *
     * @param order the order
     */
    public void sendEmail(Order order) {
        eH.sendEmail(new OrderDB().getEmailClientbyOrderId(order.getIdOrder()), "Delivery", "Your order with ID : " + order.getIdOrder() + " has started to be delivered. Hope you have a great day!");
    }
}
