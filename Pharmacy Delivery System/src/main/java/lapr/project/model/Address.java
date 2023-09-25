package lapr.project.model;

import lapr.project.data.AddressDB;
import lapr.project.graphbase.GraphAlgorithms;

/**
 * The type Address.
 */
public class Address {

    /**
     * The gps coordinates.
     */
    private String gpsCoordinates;

    /**
     * The street.
     */
    private String street;

    /**
     * The postal code.
     */
    private String postalCode;

    /**
     * The door number.
     */
    private int doorNumber;

    /**
     * The locality.
     */
    private String locality;

    /**
     * The elevation.
     */
    private int elevation;

    /**
     * Instantiates a new Address.
     *
     * @param gpsCoordinates the gps coordinates
     * @param street         the street
     * @param postalCode     the postal code
     * @param doorNumber     the door number
     * @param locality       the locality
     * @param elevation      the elevation
     */
    public Address(String gpsCoordinates, String street, String postalCode, String doorNumber, String locality, String elevation) {
        this.setGpsCoordinates(gpsCoordinates);
        this.street = street;
        this.setPostalCode(postalCode);
        this.setDoorNumber(doorNumber);
        this.locality = locality;
        this.setElevation(elevation);
    }

    /**
     * Instantiates a new Address.
     *
     * @param gpsCoordinates the gps coordinates
     * @param street         the street
     * @param postalCode     the postal code
     * @param doorNumber     the door number
     * @param locality       the locality
     * @param elevation      the elevation
     */
    public Address(String gpsCoordinates, String street, String postalCode, int doorNumber, String locality, int elevation) {
        this.gpsCoordinates = gpsCoordinates;
        this.street = street;
        this.postalCode = postalCode;
        this.doorNumber = doorNumber;
        this.locality = locality;
        this.elevation = elevation;
    }

    /**
     * Instantiates a new Address.
     */
    public Address() {

    }

    /**
     * Gets address.
     *
     * @param gpsCoordinates the gps coordinates
     * @return the address
     */
    public static Address getAddress(String gpsCoordinates) {
        return new AddressDB().getAddress(gpsCoordinates);
    }

    /**
     * Save.
     */
    public void save() {
        try {
            getAddress(this.getGPSCoordinates());
            throw new IllegalStateException();
        } catch (IllegalArgumentException ie) {
            //Of the record does not exist, save it
            new AddressDB().addAddress(this);
        } catch (IllegalStateException is) {
            throw new IllegalArgumentException("\nThe specified GPS Coordinates are already associated to one Pharmacy/Client!");
        }
    }

    /**
     * Save2.
     */
    public void save2() {
        try {
            getAddress(this.getGPSCoordinates());
        } catch (IllegalArgumentException ie) {
            new AddressDB().addAddress(this);
        }
    }

    /**
     * Gets door number.
     *
     * @return the door number
     */
    public int getDoorNumber() {
        return doorNumber;
    }

    /**
     * Sets door number.
     *
     * @param doorNumber the door number
     */
    public void setDoorNumber(String doorNumber) {
        String message = "Invalid Door Number!";
        if (doorNumber != null && !doorNumber.trim().isEmpty()) {
            try {
                if (Integer.parseInt(doorNumber) > 0) {
                    this.doorNumber = Integer.parseInt(doorNumber);
                } else {
                    throw new IllegalArgumentException("The Door Number should be 1 or higher!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sets gps coordinates.
     *
     * @param gpsCoordinates the gps coordinates
     */
    public void setGpsCoordinates(String gpsCoordinates) {
        String message = "Invalid GPS Coordinates!";
        if (gpsCoordinates != null && !gpsCoordinates.trim().isEmpty() && gpsCoordinates.contains(",")) {
            try {
                String[] c = GraphAlgorithms.splitCoordinates(gpsCoordinates);
                Double.parseDouble(c[0]);
                Double.parseDouble(c[1]);
                this.gpsCoordinates = gpsCoordinates;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        String message = "Invalid Postal Code!";
        if (postalCode != null && !postalCode.trim().isEmpty() && postalCode.contains("-")) {
            try {
                String[] c = postalCode.split("-");
                Integer.parseInt(c[0]);
                Integer.parseInt(c[1]);
                this.postalCode = postalCode;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sets elevation.
     *
     * @param elevation the elevation
     */
    public void setElevation(String elevation) {
        String message = "Invalid Elevation!";
        if (elevation != null && !elevation.trim().isEmpty()) {
            try {
                this.elevation = Integer.parseInt(elevation);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Gets locality.
     *
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Gets gps coordinates.
     *
     * @return the gps coordinates
     */
    public String getGPSCoordinates() {
        return gpsCoordinates;
    }

    /**
     * Gets elevation.
     *
     * @return the elevation
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Textual Description of Address.
     *
     * @return Textual Description
     */
    @Override
    public String toString() {
        return "Address{" +
                "GPSCoordinates='" + gpsCoordinates + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", doorNumber=" + doorNumber +
                ", locality='" + locality + '\'' +
                '}';
    }
}
