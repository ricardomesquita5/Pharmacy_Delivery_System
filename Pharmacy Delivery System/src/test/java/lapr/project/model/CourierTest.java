package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


/**
 * The type Courier test.
 */
class CourierTest {

    /**
     * The Courier courier.
     */
    private final Courier courier;

    /**
     * The Courier c.
     */
    private final Courier c;

    /**
     * The Courier co.
     */
    private final Courier co;

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;

    /**
     * The Address.
     */
    private final Address address;

    /**
     * Instantiates a new Courier test.
     */
    public CourierTest() {
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

        this.courier=new Courier("344444444", "Courier", "courier@gmail.com", "969999999", "43533333333", "pwd");
        this.c=new Courier(344444444, "Courier", "courier@gmail.com", 969999999, (int) 44444444444.0, "43533333333", "pwd");
        this.co=new Courier(344444444, "Courier", "courier@gmail.com", 969999999, "43533333333", "pwd");
        this.address = new Address("01030100,01030101", "Rua ISEP", "4460-123", 123, "São João", 23);
        this.pharmacy = new Pharmacy("testeCourier@gmail.com", this.address, "Teste1");
    }

    /**
     * Save.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void save() throws SQLException {
        System.out.println("Courier save() Test");
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new CourierDB().getAllCouriersOfPharmacy(this.pharmacy.getEmail()).size();
        this.courier.save(this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, new CourierDB().getAllCouriersOfPharmacy(this.pharmacy.getEmail()).size());
        try {
            this.courier.save(this.pharmacy.getEmail());
        } catch (IllegalArgumentException ie) {
            Assertions.assertEquals(size + 1, new CourierDB().getAllCouriersOfPharmacy(this.pharmacy.getEmail()).size());
        }
        new CourierDB().removeCourier(this.courier.getEmail());
        new UserDB().removeUser(this.courier.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets phone number.
     */
    @Test
    void getPhoneNumber() {
        System.out.println("Courier getPhoneNumber() Test");
        Assertions.assertEquals(969999999.0, this.courier.getPhoneNumber());
    }

    /**
     * Gets social security number.
     */
    @Test
    void getSocialSecurityNumber() {
        System.out.println("Courier getSocialSecurityNumber() Test");
        Assertions.assertEquals(43533333333.0, this.courier.getSocialSecurityNumber());
    }

    /**
     * Gets nif.
     */
    @Test
    void getNif() {
        System.out.println("Courier getNif() Test");
        Assertions.assertEquals(344444444.0, this.courier.getNif());
    }

    /**
     * Gets status.
     */
    @Test
    void getStatus() {
        System.out.println("Courier getStatus() Test");
        Assertions.assertEquals("Available", this.courier.getStatus());
    }

    /**
     * Sets status.
     */
    @Test
    void setStatus() {
        System.out.println("Courier setStatus() Test");
        this.courier.setStatus("Unavailable");
        Assertions.assertEquals("Unavailable", this.courier.getStatus());
    }

    /**
     * Sets phone number.
     */
    @Test
    void setPhoneNumber() {
        System.out.println("Courier setPhoneNumber() Test");
        this.courier.setPhoneNumber("933333333");
        Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        try {
            this.courier.setPhoneNumber(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("-11111111");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("000000000");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("asasadadw");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
        try {
            this.courier.setPhoneNumber("asasad");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(933333333.0, this.courier.getPhoneNumber(), 0);
        }
    }

    /**
     * Sets social security number.
     */
    @Test
    void setSocialSecurityNumber() {
        System.out.println("Courier setSocialSecurityNumber() Test");
        this.courier.setSocialSecurityNumber("93333333333");
        Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        try {
            this.courier.setSocialSecurityNumber(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("-1111111111");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("00000000000");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("asasadadwaa");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
        try {
            this.courier.setSocialSecurityNumber("asasad");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(93333333333.0, this.courier.getSocialSecurityNumber(), 0);
        }
    }

    /**
     * Sets nif.
     */
    @Test
    void setNif() {
        System.out.println("Courier setNif() Test");
        this.courier.setNif("543333333");
        Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        try {
            this.courier.setNif(null);
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("-11111111");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("000000000");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("1");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("asasadadw");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
        try {
            this.courier.setNif("asasad");
        }catch (IllegalArgumentException ia){
            Assertions.assertEquals(543333333.0, this.courier.getNif(), 0);
        }
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        System.out.println("Courier toString() Test");
        String s = "Courier{" +
                String.format("Name: %s - Email: %s", courier.getName(), courier.getEmail()) +
                ", nif=" + courier.getNif() +
                ", phoneNumber=" + courier.getPhoneNumber() +
                ", socialSecurityNumber=" + courier.getSocialSecurityNumber() +
                ", status='" + courier.getStatus() + '\'' +
                '}';
        System.out.println(s);
        Assertions.assertEquals(s,this.courier.toString());
    }
}