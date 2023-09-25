package lapr.project.utils.authorization.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type User session test.
 */
class UserSessionTest {

    /**
     * The User.
     */
    private User user;
    /**
     * The User Session.
     */
    private UserSession userSession;

    /**
     * Instantiates a new User Session Test.
     */
    public UserSessionTest() {
        this.userSession = new UserSession();
        try {
            this.userSession = new UserSession(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertNull(null);
        }
        this.user = new User("José", "jose@isep.pt", "123abc");
        this.userSession = new UserSession(this.user);
    }

    /**
     * Do Logout Test.
     */
    @Test
    void doLogout() {
        System.out.println("UserSession doLogout() Test");
        this.userSession.doLogout();
        Assertions.assertNull(this.userSession.getUser());
    }

    /**
     * Is Logged In Test.
     */
    @Test
    void isLoggedIn() {
        System.out.println("UserSession isLoggedIn() Test");
        Assertions.assertTrue(this.userSession.isLoggedIn());
        this.userSession = new UserSession();
        Assertions.assertFalse(this.userSession.isLoggedIn());
    }

    /**
     * Get User Email Test.
     */
    @Test
    void getUserEmail() {
        System.out.println("UserSession getUserEmail() Test");
        Assertions.assertEquals(this.user.getEmail(), this.userSession.getUserEmail());
        this.userSession = new UserSession();
        Assertions.assertNull(this.userSession.getUserEmail());
    }

    /**
     * Get User Email Test.
     */
    @Test
    void getUser() {
        System.out.println("UserSession getUser() Test");
        Assertions.assertEquals(this.user, this.userSession.getUser());
    }
}