package lapr.project.model;

/**
 * The type Vehicle.
 */
public class Vehicle {
    /**
     * The Vehicle's ID.
     */
    private int id;
    /**
     * The Vehicle's Capacity (KG).
     */
    private double capacity;
    /**
     * The Vehicle's Power (W).
     */
    private double power;
    /**
     * The Vehicle's Max Battery (W/H).
     */
    private double maxBattery;
    /**
     * The Vehicle's Battery (%).
     */
    private int battery;
    /**
     * The Vehicle's Weight (Kg).
     */
    private double weight;

    /**
     * Instantiates a new Vehicle.
     *
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param weight     the weight
     */
    public Vehicle(String capacity, String power, String maxBattery, String weight) {
        this.setCapacity(capacity);
        this.setPower(power);
        this.setMaxBattery(maxBattery);
        this.setWeight(weight);
        this.battery = 100;
    }

    /**
     * Instantiates a new Vehicle.
     *
     * @param id         the id
     * @param capacity   the capacity
     * @param power      the power
     * @param maxBattery the max battery
     * @param battery    the battery
     * @param weight     the weight
     */
    public Vehicle(int id, Double capacity, Double power, Double maxBattery, int battery, Double weight) {
        this.id = id;
        this.capacity = capacity;
        this.power = power;
        this.maxBattery = maxBattery;
        this.battery = battery;
        this.weight = weight;
    }

    /**
     * Gets ID.
     *
     * @return the ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets Capacity.
     *
     * @return the capacity
     */
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Gets Power.
     *
     * @return the power
     */
    public double getPower() {
        return this.power;
    }

    /**
     * Gets Max Battery.
     *
     * @return the max battery
     */
    public double getMaxBattery() {
        return this.maxBattery;
    }

    /**
     * Gets Battery.
     *
     * @return the battery
     */
    public int getBattery() {
        return this.battery;
    }

    /**
     * Gets Weight.
     *
     * @return the Weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Sets Capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(String capacity) {
        if (capacity != null && !capacity.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(capacity);
                if (aux > 0) {
                    this.capacity = aux;
                } else {
                    throw new IllegalArgumentException("ERROR: Capacity should be greater than 0!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("ERROR: Invalid Capacity!");
            }
        } else {
            throw new IllegalArgumentException("ERROR: Invalid Capacity!");
        }
    }

    /**
     * Sets Power.
     *
     * @param power the power
     */
    public void setPower(String power) {
        if (power != null && !power.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(power);
                if (aux > 0) {
                    this.power = aux;
                } else {
                    throw new IllegalArgumentException("ERROR: Power should be greater than 0!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("ERROR: Invalid Power!");
            }
        } else {
            throw new IllegalArgumentException("ERROR: Invalid Power!");
        }
    }

    /**
     * Sets Max Battery.
     *
     * @param maxBattery the max battery
     */
    public void setMaxBattery(String maxBattery) {
        if (maxBattery != null && !maxBattery.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(maxBattery);
                if (aux > 0) {
                    this.maxBattery = aux;
                } else {
                    throw new IllegalArgumentException("ERROR: Max Battery should be greater than 0!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("ERROR: Invalid Max Battery!");
            }
        } else {
            throw new IllegalArgumentException("ERROR: Invalid Max Battery!");
        }
    }

    /**
     * Set battery.
     *
     * @param battery the battery
     */
    public void setBattery(int battery) {
        this.battery = battery;
    }

    /**
     * Sets Weight.
     *
     * @param weight the weight
     */
    public void setWeight(String weight) {
        if (weight != null && !weight.trim().isEmpty()) {
            try {
                double aux = Double.parseDouble(weight);
                if (aux > 0) {
                    this.weight = aux;
                } else {
                    throw new IllegalArgumentException("ERROR: Weight should be greater than 0!");
                }
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("ERROR: Invalid Weight!");
            }
        } else {
            throw new IllegalArgumentException("ERROR: Invalid Weight!");
        }
    }

    /**
     * Sets ID.
     *
     * @param id vehicle's ID
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Textual Description of Vehicle.
     *
     * @return Textual Description
     */
    public String toString() {
        return String.format("ID: %d - Capacity(KG): %.2f - Power(W): %.2f - Max Battery(W/H): %.2f - Battery(%%): %d - Weight(Kg): %.2f",
                this.getId(), this.getCapacity(), this.getPower(), this.getMaxBattery(), this.getBattery(), this.getWeight());
    }

    /**
     * Textual Description of Vehicle.
     *
     * @return Textual Description
     */
    public String toString2() {
        return String.format("Capacity(KG): %.2f - Power(W): %.2f - Max Battery(W/H): %.2f - Battery(%%): %d - Weight(Kg): %.2f"
                , this.getCapacity(), this.getPower(), this.getMaxBattery(), this.getBattery(), this.getWeight());
    }
}
