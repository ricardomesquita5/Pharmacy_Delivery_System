package lapr.project.utils.authorization.model;

import lapr.project.data.UserDB;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The type User.
 */
public class User {

    /**
     * The User's Name.
     */
    private String name;
    /**
     * The User's Email.
     */
    private String email;
    /**
     * The User's Password.
     */
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param strName     the Name
     * @param strEmail    the Email
     * @param strPassword the Password
     */
    public User(String strName, String strEmail, String strPassword) {

        if ((strName == null) || (strEmail == null) || (strPassword == null) || (strName.isEmpty()) || (strEmail.isEmpty()) || (strPassword.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments cannot be null or empty.");
        }

        this.name = strName;
        this.setEmail(strEmail);
        this.password = strPassword;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        if (email.contains("@") && (email.contains(".pt") || email.contains(".com"))) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email with wrong format!");
        }
    }

    /**
     * Sets Name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets Name.
     *
     * @return the Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets Email.
     *
     * @return the Email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get password.
     *
     * @return password password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Has Password.
     *
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean hasPassword(String strPwd) {
        return this.password.equals(strPwd);
    }

    /**
     * Get User.
     *
     * @param email the email
     * @return the user
     */
    public User getUser(String email) {
        return new UserDB().getUser(email);
    }

    /**
     * Save User.
     *
     * @throws SQLException the sql exception
     */
    public void save() throws SQLException {
        try {
            getUser(this.getEmail());
            throw new IllegalArgumentException("The specified email is already associated to a client!");
        } catch (IllegalArgumentException ex) {
            new UserDB().addUser(this);
        }
    }

    /**
     * Textual User's Description.
     *
     * @return Textual User's Description
     */
    @Override
    public String toString() {
        return String.format("Name: %s - Email: %s", this.name, this.email);
    }

    /**
     * Remove.
     *
     * @throws SQLException the sql exception
     */
    public void remove() throws SQLException {
        new UserDB().removeUser(this.getEmail());
    }

    /**
     * Check if two Users are equals
     *
     * @param o User
     * @return true if are equals or false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    /**
     * Hash Code
     *
     * @return code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
