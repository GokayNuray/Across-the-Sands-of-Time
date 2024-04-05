package com.halenteck;

import javax.swing.*;
import java.awt.*;

public class LogInFrame extends JFrame {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private JPanel loginPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;

    LogInFrame() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Across the Sands of Time");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Login Panel
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Adjust spacing as needed

        // Title Label
        titleLabel = new JLabel("Welcome to", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        loginPanel.add(titleLabel);
        loginPanel.add(new JPanel()); // Spacer for title

        // Username Label & Field
        usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);
        usernameField = new JTextField(20);
        loginPanel.add(usernameField);

        // Password Label & Field
        passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        loginPanel.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginPanel.add(new JPanel()); // Spacer for button
        loginPanel.add(loginButton);

        // Add Login Panel to Frame
        add(loginPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        LogInFrame loginFrame = new LogInFrame();
    }
}