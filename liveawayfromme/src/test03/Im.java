package test03;

import test02.Main;

import javax.swing.*;
import java.awt.*;

public class Im extends JFrame {
    private final JTextField capField, nField, wField, valField;
    private JButton DFSButton, KDPButton, GreedyButton;
    private JTextArea outputArea;

    public Im() {

        setTitle("pack back pack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        capField = new JTextField(10);
        nField = new JTextField(10);
        wField = new JTextField(10);
        valField = new JTextField(10);

        inputPanel.add(new JLabel("Capacity:"));
        inputPanel.add(capField);
        inputPanel.add(new JLabel("Number of items:"));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Weights (comma-separated):"));
        inputPanel.add(wField);
        inputPanel.add(new JLabel("Values (comma-separated):"));
        inputPanel.add(valField);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        DFSButton = new JButton("DFS Solution");
        KDPButton = new JButton("Dynamic Programming");
        GreedyButton = new JButton("Greedy Solution");


        styleButton(DFSButton);
        styleButton(KDPButton);
        styleButton(GreedyButton);

        buttonPanel.add(DFSButton);
        buttonPanel.add(KDPButton);
        buttonPanel.add(GreedyButton);


        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));


        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);


        addButtonListeners();


        pack();
        setLocationRelativeTo(null);


        setMinimumSize(new Dimension(500, 400));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void addButtonListeners() {
        DFSButton.addActionListener(e -> processInput("DFS"));
        KDPButton.addActionListener(e -> processInput("DP"));
        GreedyButton.addActionListener(e -> processInput("Greedy"));
    }

    private void processInput(String algorithm) {
        try {
            int capacity = Integer.parseInt(capField.getText());
            int n = Integer.parseInt(nField.getText());

            String[] weightStr = wField.getText().split(",");
            String[] valueStr = valField.getText().split(",");

            int[] weights = new int[n];
            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                weights[i] = Integer.parseInt(weightStr[i].trim());
                values[i] = Integer.parseInt(valueStr[i].trim());
            }

            String result = switch (algorithm) {
                case "DFS" -> "DFS Result: " + S.knapscackDFS(weights, values, n, capacity);
                case "DP" -> "DP Result: "  + S.knapscackKDP(weights, values, capacity);
                case "Greedy" -> "Greedy Result: " +  S.GreedyKnapsack(weights, values, capacity);
                default -> "Invalid algorithm";
            };

            outputArea.setText(result);

        } catch (Exception e) {
            outputArea.setText("Error: Please check your input format.\n" +
                    "Example format:\n" +
                    "Capacity: 10\n" +
                    "Number of items: 3\n" +
                    "Weights: 2,3,4\n" +
                    "Values: 3,4,5");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Im().setVisible(true));
    }
}
