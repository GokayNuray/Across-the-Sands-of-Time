package com.halenteck.commonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInFrame extends JFrame {

    protected static final int FRAME_WIDTH = 800;
    protected static final int FRAME_HEIGHT = 500;
    protected static String userName;

    public LogInFrame() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Across the Sands of Time");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Login Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));

        // Username Panel
        JPanel usernamePanel = new JPanel(new GridLayout(2, 0));
        JLabel usernameLabel = new JLabel("username", SwingConstants.LEFT);
        usernameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        centerPanel.add(usernamePanel);

        JTextArea usernameInfo = new JTextArea("username (6-12 digits) can only contain letters, numbers and '_' character.");
        usernameInfo.setLineWrap(true); // Enable line wrapping
        usernameInfo.setWrapStyleWord(true); // Wrap text at word boundaries (optional)
        usernameInfo.setEditable(false);
        centerPanel.add(usernameInfo);

        // Password Panel
        JPanel passwordPanel = new JPanel(new GridLayout(2, 0));
        JLabel passwordLabel = new JLabel("password", SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        centerPanel.add(passwordPanel);

        JTextArea passwordInfo = new JTextArea("password (8-16 digits) must contain 1 uppercase, 1 lowercase, " +
                "1 numerical and 1 special-case (*,?,!,_,-) character.");
        passwordInfo.setLineWrap(true); // Enable line wrapping
        passwordInfo.setWrapStyleWord(true);
        passwordInfo.setEditable(false);
        centerPanel.add(passwordInfo);

        centerPanel.add(new JPanel()); // empty panel

        // Registration Button
        JPanel registerPanel = new JPanel(new GridLayout(1, 2));
        JLabel registerLabel = new JLabel("First Time Here?", SwingConstants.LEFT);
        JButton registerButton = new JButton("Register");
        registerPanel.add(registerLabel);
        registerPanel.add(registerButton);
        centerPanel.add(registerPanel);

        centerPanel.add(new JPanel()); //empty panel

        // LogIn Panel
        JPanel loginPanel = new JPanel(new GridLayout(1, 2));
        JLabel loginLabel = new JLabel("Already Have an Account?", SwingConstants.LEFT);
        JButton loginButton = new JButton("Log In");
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);
        centerPanel.add(loginPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Image Panel
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/logo.jpg"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled image
        JLabel imageLabel = new JLabel(scaledImageIcon);
        add(imageLabel, BorderLayout.WEST);


        // Welcome Panel
        JPanel northPanel = new JPanel(new GridLayout(2, 0));

        JLabel welcomeLabel = new JLabel("Welcome to", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        northPanel.add(new JPanel()); // empty panel
        northPanel.add(welcomeLabel);
        northPanel.add(new JPanel()); // empty panel
        JLabel nameLabel = new JLabel("Across the Sands of Time!", SwingConstants.RIGHT);
        nameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        northPanel.add(new JPanel()); // empty panel
        northPanel.add(nameLabel);
        add(northPanel, BorderLayout.NORTH);

        setVisible(true);

        class fieldChecker implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LogInFrame.this, "Please fill in the fields");
                } else if (!usernameChecker(usernameField.getText()) || !passwordChecker(passwordField.getText())) {
                    JOptionPane.showMessageDialog(LogInFrame.this, "Illegal username or password!");
                    return;
                } else {

                    userName = usernameField.getText();

                    if (e.getSource() == loginButton) {
                        // TODO
                        //  check if username and password are in the database
                        // if they are, open the game frame
                        GameSelectionMenu gameSelectionMenu = new GameSelectionMenu(usernameField.getText());
                        // if not, show an error message
                        // JOptionPane.showMessageDialog(LogInFrame.this, "Wrong username or password!");
                        dispose();
                    } else if (e.getSource() == registerButton) {
                        // TODO
                        //  check if username is already in the database
                        // if it is, show an error message
                        // JOptionPane.showMessageDialog(LogInFrame.this, "Username already exists!");
                        // TODO
                        //  if not, add the username and password to the database
                        // open the game frame
                        GameSelectionMenu gameSelectionMenu = new GameSelectionMenu(usernameField.getText());
                        dispose();
                    }
                }

            }


        }

        loginButton.addActionListener(new fieldChecker());
        registerButton.addActionListener(new fieldChecker());


    }

    /**
     * Checks if the username is between 6 and 12 characters,
     * contains only English letters, numbers and special-case
     * character '_'
     *
     * @param username the username to be checked
     * @return true if the username is valid, false otherwise
     */
    public boolean usernameChecker(String username) {

        if (6 <= username.length() && username.length() <= 12) {

            for (int i = 0; i < username.length(); i++) {
                if (!Character.isLetterOrDigit(username.charAt(i)) && username.charAt(i) != '_') {
                    return false;
                }
            }
            return true;
        }

        // length is not allowed
        return false;
    }

    /**
     * Checks if the password is between 8 and 16 characters,
     * one uppercase, one lowercase, one numerical and one special-case
     * (*, !, ?, _, -) character
     *
     * @param password the password to be checked
     * @return true if the password is valid, false otherwise
     */
    public boolean passwordChecker(String password) {

        if (8 <= password.length() && password.length() <= 16) {

            boolean hasUpper = false;
            boolean hasLower = false;
            boolean hasDigit = false;
            boolean hasSpecial = false;

            for (int i = 0; i < password.length(); i++) {
                if (Character.isUpperCase(password.charAt(i))) {
                    hasUpper = true;
                } else if (Character.isLowerCase(password.charAt(i))) {
                    hasLower = true;
                } else if (Character.isDigit(password.charAt(i))) {
                    hasDigit = true;
                } else if (password.charAt(i) == '*' || password.charAt(i) == '!' || password.charAt(i) == '?'
                        || password.charAt(i) == '_' || password.charAt(i) == '-') {
                    hasSpecial = true;
                }
            }

            return hasUpper && hasLower && hasDigit && hasSpecial;
        }

        // length is not allowed
        return false;
    }

}