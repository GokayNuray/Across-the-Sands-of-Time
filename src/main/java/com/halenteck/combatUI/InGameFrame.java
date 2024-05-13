package com.halenteck.combatUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.Game;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;

public class InGameFrame extends JFrame {

    public Game game;
    public int enemyX;
    public int playerX;
    public int playerHealth;
    public int enemyHealth;
    public int enemyCount;
    public boolean isAbilityActive;
    JProgressBar playerHealthBar;
    JProgressBar enemyHealthBar;
    JLabel enemyImageLabel;
    JLabel playerImageLabel;
    Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
    JButton moveForwardButton;
    JButton moveBackwardButton;
    JButton longAttackButton;
    JButton shortAttackButton;
    JButton specialAbilityButton;
    JLayeredPane layeredPane;

    public static void main(String[] args) {
        try {
            Server.connect();
            Server.login("Babapiiro31", "Gokaynu2!");
            new InGameFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InGameFrame() throws Exception {
        isAbilityActive = false;
        playerX = 250;
        enemyX = (int) bounds.getWidth() - 450;
        game = new Game(this);
        Character character = game.getLocation().getPlayer();
        setTitle("Combat Fight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(character.resourcePath + "map" + (game.getLocation().getLocationId() + 1) + ".jpg"));
        Image scaledImage = backgroundImage.getImage().getScaledInstance((int) bounds.getWidth(), (int) bounds.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);

        layeredPane = new JLayeredPane();
        imageLabel.setBounds(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER); // Add to layer 0

        // give up button
        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.addActionListener(e -> {
            game.giveUp();
            updatePanels();
        });
        giveUpButton.setBounds((int) bounds.getWidth() - 190, 10, 100, 50);
        if (Server.getUserData().getCombatLevelReached() % 4 == 0) {
            giveUpButton.setEnabled(false);
            giveUpButton.setToolTipText("You can't give up on the first level of a character!");
        }
        layeredPane.add(giveUpButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // game panel
        JLabel playerName = new JLabel(character.name, SwingConstants.LEFT);
        playerName.setFont(new Font("Arial", Font.BOLD, 30));
        playerName.setBounds(200, 100, 200, 50);
        layeredPane.add(playerName, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JLabel enemyName = new JLabel("Bats", SwingConstants.LEFT);
        enemyName.setBounds((int) bounds.getWidth() - 350, 100, 200, 50);
        enemyName.setFont(new Font("Arial", Font.BOLD, 30));
        layeredPane.add(enemyName, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JLabel playerHealthLabel = new JLabel("Player Health: ", SwingConstants.LEFT);
        playerHealthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerHealthLabel.setForeground(Color.WHITE);
        playerHealthLabel.setBounds((int) bounds.getWidth() - 1500, (int) bounds.getHeight() - 150, 200, 50);
        layeredPane.add(playerHealthLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JLabel enemyHealthLabel = new JLabel("Enemy Health: ", SwingConstants.LEFT);
        enemyHealthLabel.setBounds((int) bounds.getWidth() - 500, (int) bounds.getHeight() - 150, 200, 50);
        enemyHealthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        enemyHealthLabel.setForeground(Color.WHITE);
        layeredPane.add(enemyHealthLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        playerHealth = character.health;
        enemyHealth = game.getLocation().getEnemyHealth();
        enemyX = (int) bounds.getWidth() - 500;
        int playerMaxHealth = character.health;
        int enemyMaxHealth = game.getLocation().getEnemyHealth();
        playerHealthBar = new JProgressBar(0, playerMaxHealth);
        playerHealthBar.setValue(character.health);
        playerHealthBar.setString(character.health + "/" + playerMaxHealth);
        playerHealthBar.setMaximum(playerMaxHealth);
        playerHealthBar.setStringPainted(true);
        enemyHealthBar = new JProgressBar(0, character.health);
        enemyHealthBar.setValue(enemyHealth);
        enemyHealthBar.setString(enemyHealth + "/" + enemyMaxHealth + "x" + enemyCount);
        enemyHealthBar.setStringPainted(true);
        enemyHealthBar.setMaximum(enemyMaxHealth);
        playerHealthBar.setBounds((int) bounds.getWidth() - 1500, (int) bounds.getHeight() - 100, 400, 50);
        enemyHealthBar.setBounds((int) bounds.getWidth() - 500, (int) bounds.getHeight() - 100, 400, 50);
        layeredPane.add(playerHealthBar, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        layeredPane.add(enemyHealthBar, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // player images
        ImageIcon playerImage = new ImageIcon(getClass().getResource(character.resourcePath + "skin.png"));
        Image scaledPlayerImage = playerImage.getImage().getScaledInstance(300, 550, Image.SCALE_SMOOTH);
        ImageIcon scaledPlayerImageIcon = new ImageIcon(scaledPlayerImage);
        playerImageLabel = new JLabel(scaledPlayerImageIcon);
        playerImageLabel.setBounds(250, 130, 300, 600);
        layeredPane.add(playerImageLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        Dimension playerImageBounds = playerImageLabel.getSize();
        // player move buttons
        moveForwardButton = new JButton("->");
        moveForwardButton.addActionListener(e -> {
            if (playerX < 450) {
                game.goForward();
            } else {
                JOptionPane.showMessageDialog(this, "You can't move forward anymore!");
            }
        });
        moveForwardButton.setToolTipText("Move Forward");
        moveForwardButton.setBounds((int) (playerX + playerImageBounds.getWidth() - 425), 300, 100, 100);
        layeredPane.add(moveForwardButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        moveBackwardButton = new JButton("<-");
        moveBackwardButton.setToolTipText("Move Backward");
        moveBackwardButton.addActionListener(e -> {
            if (playerX > 250) {
                game.goBackward();
            } else {
                JOptionPane.showMessageDialog(this, "You can't move backward anymore!");
            }

        });
        moveBackwardButton.setBounds((int) (playerX + playerImageBounds.getWidth() - 425), 450, 100, 100);
        layeredPane.add(moveBackwardButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        shortAttackButton = new JButton("\uD83E\uDD1C");
        shortAttackButton.setToolTipText("Close Attack");
        shortAttackButton.addActionListener(e -> {
            game.shortRangeAttack();
        });
        shortAttackButton.setBounds(250 + playerImageBounds.width + 10, 200, 100, 100);
        layeredPane.add(shortAttackButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        longAttackButton = new JButton("\uD83D\uDDE1\uFE0F");
        longAttackButton.setToolTipText("Long Attack");
        longAttackButton.addActionListener(e -> {
            game.longRangeAttack();
        });
        longAttackButton.setBounds(250 + playerImageBounds.width + 10, 350, 100, 100);
        if (!Server.getUserData().getCharacters()[Server.getUserData().getLastSelectedCharacter() - 1].getUnlockedWeapons()[1]) {
            longAttackButton.setEnabled(false);
            longAttackButton.setToolTipText("Not available as no long-range weapons were found!");
        }
        longAttackButton.setBounds(250 + playerImageBounds.width + 10, 350, 100, 100);
        layeredPane.add(longAttackButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        specialAbilityButton = new JButton("\uD83C\uDF1F");
        specialAbilityButton.setToolTipText("Special Ability");
        if (!Server.getUserData().getCharacters()[Server.getUserData().getLastSelectedCharacter() - 1].isSpecialAbilityUnlocked()
                || !isAbilityActive) {
            specialAbilityButton.setEnabled(false);
            specialAbilityButton.setToolTipText("Special Ability is locked");
        }
        specialAbilityButton.addActionListener(e -> {
            game.useAbility();
        });
        specialAbilityButton.setBounds(250 + playerImageBounds.width + 10, 500, 100, 100);
        layeredPane.add(specialAbilityButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // enemy images
        ImageIcon enemyImage = new ImageIcon(getClass().getResource(character.resourcePath + "enemy" + (game.getLocation().getLocationId() + 1) + ".png"));
        Image scaledEnemyImage = enemyImage.getImage().getScaledInstance(300, 550, Image.SCALE_SMOOTH);
        ImageIcon scaledEnemyImageIcon = new ImageIcon(scaledEnemyImage);
        enemyImageLabel = new JLabel(scaledEnemyImageIcon);
        enemyImageLabel.setBounds((int) bounds.getWidth() - 450, 130, 300, 600);
        layeredPane.add(enemyImageLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        add(layeredPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void updatePanels() {
        if (game.isGameOver()) {
            showPopUp(new EndGameFrame(this));
            int layerToRemove = JLayeredPane.PALETTE_LAYER;

            // Get all components in the JLayeredPane
            Component[] components = layeredPane.getComponentsInLayer(layerToRemove);

            // Iterate over the components
            for (Component component : components) {
                // Remove the component from the JLayeredPane
                layeredPane.remove(component);
            }

            // Refresh the JLayeredPane
            layeredPane.revalidate();
            layeredPane.repaint();
            return;
        }

        // Update player and enemy health
        playerHealthBar.setValue(playerHealth);
        playerHealthBar.setString(playerHealth + "/" + playerHealthBar.getMaximum());
        enemyHealthBar.setValue(enemyHealth);
        enemyHealthBar.setString(enemyHealth + "/" + enemyHealthBar.getMaximum() + "x" + enemyCount);

        // update player and enemy images
        playerImageLabel.setBounds(playerX, 130, 300, 600);
        enemyImageLabel.setBounds(enemyX, 130, 300, 600);

        // update buttons accordingly
        shortAttackButton.setBounds(playerX + playerImageLabel.getWidth() + 10, 200, 100, 100);
        longAttackButton.setBounds(playerX + playerImageLabel.getWidth() + 10, 350, 100, 100);
        specialAbilityButton.setBounds(playerX + playerImageLabel.getWidth() + 10, 500, 100, 100);
        moveBackwardButton.setBounds(playerX + playerImageLabel.getWidth() - 425, 450, 100, 100);
        moveForwardButton.setBounds(playerX + playerImageLabel.getWidth() - 425, 300, 100, 100);

    }

    public void showPopUp(JFrame frame) {
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


}
