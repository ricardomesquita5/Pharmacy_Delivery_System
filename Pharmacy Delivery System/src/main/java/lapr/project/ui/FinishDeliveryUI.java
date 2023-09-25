package lapr.project.ui;

import lapr.project.controller.DeliveryController;
import lapr.project.model.Spot;
import lapr.project.utils.controller.ApplicationPOT;

import java.io.IOException;
import java.util.List;

/**
 * The type Finish delivery ui.
 */
public class FinishDeliveryUI {
    /**
     * the Controller.
     */
    private final DeliveryController controller;

    /**
     * Instantiates a new Finish delivery ui.
     *
     * @throws IOException the io exception
     */
    public FinishDeliveryUI() throws IOException {
        this.controller = new DeliveryController();
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {
        controller.getCourierByEmail(ApplicationPOT.getInstance().getCurrentSession().getUserEmail());
        if (controller.getDeliveryByScooter("assigned")) {
            System.out.println("You can´t finish a delivery that you haven´t started yet");
        } else if (controller.getDeliveryByScooter("started")) {
            if (Utils.confirm("Have you finished the delivery? (Y/N)")) {
                System.out.println("Where do you want to park the scooter");
                String email = controller.getEmailPharmacy();
                List<Spot> availableSpotsList = controller.getAvailableSpots(email);
                Spot spot = (Spot) Utils.showsAndSelect(availableSpotsList, "Select the spot you want to put the scooter in");
                controller.getScooter();
                controller.getSpot(spot.getId(), spot.getIdPark());
                int battery = controller.getBatteryScooter();
                controller.changeScooter(battery);
                controller.updateScooter();
                controller.changeCourier("Available");
                controller.updateCourier();
                controller.updateSpot(true, controller.getScooter());
                controller.changeDeliveryByScooter("Finished");
                controller.updateDeliveryByScooter();
                System.out.println("You´ve finished the delivery successfully");
            }
        } else {
            System.out.println("You don´t have any delivery unfinished");
        }
    }
}
