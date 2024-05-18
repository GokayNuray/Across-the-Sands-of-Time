package com.halenteck.fpsUI;

import com.halenteck.commonUI.GameSelectionMenu;

import javax.swing.*;
import java.awt.*;

public class FpsDeathFrame extends JFrame {

    /**
     * Constructor for the FpsDeathFrame class
     * @param fpsInGame the game frame to close
     * @param kills the number of kills the player has
     * @param deaths the number of deaths the player has
     */
    FpsDeathFrame(JFrame fpsInGame, int kills, int deaths) {
        setTitle("You died.");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JLabel deathLabel = new JLabel("You died.", SwingConstants.CENTER);
        deathLabel.setFont(deathLabel.getFont().deriveFont(40.0f));
        add(deathLabel, BorderLayout.NORTH);

        JPanel respawnPanel = new JPanel(new GridLayout(2, 1));
        JLabel respawnLabel = new JLabel("Do you want to be respawned?", SwingConstants.CENTER);
        respawnLabel.setFont(respawnLabel.getFont().deriveFont(20.0f));
        respawnPanel.add(respawnLabel);
        JLabel statsLabel = new JLabel("Your stats: " + kills + "/" + deaths, SwingConstants.CENTER);
        statsLabel.setFont(statsLabel.getFont().deriveFont(15.0f));
        respawnPanel.add(statsLabel);
        add(respawnPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton respawnButton = new JButton("Respawn");
        respawnButton.setFont(respawnButton.getFont().deriveFont(20.0f));
        respawnButton.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(respawnButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(exitButton.getFont().deriveFont(20.0f));
        exitButton.addActionListener(e -> {
            fpsInGame.dispose();
            dispose();
            new GameSelectionMenu();
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
