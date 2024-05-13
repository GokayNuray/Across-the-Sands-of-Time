package com.halenteck.fpsUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.fpsGame.Game;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FpsInGame extends JFrame {
    public static void main(String[] args) {
        try {
            Server.connect();
            Server.login("Babapiiro31", "Gokaynu2!");
            new FpsInGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game game;
    public int playerHealth;
    public int playerArmour;
    public boolean isAbilityActive;
    public boolean isAbilityUsed;
    JProgressBar playerHealthBar;
    JProgressBar playerArmourBar;
    Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
    JButton specialAbilityButton;
    JLayeredPane layeredPane;

    public FpsInGame() {
        Character character = Character.characters.get(Server.getUserData().getLastSelectedCharacter());
        setTitle("FPS Match");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layeredPane = new JLayeredPane();
        JLabel background = new JLabel();
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        playerHealthBar = new JProgressBar();
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setBounds(10, 10, 300, 20);
        playerHealthBar.setMaximum(100);
        playerHealthBar.setValue(100);
        playerHealthBar.setString("Health");
        playerHealthBar.setForeground(Color.BLACK);
        layeredPane.add(playerHealthBar, JLayeredPane.PALETTE_LAYER);

        playerArmourBar = new JProgressBar();
        playerArmourBar.setStringPainted(true);
        playerArmourBar.setBounds(310, 10, 150, 20);
        playerArmourBar.setMaximum(100);
        playerArmourBar.setValue(80);
        playerArmourBar.setString("Armour");
        playerArmourBar.setForeground(Color.GREEN);
        layeredPane.add(playerArmourBar, JLayeredPane.PALETTE_LAYER);

        JLabel kdaLabel = new JLabel("K/D/A");
        kdaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        kdaLabel.setBounds((int) bounds.getWidth() - 150, 10, 100, 20);
        layeredPane.add(kdaLabel, JLayeredPane.PALETTE_LAYER);

        JButton chatButton = new JButton("\uD83D\uDCAC");
        chatButton.setBounds(10, 50, 50, 50);
        chatButton.setEnabled(false);
        JTextArea chatArea = new JTextArea();
        chatArea.setBounds(65, 50, 200, 200);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(Color.WHITE);
        chatArea.setForeground(Color.BLACK);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 12));
        chatArea.setText("Welcome to the game chat!");

        JTextField chatField = new JTextField();
        chatField.setBounds(65, 255, 200, 40);
        layeredPane.add(chatArea, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatField, JLayeredPane.PALETTE_LAYER);

        // weapon showcases
        ImageIcon weapon1Image = new ImageIcon(getClass().getResource(character.resourcePath + "weapon1.png"));
        Image scaledWeapon1Image = weapon1Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon1Icon = new ImageIcon(scaledWeapon1Image);
        JLabel weapon1Label = new JLabel(scaledWeapon1Icon);
        weapon1Label.setBounds(50, 400, 75, 100);
        layeredPane.add(weapon1Label, JLayeredPane.PALETTE_LAYER);

        ImageIcon weapon2Image = new ImageIcon(getClass().getResource(character.resourcePath + "weapon2.png"));
        Image scaledWeapon2Image = weapon2Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon2Icon = new ImageIcon(scaledWeapon2Image);
        JLabel weapon2Label = new JLabel(scaledWeapon2Icon);
        weapon2Label.setBounds(50, 475, 75, 100);
        layeredPane.add(weapon2Label, JLayeredPane.PALETTE_LAYER);

        // Special Ability Button
        specialAbilityButton = new JButton("\uD83C\uDF1F");
        specialAbilityButton.setBounds((int) bounds.getWidth() - 175, 400, 75, 75);
        if (!isAbilityActive) {
            specialAbilityButton.setEnabled(false);
        }
        specialAbilityButton.setEnabled(true);
        layeredPane.add(specialAbilityButton, JLayeredPane.PALETTE_LAYER);

        JLabel ammoLabel = new JLabel("27/30");
        ammoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ammoLabel.setBounds((int) bounds.getWidth() - 150, (int) bounds.getHeight() - 75, 100, 20);
        layeredPane.add(ammoLabel, JLayeredPane.PALETTE_LAYER);

        JLabel returnLabel = new JLabel("Press ESC to return to the main menu");
        returnLabel.setFont(new Font("Arial", Font.BOLD, 15));
        returnLabel.setForeground(Color.PINK);
        returnLabel.setBounds(10, (int) bounds.getHeight() - 70, 300, 20);
        layeredPane.add(returnLabel, JLayeredPane.PALETTE_LAYER);

        Timer abilityTimer = new Timer(1000, e -> {
            if (isAbilityActive && isAbilityUsed) {
                specialAbilityButton.setEnabled(true);
                isAbilityUsed = false;
            }
        });

        // shortcut for returning to game selection menu
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                new GameSelectionMenu();
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        layeredPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '/') {
                    chatField.setEditable(true);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        chatField.setEditable(false);
//                        sendChatMessage(chatField.getText());
                    }
                }
            }
        });
        layeredPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'x' || e.getKeyChar() == 'X') {
                    if (isAbilityActive && !isAbilityUsed) {
                        specialAbilityButton.setEnabled(false);
                        isAbilityUsed = true;
                    }
                }
            }
        });

        // viewing in-game scoreboard with tab
        layeredPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    new InGameLeaderboard();
                }
            }
        });

        // key listener for the escape key
        layeredPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    new FpsEndGame(false);
                }
            }
        });

        add(layeredPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void updatePanels() {
        playerHealthBar.setValue(playerHealth);
        playerArmourBar.setValue(playerArmour);
//        if (abilityCooldown > 0) {
//            specialAbilityButton.setEnabled(false);
//        } else {
//            specialAbilityButton.setEnabled(true);
//        }
    }

}
