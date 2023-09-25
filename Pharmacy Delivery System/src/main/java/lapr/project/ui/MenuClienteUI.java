package lapr.project.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu Administrator UI.
 */
public class MenuClienteUI {

    /**
     * Instantiates a new Menu Administrator UI.
     */
    public MenuClienteUI() {
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
        options.add("Perform Order");
        int opcao;
        do {
            opcao = Utils.showsAndSelectIndex(options, "\n\nMenu Cliente");
            if (opcao == 0) {
                PerformOrderUI uiPerformOrder = new PerformOrderUI();
                uiPerformOrder.run();
            } else {
                System.out.println("Invalid Option:");
            }
        }
        while (opcao != -1);
    }
}

