package lapr.project.utils.controller;

import lapr.project.data.DataHandler;
import lapr.project.data.UserDB;
import lapr.project.utils.authorization.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Login controller test.
 */
class LoginControllerTest {

    /**
     * The Login Controller.
     */
    private final LoginController controller;
    /**
     * The User.
     */
    private final User user;

    /**
     * Instantiates a new Login Controller Test.
     */
    public LoginControllerTest() {
        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initial Database Setup
        new DataHandler();

        this.controller = new LoginController();
        this.user = new User("José", "testUser@isep.pt", "123abc");
    }

    /**
     * Do Login Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void doLogin() throws SQLException {
        System.out.println("LoginController doLogin() Test");
        new UserDB().removeUser(this.user.getEmail());
        this.user.save();
        Assertions.assertTrue(this.controller.doLogin(this.user.getEmail(), this.user.getPassword()));
        new UserDB().removeUser(this.user.getEmail());
        this.controller.doLogout();
        Assertions.assertFalse(this.controller.doLogin("A", "A"));
    }

    /**
     * Gets User Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getUser() throws SQLException {
        System.out.println("LoginController getUser() Test");
        new UserDB().removeUser(this.user.getEmail());
        this.user.save();
        this.controller.doLogin(this.user.getEmail(), this.user.getPassword());
        Assertions.assertEquals(this.user.toString(), this.controller.getUser().toString());
        this.controller.doLogout();
        new UserDB().removeUser(this.user.getEmail());
    }
}