package lapr.project.ui;

import lapr.project.controller.AddParkController;
import lapr.project.model.Pharmacy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Add park ui.
 */
public class AddParkUI {

    /**
     * The Controller.
     */
    private final AddParkController controller;

    /**
     * Instantiates a new Add Scooter UI.
     */
    public AddParkUI() {
        this.controller = new AddParkController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\n\n# Add Park #");
        try {
            if (enterData()) {
                if (this.controller.addPark()) {
                    System.out.println("\nAdd successful.");
                } else {
                    System.out.println("It was not possible to complete the registration successfully.");
                }
            } else {
                System.out.println("An error has occurred. Operation canceled.");
            }
        } catch (IllegalArgumentException | SQLException ia) {
            System.out.println("\n" + ia.getMessage());
        }
    }

    /**
     * enterData.
     *
     * @return the Park
     * @throws SQLException the exception
     */
    private boolean enterData() throws SQLException {
        List<Integer> list = new ArrayList<>();
        List<Pharmacy> pharList = this.controller.getPharmaciesList();
        if (pharList.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No Pharmacies in the system!");
        } else {
            Pharmacy phar = (Pharmacy) Utils.showsAndSelect(pharList, "\nSelect the Pharmacy you want to add a scooter to:");
            if (phar != null) {
                String pharEmail = phar.getEmail();
                this.controller.getPharmacyByEmail(pharEmail);
                String designation;
                do {
                    designation = Utils.readLineFromConsole("Type of park (scooter/drone): ");
                } while (!Objects.requireNonNull(designation).equalsIgnoreCase("scooter") && !designation.equalsIgnoreCase("drone"));
                designation = designation.toLowerCase();
                String spotsCapacity = Utils.readLineFromConsole("Spots Capacity: ");
                String powerCapacity = Utils.readLineFromConsole("Power Capacity for Charging: ");

                try {
                    for (int i = 1; i <= Integer.parseInt(Objects.requireNonNull(spotsCapacity)); i++) {
                        list.add(i);
                    }

                    int x = -1;
                    while (x != 0 && !list.isEmpty()) {
                        System.out.println("Now you need to create a scooters park for the pharmacy...");
                        System.out.println("Please select the spots that will allow to charge");
                        System.out.println("When you don´t want to select more spots select 0");
                        Utils.showList(list, "List of Spots (ID´s):\n");
                        x = Integer.parseInt(Objects.requireNonNull(Utils.readLineFromConsole("Option: ")));
                        if (x <= list.size() && x > 0) {
                            list.remove(x - 1);
                        }
                    }

                    return this.controller.newPark(designation, spotsCapacity, powerCapacity) && this.controller.newSpots(list);

                } catch (IllegalArgumentException nfe) {
                    System.out.println("\nInvalid Spots Capacity!");
                    return false;
                }

            } else {
                return false;
            }
        }
    }

}
