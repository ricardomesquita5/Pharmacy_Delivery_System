package lapr.project.utils.controller;

import lapr.project.utils.authorization.*;
import lapr.project.utils.authorization.model.User;
import lapr.project.utils.authorization.model.UserSession;
import lapr.project.model.Platform;

/**
 * The type Application pot.
 */
public class ApplicationPOT {

    /**
     * The ApplicationPOT.
     */
    private static ApplicationPOT singleton = null;
    /**
     * The Platform.
     */
    private final Platform platform;
    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization authorization;

    /**
     * Instantiates a new ApplicationPOT.
     */
    public ApplicationPOT() {
        this.platform = new Platform("Company Pharmacies");
        this.authorization = this.platform.getFacadeAuthorization();
    }

    /**
     * Gets Instance.
     *
     * @return the Instance
     */
    public static ApplicationPOT getInstance() {
        if (singleton == null) {
            synchronized (ApplicationPOT.class) {
                singleton = new ApplicationPOT();
            }
        }
        return singleton;
    }

    /**
     * Gets Platform.
     *
     * @return the Platform
     */
    public Platform getPlatform() {
        return this.platform;
    }

    /**
     * Gets Current Session.
     *
     * @return the current session
     */
    public UserSession getCurrentSession() {
        return this.authorization.getCurrentSession();
    }

    /**
     * Gets Facade Authorization.
     *
     * @return the Facade Authorization
     */
    public FacadeAuthorization getFacadeAuthorization() {
        return this.authorization;
    }

    /**
     * Do Login.
     *
     * @param strId  the ID
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean doLogin(String strId, String strPwd) {
        return this.authorization.doLogin(strId, strPwd) != null;
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        this.authorization.doLogout();
    }

    /**
     * Get User.
     *
     * @return the user
     */
    public User getUser() {
        return this.authorization.getUser();
    }
}
