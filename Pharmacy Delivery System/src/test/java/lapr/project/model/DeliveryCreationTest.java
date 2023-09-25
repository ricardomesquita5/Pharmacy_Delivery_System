package lapr.project.model;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Delivery creation test.
 */
class DeliveryCreationTest {

    /**
     * The FileReader.
     */
    private static final FileReader fr=new FileReader();

    /**
     * The Graph graph.
     */
    private final Graph<String, Integer> graph;

    /**
     * The Graph graph1.
     */
    private final Graph<String,Integer> graph1;

    /**
     * The Courier.
     */
    Courier courier;
    /**
     * The Scooter.
     */
    Scooter scooter;
    /**
     * The Address.
     */
    Address address;
    /**
     * The Pharmacy.
     */
    Pharmacy pharmacy;
    /**
     * The Order.
     */
    Order order;
    /**
     * The Client.
     */
    Client client;
    /**
     * The Park.
     */
    Park park;
    /**
     * The Spot.
     */
    Spot spot;
    /**
     * The Drone
     */
    Drone drone;
    /**
     * The Park1
     */
    Park park1;


    /**
     * Instantiates a new Delivery creation test.
     *
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public DeliveryCreationTest() throws ParseException, IOException {
        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initial Database Setup
        new DataHandler();

        this.drone = new Drone("111", "111", "111", "111");
        this.graph=fr.getGraph();
        this.graph1=GraphAlgorithms.minDistGraph();
        this.address = new Address("0101010123,022223142", "Rua ISEP", "4460-123", 123, "São João", 26);
        this.pharmacy = new Pharmacy("testDeliveryCreation@gmail.com", this.address, "Pharmacy Teste");
        this.scooter = new Scooter("111", "111", "111", "111");
        this.courier = new Courier("112121232", "quim", "testeDeliveryCreationCourier@gmail.com", "912342561", "11121212121", "22222");
        this.order = new Order(1, 50.0);
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");
        this.client = new Client("testeDeliveryCreation", "DeliveryCreationUserTest@gmail.com", "123", this.address, 1234567890123456L, d, 123);
        this.park = new Park(1, "Scooter", 5, 300);
        this.park1=new Park(1,"Drone",5,300);
        this.spot = new Spot(1, "no", 5);


    }

    /**
     * Is possible.
     *
     * @throws IOException the io exception
     */
    @Test
    void isPossible() throws  IOException {
        LinkedList<String> pathList = new LinkedList<>();
        List<Order> ordersToDeliver = new LinkedList<>();
        List<Scooter> listScooters = new LinkedList<>();
        List<Scooter> listScooters2 = new LinkedList<>();
        pathList.add("Street of ducks");
        pathList.add("Street of dogs");
        Order order = new Order(222, 25.3);
        Order order1 = new Order(223, 28.1);
        ordersToDeliver.add(order);
        ordersToDeliver.add(order1);
        Scooter scooter1 = new Scooter(222, 2.0, 1.0, 11.0, 0, 10.0);
        Scooter scooter2 = new Scooter(111, 10.0, 130.0, 350.0, 100, 10.0);
        listScooters.add(scooter1);
        listScooters2.add(scooter2);
        Assertions.assertFalse(DeliveryCreation.isPossible(new LinkedList<>(),pathList, ordersToDeliver,this.graph, listScooters));
        Assertions.assertTrue(DeliveryCreation.isPossible(new LinkedList<>(),pathList, ordersToDeliver,this.graph, listScooters2));
    }

    /**
     * Split coordinates.
     */
    @Test
    void splitCoordinates() {
        String latitude = "36.204823";
        String longitude = "138.252930";
        String[] result = GraphAlgorithms.splitCoordinates("36.204823,138.252930");
        String result1 = result[0];
        String result2 = result[1];
        Assertions.assertEquals(latitude, result1);
        Assertions.assertEquals(longitude, result2);
    }

    /**
     * Is possible for drones.
     *
     * @throws IOException the io exception
     */
    @Test
    void isPossibleForDrones() throws IOException {
        List<String> pathList = new LinkedList<>();
        List<Order> ordersToDeliver = new LinkedList<>();
        List<Drone> listDrones = new LinkedList<>();
        List<Drone> listDrones2 = new LinkedList<>();
        pathList.add("41.15926217793073, -8.630302754195354");
        pathList.add("41.15938334121363, -8.628714886575583");
        Order order = new Order(222, 25.3);
        Order order1 = new Order(223, 28.1);
        ordersToDeliver.add(order);
        ordersToDeliver.add(order1);
        Drone drone1 = new Drone(222, 2.0, 1.0, 11.0, 0, 10.0);
        Drone drone2 = new Drone(111, 10.0, 130.0, 350.0, 100, 10.0);
        listDrones.add(drone1);
        listDrones2.add(drone2);
        Assertions.assertFalse(DeliveryCreation.isPossibleForDrones(new LinkedList<>(),pathList, ordersToDeliver,this.graph1, listDrones));
        Assertions.assertTrue(DeliveryCreation.isPossibleForDrones(new LinkedList<>(),pathList, ordersToDeliver, this.graph1, listDrones2));
    }

    /*@Test
    void deliveryData() throws SQLException, IOException {
        LinkedHashMap<Scooter, List<String>> newMap = new LinkedHashMap<>();
        List <String> listPath = new LinkedList<>();
        List<Order> orderList=new LinkedList<>();
        try {
            for (Park park :
                    new ParkDB().getAllParks(this.pharmacy.getEmail())) {
                for (Spot spot :
                        new SpotDB().getAllSpots(park.getId())) {
                    new SpotDB().removeSpot(spot.getId(), park.getId());
                }
                new ParkDB().removePark(park.getId());
            }
        } catch (IllegalArgumentException iae) {
            Assertions.assertTrue(true);
        }
        for (Order order :
                new OrderDB().
                        getAllOrders(this.pharmacy.getEmail())) {
            new OrderDB().removeOrder(order.getIdOrder());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        for (Scooter scooter : new ScooterDB().getAllScooters(this.pharmacy.getEmail())) {
            new ScooterDB().removeScooter(scooter.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.courier, this.pharmacy.getEmail());
        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        new ClientDB().addClient(this.client);
        new OrderDB().addOrder(this.order, this.pharmacy.getEmail(), this.client.getEmail());
        new ParkDB().addPark(this.park, this.pharmacy.getEmail());
        new SpotDB().addSpot(this.spot, new ParkDB().getLastPark(this.pharmacy.getEmail()));
        new SpotDB().updateSpot(new SpotDB().getAllSpots(new ParkDB().getLastPark(this.pharmacy.getEmail())).get(0).getId(), new ParkDB().getLastPark(this.pharmacy.getEmail()), new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
        newMap.put(this.scooter, listPath);
        Assertions.assertEquals(DeliveryCreation.deliveryData(orderList,this.graph,this.pharmacy).toString(), newMap.toString());
        for (Park park : new ParkDB().getAllParks(this.pharmacy.getEmail())) {
            for (Spot spot :
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        for (Order order : new OrderDB().getAllOrders(this.pharmacy.getEmail())) {
            new OrderDB().removeOrder(order.getIdOrder());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        for (Scooter scooter :
                new ScooterDB().getAllScooters(this.pharmacy.getEmail())) {
            new ScooterDB().removeScooter(scooter.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }*/


    /*@Test
    void deliveryDroneData() throws SQLException {
        LinkedHashMap<Drone, List<String>> newMap = new LinkedHashMap<>();
        List <String> listPath = new LinkedList<>();
        List<Order> orderList=new LinkedList<>();
        try {
            for (Park park :
                    new ParkDB().getAllParks(this.pharmacy.getEmail())) {
                for (Spot spot :
                        new SpotDB().getAllSpots(park.getId())) {
                    new SpotDB().removeSpot(spot.getId(), park.getId());
                }
                new ParkDB().removePark(park.getId());
            }
        } catch (IllegalArgumentException iae) {
            Assertions.assertTrue(true);
        }
        for (Order order :
                new OrderDB().
                        getAllOrders(this.pharmacy.getEmail())) {
            new OrderDB().removeOrder(order.getIdOrder());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        for (Drone drone : new DroneDB().getAllDrones(this.pharmacy.getEmail())) {
            new DroneDB().removeDrone(drone.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone,this.pharmacy.getEmail());
        new ClientDB().addClient(this.client);
        new OrderDB().addOrder(this.order, this.pharmacy.getEmail(), this.client.getEmail());
        new ParkDB().addPark(this.park1, this.pharmacy.getEmail());
        new SpotDB().addSpot(this.spot, new ParkDB().getLastPark(this.pharmacy.getEmail()));
        new SpotDB().updateSpot3(new SpotDB().getAllSpots(new ParkDB().getLastPark(this.pharmacy.getEmail())).get(0).getId(), new ParkDB().getLastPark(this.pharmacy.getEmail()), new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        newMap.put(this.drone, listPath);
        Assertions.assertEquals(DeliveryCreation.deliveryDroneData(orderList,this.graph1,this.pharmacy).toString(), newMap.toString());
        for (Park park : new ParkDB().getAllParks(this.pharmacy.getEmail())) {
            for (Spot spot :
                    new SpotDB().getAllSpots(park.getId())) {
                new SpotDB().removeSpot(spot.getId(), park.getId());
            }
            new ParkDB().removePark(park.getId());
        }
        for (Order order : new OrderDB().getAllOrders(this.pharmacy.getEmail())) {
            new OrderDB().removeOrder(order.getIdOrder());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        for (Drone drone :
                new DroneDB().getAllDrones(this.pharmacy.getEmail())) {
            new DroneDB().removeDrone(drone.getId());
        }
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }*/

}