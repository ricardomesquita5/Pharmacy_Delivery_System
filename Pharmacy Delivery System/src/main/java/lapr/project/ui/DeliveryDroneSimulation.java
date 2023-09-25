package lapr.project.ui;

import lapr.project.controller.DeliveryController;
import lapr.project.data.SpotDB;
import lapr.project.model.Drone;
import lapr.project.model.Pharmacy;
import lapr.project.model.Spot;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Delivery drone simulation.
 */
public class DeliveryDroneSimulation {
    /**
     * The Timer.
     */
    Timer timer;
    /**
     * The Controller.
     */
    DeliveryController controller = new DeliveryController();

    /**
     * Instantiates a new Delivery drone simulation.
     *
     * @param seconds the seconds
     * @param phar    the phar
     * @param drone   the drone
     * @throws IOException the io exception
     */
    public DeliveryDroneSimulation(double seconds, Pharmacy phar, Drone drone) throws IOException {
        timer = new Timer();
        long time = (long) (seconds);
        timer.schedule(new DroneTask(phar, drone), time * 1000);
    }

    /**
     * The type Drone task.
     */
    class DroneTask extends TimerTask {
        /**
         * The Pharmacy.
         */
        Pharmacy pharmacy;
        /**
         * The Drone.
         */
        Drone drone;

        /**
         * Instantiates a new Drone task.
         *
         * @param pharmacy the pharmacy
         * @param drone    the drone
         */
        public DroneTask(Pharmacy pharmacy, Drone drone) {
            this.pharmacy = pharmacy;
            this.drone = drone;
        }

        /**
         * Run.
         */
        public void run() {
            try {
                List<Spot> availableSpotsList = new SpotDB().getAvailableSpots(pharmacy.getEmail(), "Drone");
                controller.getSpot(availableSpotsList.get(0).getId(), availableSpotsList.get(0).getIdPark());
                controller.getDeliveryByDrone("assigned", drone);
                int battery = controller.getBatteryDrone(pharmacy, drone);
                controller.changeDeliveryByDrone("Finished");
                controller.updateDeliveryByDrone();
                controller.getDrone(drone);
                controller.changeDrone(battery);
                controller.updateDrone();
                controller.updateSpotDrone(true, drone);
                timer.cancel();
            } catch (IOException ax) {
                throw new IllegalStateException(ax.getMessage());
            }
        }
    }
}
