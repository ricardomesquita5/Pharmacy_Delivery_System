package lapr.project.utils.controller;

import lapr.project.data.DataHandler;
import lapr.project.model.Platform;
import lapr.project.utils.authorization.FacadeAuthorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Application pot test.
 */
class ApplicationPOTTest {

    /**
     * The Facaade Authorization.
     */
    private ApplicationPOT app;

    /**
     * Instantiates a new Facade Authorization Test.
     */
    public ApplicationPOTTest() {
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

        this.app = new ApplicationPOT();
    }

    /**
     * Gets instance.
     */
    @Test
    void getInstance() {
        System.out.println("Application POT getInstance() Test");
        this.app = null;
        ApplicationPOT.getInstance();
        ApplicationPOT.getInstance();
    }

    /**
     * Gets platform.
     */
    @Test
    void getPlatform() {
        System.out.println("Application POT getPlatform() Test");
        Platform p = new Platform("Company Pharmacies");
        Assertions.assertEquals(p,this.app.getPlatform());
    }

    /**
     * Gets facade authorization.
     */
    @Test
    void getFacadeAuthorization() {
        System.out.println("Application POT getFacadeAuthorization() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        this.app.doLogin(strEmail, strPwd);
        FacadeAuthorization fa = this.app.getFacadeAuthorization();
        Assertions.assertEquals(fa.getUser().getEmail(),strEmail);
    }

    /**
     * Gets current session.
     */
    @Test
    void getCurrentSession() {
        System.out.println("Application POT getCurrentSession() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        this.app.doLogin(strEmail, strPwd);
        Assertions.assertEquals(this.app.getCurrentSession().getUser().getEmail(),strEmail);
        this.app.doLogout();
    }

    /**
     * Do login.
     */
    @Test
    void doLogin() {
        System.out.println("Application POT doLogin() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";

        this.app.doLogin(strEmail, strPwd);
        Assertions.assertTrue(this.app.doLogin(strEmail, strPwd));
        this.app.doLogout();

        this.app.doLogin("strEmail", "strPwd");
        Assertions.assertFalse(this.app.doLogin("strEmail", "strPwd"));
        this.app.doLogout();
    }

    /**
     * Do logout.
     */
    @Test
    void doLogout() {
        System.out.println("Application POT doLogout() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        this.app.doLogin(strEmail, strPwd);
        this.app.doLogout();
        if (this.app.getCurrentSession() == null) {
            strEmail = strPwd;
        }
        Assertions.assertEquals(strPwd,strEmail);
    }

    /**
     * Gets user.
     */
    @Test
    void getUser() {
        System.out.println("Application POT getUser() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        this.app.doLogin(strEmail, strPwd);
        Assertions.assertEquals(strEmail, this.app.getUser().getEmail());
    }
}