// TrafficFlowSimulation.java

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TrafficFlowSimulation extends JFrame {
    private SimulationPanel simPanel;
    private JButton startBtn, pauseBtn, resetBtn;
    private Timer timer;
    private Config config;

    public TrafficFlowSimulation() {
        super("Traffic Flow Simulation");

        try {
            config = Config.loadFromFile("config.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load config file. Using default values.");
            config = new Config();
        }

        simPanel = new SimulationPanel(config);
        add(simPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        startBtn = new JButton("Start");
        pauseBtn = new JButton("Pause");
        resetBtn = new JButton("Reset");
        controlPanel.add(startBtn);
        controlPanel.add(pauseBtn);
        controlPanel.add(resetBtn);
        add(controlPanel, BorderLayout.SOUTH);

        startBtn.addActionListener(e -> timer.start());
        pauseBtn.addActionListener(e -> timer.stop());
        resetBtn.addActionListener(e -> {
            timer.stop();
            simPanel.reset();
        });

        timer = new Timer(1000, e -> simPanel.step());

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrafficFlowSimulation::new);
    }
}
