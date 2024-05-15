package com.halenteck.commonUI;

import com.halenteck.server.Server;
import com.halenteck.server.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserCard extends JFrame {


    UserCard(UserData userData) {

        setTitle("User Card");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // shortcut for returning to game selection menu
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                new LeaderboardFrame();
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        getContentPane().setBackground(new Color(198,152,116));

        // User Card Panel
        JPanel userCardPanel = new JPanel(new GridLayout(6, 1));
        userCardPanel.setBackground(new Color(213,176,124));

        JLabel userCardLabel = new JLabel("You're viewing stats for: " + userData.getPlayerName(), SwingConstants.CENTER);
        userCardLabel.setFont(new Font("Sans Serif", Font.BOLD, 30));
        userCardLabel.setBackground(new Color(198,152,116));
        userCardPanel.add(userCardLabel);

        // data retrieved to show level and xp
        // write the level number inside a circle

        JLabel levelLabel = new JLabel("Level: " + userData.getLevel(), SwingConstants.LEFT);
        levelLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        userCardPanel.add(levelLabel);
        // level bar panel
        JPanel levelPanel = new JPanel(new GridLayout(1, 2));
        levelPanel.setBackground(new Color(213,176,124));
        JProgressBar levelBar = new JProgressBar();
        levelBar.setBackground(new Color(213,176,124));
        levelBar.setForeground(new Color(188,106,60));
        levelBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        levelBar.setValue(userData.getXp()); // current xp / level up xp * 100
        levelBar.setStringPainted(true);

        JLabel xpLabel = new JLabel("XP: " + userData.getXp(), SwingConstants.CENTER);
        xpLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        levelPanel.add(levelBar);
        levelPanel.add(xpLabel);
        userCardPanel.add(levelPanel);

        // data retrieved to show character number
        JPanel characterPanel = new JPanel(new GridLayout(1, 2));
        characterPanel.setBackground(new Color(213,176,124));
        JLabel characterLabel = new JLabel("Unlocked Characters: " + userData.getUnlockedCharacterCount(), SwingConstants.LEFT);
        // getCharacterCount() to get the number of characters unlocked
        characterLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        characterPanel.add(characterLabel);
        JButton characterButton = new JButton("View Character Collection");
        characterButton.setFont(new Font("Sans Serif", Font.BOLD, 25));
        characterButton.setBackground(new Color(198,152,116));
        characterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        characterButton.addActionListener(e -> {
            new CharacterCollection(userData);
            dispose();
        });
        characterPanel.add(characterButton);
        userCardPanel.add(characterPanel);

        // Ranked Info
        JLabel rankedLabel = new JLabel("Ranked Points: " + userData.getRankPoints(), SwingConstants.LEFT);
        // getRankedPoints() to get the number of ranked points
        rankedLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        userCardPanel.add(rankedLabel);
        JLabel leaderboardLabel = new JLabel("Global Rank: " + userData.getGlobalRank(), SwingConstants.LEFT);
        leaderboardLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        // getGlobalRank() to get the global rank
        userCardPanel.add(leaderboardLabel);
        add(userCardPanel, BorderLayout.CENTER);

        // Return Panel
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Return to Game Selection Menu");
        returnButton.setBackground(new Color(198,152,116));
        returnButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        returnButton.addActionListener(e -> {
            new GameSelectionMenu();
            dispose();
        });
        returnPanel.setBackground(new Color(213,176,124));
        returnPanel.add(returnButton);

        add(returnPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);


    }
}
