import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

class SimulationPanel extends JPanel {
    private Config config;
    private int time = 0;
    private TrafficLight light;
    private Queue<Vehicle> vehicleQueue = new LinkedList<>();
    private Random rand = new Random();
    private int vehiclesPassed = 0;
    private int totalWaitTime = 0;

    public SimulationPanel(Config config) {
        this.config = config;
        this.light = new TrafficLight(config.greenDuration, config.redDuration);
        setBackground(Color.WHITE);
    }

    public void step() {
        time++;
        light.update();

        if (time > config.simulationDuration) {
            JOptionPane.showMessageDialog(this, "Simulation ended. See console for summary.");
            printStatsToConsole();
            return;
        }

        if (rand.nextInt(config.arrivalRate) == 0) {
            vehicleQueue.add(generateRandomVehicle());
        }

        if (light.isGreen() && !vehicleQueue.isEmpty()) {
            Vehicle v = vehicleQueue.poll();
            vehiclesPassed++;
            totalWaitTime += (time - v.arrivalTime);
        }

        repaint();
    }

    private Vehicle generateRandomVehicle() {
        String type = config.vehicleTypes.get(rand.nextInt(config.vehicleTypes.size()));
        return switch (type.trim()) {
            case "Truck" -> new Truck(time);
            case "Bus" -> new Bus(time);
            default -> new Car(time);
        };
    }

    public void reset() {
        time = 0;
        vehiclesPassed = 0;
        totalWaitTime = 0;
        vehicleQueue.clear();
        light.reset();
        repaint();
    }

    private void printStatsToConsole() {
        int avgWait = vehiclesPassed > 0 ? totalWaitTime / vehiclesPassed : 0;
        System.out.println("=== Simulation Summary ===");
        System.out.println("Vehicles passed: " + vehiclesPassed);
        System.out.println("Average wait time: " + avgWait + " seconds");
        System.out.println("Vehicles remaining in queue: " + vehicleQueue.size());

        try (PrintWriter out = new PrintWriter(new FileWriter("stats.txt"))) {
            out.println("Vehicles passed: " + vehiclesPassed);
            out.println("Average wait time: " + avgWait + " seconds");
            out.println("Vehicles remaining in queue: " + vehicleQueue.size());
        } catch (Exception e) {
            System.out.println("Failed to write stats file.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(light.isGreen() ? Color.GREEN : Color.RED);
        g.fillOval(700, 50, 30, 30);

        g.setColor(Color.BLACK);
        int y = 100;
        for (Vehicle v : vehicleQueue) {
            g.drawRect(100, y, 50, 30);
            g.drawString(v.getType(), 110, y + 20);
            y += 40;
        }

        g.drawString("Time: " + time + "s", 600, 100);
        g.drawString("Passed: " + vehiclesPassed, 600, 120);
        int avg = vehiclesPassed > 0 ? totalWaitTime / vehiclesPassed : 0;
        g.drawString("Avg Wait: " + avg + "s", 600, 140);
    }
}
