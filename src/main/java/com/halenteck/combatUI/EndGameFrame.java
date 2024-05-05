package com.halenteck.combatUI;

import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.server.Server;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EndGameFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;
    public EndGameFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Game Over");
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

        JPanel endGamePanel = new JPanel(new GridLayout(3,1));
        JLabel endGameLabel = new JLabel("You " + "won/lost!", SwingConstants.CENTER); // Add win/loss condition
        endGamePanel.add(endGameLabel);

        JPanel midPanel = new JPanel(new GridLayout(1,2));
        JPanel statsPanel = new JPanel(new GridLayout(3,1));
        JLabel additionLabel = new JLabel("+ item 2" + "\t+ $25", SwingConstants.CENTER);
        statsPanel.add(additionLabel);

        JLabel levelLabel = new JLabel("Level: " + Server.getUserData().getLevel(), SwingConstants.RIGHT);
        levelLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        statsPanel.add(levelLabel);
        // level bar panel
        JPanel levelPanel = new JPanel(new BorderLayout());
        JProgressBar levelBar = new JProgressBar();
        levelBar.setValue(Server.getUserData().getXp()); // current xp / level up xp * 100
        levelBar.setStringPainted(true);
        JLabel xpLabel = new JLabel("+ 15 XP", SwingConstants.CENTER);
        xpLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        levelPanel.add(levelBar, BorderLayout.CENTER);
        levelPanel.add(xpLabel, BorderLayout.EAST);
        statsPanel.add(levelPanel);
        midPanel.add(statsPanel);

        JButton enterBattleButton = new JButton("Enter Another Battle");
        midPanel.add(enterBattleButton);
        endGamePanel.add(midPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        JButton shopButton = new JButton("Shop/Upgrade");
        bottomPanel.add(shopButton);
        bottomPanel.add(new JPanel());
        JButton returnToMenuButton = new JButton("Return to Menu");
        bottomPanel.add(returnToMenuButton);
        endGamePanel.add(bottomPanel);
        endGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(endGamePanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
