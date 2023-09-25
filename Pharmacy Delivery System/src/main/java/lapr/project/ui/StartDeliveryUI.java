package lapr.project.ui;

import lapr.project.controller.DeliveryController;
import lapr.project.model.Order;
import lapr.project.model.Scooter;
import lapr.project.utils.controller.ApplicationPOT;

import java.io.IOException;
import java.util.List;

/**
 * The type Start delivery ui.
 */
public class StartDeliveryUI {

    /**
     * The Controller.
     */
    private final DeliveryController controller;

    /**
     * Instantiates a new Start delivery ui.
     *
     * @throws IOException the io exception
     */
    public StartDeliveryUI() throws IOException {
        this.controller = new DeliveryController();
    }

    /**
     * Run.
     */
    public void run() {

        this.controller.getCourierByEmail(ApplicationPOT.getInstance().getCurrentSession().getUserEmail());
        if (controller.getDeliveryByScooter("started")) {
            System.out.println("You are already doing a delivery ,please finish it first!");
        } else if (controller.getDeliveryByScooter("assigned")) {
            if (Utils.confirm("Do you want to start the delivery? (Y/N)")) {

                this.controller.changeDeliveryByScooter("started");
                this.controller.updateDeliveryByScooter();

                System.out.println("Orders to deliver:");

                List<Order> orderList = this.controller.getOrdersFromDelivery();
                for (Order order : orderList) {
                    System.out.println(order.toString());
                    controller.sendEmail(order);
                }

                System.out.println("Scooter to use");
                Scooter scooter = this.controller.getScooter();
                System.out.println(scooter.toString());
            }
        } else {
            System.out.println("There isn´t any delivery assigned to you.Please wait!");
        }
    }
}
