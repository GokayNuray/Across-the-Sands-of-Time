package com.halenteck.commonUI;

import com.halenteck.server.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserCard extends JFrame {


    UserCard(UserData userData) {

        setTitle("User Card");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // shortcut for returning to game selection menu
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    new GameSelectionMenu();
                    dispose();
                }
            }
        });

        // User Card Panel
        JPanel userCardPanel = new JPanel(new GridLayout(6, 1));

        JLabel userCardLabel = new JLabel("You're viewing stats for: " + userData.getPlayerName(), SwingConstants.LEFT);
        userCardLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        userCardPanel.add(userCardLabel);

        // data retrieved to show level and xp
        // write the level number inside a circle

        JLabel levelLabel = new JLabel("Level: " + userData.getLevel(), SwingConstants.LEFT);
        levelLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        userCardPanel.add(levelLabel);
        // level bar panel
        JPanel levelPanel = new JPanel(new GridLayout(1, 2));
        JProgressBar levelBar = new JProgressBar();
        levelBar.setValue(userData.getXp()); // current xp / level up xp * 100
        levelBar.setStringPainted(true);

        JLabel xpLabel = new JLabel("XP: " + userData.getXp(), SwingConstants.CENTER);
        xpLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        levelPanel.add(levelBar);
        levelPanel.add(xpLabel);
        userCardPanel.add(levelPanel);

        // data retrieved to show character number
        JPanel characterPanel = new JPanel(new GridLayout(1, 2));
        JLabel characterLabel = new JLabel("Unlocked Characters: " + userData.getUnlockedCharacterCount(), SwingConstants.LEFT);
        // getCharacterCount() to get the number of characters unlocked
        characterLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        characterPanel.add(characterLabel);
        JButton characterButton = new JButton("View Character Collection");
        characterButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        characterButton.addActionListener(e -> {
            new CharacterCollection(userData);
            dispose();
        });
        characterPanel.add(characterButton);
        userCardPanel.add(characterPanel);

        // Ranked Info
        JLabel rankedLabel = new JLabel("Ranked Points: " + userData.getRankPoints(), SwingConstants.LEFT);
        // getRankedPoints() to get the number of ranked points
        rankedLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        userCardPanel.add(rankedLabel);
        JLabel leaderboardLabel = new JLabel("Global Rank: " + userData.getGlobalRank(), SwingConstants.LEFT);
        leaderboardLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        // getGlobalRank() to get the global rank
        userCardPanel.add(leaderboardLabel);


        add(userCardPanel, BorderLayout.CENTER);


        // Return Panel
        JPanel returnPanel = new JPanel(new GridLayout(1, 2));
        JButton returnButton = new JButton("Return to Game Selection Menu");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        String finalUserName = userData.getPlayerName();
        returnButton.addActionListener(e -> {
            new GameSelectionMenu();
            dispose();
        });
        returnPanel.add(new JPanel());
        returnPanel.add(returnButton);

        add(returnPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);


    }
}
