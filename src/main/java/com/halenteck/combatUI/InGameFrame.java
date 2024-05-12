package com.halenteck.combatUI;

import com.halenteck.CombatGame.Game;
import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.Server;
import com.halenteck.CombatGame.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;

public class InGameFrame extends JFrame {

    private Game game;
    public int enemyX;
    public int playerHealth;
    public int enemyHealth;

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
        game = new Game(this);
        setTitle("Combat Fight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/characters/historymuseum.jpeg"));
        Image scaledImage = backgroundImage.getImage().getScaledInstance((int) bounds.getWidth(), (int) bounds.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);

        // Check the image loading
        if (backgroundImage == null) {
            System.out.println("Image could not be loaded");
            return;
        }

        JLayeredPane layeredPane = new JLayeredPane();
        imageLabel.setBounds(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER); // Add to layer 0

        // give up button
        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.addActionListener(e -> {
            showPopUp(new EndGameFrame(this));
        });
        giveUpButton.setBounds((int) bounds.getWidth() - 190, 10, 100, 50);
        layeredPane.add(giveUpButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // game panel
        JLabel playerName = new JLabel("Grok", SwingConstants.LEFT);
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
        byte lastSelectedCharacter = Server.getUserData().getLastSelectedCharacter();
        Character character;
        switch (lastSelectedCharacter) {
            case 1 -> character = new CaveMan();
            case 2 -> character = new Antidote();
            case 3 -> character = new Nazi();
            case 4 -> character = new GlobalWarming();
            case 5 -> character = new Boss();
            default -> throw new IllegalStateException("Unexpected value: " + lastSelectedCharacter);
        }
        int playerMaxHealth = character.health;
        JProgressBar playerHealthBar = new JProgressBar(0, playerMaxHealth);
        playerHealthBar.setValue(character.health);
        playerHealthBar.setString(character.health + "/" + playerMaxHealth);
        playerHealthBar.setStringPainted(true);
        JProgressBar enemyHealthBar = new JProgressBar(0, character.health);
        enemyHealthBar.setValue(character.health);
        enemyHealthBar.setString(character.health + "/" + playerMaxHealth);
        enemyHealthBar.setStringPainted(true);
        playerHealthBar.setBounds((int) bounds.getWidth() - 1500, (int) bounds.getHeight() - 100, 400, 50);
        enemyHealthBar.setBounds((int) bounds.getWidth() - 500, (int) bounds.getHeight() - 100, 400, 50);
        layeredPane.add(playerHealthBar, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        layeredPane.add(enemyHealthBar, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // player images
        ImageIcon playerImage = new ImageIcon(getClass().getResource(character.resourcePath + "skin.jpg"));
        Image scaledPlayerImage = playerImage.getImage().getScaledInstance(300, 550, Image.SCALE_SMOOTH);
        ImageIcon scaledPlayerImageIcon = new ImageIcon(scaledPlayerImage);
        JLabel playerImageLabel = new JLabel(scaledPlayerImageIcon);
        playerImageLabel.setBounds(250, 130, 300, 600);
        layeredPane.add(playerImageLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        Dimension playerImageBounds = playerImageLabel.getSize();
        // player move buttons
        JButton moveForwardButton = new JButton("->");
        moveForwardButton.addActionListener(e -> {
            playerImageLabel.setBounds((int) (playerImageLabel.getX() + 50), playerImageLabel.getY(), playerImageBounds.width, playerImageBounds.height);
            game.goForward();
        });
        moveForwardButton.setToolTipText("Move Forward");
        moveForwardButton.setBounds((int) (250 + playerImageBounds.getWidth() - 425), 300, 100, 100);
        layeredPane.add(moveForwardButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JButton moveBackwardButton = new JButton("<-");
        moveBackwardButton.setToolTipText("Move Backward");
        moveBackwardButton.addActionListener(e -> {
            playerImageLabel.setBounds((int) (playerImageLabel.getX() - 50), playerImageLabel.getY(), playerImageBounds.width, playerImageBounds.height);
            game.goBackward();
        });
        moveBackwardButton.setBounds((int) (250 + playerImageBounds.getWidth() - 425), 450, 100, 100);
        layeredPane.add(moveBackwardButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JButton closeAttackButton = new JButton("\uD83E\uDD1C");
        closeAttackButton.setToolTipText("Close Attack");
        closeAttackButton.addActionListener(e -> {
            game.shortRangeAttack();
            enemyHealthBar.setValue(character.health);
            enemyHealthBar.setString(character.health + "/" + playerMaxHealth);
        });
        closeAttackButton.setBounds(250 + playerImageBounds.width + 10, 200, 100, 100);
        layeredPane.add(closeAttackButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JButton longAttackButton = new JButton("\uD83D\uDDE1\uFE0F");
        longAttackButton.setToolTipText("Long Attack");
        longAttackButton.addActionListener(e -> {
            game.longRangeAttack();
            enemyHealthBar.setValue(character.health);
            enemyHealthBar.setString(character.health + "/" + playerMaxHealth);
        });
        longAttackButton.setBounds(250 + playerImageBounds.width + 10, 350, 100, 100);
        if (Server.getUserData().getCharacters()[Server.getUserData().getLastSelectedCharacter() - 1].getUnlockedWeapons().length == 1) {
            longAttackButton.setEnabled(false);
            longAttackButton.setToolTipText("Not available as no long-range weapons were found!");
        }
        longAttackButton.setBounds(250 + playerImageBounds.width + 10, 350, 100, 100);
        layeredPane.add(longAttackButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1
        JButton specialAbilityButton = new JButton("\uD83C\uDF1F");
        specialAbilityButton.setToolTipText("Special Ability");
        if (!Server.getUserData().getCharacters()[Server.getUserData().getLastSelectedCharacter() - 1].isSpecialAbilityUnlocked()) {
            specialAbilityButton.setEnabled(false);
            specialAbilityButton.setToolTipText("Special Ability is locked");
        }
        specialAbilityButton.addActionListener(e -> {
            game.useAbility();
            enemyHealthBar.setValue(character.health);
            enemyHealthBar.setString(character.health + "/" + playerMaxHealth);
        });
        specialAbilityButton.setBounds(250 + playerImageBounds.width + 10, 500, 100, 100);
        layeredPane.add(specialAbilityButton, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        // enemy images
        ImageIcon enemyImage = new ImageIcon(getClass().getResource(character.resourcePath + "specialability.png"));
        Image scaledEnemyImage = enemyImage.getImage().getScaledInstance(300, 550, Image.SCALE_SMOOTH);
        ImageIcon scaledEnemyImageIcon = new ImageIcon(scaledEnemyImage);
        JLabel enemyImageLabel = new JLabel(scaledEnemyImageIcon);
        enemyImageLabel.setBounds((int) bounds.getWidth() - 450, 130, 300, 600);
        layeredPane.add(enemyImageLabel, JLayeredPane.PALETTE_LAYER); // Add to layer 1

        add(layeredPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void updatePanels() {
        if (game.isGameOver) {
            showPopUp(new EndGameFrame(this));
        }
    }

    public void showPopUp(JFrame frame) {
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


}
