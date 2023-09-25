package lapr.project.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu courier ui.
 */
public class MenuCourierUI {
    /**
     * Instantiates a new Menu courier ui.
     */
    public MenuCourierUI() {
        //does nothing yet
    }

    /**
     * Run.
     *
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public void run() throws SQLException, IOException {
        List<String> options = new ArrayList<>();
        options.add("Start a delivery by scooter");
        options.add("Finish Delivery by scooter");

        int opcao;
        do {
            opcao = Utils.showsAndSelectIndex(options, "\n\nCourier Menu");

            switch (opcao) {
                case 0:
                    StartDeliveryUI startDeliveryUI = new StartDeliveryUI();
                    startDeliveryUI.run();
                    break;
                case 1:
                    FinishDeliveryUI upcUI = new FinishDeliveryUI();
                    upcUI.run();
                    break;
                default:
                    System.out.println("Invalid Option:");
            }
        }
        while (opcao != -1);
    }
}
