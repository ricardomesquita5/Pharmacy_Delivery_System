package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import lapr.project.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Delivery controller test.
 */
class DeliveryControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Delivery Controller.
     */
    private final DeliveryController controller;
    /**
     * The Courier.
     */
    private final Courier courier;
    /**
     * The Scooter.
     */
    private final Scooter scooter;
    /**
     * The Client.
     */
    private final Client client;
    /**
     * The Spot.
     */
    private final Spot spot;
    /**
     * The Park.
     */
    private final Park park;
    /**
     * The Order.
     */
    private final Order order;
    /**
     * The File Reader.
     */
    private final FileReader fr;
    /**
     * The Drone.
     */
    private final Drone drone;
    /**
     * The Delivery By Scooter.
     */
    private final DeliveryByScooter dbs;
    /**
     * The Delivery By Drone.
     */
    private final DeliveryByDrone dbd;
    /**
     * The Graph.
     */
    private Graph<String,Integer> graph = new Graph<>(false);

    /**
     * Instantiates a new Delivery controller test.
     *
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public DeliveryControllerTest() throws ParseException, IOException {
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
        new DataHandler();
        this.controller = new DeliveryController();
        this.address = new Address("01010101,044434342", "Rua ISEP", "4460-123", 123, "São João", 9);
        this.pharmacy = new Pharmacy("testDeliveryController@gmail.com", this.address, "Pharmacy Teste");
        this.courier = new Courier("100000000", "quimze", "testeDelivery102Courier@gmail.com", "913212111", "11121212199", "22223");
        this.scooter = new Scooter("100", "100", "100","30");
        this.order = new Order(1, 50.0);
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("20/01/2222");
        this.client = new Client("testeDeliveryController", "DeliveryControllerUserTest@gmail.com", "123", this.address, 1234567890123456L, d, 123);
        this.park = new Park(1, "Scooter", 5, 300);
        this.spot = new Spot(1, "no", 5);
        this.drone = new Drone(1,10.0, 23.0, 360.0,100,30.0);
        this.dbs = new DeliveryByScooter();
        this.dbd=new DeliveryByDrone();
        this.fr = new FileReader();
        fr.insertVertices("Coordinates.txt");
        fr.insertEdges("Connections.txt");
    }

    /**
     * Create delivery for scooter.
     *
     * @throws IOException the io exception
     */
    @Test
    void createDeliveryForScooter() throws IOException {
        List<Order> ordersList = new LinkedList<>();
        Map<Scooter, List<String>> map = new HashMap<>(controller.createDeliveryForScooter(ordersList, this.pharmacy));
        Map<Scooter, List<String>> map1 = new HashMap<>();
        Assertions.assertEquals(map1, map);
    }

    /**
     * Create delivery for drone.
     *
     * @throws IOException the io exception
     */
    @Test
    void createDeliveryForDrone() throws IOException {
        List<Order> ordersList = new LinkedList<>();
        Map<Drone, List<String>> map = new HashMap<>(controller.createDeliveryForDrone(ordersList, this.pharmacy));
        Map<Drone, List<String>> map1 = new HashMap<>();
        Assertions.assertEquals(map1, map);
    }

    /**
     * Gets pharmacies list.
     */
    @Test
    void getPharmaciesList()  {
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        int size = new PharmacyDB().getAllPharmacies().size();
        new PharmacyDB().addPharmacy(this.pharmacy);
        Assertions.assertEquals(size + 1, this.controller.getPharmaciesList().size());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }


    /**
     * Gets drone.
     */
    @Test
    void getDrone() {
        this.controller.getDrone(this.drone);
        Assertions.assertEquals(this.drone, this.controller.getDronee());
    }

    /**
     * Add delivery by scooter.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void addDeliveryByScooter() throws SQLException {
        System.out.println("DeliveryController getDrone() Test");

        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.courier, this.pharmacy.getEmail());
        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
        this.controller.getCourierByEmail(this.courier.getEmail());
        Assertions.assertTrue(this.controller.addDeliveryByScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0)));
        new DeliveryByScooterDB().removeDeliveryByScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId(), this.courier.getEmail());
        new DeliveryDB().removeDelivery(new DeliveryDB().getLastDeliveryId());
        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Add delivery by drone.
     */
    @Test
    void addDeliveryByDrone()  {
        System.out.println("DeliveryController addDeliveryByDrone() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone, this.pharmacy.getEmail());
        Assertions.assertTrue(this.controller.addDeliveryByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0)));
        new DeliveryByDroneDB().removeDeliveryByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new DeliveryDB().removeDelivery(new DeliveryDB().getLastDeliveryId());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Update courier.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void updateCourier() throws SQLException {
        System.out.println("DeliveryController updateCourier() Test");
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new CourierDB().addCourier(this.courier, this.pharmacy.getEmail());
        this.controller.getCourierByEmail("testeDelivery102Courier@gmail.com");
        this.controller.updateCourier();
        Assertions.assertEquals(this.courier.toString(), new CourierDB().getCourier(this.courier.getEmail()).toString());
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets path.
     */
    @Test
    void getPath()  {
        System.out.println("DeliveryController getPath() Test");
        LinkedList<String> path = new LinkedList<>();
        List<Order> listOrder = new LinkedList<>();
        for (Order order :
                new OrderDB().
                        getAllOrders(this.pharmacy.getEmail())) {
            new OrderDB().removeOrder(order.getIdOrder());
        }
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ClientDB().addClient(this.client);
        new OrderDB().addOrder(this.order, this.pharmacy.getEmail(), this.client.getEmail());
        listOrder.add(new OrderDB().getAllOrders(this.pharmacy.getEmail()).get(0));
        path.add(this.client.getAddress().getGPSCoordinates());
        Assertions.assertEquals(path,this.controller.getPath(listOrder,path));
            for (Order order : new OrderDB().getAllOrders(this.pharmacy.getEmail())) {
                new OrderDB().removeOrder(order.getIdOrder());
            }
            new ClientDB().removeClient(this.client.getEmail());
            new UserDB().removeUser(this.client.getEmail());
            new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
            new AddressDB().removeAddress(this.address.getGPSCoordinates());
        }


    /**
     * Gets all orders.
     */
    @Test
    void getAllOrders()  {
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ClientDB().addClient(this.client);
        int size = new OrderDB().getAllOrders(this.pharmacy.getEmail()).size();
        new OrderDB().addOrder(this.order, this.pharmacy.getEmail(), this.client.getEmail());
        Assertions.assertEquals(size + 1, this.controller.getAllOrders(this.pharmacy.getEmail()).size());
        new OrderDB().removeOrder(this.controller.getAllOrders(this.pharmacy.getEmail()).get(0).getIdOrder());
        new ClientDB().removeClient(this.client.getEmail());
        new UserDB().removeUser(this.client.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}
