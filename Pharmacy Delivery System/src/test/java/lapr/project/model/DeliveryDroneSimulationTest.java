package lapr.project.model;

import lapr.project.controller.DeliveryController;
import lapr.project.ui.DeliveryDroneSimulation;

import java.io.IOException;

/**
 * The type Delivery drone simulation test.
 */
class DeliveryDroneSimulationTest {

    /**
     * The DeliveryController.
     */
    private DeliveryController dc;

    /**
     * The DeliveryDroneSimulation.
     */
    private DeliveryDroneSimulation dbds;

    /**
     * The Address.
     */
    private Address address;

    /**
     * The Pharmacy.
     */
    private Pharmacy pharmacy;

    /**
     * The Drone.
     */
    private Drone drone;


    /**
     * Instantiates a new Delivery drone simulation test.
     *
     * @throws IOException the io exception
     */
    public DeliveryDroneSimulationTest() throws IOException {
        this.address = new Address("0101,0444", "Rua ISEP", "4460-123", 123, "São João", 27);
        this.pharmacy = new Pharmacy("testDel@gmail.com", this.address, "TesteDS");
        this.drone = new Drone("200", "100", "100","100");
        this.dc = new DeliveryController();
        this.dbds = new DeliveryDroneSimulation(1,this.pharmacy,this.drone);
    }

}