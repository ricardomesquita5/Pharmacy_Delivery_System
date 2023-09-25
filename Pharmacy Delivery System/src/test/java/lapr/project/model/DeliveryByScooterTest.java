package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Delivery by scooter test.
 */
class DeliveryByScooterTest {

    /**
     * The DeliveryByScooter.
     */
    private DeliveryByScooter dbs;

    /**
     * The Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * The Address.
     */
    private Address address;

    /**
     * The Scooter.
     */
    private Scooter scooter;

    /**
     * The Courier.
     */
    private Courier courier;

    /**
     * Instantiates a new Delivery by scooter test.
     */
    public DeliveryByScooterTest() {
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

        this.dbs = new DeliveryByScooter(3, "assigned", 1);
        this.address = new Address("38.99,44.66", "Rua ISEP", "4460-123", 123, "São João", 25);
        this.pharmacy = new Pharmacy("testDelivery3@gmail.com", this.address, "TesteD");
        this.scooter = new Scooter("111", "111", "111", "111");
        this.courier = new Courier("112121232", "quim", "testeDelivery3Courier@gmail.com", "912342561", "11121212121", "22222");
    }

    /**
     * Gets by scooter id.
     */
    @Test
    void getdeliveryByScooterId() {
        Assertions.assertEquals(3, this.dbs.getdeliveryByScooterId());
    }

    /**
     * Gets status.
     */
    @Test
    void getStatus() {
        Assertions.assertEquals("assigned", this.dbs.getStatus());
    }

    /**
     * Sets status.
     */
    @Test
    void setStatus() {
        this.dbs.setStatus("unavailable");
        Assertions.assertEquals("unavailable", this.dbs.getStatus());
    }

    /**
     * Update.
     *
     */
//    @Test
//    void update()  {
//        new CourierDB().removeCourier(this.courier.getEmail());
//        new UserDB().removeUser(this.courier.getEmail());
//        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
//        new AddressDB().removeAddress(this.address.getGPSCoordinates());
//        new AddressDB().addAddress(this.address);
//        new PharmacyDB().addPharmacy(this.pharmacy);
//        new ScooterDB().addScooter(this.scooter, this.pharmacy.getEmail());
//        new CourierDB().addCourier(this.courier,this.pharmacy.getEmail());
//        this.dbs.save(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId(), this.courier.getEmail());
//        this.dbs = new DeliveryByScooterDB().getDeliveryByScooterByEmailCourier("assigned", this.courier.getEmail());
//        dbs.setStatus("finished");
//        dbs.update();
//        Assertions.assertEquals(dbs.getStatus(), new DeliveryByScooterDB().getDeliveryByScooterByEmailCourier("finished", this.courier.getEmail()).getStatus());
//        new DeliveryByScooterDB().removeDeliveryByScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId(), this.courier.getEmail());
//        new DeliveryDB().removeDelivery(new DeliveryDB().getLastDeliveryId());
//        new CourierDB().removeCourier(this.courier.getEmail());
//        new UserDB().removeUser(this.courier.getEmail());
//        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
//        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
//        new AddressDB().removeAddress(this.address.getGPSCoordinates());
//    }
}