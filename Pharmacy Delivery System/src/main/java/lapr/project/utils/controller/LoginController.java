package lapr.project.utils.controller;

import lapr.project.utils.authorization.model.User;

/**
 * The type Login Controller.
 *
 * @author paulomaio
 */
public class LoginController {

    /**
     * The ApplicationPOT.
     */
    private final ApplicationPOT applicationPOT;

    /**
     * Instantiates a new Login Controller.
     */
    public LoginController() {
        this.applicationPOT = ApplicationPOT.getInstance();
    }

    /**
     * Do Login.
     *
     * @param strId  the ID
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean doLogin(String strId, String strPwd) {
        return this.applicationPOT.doLogin(strId, strPwd);
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        this.applicationPOT.doLogout();
    }

    /**
     * Get User.
     *
     * @return the user
     */
    public User getUser() {
        return this.applicationPOT.getUser();
    }
}
