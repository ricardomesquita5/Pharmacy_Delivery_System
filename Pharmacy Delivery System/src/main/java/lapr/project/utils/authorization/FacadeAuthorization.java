package lapr.project.utils.authorization;

import lapr.project.data.UserDB;
import lapr.project.utils.authorization.model.*;

/**
 * The type Facade authorization.
 */
public class FacadeAuthorization {

    /**
     * The User.
     */
    private User user;
    /**
     * The User Session.
     */
    private UserSession session = null;

    /**
     * Do Login User Session.
     *
     * @param strEmail the ID
     * @param strPwd   the Password
     * @return the User Session
     */
    public UserSession doLogin(String strEmail, String strPwd) {
        this.user = new UserDB().findUser(strEmail, strPwd);
        if (this.user != null) {
            this.session = new UserSession(this.user);
        }
        return getCurrentSession();
    }

    /**
     * Gets Current Session.
     *
     * @return the Current Session
     */
    public UserSession getCurrentSession() {
        return this.session;
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        if (this.session != null) {
            this.session.doLogout();
        }
        this.session = null;
        this.user = null;
    }

    /**
     * Get User.
     *
     * @return the user
     */
    public User getUser() {
        return this.user;
    }
}
