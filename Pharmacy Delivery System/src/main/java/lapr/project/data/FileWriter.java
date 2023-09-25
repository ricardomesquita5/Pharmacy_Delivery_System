package lapr.project.data;

import lapr.project.model.Park;
import lapr.project.model.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The type File writer.
 */
public class FileWriter {
    // This Class is not used because there is no way we could test it.
    // On the other hand we agreed on the creation of this class just for study purposes.

    /**
     * Instantiates a new File writer.
     *
     * @param vehicle the vehicle
     * @param park    the park
     * @throws FileNotFoundException the file not found exception
     */
    public FileWriter(Vehicle vehicle, Park park) throws FileNotFoundException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String strDate = sdf.format(cal.getTime());

        File flagfile = new File("lock_" + strDate + ".data.flag");

        File datafile = new File("lock_" + strDate + ".data");

        PrintWriter pw = new PrintWriter(datafile);

        pw.println("ID:");
        pw.println(vehicle.getId());
        pw.println("Designation:");
        pw.println(park.getDesignation());
        pw.println("Current Battery:");
        pw.println(vehicle.getBattery());
        pw.println("Battery power:");
        pw.println(vehicle.getMaxBattery());
        pw.println("Park maximum power:");
        pw.println(park.getPowerCapacity());
        pw.println("Number of vehicles charging:");
        pw.println(park.getChargingScooters());

        pw.close();
    }
}