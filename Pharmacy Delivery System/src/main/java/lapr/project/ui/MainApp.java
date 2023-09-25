package lapr.project.ui;

import lapr.project.data.BatteryIncrease;
import lapr.project.data.DataHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Main app.
 */
public class MainApp {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {

            Properties properties =
                    new Properties(System.getProperties());

            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

            new DataHandler();
            new BatteryIncrease();
            MenuUI uiMenu = new MenuUI();

            uiMenu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
