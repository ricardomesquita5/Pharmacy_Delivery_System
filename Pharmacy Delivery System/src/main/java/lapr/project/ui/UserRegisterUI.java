package lapr.project.ui;

import lapr.project.controller.UserRegisterController;

/**
 * The type User register ui.
 */
public class UserRegisterUI {

    /**
     * The Controller.
     */
    private final UserRegisterController controller;

    /**
     * Instantiates a new User register ui.
     */
    public UserRegisterUI() {
        this.controller = new UserRegisterController();
    }

    /**
     * Run.
     */
    public void run() {
        System.out.println("\nRegister as Client:");

        if (enterData()) {
            showData();

            if (Utils.confirm("Do you confirm the data entered? (Y/N)")) {
                if (this.controller.addClient()) {
                    System.out.println("\n");
                } else {
                    System.out.println("It was not possible to complete the registration successfully.");
                }
            }
        } else {
            System.out.println("An error has occurred. Operation canceled.");
        }
    }

    /**
     * Enter data.
     * @return the client
     */
    private boolean enterData() {
        String name = Utils.readLineFromConsole("Name: ");
        String email = Utils.readLineFromConsole("Email: ");
        String password = Utils.readLineFromConsole("Password: ");
        String gpsCoordinates = Utils.readLineFromConsole("GPS Coordinates: ");
        String elevation = Utils.readLineFromConsole("Elevation: ");
        String street = Utils.readLineFromConsole("Street: ");
        String postalCode = Utils.readLineFromConsole("Postal Code: ");
        String doorNumber = Utils.readLineFromConsole("Door Number: ");
        String locality = Utils.readLineFromConsole("Locality: ");
        String creditCardNumber = Utils.readLineFromConsole("CreditCardNumber: ");
        String validityDate = Utils.readLineFromConsole("ValidityDate: ");
        String ccv = Utils.readLineFromConsole("Ccv: ");


        return this.controller.newClient(name, email, password, street, postalCode, doorNumber, locality, gpsCoordinates, elevation, creditCardNumber, validityDate, ccv);
    }

    /**
     * Show Data.
     */
    private void showData() {
        System.out.println("\nClient:\n" + this.controller.getClientString());
    }
}
