package com.halenteck.combatUI;

import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EndGameFrame extends JFrame {

    public EndGameFrame(InGameFrame gameFrame) {
        setSize(700, 500);
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // shortcut for returning to game selection menu
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    new GameSelectionMenu();
                    gameFrame.dispose();
                    dispose();
                }
            }
        });

        JPanel endGamePanel = new JPanel(new GridLayout(3, 1));
        JLabel endGameLabel = new JLabel();
        if (gameFrame.game.isGameWon()) {
            JLabel text = new JLabel("You won!", SwingConstants.CENTER);
            text.setFont(new Font("Sans Serif", Font.PLAIN, 30));
            endGameLabel = text;
        } else {
            JLabel text = new JLabel("You lost!", SwingConstants.CENTER);
            text.setFont(new Font("Sans Serif", Font.PLAIN, 30));
            endGameLabel = text;
        }
        endGamePanel.add(endGameLabel);

        JPanel midPanel = new JPanel(new GridLayout(1, 2));
        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        JLabel additionLabel = new JLabel("+ " + gameFrame.game.getLocation().getAward() + "\t+ " + gameFrame.game.getLocation().getReward() + "$", SwingConstants.CENTER);
        statsPanel.add(additionLabel);

        JLabel levelLabel = new JLabel("Level: " + Server.getUserData().getLevel(), SwingConstants.RIGHT);
        levelLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        statsPanel.add(levelLabel);
        // level bar panel
        JPanel levelPanel = new JPanel(new BorderLayout());
        JProgressBar levelBar = new JProgressBar();
        levelBar.setValue(Server.getUserData().getXp());
        levelBar.setStringPainted(true);
        int xpEarned;
        switch (gameFrame.game.getLocation().getLocationId()) {
            case 0 -> xpEarned = 15;
            case 1 -> xpEarned = 20;
            case 2 -> xpEarned = 25;
            case 3 -> xpEarned = 40;
            default ->
                    throw new IllegalStateException("Unexpected value: " + gameFrame.game.getLocation().getLocationId());
        }
        xpEarned += Server.getUserData().getUnlockedCharacterCount() * 10 - 10;
        JLabel xpLabel = new JLabel("+ " + xpEarned + " XP", SwingConstants.CENTER);
        xpLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        levelPanel.add(levelBar, BorderLayout.CENTER);
        levelPanel.add(xpLabel, BorderLayout.EAST);
        statsPanel.add(levelPanel);
        midPanel.add(statsPanel);

        JButton enterBattleButton = new JButton("Enter Another Battle");
        enterBattleButton.addActionListener(e -> {
            try {
                new InGameFrame();
                gameFrame.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        midPanel.add(enterBattleButton);
        endGamePanel.add(midPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        JButton shopButton = new JButton("Shop/Upgrade");
        shopButton.addActionListener(e -> {
            ShopFrame.getInstance().setVisible(true);
            dispose();
            gameFrame.dispose();

        });
        bottomPanel.add(shopButton);
        bottomPanel.add(new JPanel());
        JButton returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.addActionListener(e -> {
            new GameSelectionMenu();
            dispose();
            gameFrame.dispose();

        });
        bottomPanel.add(returnToMenuButton);
        endGamePanel.add(bottomPanel);
        endGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(endGamePanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
