package lapr.project.ui;

import lapr.project.controller.CreatePharmacyController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Create pharmacy ui.
 */
public class CreatePharmacyUI {

    /**
     * the controller
     */
    private final CreatePharmacyController controller;

    /**
     * Instantiates a new Create pharmacy ui.
     */
    public CreatePharmacyUI() {
        this.controller = new CreatePharmacyController();
    }

    /**
     * Run.
     *
     * @throws SQLException the sql exception
     */
    public void run() throws SQLException {
        System.out.println("\nCreate Pharmacy:");

        if (enterData()) {
            showData();

            if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                if (this.controller.addPharmacy()) {
                    System.out.println("\nAdd successful.");
                } else {
                    System.out.println("It was not possible to complete the registration successfully.");
                }
            }
        } else {
            System.out.println("\nAn error has occurred. Operation canceled.");
        }
    }

    /**
     * enterData.
     *
     * @return new Pharmacy.
     */
    private boolean enterData() {
        List<Integer> list = new ArrayList<>();
        String gpsCoordinates = Utils.readLineFromConsole("GPS Coordinates (Latitude,Longitude): ");
        String street = Utils.readLineFromConsole("Street: ");
        String postalCode = Utils.readLineFromConsole("Postal Code (xxxx-xxx): ");
        String doorNumber = Utils.readLineFromConsole("Door Number: ");
        String locality = Utils.readLineFromConsole("Locality: ");
        String email = Utils.readLineFromConsole("Email: ");
        String designation = Utils.readLineFromConsole("Designation: ");
        String elevation = Utils.readLineFromConsole("Elevation (m): ");

        String designationPark = "scooter";
        String spotsCapacity;
        String powerCapacity;
        spotsCapacity = Utils.readLineFromConsole("Spots Capacity of the Park: ");
        powerCapacity = Utils.readLineFromConsole("Power Capacity for Charging of the Park: ");

        int i;
        try {
            for (i = 1; i <= Integer.parseInt(Objects.requireNonNull(spotsCapacity)); i++) {
                list.add(i);
            }

            int y = -2;
            while (!list.isEmpty() && y != 0) {
                System.out.println("\nPlease select the spots that will allow to charge\nWhen you don´t want to select more spots select 0");
                Utils.showList(list, "List of Spots:\n");
                y = Integer.parseInt(Objects.requireNonNull(Utils.readLineFromConsole("Option: ")));
                if (y <= list.size() && y > 0) {
                    list.remove(y - 1);
                }
            }


            return this.controller.newPharmacy(designation, email, street, postalCode, doorNumber, locality, gpsCoordinates, elevation,
                    designationPark, spotsCapacity, powerCapacity, list);

        } catch (IllegalArgumentException nfe) {
            System.out.println("\nInvalid Spots Capacity!");
            return false;
        }

    }

    /**
     * showData.
     */
    private void showData() {
        System.out.println("\nPharmacy:\n" + this.controller.getPharmacyString());
    }
}
