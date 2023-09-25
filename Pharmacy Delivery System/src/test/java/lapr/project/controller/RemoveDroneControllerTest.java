package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Remove drone controller test.
 */
class RemoveDroneControllerTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Remove Drone Controler.
     */
    private final RemoveDroneController controller;
    /**
     * The Drone.
     */
    private final Drone drone;

    /**
     * Instantiates a new Remove Drone Controller Test.
     */
    public RemoveDroneControllerTest() {
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

        this.controller = new RemoveDroneController();

        this.address = new Address("09010900.01,00000111.02", "Rua ISEP", "4460-123", 123, "São João", 11);
        this.pharmacy = new Pharmacy("testeRemoveDroneController@gmail.com", this.address, "Pharmacy Teste");
        this.drone = new Drone("100", "100", "100","30");
    }

    /**
     * Gets Pharmacies List Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmaciesList() throws SQLException {
        System.out.println("RemoveDroneController getPharmaciesList() Test");
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
     * Gets Pharmacy by Email Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getPharmacyByEmail() throws SQLException {
        System.out.println("RemoveDroneController getPharmacyByEmail() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(this.pharmacy.toString(), this.controller.getPharmacy().toString());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets Drone List Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getDroneList() throws SQLException {
        System.out.println("RemoveDroneController getDroneList() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new DroneDB().getAllDrones(this.pharmacy.getEmail()).size();
        new DroneDB().addDrone(this.drone, this.pharmacy.getEmail());
        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, this.controller.getDronesList().size());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets Drone By ID Test.
     */
    @Test
    void getDroneByID() {
        System.out.println("RemoveDroneController getDroneByID() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone, this.pharmacy.getEmail());
        Drone d = new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0);
        this.controller.getDroneByID(d.getId());
        this.drone.setID(d.getId());
        Assertions.assertEquals(this.drone.toString(), this.controller.getDroneString());
        new DroneDB().removeDrone(d.getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Remove Drone Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void removeDrone() throws SQLException {
        System.out.println("RemoveDroneController removeDrone() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new DroneDB().addDrone(this.drone, this.pharmacy.getEmail());
        int size = new DroneDB().getAllDrones(this.pharmacy.getEmail()).size();
        Park p = new Park("Drone", "1", "150");
        Spot s = new Spot(1, "no");
        new ParkDB().addPark(p, this.pharmacy.getEmail());
        int idPark = new ParkDB().getLastPark(this.pharmacy.getEmail());
        new SpotDB().addSpot(s, idPark);
        int idDrone = new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId();
        new SpotDB().updateSpot3(s.getId(), idPark, idDrone);

        this.controller.getPharmacyByEmail(this.pharmacy.getEmail());
        this.controller.getDroneByID(idDrone);
        Assertions.assertTrue(this.controller.removeDrone());

        new DroneDB().addDrone(this.drone, this.pharmacy.getEmail());
        idDrone = new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId();

        try {
            this.controller.removeDrone();
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(size, new DroneDB().getAllDrones(this.pharmacy.getEmail()).size());
        }

        new SpotDB().removeSpot(s.getId(), idPark);
        new ParkDB().removePark(idPark);
        new DroneDB().removeDrone(idDrone);
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}