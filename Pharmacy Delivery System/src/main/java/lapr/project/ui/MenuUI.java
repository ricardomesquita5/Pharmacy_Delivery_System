package lapr.project.ui;

import lapr.project.data.AdministratorDB;
import lapr.project.data.ClientDB;
import lapr.project.data.CourierDB;
import lapr.project.utils.authorization.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu UI.
 */
public class MenuUI {

    /**
     * Instantiates a new Menu UI.
     */
    public MenuUI() {
        //does nothing yet
    }

    /**
     * Run.
     *
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public void run() throws IOException, SQLException {
        List<String> options = new ArrayList<>();
        options.add("Login");
        options.add("Create Account");
        options.add("Close Application");
        int opcao;
        do {
            opcao = Utils.showsAndSelectIndex(options, "\n# Menu #");

            switch (opcao) {
                case 0:
                    LoginUI uiLogin = new LoginUI();
                    boolean success = uiLogin.run();
                    if (success) {
                        redirectsToUI(uiLogin.getUser());
                    }
                    uiLogin.logout();
                    break;
                case 1:
                    UserRegisterUI user = new UserRegisterUI();
                    user.run();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option:");
            }
        }
        while (opcao != -1);
    }

    /**
     * Redirects To UI
     *
     * @param user the user
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    private void redirectsToUI(User user) throws SQLException, IOException {

        if (new AdministratorDB().isAdmin(user.getEmail())) {
            MenuAdministratorUI ui = new MenuAdministratorUI();
            ui.run();
        } else {
            if (new ClientDB().isClient(user.getEmail())) {
                MenuClienteUI clienteUI = new MenuClienteUI();
                clienteUI.run();
            } else {
                if (new CourierDB().isCourier(user.getEmail())) {
                    MenuCourierUI ui = new MenuCourierUI();
                    ui.run();

                }
            }
        }
    }
}
