package lapr.project.data;

import lapr.project.model.Drone;
import lapr.project.model.Scooter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The type Battery increase.
 */
public class BatteryIncrease {

    /**
     * Instantiates a new Battery increase.
     *
     * @throws IOException the io exception
     */
    public BatteryIncrease() throws IOException {
        Timer timer;
        timer = new Timer();
        timer.scheduleAtFixedRate(new Charge(), 0, 1000);
    }

    /**
     * The type Charge.
     */
    static class Charge extends TimerTask {
        /**
         * The Paths.
         */
        private final List<String> paths = new ArrayList<>();
        /**
         * The Fr.
         */
        FileReader fr = new FileReader();
        /**
         * The Timer.
         */
        Timer timer;
        /**
         * The Eh.
         */
        EmailHandler eh = new EmailHandler();

        /**
         * Instantiates a new Charge.
         *
         * @throws IOException the io exception
         */
        Charge() throws IOException {
            run();
        }

        /**
         * Run.
         */
        public void run() {
            String fileName;
            File folder = new File("src/main/resources/C_Assembly");
            File[] listOfFiles = folder.listFiles();

            assert listOfFiles != null;
            for (File file : listOfFiles) {
                if (!paths.contains(file.getName())) {
                    fileName = file.getName();
                    String flagFile = (fileName + ".flag");
                    File fileFlag = null;
                    boolean vf = false;
                    for (File flag : listOfFiles) {
                        if (flag.getName().equals(flagFile)) {
                            vf = true;
                            fileFlag = flag;
                        }
                    }

                    String[] bars = fileName.split("\\\\");
                    String ficheiro = bars[bars.length - 1];
                    String[] data = ficheiro.split("_");
                    String[] dots = ficheiro.split("\\.");
                    if (data[0].equals("estimate") && dots[dots.length - 1].equals("data") && vf) {
                        timer = new Timer();
                        double period;
                        Scanner sc = null;
                        try {
                            sc = new Scanner(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        assert sc != null;
                        String id = sc.nextLine();
                        String designation = sc.nextLine();
                        if (designation.equalsIgnoreCase("scooter")) {
                            Scooter scooter = new ScooterDB().getScooter(Integer.parseInt(id));
                            try {
                                period = fr.getTimeInMilisec(file) / (100 - scooter.getBattery());
                            } catch (FileNotFoundException e) {
                                break;
                            }
                            timer.scheduleAtFixedRate(new ChargeBattery(scooter, timer), 0, (long) period);
                            int year = Integer.parseInt(sc.nextLine());
                            int month = Integer.parseInt(sc.nextLine());
                            int day = Integer.parseInt(sc.nextLine());
                            int hour = Integer.parseInt(sc.nextLine());
                            int minute = Integer.parseInt(sc.nextLine());
                            int second = Integer.parseInt(sc.nextLine());
                            sc.close();
                            if (file.delete() && fileFlag.delete()) {
                                System.out.println("Files %s and %s removed!" + fileName + fileFlag.getName());
                            }
                            String emailCourier = new DeliveryByScooterDB().getLastDeliveryByScooterId(scooter.getId());
                            paths.add(file.getName());
                            eh.sendEmail(emailCourier, "Scooter Parked", String.format("Hello,\nThe Scooter with the ID:%d will be fully charged in about " +
                                    "%d hour(s),%d minute(s),%d second(s) on day: %d, month: %d, year: %d", scooter.getId(), hour, minute, second, day, month, year));
                        } else if (designation.equalsIgnoreCase("drone")) {
                            Drone drone = new DroneDB().getDrone(Integer.parseInt(id));
                            sc.close();
                            if (file.delete() && fileFlag.delete()) {
                                //does nothing
                            }
                            try {
                                period = fr.getTimeInMilisec(file) / (100 - drone.getBattery());
                            } catch (FileNotFoundException e) {
                                break;
                            }
                            timer.scheduleAtFixedRate(new ChargeBattery(drone, timer), 0, (long) period);
                        } else {
                            sc.close();
                            if (file.delete() && fileFlag.delete()) {
                                //does nothing
                            }
                            throw new IllegalArgumentException("Invalid Desigantion! Try: \"Scooter\" or \"Drone\"!");
                        }
                    }
                } else throw new IllegalArgumentException("This File was alreaady read!");
            }
        }

        /**
         * The type Charge battery.
         */
        static class ChargeBattery extends TimerTask {
            /**
             * The Scooter.
             */
            private final Scooter scooter;
            /**
             * The Drone.
             */
            private final Drone drone;
            /**
             * The Timer.
             */
            private final Timer timer;

            /**
             * Instantiates a new Charge battery.
             *
             * @param scooter the scooter
             * @param timer   the timer
             */
            public ChargeBattery(Scooter scooter, Timer timer) {
                this.scooter = scooter;
                this.drone = null;
                this.timer = timer;
            }

            /**
             * Instantiates a new Charge battery.
             *
             * @param drone the drone
             * @param timer the timer
             */
            public ChargeBattery(Drone drone, Timer timer) {
                this.scooter = null;
                this.drone = drone;
                this.timer = timer;
            }

            /**
             * Run.
             */
            @Override
            public void run() {
                if (this.scooter != null) {
                    int newBat = this.scooter.getBattery() + 1;
                    if (newBat <= 100) {
                        this.scooter.setBattery(newBat);
                        new ScooterDB().updateScooterBattery(newBat, this.scooter.getId());
                    } else timer.cancel();
                } else if (this.drone != null) {
                    int newBat = this.drone.getBattery() + 1;
                    if (newBat <= 100) {
                        this.drone.setBattery(newBat);
                        new DroneDB().updateDrone2(drone);
                    } else timer.cancel();
                }
            }
        }
    }
}



