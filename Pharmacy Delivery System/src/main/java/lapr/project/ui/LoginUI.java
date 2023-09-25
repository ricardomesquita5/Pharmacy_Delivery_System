package lapr.project.ui;

import lapr.project.utils.authorization.model.User;
import lapr.project.utils.controller.LoginController;

/**
 * The type Login UI.
 */
public class LoginUI {

    /**
     * the Controller.
     */
    private final LoginController controller;

    /**
     * Instantiates a new Login UI.
     */
    public LoginUI() {
        controller = new LoginController();
    }

    /**
     * Run.
     *
     * @return the boolean
     */
    public boolean run() {
        System.out.println("\n\n# Login #");

        int maxAttempts = 3;
        boolean success;
        do {
            maxAttempts--;
            String sEmail = Utils.readLineFromConsole("Email: ");
            String sPwd = Utils.readLineFromConsole("Password: ");

            success = controller.doLogin(sEmail, sPwd);
            if (!success) {
                System.out.println("\nWrong Email or Password. \nYou have more " + maxAttempts + " attempts\n");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    /**
     * Logout.
     */
    public void logout() {
        controller.doLogout();
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return controller.getUser();
    }
}
