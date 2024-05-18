package com.halenteck.commonUI;

import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInFrame extends JFrame {
    /**
     * Constructor for the LogInFrame class
     */
    public LogInFrame() {
        setTitle("Across the Sands of Time");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(198,152,116));

        // Create Login Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(213, 176, 124));
        centerPanel.setLayout(new GridLayout(4, 2));

        // Username Panel
        JPanel usernamePanel = new JPanel(new GridLayout(2, 0));
        usernamePanel.setBackground(new Color(213, 176, 124));
        JLabel usernameLabel = new JLabel("username", SwingConstants.LEFT);
        usernameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        centerPanel.add(usernamePanel);

        JTextArea usernameInfo = new JTextArea("Username (6-12 digits) can only contain letters, numbers and '_' character.");
        usernameInfo.setColumns(70);
        usernameInfo.setSize(usernameInfo.getPreferredSize().width, 1);
        usernameInfo.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        usernameInfo.setLineWrap(true);
        usernameInfo.setWrapStyleWord(true);
        usernameInfo.setEditable(false);
        centerPanel.add(usernameInfo);

        // Password Panel
        JPanel passwordPanel = new JPanel(new GridLayout(2, 0));
        passwordPanel.setBackground(new Color(213, 176, 124));
        JLabel passwordLabel = new JLabel("password", SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        centerPanel.add(passwordPanel);

        JTextArea passwordInfo = new JTextArea("Password (8-16 digits) must contain 1 uppercase, 1 lowercase, " +
                "1 numerical and 1 special-case (*,?,!,_,-) character.");
        passwordInfo.setColumns(70);
        passwordInfo.setSize(passwordInfo.getPreferredSize().width, 1);
        passwordInfo.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        passwordInfo.setLineWrap(true);
        passwordInfo.setWrapStyleWord(true);
        passwordInfo.setEditable(false);
        centerPanel.add(passwordInfo);
        centerPanel.add(emptyPanel); // empty panel
        centerPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });

        // Registration Button
        JPanel registerPanel = new JPanel(new GridLayout(1, 2));
        registerPanel.setBackground(new Color(213, 176, 124));
        JLabel registerLabel = new JLabel("First Time Here?", SwingConstants.LEFT);
        registerLabel.setFont(new Font("Sans Serif", Font.ITALIC, 18));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        registerButton.setBackground(new Color(198, 152, 116));
        registerPanel.add(registerLabel);
        registerPanel.add(registerButton);
        centerPanel.add(registerPanel);

        centerPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124)); // Set to blue color
            }
        });
        // LogIn Panel
        JPanel loginPanel = new JPanel(new GridLayout(1, 2));
        loginPanel.setBackground(new Color(213, 176, 124));
        JLabel loginLabel = new JLabel("Already Have an Account?", SwingConstants.LEFT);
        loginLabel.setFont(new Font("Sans Serif", Font.ITALIC, 18));
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        loginButton.setBackground(new Color(198, 152, 116));
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);
        centerPanel.add(loginPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(213, 176, 124));
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/logo.jpg"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled image
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBackground(new Color(213,176,124));
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.WEST);


        // Welcome Panel
        JPanel northPanel = new JPanel(new GridLayout(2, 3));
        northPanel.setBackground(new Color(198,152,116));
        northPanel.add(emptyPanel);
        JLabel welcomeLabel = new JLabel("Welcome to", SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));
        northPanel.add(welcomeLabel);
        northPanel.add(emptyPanel); // empty panel
        northPanel.add(emptyPanel); // empty panel
        northPanel.add(emptyPanel); // empty panel
        JLabel nameLabel = new JLabel("Across the Sands of Time!", SwingConstants.RIGHT);
        nameLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));
        northPanel.add(emptyPanel); // empty panel
        northPanel.add(nameLabel);
        add(northPanel, BorderLayout.NORTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        /**
         * ActionListener for the login and register buttons
         */
        class fieldChecker implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LogInFrame.this, "Please fill in the fields");
                } else if (!usernameChecker(username) || !passwordChecker(password)) {
                    JOptionPane.showMessageDialog(LogInFrame.this, "Illegal username or password!");
                } else {

                    if (e.getSource() == loginButton) {
                        if (Server.login(username, password)) {
                            new GameSelectionMenu();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(LogInFrame.this, "Wrong username or password!");
                        }
                    } else if (e.getSource() == registerButton) {
                        if (Server.register(username, password)) {
                            String firstMessage = "You take on" +
                                    "a time-travel journey, starting with one character and 3 enemies + 1 boss level" +
                                    "to defeat and items, XP as well as currency to collect along the way! " +
                                    "Once you've collected all items, you will unlock the next character and be able" +
                                    "to upgrade it at the shop! You will be teleported to designated maps for all 5 characters" +
                                    "to fight in a turn-based combat one by one. Don't forget to equip your favourite" +
                                    "character to use in the FPS game! (All controls are button based for the combat mode!) " +
                                    "Good luck!";
                            JTextArea firstTextArea = new JTextArea(firstMessage);
                            firstTextArea.setColumns(70);
                            firstTextArea.setLineWrap(true);
                            firstTextArea.setWrapStyleWord(true);
                            firstTextArea.setEditable(false);
                            firstTextArea.setSize(firstTextArea.getPreferredSize().width, 1);
                            JOptionPane.showMessageDialog(LogInFrame.this, firstTextArea, "Registration successful!", JOptionPane.INFORMATION_MESSAGE);
                            String secondMessage = "A voxelated FPS game awaits you where" +
                                    "all you need to do is help your team get the most kills at the end of the game! Join with your" +
                                    "character equipped and don't forget to purchase its special ability because you may (will) need it." +
                                    "(WASD for movement, mouse for turning and shooting, R for reloading and X for special ability)" +
                                    "You will climb the leaderboard ranks from ranked games and can chill in the casual games!";
                            JTextArea secondTextArea = new JTextArea(secondMessage);
                            secondTextArea.setColumns(70);
                            secondTextArea.setLineWrap(true);
                            secondTextArea.setWrapStyleWord(true);
                            secondTextArea.setEditable(false);
                            secondTextArea.setSize(secondTextArea.getPreferredSize().width, 1);
                            JOptionPane.showMessageDialog(LogInFrame.this, secondTextArea, "Registration successful!", JOptionPane.INFORMATION_MESSAGE);
                            new GameSelectionMenu();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(LogInFrame.this, "Username already exists!");
                        }
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
                } else { // password has illegal characters
                    return false;
                }
            }

            return hasUpper && hasLower && hasDigit && hasSpecial;
        }

        // length is not allowed
        return false;
    }


}