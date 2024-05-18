package com.halenteck.commonUI;

import com.halenteck.combatUI.ShopFrame;
import com.halenteck.fpsUI.LobbyFrame;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameSelectionMenu extends JFrame {

    /**
     * Constructor for the Game Selection Menu
     */
    public GameSelectionMenu() {

        setTitle("Game Selection Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome Panel
        JPanel welcomePanel = new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        };
        welcomePanel.setLayout(new GridLayout(2, 0));
        JLabel welcomeLabel = new JLabel("       Hello, welcome " + Server.getUserData().getPlayerName() + "!", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        JLabel welcomeInfo = new JLabel("               Select a mode to join the fun", SwingConstants.LEFT);
        welcomeInfo.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        welcomePanel.add(welcomeInfo);
        add(welcomePanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton combatButton = new JButton("Enter Combat Mode");
        combatButton.setFont(new Font("Sans Serif", Font.BOLD, 40));
        combatButton.setFont(new Font("Sans Serif", Font.BOLD, 40));
        combatButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        combatButton.setBackground(new Color(198,152,116));
        combatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Combat Mode
                ShopFrame.getInstance().setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(combatButton);

        JButton fpsButton = new JButton("Join FPS Match");
        fpsButton.setFont(new Font("Sans Serif", Font.BOLD, 40));
        fpsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        fpsButton.setBackground(new Color(198,152,116));
        fpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide Combat and FPS buttons
                buttonPanel.remove(combatButton);
                buttonPanel.remove(fpsButton);

                // Create Ranked and Casual buttons
                JButton rankedButton = new JButton("Join Ranked Match");
                JButton casualButton = new JButton("Join Casual Match");
                rankedButton.setFont(new Font("Sans Serif", Font.BOLD, 40));
                casualButton.setFont(new Font("Sans Serif", Font.BOLD, 40));
                rankedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                casualButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                rankedButton.setBackground(new Color(198,152,116));
                casualButton.setBackground(new Color(198,152,116));

                // Add Ranked and Casual buttons
                buttonPanel.add(rankedButton);
                buttonPanel.add(casualButton);

                // Action Listeners for Ranked and Casual buttons
                rankedButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Open Ranked Match
                        new LobbyFrame(true);
                        dispose();
                    }
                });

                casualButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Open Casual Match
                        new LobbyFrame(false);
                        dispose();
                    }
                });

                // Revalidate and repaint the panel to reflect changes
                buttonPanel.revalidate();
                buttonPanel.repaint();
            }
        });
        buttonPanel.add(fpsButton);

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.removeAll();
                buttonPanel.add(combatButton);
                buttonPanel.add(fpsButton);
                buttonPanel.revalidate();
                buttonPanel.repaint();
            }
        };

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        add(buttonPanel, BorderLayout.CENTER);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(3, 3));
        statsPanel.setBackground(new Color(213,176,124));
        JLabel statsLabel = new JLabel("or view stats", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        statsPanel.add(statsLabel);
        statsPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });
        statsPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });

        JButton statsButton = new JButton("View Your Stats");
        statsButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        statsButton.setBackground(new Color(198,152,116));
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Stats Frame
                new UserCard(Server.getUserData());
                dispose();
            }
        });

        statsPanel.add(statsButton);

        JButton leaderboardButton = new JButton("View Leaderboard");
        leaderboardButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        leaderboardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        leaderboardButton.setBackground(new Color(198,152,116));
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Leaderboard Frame
                new LeaderboardFrame();
                dispose();
            }
        });

        statsPanel.add(leaderboardButton);
        statsPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });
        statsPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });
        statsPanel.add(new JPanel() {
            {
                setBackground(new Color(213,176,124));
            }
        });

        // Logout Panel
        JButton logOutButton = new JButton("Logout");
        logOutButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        logOutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        logOutButton.setBackground(new Color(198,152,116));
        logOutButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Login Frame
                new LogInFrame();
                dispose();
            }
        });

        statsPanel.add(logOutButton);

        add(statsPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }
}
