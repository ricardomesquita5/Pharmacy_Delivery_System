package lapr.project.model;

import lapr.project.data.*;
import lapr.project.graphbase.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

/**
 * The type Product In Nearby Pharmacies Algorithm Test.
 */
class ProductInNearbyPharmaciesAlgorithmTest {

    /**
     * The Pharmacy.
     */
    private final Pharmacy pharmacy;
    /**
     * The Address.
     */
    private final Address address;
    /**
     * The Product.
     */
    private final Product product;
    /**
     * The Pharmacy Product.
     */
    private final PharmacyProduct pharmacyProduct;

    /**
     * Instantiates a new Product In Nearby Pharmacies Algorithm Test.
     */
    public ProductInNearbyPharmaciesAlgorithmTest() {
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

        this.address = new Address("41.16123307265623, -8.62816771599612", "Rua ISEP", "4460-123", 126, "São João", 32);
        this.pharmacy = new Pharmacy("testeProductInNearbyPharmaciesAlgorithm@gmail.com", this.address, "Pharmacy Teste");
        this.product = new Product("123", "Name30000", "Desc3000000", "1.10", "1.10");
        this.pharmacyProduct = new PharmacyProduct(this.product.getReference(), this.pharmacy.getEmail(), 2);
    }

    /**
     * Make Transferences Test.
     *
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    @Test
    void makeTransferences() throws SQLException, IOException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm makeTransferences() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        Address a1 = new Address("41.1511111, -8.6311111", "Rua ISEP", "4460-123", 126, "São João", 10);
        new AddressDB().addAddress(a1);
        new PharmacyDB().addPharmacy(this.pharmacy);
        Pharmacy p1 = new Pharmacy("p1@isep.pt", a1, "Pharmacy 1");
        new PharmacyDB().addPharmacy(p1);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);

        Map<Pharmacy, Integer> pharmaciesAndProductAmount = new LinkedHashMap<>();
        pharmaciesAndProductAmount.put(this.pharmacy, 1);

        int size = new TransferenceDB().getAllTransferences().size();
        ProductInNearbyPharmaciesAlgorithm.makeTransferences(p1, pharmaciesAndProductAmount, this.product);
        Assertions.assertEquals(size + 1, new TransferenceDB().getAllTransferences().size());

        new TransferenceDB().removeTransference(new TransferenceDB().getLastTransference(p1.getEmail()));
        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), p1.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(p1.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(a1.getGPSCoordinates());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Update products in data base Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void updateProductsInDataBase() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm updateProductsInDataBase() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        Address a1 = new Address("41.1511111, -8.6311111", "Rua ISEP", "4460-123", 126, "São João", 10);
        new AddressDB().addAddress(a1);
        new PharmacyDB().addPharmacy(this.pharmacy);
        Pharmacy p1 = new Pharmacy("p1@isep.pt", a1, "Pharmacy 1");
        new PharmacyDB().addPharmacy(p1);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);

        ProductInNearbyPharmaciesAlgorithm.updateProductsInDataBase(p1, this.pharmacy, this.product, 1);
        Assertions.assertEquals(1, new PharmacyProductDB().getPharmacyProduct(this.pharmacyProduct.getReference(), this.pharmacy.getEmail()).getAmount());
        Assertions.assertEquals(1, new PharmacyProductDB().getPharmacyProduct(this.pharmacyProduct.getReference(), p1.getEmail()).getAmount());

        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), p1.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(p1.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(a1.getGPSCoordinates());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Check amounts Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void checkAmounts() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm checkAmounts() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        Address a1 = new Address("41.1511111, -8.6311111", "Rua ISEP", "4460-123", 126, "São João", 10);
        new AddressDB().addAddress(a1);
        new PharmacyDB().addPharmacy(this.pharmacy);
        Pharmacy p1 = new Pharmacy("p1@isep.pt", a1, "Pharmacy 1");
        new PharmacyDB().addPharmacy(p1);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);
        PharmacyProduct pp = new PharmacyProduct(this.product.getReference(), p1.getEmail(), 5);
        new PharmacyProductDB().addPharmacyProduct(pp);

        Map<Pharmacy, Double> pharmaciesNearbyWithProducts = new LinkedHashMap<>();
        pharmaciesNearbyWithProducts.put(this.pharmacy, 100.0);
        pharmaciesNearbyWithProducts.put(p1, 100.0);

        Map<Pharmacy, Integer> mapExp = new LinkedHashMap<>();
        mapExp.put(this.pharmacy, 2);
        mapExp.put(p1, 3);

        Assertions.assertEquals(mapExp.toString(), ProductInNearbyPharmaciesAlgorithm.checkAmounts(pharmaciesNearbyWithProducts, this.product, 5).toString());

        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), p1.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(p1.getEmail());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(a1.getGPSCoordinates());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets all pharmacies Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getAllPharmacies() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getAllPharmacies() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        int size = new PharmacyDB().getAllPharmacies().size();
        new PharmacyDB().addPharmacy(this.pharmacy);
        Assertions.assertEquals(size + 1, ProductInNearbyPharmaciesAlgorithm.getAllPharmacies().size());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Is pharmacy coordinates Test.
     */
    @Test
    void isPharmacyCoordinates() {
        System.out.println("ProductInNearbyPharmaciesAlgorithm isPharmacyCoordinates() Test");
        Pharmacy p1 = new Pharmacy("p1@isep.pt", new Address("123432.001,123432.001", "Rua ISEP", "4460-123", 126, "São João", 35), "Pharmacy 1");
        Pharmacy p2 = new Pharmacy("p2@isep.pt", new Address("123432.002,123432.002", "Rua ISEP", "4460-123", 126, "São João", 36), "Pharmacy 2");
        List<Pharmacy> listPharmacy = new ArrayList<>();
        listPharmacy.add(p1);
        listPharmacy.add(p2);
        Assertions.assertNull(ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(listPharmacy, "123432.003,123432.003"));
        Assertions.assertEquals(p1, ProductInNearbyPharmaciesAlgorithm.isPharmacyCoordinates(listPharmacy, "123432.001,123432.001"));
    }

    /**
     * Gets all scooters Test.
     */
    @Test
    void getAllScooters() {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getAllScooters() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).size();
        Scooter scooter = new Scooter("100", "100", "100", "100");
        new ScooterDB().addScooter(scooter, this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, ProductInNearbyPharmaciesAlgorithm.getAllScooters(this.pharmacy).size());
        new ScooterDB().removeScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets all drones Test.
     */
    @Test
    void getAllDrones() {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getAllDrones() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        int size = new DroneDB().getAllDrones(this.pharmacy.getEmail()).size();
        Drone drone = new Drone("100", "100", "100", "100");
        new DroneDB().addDrone(drone, this.pharmacy.getEmail());
        Assertions.assertEquals(size + 1, ProductInNearbyPharmaciesAlgorithm.getAllDrones(this.pharmacy).size());
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets best scooter Test.
     */
    @Test
    void getBestScooter() {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getBestScooter() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        Scooter scooter = new Scooter("100", "100", "100", "20");
        new ScooterDB().addScooter(scooter, this.pharmacy.getEmail());
        Scooter s = new ScooterDB().getAllScooters(this.pharmacy.getEmail()).get(0);
        scooter.setID(s.getId());
        Assertions.assertEquals(scooter.toString(), ProductInNearbyPharmaciesAlgorithm.getBestScooter(new ScooterDB().getAllScooters(this.pharmacy.getEmail())).toString());
        new ScooterDB().removeScooter(s.getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets best drone Test.
     */
    @Test
    void getBestDrone() {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getBestDrone() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        Drone drone = new Drone("100", "100", "100", "5");
        new DroneDB().addDrone(drone, this.pharmacy.getEmail());
        Assertions.assertEquals(4.750558405047522, ProductInNearbyPharmaciesAlgorithm.getBestDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()), this.product));
        new DroneDB().removeDrone(new DroneDB().getAllDrones(this.pharmacy.getEmail()).get(0).getId());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Check nearby products Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void checkNearbyProducts() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm checkNearbyProducts() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);

        Map<Pharmacy, Double> pharmaciesNearbyWithProducts = new LinkedHashMap<>();
        pharmaciesNearbyWithProducts.put(this.pharmacy, 100.0);
        Assertions.assertEquals(pharmaciesNearbyWithProducts.toString(), ProductInNearbyPharmaciesAlgorithm.checkNearbyProducts(pharmaciesNearbyWithProducts, this.product).toString());

        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Check products Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void checkProducts() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm checkProducts() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);
        Assertions.assertTrue(ProductInNearbyPharmaciesAlgorithm.checkProducts(this.pharmacy, this.product));

        this.pharmacyProduct.setAmount("0");
        new PharmacyProductDB().updatePharmacyProduct(this.pharmacyProduct);
        Assertions.assertFalse(ProductInNearbyPharmaciesAlgorithm.checkProducts(this.pharmacy, this.product));

        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }

    /**
     * Gets all products Test.
     *
     * @throws SQLException the sql exception
     */
    @Test
    void getAllProducts() throws SQLException {
        System.out.println("ProductInNearbyPharmaciesAlgorithm getAllProducts() Test");
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
        new AddressDB().addAddress(this.address);
        new PharmacyDB().addPharmacy(this.pharmacy);
        new ProductDB().addProduct(this.product);
        new PharmacyProductDB().addPharmacyProduct(this.pharmacyProduct);

        Assertions.assertEquals(this.pharmacyProduct.toString(), Objects.requireNonNull(ProductInNearbyPharmaciesAlgorithm.getPharmacyProduct(this.pharmacy, this.product)).toString());

        Product p = new Product("9999", "Name30000", "Desc3000000", "1.10", "1.10");
        Assertions.assertNull(ProductInNearbyPharmaciesAlgorithm.getPharmacyProduct(this.pharmacy, p));

        new PharmacyProductDB().removePharmacyProduct(this.product.getReference(), this.pharmacy.getEmail());
        new ProductDB().removeProduct(this.product.getReference());
        new PharmacyDB().removePharmacy(this.pharmacy.getEmail());
        new AddressDB().removeAddress(this.address.getGPSCoordinates());
    }
}