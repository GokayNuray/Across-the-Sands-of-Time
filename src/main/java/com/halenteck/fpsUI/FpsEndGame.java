package com.halenteck.fpsUI;

import com.halenteck.commonUI.GameSelectionMenu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FpsEndGame extends JFrame {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    public FpsEndGame(boolean rankedGame) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // End Game Panel
        JPanel endGamePanel = new JPanel(new GridLayout(4,1));

        JLabel endGameLabel = new JLabel("Your team has " + "won/lost!", SwingConstants.CENTER); // Add win/loss condition
        endGameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        endGamePanel.add(endGameLabel);
        if (rankedGame) {
            JLabel rankChangeLabel = new JLabel("+ 20 WP / -15 LP", SwingConstants.CENTER);
            rankChangeLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            endGamePanel.add(rankChangeLabel);
        }
        else {
            endGamePanel.add(new JLabel(" "));
        }

        JPanel statsPanel = new JPanel(new GridLayout(2,3));
        JLabel killsLabel = new JLabel("Kills: 10", SwingConstants.CENTER); // Add kills
        killsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsPanel.add(killsLabel);
        JLabel deathsLabel = new JLabel("Deaths: 5", SwingConstants.CENTER); // Add deaths
        deathsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsPanel.add(deathsLabel);
        statsPanel.add(new JPanel());
        if (rankedGame) {
            JLabel pointsLabel = new JLabel("Overall Points: 5", SwingConstants.CENTER); // Add rank
            pointsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            statsPanel.add(pointsLabel);
            JLabel positionLabel = new JLabel("Position at LB: 10", SwingConstants.CENTER); // Add position
            positionLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            statsPanel.add(positionLabel);
        }
        else {
            statsPanel.add(new JPanel());
            statsPanel.add(new JPanel());
        }
        statsPanel.add(new JPanel());
        statsPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.RED),"Stats"));
        endGamePanel.add(statsPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.add(new JPanel());
        JButton joinMatchButton = new JButton("Join Another Match");
        joinMatchButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        joinMatchButton.addActionListener(e -> {
            new GameSelectionMenu();
            dispose();
        });
        buttonPanel.add(joinMatchButton);

        JButton returnButton = new JButton("Return to Game Selection Menu");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        returnButton.addActionListener(e -> {
            new LobbyFrame(rankedGame);
            dispose();
        });
        buttonPanel.add(returnButton);

        endGamePanel.add(buttonPanel);
        endGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(endGamePanel);
    }

}
