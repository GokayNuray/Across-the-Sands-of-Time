package com.halenteck.commonUI;

import com.halenteck.combatUI.ShopFrame;
import com.halenteck.combatUI.UpgradeShopFrame;
import com.halenteck.fpsUI.LobbyFrame;
import com.halenteck.server.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectionMenu extends JFrame {

    public GameSelectionMenu() {

        setTitle("Game Selection Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(2, 0));
        JLabel welcomeLabel = new JLabel("       Hello, welcome " + Server.getUserData().getPlayerName() + "!", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        JLabel welcomeInfo = new JLabel("               Select a mode to join the fun", SwingConstants.LEFT);
        welcomeInfo.setFont(new Font("Sans Serif", Font.ITALIC,20 ));
        welcomePanel.add(welcomeInfo);
        add(welcomePanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JButton combatButton = new JButton("Enter Combat Mode");
        combatButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
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
        fpsButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        fpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide Combat and FPS buttons
                buttonPanel.remove(combatButton);
                buttonPanel.remove(fpsButton);

                // Create Ranked and Casual buttons
                JButton rankedButton = new JButton("Join Ranked Match");
                JButton casualButton = new JButton("Join Casual Match");
                rankedButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
                casualButton.setFont(new Font("Sans Serif", Font.BOLD, 20));

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
        add(buttonPanel, BorderLayout.CENTER);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(3,3));
        JLabel statsLabel = new JLabel("or view stats", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        statsPanel.add(statsLabel);
        statsPanel.add(new JPanel());
        statsPanel.add(new JPanel());

        JButton statsButton = new JButton("View Your Stats");
        statsButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Stats Frame
                new UserCard();
                dispose();
            }
        });

        statsPanel.add(statsButton);

        JButton leaderboardButton = new JButton("View Leaderboard");
        leaderboardButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Leaderboard Frame
                new LeaderboardFrame();
                dispose();
            }
        });

        statsPanel.add(leaderboardButton);
        statsPanel.add(new JPanel());
        statsPanel.add(new JPanel());
        statsPanel.add(new JPanel());

        // Logout Panel
        JButton logOutButton = new JButton("Logout");
        logOutButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
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
