package lapr.project.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu Administrator UI.
 */
public class MenuAdministratorUI {

    /**
     * Instantiates a new Menu Administrator UI.
     */
    public MenuAdministratorUI() {
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
        options.add("Create Pharmacy");
        options.add("Add Scooter");
        options.add("Remove Scooter");
        options.add("Update Scooter");
        options.add("Add Drone");
        options.add("Remove Drone");
        options.add("Update Drone");
        options.add("Add Product");
        options.add("Remove Product");
        options.add("Update Product");
        options.add("Add Courier");
        options.add("Remove Courier");
        options.add("Add Park");
        options.add("Create Delivery");

        int opcao;
        do {
            opcao = Utils.showsAndSelectIndex(options, "\n\nMenu Administrator");

            switch (opcao) {
                case 0:
                    CreatePharmacyUI uiCreatePharmacyUI = new CreatePharmacyUI();
                    uiCreatePharmacyUI.run();
                    break;
                case 1:
                    AddScooterUI uiAddScooter = new AddScooterUI();
                    uiAddScooter.run();
                    break;
                case 2:
                    RemoveScooterUI uiRemoveScooter = new RemoveScooterUI();
                    uiRemoveScooter.run();
                    break;
                case 3:
                    UpdateScooterUI uiUpdateScooter = new UpdateScooterUI();
                    uiUpdateScooter.run();
                    break;
                case 4:
                    AddDroneUI uiAddDrone = new AddDroneUI();
                    uiAddDrone.run();
                    break;
                case 5:
                    RemoveDroneUI uiRemoveDrone = new RemoveDroneUI();
                    uiRemoveDrone.run();
                    break;
                case 6:
                    UpdateDroneUI uiUpdateDrone = new UpdateDroneUI();
                    uiUpdateDrone.run();
                    break;
                case 7:
                    AddProductUI uiAddProduct = new AddProductUI();
                    uiAddProduct.run();
                    break;
                case 8:
                    RemoveProductUI uiRemoveProduct = new RemoveProductUI();
                    uiRemoveProduct.run();
                    break;
                case 9:
                    UpdateProductUI uiUpdateProduct = new UpdateProductUI();
                    uiUpdateProduct.run();
                    break;
                case 10:
                    AddCourierUI uiAddCourier = new AddCourierUI();
                    uiAddCourier.run();
                    break;
                case 11:
                    RemoveCourierUI removeCourierUI = new RemoveCourierUI();
                    removeCourierUI.run();
                    break;
                case 12:
                    AddParkUI addParkUI = new AddParkUI();
                    addParkUI.run();
                    break;
                case 13:
                    CreateDeliveryUI cdUI = new CreateDeliveryUI();
                    cdUI.run();
                    break;
                default:
                    System.out.println("Invalid Option:");
                    break;
            }
        }
        while (opcao != -1);
    }
}
