package lapr.project.ui;

import lapr.project.controller.DeliveryController;
import lapr.project.data.FileReader;
import lapr.project.model.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The type Create delivery ui.
 */
public class CreateDeliveryUI {
    /**
     * the controller
     */
    private final DeliveryController controller;
    /**
     * the fileReader
     */
    private final FileReader fr;

    /**
     * Instantiates a new Create delivery ui.
     *
     * @throws IOException the io exception
     */
    public CreateDeliveryUI() throws IOException {
        this.controller = new DeliveryController();
        this.fr = new FileReader();
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {

        List<Pharmacy> pharmacyList = this.controller.getPharmaciesList();

        Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharmacyList, "Select a pharmacy:");

        List<Order> orderList = this.controller.getAllOrders(phar.getEmail());
        List<Order> orderList1 = new LinkedList<>();
        int i = 0;
        while (true) {
            if (orderList.isEmpty()) {
                System.out.println("There are no more orders to be delivered");
                break;
            }
            if (i > 0) {
                boolean flag = Utils.confirm("Do you want to add another order?(y/n)");
                if (!flag) {
                    break;
                }
            }
            Order order = (Order) Utils.showsAndSelect(orderList, "Select the order to add to the delivery");
            orderList1.add(order);
            orderList.remove(order);
            i++;
        }
        Map<Scooter, List<String>> mapScooter = this.controller.createDeliveryForScooter(orderList1, phar);

        Map<Drone, List<String>> mapDrone = this.controller.createDeliveryForDrone(orderList1, phar);

        if (mapScooter.isEmpty() && mapDrone.isEmpty()) {
            System.out.println("Our drones and scooters can´t perform a delivery with this orders right now!");
        } else if (mapScooter.isEmpty()) {
            System.out.println("It is only possible to make this delivery by drone");
            if (Utils.confirm("Do you want to make the delivery by Drone?(y/n)")) {
                createDeliveryByDrone(mapDrone, orderList1, phar);
            }
        } else if (mapDrone.isEmpty()) {
            System.out.println("It is only possible to make this delivery by scooter");
            if (Utils.confirm("Do you want to make the delivery by Scooter?(y/n)")) {
                createDeliveryByScooter(mapScooter, phar, orderList1);
            }
        } else {
            fr.saveCoordinatesElevation("Coordinates.txt");
            double scooterWaste = this.controller.getBatteryWasteForScooter(orderList1, phar, mapScooter.keySet().iterator().next());
            double droneWaste = this.controller.getBatteryWasteForDrone(orderList1, phar, mapDrone.keySet().iterator().next());
            System.out.println("Path for Drone: \n");
            for (int k = 0; k < mapDrone.get(mapDrone.keySet().iterator().next()).size(); k++) {
                System.out.println(k + " - " + fr.getStreetByCoordinates(mapDrone.get(mapDrone.keySet().iterator().next()).get(k)));
            }
            System.out.println(" ");
            System.out.println("Path for Scooter: \n");
            for (int l = 0; l < mapScooter.get(mapScooter.keySet().iterator().next()).size(); l++) {
                System.out.println(l + " - " + fr.getStreetByCoordinates(mapScooter.get(mapScooter.keySet().iterator().next()).get(l)));
            }

            if (scooterWaste > droneWaste) {
                System.out.println("The Drone is better for this delivery\n");
                System.out.println("The energy spent by the drone will be :" + (int) droneWaste + " Wh\n");
                System.out.println("The energy spent by the scooter will be :" + (int) scooterWaste + " Wh\n");
                if (Utils.confirm("Do you want to make the delivery by Drone?(y/n)")) {
                    createDeliveryByDrone(mapDrone, orderList1, phar);
                } else {
                    if (Utils.confirm("Do you want to make the delivery by Scooter (knowing that it is less efficient)?(y/n)")) {
                        createDeliveryByScooter(mapScooter, phar, orderList1);
                    }
                }
            } else if (scooterWaste < droneWaste) {
                System.out.println("The Scooter is better for this delivery\n");
                System.out.println("The energy spent by the drone will be :" + (int) droneWaste + " Wh\n");
                System.out.println("The energy spent by the scooter will be :" + (int) scooterWaste + " Wh\n");
                if (Utils.confirm("Do you want to make the delivery by Scooter?(y/n)")) {
                    createDeliveryByScooter(mapScooter, phar, orderList1);
                } else {
                    if (Utils.confirm("Do you want to make the delivery by Drone (knowing that it is less efficient)?(y/n)")) {
                        createDeliveryByDrone(mapDrone, orderList1, phar);
                    }
                }
            } else {
                System.out.println("The scooter and the drone are equally efficient for this delivery\n");
                System.out.println("The energy spent by both of them is :" + (int) droneWaste + "Wh\n");
                List<String> options = new LinkedList<>();
                options.add("Do the delivery by scooter!");
                options.add("Do the delivery by drone!");
                int opcao;
                do {
                    opcao = Utils.showsAndSelectIndex(options, "Choose");
                    switch (opcao) {
                        case 0:
                            createDeliveryByScooter(mapScooter, phar, orderList1);
                            break;
                        case 1:
                            createDeliveryByDrone(mapDrone, orderList1, phar);
                            break;
                    }
                }
                while (opcao != -1);
            }
        }
    }

    /**
     * Create delivery by drone.
     *
     * @param map        the map
     * @param orderList1 the order list 1
     * @param phar       the phar
     * @throws IOException the io exception
     */
    public void createDeliveryByDrone(Map<Drone, List<String>> map, List<Order> orderList1, Pharmacy phar) throws IOException {
        this.controller.addDeliveryByDrone(map.keySet().iterator().next());
        this.controller.getDeliveryByDrone("assigned", map.keySet().iterator().next());
        this.controller.updateOrderDrone(orderList1);

        System.out.println("Orders to be delivered by Drone:\n");
        for (Order order : orderList1) {
            System.out.println(order.toString());
        }
        System.out.println("\n");
        System.out.println("Path : \n");
        for (int i = 0; i < map.get(map.keySet().iterator().next()).size(); i++) {
            System.out.println(i + " - " + fr.getStreetByCoordinates(map.get(map.keySet().iterator().next()).get(i)));
        }
        System.out.println("\n");
        System.out.println("Drone that is going to be used:\n");
        System.out.println(map.keySet().iterator().next().toString());
        this.controller.getSpotByDroneId(map.keySet().iterator().next().getId());
        this.controller.updateSpotDrone(false, map.keySet().iterator().next());

        double time = this.controller.getTimeOfDeliveryForDrone(orderList1, phar) * 3600;
        System.out.println("The delivery will take aproximately " + (int) time + " seconds");
        new DeliveryDroneSimulation(time, phar, map.keySet().iterator().next());
        System.out.println("The drone delivery has started.");
    }

    /**
     * Create delivery by scooter.
     *
     * @param map        the map
     * @param phar       the phar
     * @param orderList1 the order list 1
     * @throws IOException the io exception
     */
    public void createDeliveryByScooter(Map<Scooter, List<String>> map, Pharmacy phar, List<Order> orderList1) throws IOException {
        List<Courier> listCouriers = this.controller.getAvailableCouriers(phar.getEmail());
        Courier courier = (Courier) Utils.showsAndSelect(listCouriers, "Choose the courier you want to assign this delivery to :\n");
        this.controller.getCourierByEmail(courier.getEmail());
        this.controller.addDeliveryByScooter(map.keySet().iterator().next());
        this.controller.getDeliveryByScooter("assigned");
        this.controller.updateOrder(orderList1);
        this.controller.changeCourier("Unavailable");
        this.controller.updateCourier();

        System.out.println("Orders to be delivered by Scooter:\n");
        for (Order order : orderList1) {
            System.out.println(order.toString());
        }
        System.out.println();
        System.out.println("Path : \n");
        for (int i = 0; i < map.get(map.keySet().iterator().next()).size(); i++) {
            System.out.println(i + " - " + fr.getStreetByCoordinates(map.get(map.keySet().iterator().next()).get(i)));
        }
        System.out.println();
        System.out.println("Scooter that the Courier will use\n");
        System.out.println(map.keySet().iterator().next().toString());
        this.controller.getSpotByScooterId(map.keySet().iterator().next().getId());
        this.controller.updateSpot(false, map.keySet().iterator().next());
    }
}
