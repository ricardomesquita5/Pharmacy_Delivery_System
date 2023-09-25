package lapr.project.utils.authorization.model;

import lapr.project.data.DataHandler;
import lapr.project.data.UserDB;
import lapr.project.model.Drone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type User Test.
 */
class UserTest {

    /**
     * The User.
     */
    private User user;

    /**
     * Instantiates a new User Test.
     */
    public UserTest() {
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

        this.user = new User("José", "testUser@isep.pt", "123abc");
    }

    /**
     * User Constructors Test.
     */
    @Test
    void UserConstructor() {
        System.out.println("User Constructor Test");
        try {
            this.user = new User(null, "testUser@isep.pt", "123abc");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("José", this.user.getName());
        }
        try {
            this.user = new User("", "testUser@isep.pt", "123abc");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("José", this.user.getName());
        }
        try {
            this.user = new User("José", null, "123abc");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user = new User("José", "", "123abc");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user = new User("José", "testUser@isep.pt", null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("123abc", this.user.getPassword());
        }
        try {
            this.user = new User("José", "testUser@isep.pt", "");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("123abc", this.user.getPassword());
        }
    }

    /**
     * Sets Email Test.
     */
    @Test
    void setEmail() {
        System.out.println("User setEmail() Test");
        this.user.setEmail("testUser@isep.pt");
        Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        try {
            this.user.setEmail("joseisep.pt");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("joseisep.com");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("jose@isep");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("joseisep");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
    }

    /**
     * Sets Name Test.
     */
    @Test
    void setName() {
        System.out.println("User setName() Test");
        this.user.setName("Arlindo");
        Assertions.assertEquals("Arlindo", this.user.getName());
    }

    /**
     * Sets Password Test.
     */
    @Test
    void setPassword() {
        System.out.println("User setPassword() Test");
        this.user.setPassword("abc");
        Assertions.assertEquals("abc", this.user.getPassword());
    }

    /**
     * Gets Email Test.
     */
    @Test
    void getName() {
        System.out.println("User getName() Test");
        Assertions.assertEquals("José", this.user.getName());
    }

    /**
     * Gets Email Test.
     */
    @Test
    void getEmail() {
        System.out.println("User getEmail() Test");
        Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
    }

    /**
     * Gets Password Test.
     */
    @Test
    void getPassword() {
        System.out.println("User getPassword() Test");
        Assertions.assertEquals("123abc", this.user.getPassword());
    }

    /**
     * Has Password Test.
     */
    @Test
    void hasPassword() {
        System.out.println("User hasPassword() Test");
        Assertions.assertTrue(this.user.hasPassword("123abc"));
        Assertions.assertFalse(this.user.hasPassword("12bc"));
    }

    /**
     * Has Password Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getUser() throws SQLException {
        System.out.println("User getUser() Test");
        new UserDB().removeUser(this.user.getEmail());
        this.user = new User("José", "testUser@isep.pt", "123abc");
        this.user.save();
        Assertions.assertEquals("testUser@isep.pt",this.user.getUser("testUser@isep.pt").getEmail());
        new UserDB().removeUser(this.user.getEmail());
    }

    /**
     * Save User Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("User save() Test");
        new UserDB().removeUser(this.user.getEmail());
        this.user = new User("José", "testUser@isep.pt", "123abc");
        this.user.save();
        Assertions.assertEquals(this.user.toString(), new UserDB().findUser(this.user.getEmail(), this.user.getPassword()).toString());
        try {
            this.user.save();
        } catch (IllegalArgumentException iae){
            System.out.println(iae.toString());
        }
        try {
            this.user.setEmail("Teste");
            this.user.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("Teste@i");
            this.user.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("Teste.pt");
            this.user.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        try {
            this.user.setEmail("Teste.com");
            this.user.save();
        } catch (IllegalArgumentException iae){
            Assertions.assertEquals("testUser@isep.pt", this.user.getEmail());
        }
        new UserDB().removeUser(this.user.getEmail());
    }

    /**
     * Remove User Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void remove() throws SQLException {
        System.out.println("User remove() Test");
        new UserDB().removeUser(this.user.getEmail());
        new UserDB().addUser(this.user);
        Assertions.assertEquals(this.user.toString(), new UserDB().findUser(this.user.getEmail(), this.user.getPassword()).toString());
        this.user.remove();
        Assertions.assertNull(new UserDB().findUser(this.user.getEmail(), this.user.getPassword()));
    }

    /**
     * To String Test.
     */
    @Test
    void testToString() {
        System.out.println("User toString() Test");
        String s = String.format("Name: %s - Email: %s", "José", "testUser@isep.pt");
        Assertions.assertEquals(s, this.user.toString());
    }

    /**
     * Equals Test.
     */
    @Test
    public void equals() {
        System.out.println("User equals() Test");

        Drone d = new Drone(1, 10.0, 134.0, 35.0, 100,20.0);
        User u1 = new User("José", "testUser2@isep.pt", "123abc");
        User u2 = new User("José", "testUser@isep.pt", "123abc");

        //objetos iguais
        Assertions.assertEquals(this.user, this.user);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.user, d);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.user, u1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.user, u2);
    }

    /**
     * Hash Code Test.
     */
    @Test
    public void HashCodeTest() {
        System.out.println("User hashCode() Test");
        User u1 = new User("José", "testUser2@isep.pt", "123abc");
        User u2 = new User("José", "testUser@isep.pt", "123abc");
        Assertions.assertEquals(this.user.hashCode(), u2.hashCode());
        Assertions.assertNotEquals(this.user.hashCode(), u1.hashCode());
    }
}