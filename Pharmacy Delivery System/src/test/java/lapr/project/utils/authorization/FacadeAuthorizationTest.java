package lapr.project.utils.authorization;

import lapr.project.data.DataHandler;
import lapr.project.utils.authorization.model.User;
import lapr.project.utils.authorization.model.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Facade Authorization Test.
 */
class FacadeAuthorizationTest {

    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization fa;

    /**
     * The Facade Authorization.
     */
    private User user;

    /**
     * Instantiates a new Facade Authorization Test.
     */
    public FacadeAuthorizationTest() {
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

        this.fa = new FacadeAuthorization();
    }

    /**
     * Do Login Test.
     */
    @Test
    void doLogin() {
        System.out.println("Facade Authorization doLogin() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        UserSession userSession = fa.doLogin(strEmail, strPwd);
        this.user = userSession.getUser();
        Assertions.assertEquals(this.user.getEmail(), strEmail);
        fa.doLogout();
        strEmail = "admadm@lapr.pt";
        strPwd = "123";
        userSession = fa.doLogin(strEmail, strPwd);
        if (userSession == null) {
            strPwd = strEmail;
        }
        Assertions.assertEquals(strPwd, strEmail);
        fa.doLogout();
    }

    /**
     * Gets Current Session Test.
     */
    @Test
    void getCurrentSession() {
        System.out.println("Facade Authorization getCurrentSession() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        fa.doLogin(strEmail, strPwd);
        this.user = fa.getCurrentSession().getUser();
        Assertions.assertEquals(this.user.getEmail(), strEmail);
        fa.doLogout();
    }

    /**
     * Do logout Test.
     */
    @Test
    void doLogout() {
        System.out.println("Facade Authorization doLogout() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        fa.doLogin(strEmail, strPwd);
        Assertions.assertNotNull(fa.getUser());
        Assertions.assertNotNull(fa.getCurrentSession());
        fa.doLogout();
        Assertions.assertNull(fa.getUser());
        Assertions.assertNull(fa.getCurrentSession());
        fa.doLogout();
        Assertions.assertNull(fa.getUser());
        Assertions.assertNull(fa.getCurrentSession());
    }

    /**
     * Gets User Test.
     */
    @Test
    void getUser() {
        System.out.println("Facade Authorization getUser() Test");
        String strEmail = "adm@lapr.pt";
        String strPwd = "123";
        fa.doLogin(strEmail, strPwd);
        this.user = fa.getUser();
        Assertions.assertEquals(this.user.getEmail(), strEmail);
        fa.doLogout();
    }
}