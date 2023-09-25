package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Platform Test.
 */
class PlatformTest {

    /**
     * The Platform.
     */
    private final Platform platform;

    /**
     * Instantiates a new Platform Test.
     */
    public PlatformTest() {
        this.platform = new Platform("Test");
    }

    /**
     * Test equals.
     */
    @Test
    void testEquals() {
        System.out.println("Platform equals() Test");

        Scooter s1 = new Scooter(2, 10.0, 134.0, 35.0, 100, 3.0);
        Platform p1 = new Platform("Test 1");
        Platform p2 = new Platform("Test");

        //objetos iguais
        Assertions.assertEquals(this.platform, this.platform);
        //objetos classes diferentes
        Assertions.assertNotEquals(this.platform, s1);
        //objetos da mesma classe ids diferentes
        Assertions.assertNotEquals(this.platform, p1);
        //objetos da mesma classe ids iguais
        Assertions.assertEquals(this.platform, p2);
    }

    /**
     * Test hash code.
     */
    @Test
    void testHashCode() {
        System.out.println("Platform hashCode() Test");
        Platform p1 = new Platform("Test");
        Platform p2 = new Platform("Test 1");
        Assertions.assertEquals(this.platform.hashCode(), p1.hashCode());
        Assertions.assertNotEquals(this.platform.hashCode(), p2.hashCode());
    }

    /**
     * Test gets facade authorization.
     */
    @Test
    void getFacadeAuthorization() {
        System.out.println("Platform getFacadeAuthorization() Test");
    }
}