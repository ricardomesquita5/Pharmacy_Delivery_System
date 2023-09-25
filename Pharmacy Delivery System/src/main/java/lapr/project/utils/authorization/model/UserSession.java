package lapr.project.utils.authorization.model;

/**
 * The type User session.
 */
public class UserSession {

    /**
     * The User.
     */
    private User user;

    /**
     * Instantiates a new User Session.
     *
     * @param oUser the User
     */
    public UserSession(User oUser) {
        if (oUser == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        this.user = oUser;
    }

    /**
     * Instantiates a new User Session.
     */
    public UserSession() {
    }

    /**
     * Do logout.
     */
    public void doLogout() {
        this.user = null;
    }

    /**
     * Is Logged In.
     *
     * @return true if is logged in or false if not
     */
    public boolean isLoggedIn() {
        return this.user != null;
    }

    /**
     * Gets User's Email.
     *
     * @return the User's Email
     */
    public String getUserEmail() {
        if (isLoggedIn()) {
            return this.user.getEmail();
        }
        return null;
    }

    /**
     * Get user.
     *
     * @return the user
     */
    public User getUser() {
        return this.user;
    }
}
