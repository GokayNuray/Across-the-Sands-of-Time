package com.halenteck.commonUI;

import javax.swing.*;
import java.awt.*;

public class LeaderboardFrame extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

    LeaderboardFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Global Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        JLabel leaderboardLabel = new JLabel("Global Leaderboard", SwingConstants.LEFT);
        leaderboardLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        add(leaderboardLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.add(new JPanel());
        JLabel levelLabel = new JLabel("Level", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        infoPanel.add(levelLabel);
        JLabel pointsLabel = new JLabel("Points", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        infoPanel.add(pointsLabel);

        JPanel leaderboardPanel = new JPanel(new GridLayout(10, 4));
        // data to be retrieved
        add(leaderboardLabel, BorderLayout.CENTER);

        // add scroller for users who cant be seen on top 10
        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

    }

}
