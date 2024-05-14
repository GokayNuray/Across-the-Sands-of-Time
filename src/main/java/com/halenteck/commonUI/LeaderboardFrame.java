package com.halenteck.commonUI;

import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LeaderboardFrame extends JFrame {

    LeaderboardFrame() {
        setTitle("Global Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();

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

        JLabel leaderboardLabel = new JLabel("Global Leaderboard", SwingConstants.LEFT);
        leaderboardLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        add(leaderboardLabel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new GridLayout(1, 3));
        titlePanel.add(new JPanel());
        JLabel levelLabel = new JLabel("Level", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        titlePanel.add(levelLabel);
        JLabel pointsLabel = new JLabel("Points", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        titlePanel.add(pointsLabel);
        leaderboardPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Change layout manager to BoxLayout
        Object[] leaderboardInfo = Server.getLeaderboard();
        String[] userNames = (String[]) leaderboardInfo[0];
        byte[] userLevels = (byte[]) leaderboardInfo[1];
        int[] userPoints = (int[]) leaderboardInfo[2];

        for (int i = 0; i < userNames.length; i++) {
            JPanel userPanel = new JPanel(new GridLayout(1, 4));
            userPanel.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) (bounds.getHeight() / 10)));
            JPanel userNamePanel = new JPanel(new BorderLayout());
            userNamePanel.add(new JLabel(String.valueOf((i + 1)), SwingConstants.CENTER), BorderLayout.WEST);
            userNamePanel.add(new JLabel(userNames[i], SwingConstants.CENTER), BorderLayout.CENTER);
            userPanel.add(userNamePanel);
            userPanel.add(new JLabel("" + userLevels[i], SwingConstants.CENTER));
            userPanel.add(new JLabel("" + userPoints[i], SwingConstants.CENTER));
            JButton viewProfileButton = new JButton("View Profile");
            int finalI = i;
            viewProfileButton.addActionListener(e -> {
                new UserCard(Server.getUserData(userNames[finalI]));
                dispose();
            });
            userPanel.add(viewProfileButton);
            infoPanel.add(userPanel);
        }
        leaderboardPanel.add(infoPanel, BorderLayout.CENTER);
        // scroller
        JScrollPane scroller = new JScrollPane(leaderboardPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroller, BorderLayout.CENTER);
        pack();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }

}
