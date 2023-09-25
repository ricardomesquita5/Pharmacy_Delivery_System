package lapr.project.model;

import lapr.project.data.DataHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Vehicle test.
 */
public class VehicleTest {
    /**
     * The Vehicle.
     */
    private Vehicle vehicle;

    /**
     * Instantiates a new Vehicle Test.
     */
    public VehicleTest() {
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

        this.vehicle = new Vehicle("10.0", "134.0", "35.0", "20.0");
        this.vehicle = new Vehicle(1, 10.0, 134.0, 35.0, 100, 20.0);
    }

    /**
     * Gets ID Test.
     */
    @Test
    public void getId() {
        System.out.println("Vehicle getId() Test");
        Assertions.assertEquals(1, this.vehicle.getId());
    }

    /**
     * Gets Capacity Test.
     */
    @Test
    public void getCapacity() {
        System.out.println("Vehicle getCapacity() Test");
        Assertions.assertEquals(10.0, this.vehicle.getCapacity(), 0);
    }

    /**
     * Gets Power Test.
     */
    @Test
    public void getPower() {
        System.out.println("Vehicle getPower() Test");
        Assertions.assertEquals(134.0, this.vehicle.getPower(), 0);
    }

    /**
     * Gets Max Power Test.
     */
    @Test
    public void getMaxPower() {
        System.out.println("Vehicle getMaxPower() Test");
        Assertions.assertEquals(35.0, this.vehicle.getMaxBattery(), 0);
    }

    /**
     * Gets Battery Test.
     */
    @Test
    public void getBattery() {
        System.out.println("Vehicle getBattery() Test");
        Assertions.assertEquals(100, this.vehicle.getBattery());
    }

    /**
     * Gets Weight Test.
     */
    @Test
    public void getWeight() {
        System.out.println("Vehicle getWeight() Test");
        Assertions.assertEquals(20.0, this.vehicle.getWeight(), 0);
    }

    /**
     * Sets Capacity Test.
     */
    @Test
    public void setCapacity() {
        System.out.println("Vehicle setCapacity() Test");
        this.vehicle.setCapacity("130");
        Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        try {
            this.vehicle.setCapacity(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        }
        try {
            this.vehicle.setCapacity("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        }
        try {
            this.vehicle.setCapacity("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        }
        try {
            this.vehicle.setCapacity("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        }
        try {
            this.vehicle.setCapacity("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getCapacity(), 0);
        }
    }

    /**
     * Sets Power Test.
     */
    @Test
    public void setPower() {
        System.out.println("Vehicle setMaxPower() Test");
        this.vehicle.setPower("130");
        Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        try {
            this.vehicle.setPower(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        }
        try {
            this.vehicle.setPower("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        }
        try {
            this.vehicle.setPower("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        }
        try {
            this.vehicle.setPower("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        }
        try {
            this.vehicle.setPower("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getPower(), 0);
        }
    }

    /**
     * Sets Max Power Test.
     */
    @Test
    public void setMaxPower() {
        System.out.println("Vehicle setMaxPower() Test");
        this.vehicle.setMaxBattery("130");
        Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        try {
            this.vehicle.setMaxBattery(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        }
        try {
            this.vehicle.setMaxBattery("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        }
        try {
            this.vehicle.setMaxBattery("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        }
        try {
            this.vehicle.setMaxBattery("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        }
        try {
            this.vehicle.setMaxBattery("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getMaxBattery(), 0);
        }
    }

    /**
     * Sets Battery Test.
     */
    @Test
    public void setBattery() {
        System.out.println("Vehicle setBattery() Test");
        this.vehicle.setBattery(12);
        Assertions.assertEquals(12, this.vehicle.getBattery());
    }

    /**
     * Sets Weight Test.
     */
    @Test
    public void setWeight() {
        System.out.println("Vehicle setWeight() Test");
        this.vehicle.setWeight("130");
        Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        try {
            this.vehicle.setWeight(null);
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        }
        try {
            this.vehicle.setWeight("");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        }
        try {
            this.vehicle.setWeight("-1");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        }
        try {
            this.vehicle.setWeight("0");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        }
        try {
            this.vehicle.setWeight("asasad");
        } catch (IllegalArgumentException ia) {
            Assertions.assertEquals(130.0, this.vehicle.getWeight(), 0);
        }
    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest() {
        System.out.println("Vehicle toString() Test");
        String s = String.format("ID: %d - Capacity(KG): %.2f - Power(W): %.2f - Max Battery(W/H): %.2f - Battery(%%): %d - Weight(Kg): %.2f", 1, 10.0, 134.0, 35.0, 100, 20.0);
        Assertions.assertEquals(s, this.vehicle.toString());
    }

    /**
     * To String Test.
     */
    @Test
    public void toStringTest2() {
        System.out.println("Scooter toStringTest2() Test");
        String s = String.format("Capacity(KG): %.2f - Power(W): %.2f - Max Battery(W/H): %.2f - Battery(%%): %d - Weight(Kg): %.2f", 10.0, 134.0, 35.0, 100, 20.0);
        Assertions.assertEquals(s, this.vehicle.toString2());
    }
}
