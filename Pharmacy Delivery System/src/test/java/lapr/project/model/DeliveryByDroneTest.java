package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Delivery by drone test.
 */
class DeliveryByDroneTest {

    /**
     * The DeliveryByDrone.
     */
    private DeliveryByDrone dbd;

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * The Scooter.
     */
    private final Scooter scooter;

    /**
     * The Courier.
     */
    private final Courier courier;

    /**
     * The Drone.
     */
    private final Drone drone;

    /**
     * Instantiates a new Delivery by drone test.
     */
    public DeliveryByDroneTest() {
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
        this.dbd = new DeliveryByDrone(1, "assigned",1);
        this.address = new Address("0101010123,044434342", "Rua ISEP", "4460-123", 123, "São João", 24);
        this.pharmacy = new Pharmacy("testDelivery2@gmail.com", this.address, "Pharmacy Teste");
        this.scooter=new Scooter("111", "111", "111", "111");
        this.courier=new Courier("112121232", "quim","testeDelivery3Courier@gmail.com", "912342561","11121212121", "22222");
        this.drone = new Drone("100", "100", "100", "100");
    }

    /**
     * Gets by drone id.
     */
    @Test
    void getdeliveryByDroneId() {
        Assertions.assertEquals(1, this.dbd.getdeliveryByDroneId());
    }

    /**
     * Gets status.
     */
    @Test
    void getStatus() {
        Assertions.assertEquals("assigned", this.dbd.getStatus());
    }

    /**
     * Sets status.
     */
    @Test
    void setStatus() {
        this.dbd.setStatus("unavailable");
        Assertions.assertEquals("unavailable", this.dbd.getStatus());
    }

    /**
     * Save.
     */
    @Test
    void save()  {
        System.out.println("DeliveryByDrone save() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone,this.pharmacy.getEmail());
        int size = new DeliveryByDroneDB().getAllDeliveriesByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId()).size();
        this.dbd.save(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        Assertions.assertEquals(size + 1, new DeliveryByDroneDB().getAllDeliveriesByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId()).size());
        new DeliveryByDroneDB().removeDeliveryByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new DeliveryDB().removeDelivery(new DeliveryDB().getLastDeliveryId());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }


    /**
     * Update.
     */
    @Test
    void update() {
        System.out.println("DeliveryByDrone update() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone,this.pharmacy.getEmail());
        this.dbd.save(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        this.dbd= new DeliveryByDroneDB().getDeliveryByDroneByDroneId("assigned",new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        dbd.setStatus("finished");
        dbd.update();
        Assertions.assertEquals(dbd.getStatus(), new DeliveryByDroneDB().getDeliveryByDroneByDroneId("finished",new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId()).getStatus());
        new DeliveryByDroneDB().removeDeliveryByDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new DeliveryDB().removeDelivery(new DeliveryDB().getLastDeliveryId());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}