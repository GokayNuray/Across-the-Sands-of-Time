package com.halenteck.fpsUI;

import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.server.Server;
import com.halenteck.server.UserData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FpsEndGame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

    /**
     * Constructor for the FpsEndGame class
     * @param isGameWon whether the game was won or lost
     * @param kills the number of kills the player has
     * @param deaths the number of deaths the player has
     */
    public FpsEndGame(boolean isGameWon, int kills, int deaths) {
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);


        // End Game Panel
        JPanel endGamePanel = new JPanel(new GridLayout(4,1));

        JLabel endGameLabel = new JLabel(isGameWon ? "Victory!" : "Defeat!", SwingConstants.CENTER);
        endGameLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));
        endGamePanel.add(endGameLabel);
        if (isGameWon) {
            JLabel rankChangeLabel = new JLabel("+ 20 WP", SwingConstants.CENTER);
            rankChangeLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            endGamePanel.add(rankChangeLabel);
        }
        else {
            endGamePanel.add(new JLabel("-15 LP", SwingConstants.CENTER));
        }

        JPanel statsPanel = new JPanel(new GridLayout(2,3));
        JLabel killsLabel = new JLabel("Kills: " + kills, SwingConstants.CENTER); // Add kills
        killsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsPanel.add(killsLabel);
        JLabel deathsLabel = new JLabel("Deaths: " + deaths, SwingConstants.CENTER); // Add deaths
        deathsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statsPanel.add(deathsLabel);
        statsPanel.add(new JPanel());
        if (isGameWon) {
            JLabel pointsLabel = new JLabel("Overall Points: " + Server.getUserData().getRankPoints(), SwingConstants.CENTER); // Add rank
            pointsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            statsPanel.add(pointsLabel);
            JLabel positionLabel = new JLabel("Position at LB: " + Server.getUserData().getGlobalRank(), SwingConstants.CENTER); // Add position
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
            new LobbyFrame(true);
        });
        buttonPanel.add(joinMatchButton);

        JButton returnButton = new JButton("Return to Menu");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        returnButton.addActionListener(e -> {
            new GameSelectionMenu();
        });
        buttonPanel.add(returnButton);

        endGamePanel.add(buttonPanel);
        endGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(endGamePanel);
        System.out.println("End Game Frame created");
        setVisible(true);
    }

}
