import java.io.*;
import java.util.*;

class Config {
    public int numLanes = 2;
    public int arrivalRate = 3;
    public int greenDuration = 10;
    public int redDuration = 7;
    public int simulationDuration = 60;
    public List<String> vehicleTypes = Arrays.asList("Car", "Truck", "Bus");

    public static Config loadFromFile(String filename) throws IOException {
        Config config = new Config();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("=");
            if (parts.length < 2) continue;
            switch (parts[0]) {
                case "numLanes" -> config.numLanes = Integer.parseInt(parts[1]);
                case "arrivalRate" -> config.arrivalRate = Integer.parseInt(parts[1]);
                case "lightGreenDuration" -> config.greenDuration = Integer.parseInt(parts[1]);
                case "lightRedDuration" -> config.redDuration = Integer.parseInt(parts[1]);
                case "simulationDuration" -> config.simulationDuration = Integer.parseInt(parts[1]);
                case "vehicleTypes" -> config.vehicleTypes = Arrays.asList(parts[1].split(","));
            }
        }
        return config;
    }
}
