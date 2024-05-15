package com.halenteck.fpsUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.fpsGame.Game;
import com.halenteck.fpsGame.Player;
import com.halenteck.render.OpenGLComponent;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FpsInGame extends JFrame {
    public static void main(String[] args) {
        try {
            Server.connect();
            Server.login("Babapiiro31", "Gokaynu2!");
            new FpsInGame(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game game;
    public Player player;
    public boolean isGameOver = false;
    public boolean isGameWon = false;
    public int playerHealth;
    public int playerArmour;
    public boolean isAbilityActive = true;
    public boolean isAbilityUsed = false;
    public int magazineSize = 0;
    public int ammoInMagazine = 0;
    public int kills = 0;
    public int deaths = 0;

    public int redScore;
    public int blueSCore;

    JProgressBar playerHealthBar;
    JProgressBar playerArmourBar;
    Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
    JButton specialAbilityButton;
    JLabel kdaLabel;
    JLayeredPane layeredPane;
    JLabel debugLabel;
    JLabel ammoLabel;
    OpenGLComponent renderer;
    JTextArea chat;

    public FpsInGame(int id) {
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
        playerHealthBar.setForeground(Color.GREEN);
        layeredPane.add(playerHealthBar, JLayeredPane.PALETTE_LAYER);

        playerArmourBar = new JProgressBar();
        playerArmourBar.setStringPainted(true);
        playerArmourBar.setBounds(310, 10, 150, 20);
        playerArmourBar.setMaximum(100);
        playerArmourBar.setValue(80);
        playerArmourBar.setString("Armour");
        playerArmourBar.setForeground(Color.BLACK);
        layeredPane.add(playerArmourBar, JLayeredPane.PALETTE_LAYER);

        kdaLabel = new JLabel(kills + "/" + deaths);
        kdaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        kdaLabel.setBounds((int) bounds.getWidth() - 150, 10, 100, 20);
        layeredPane.add(kdaLabel, JLayeredPane.PALETTE_LAYER);

        JButton chatButton = new JButton("\uD83D\uDCAC");
        chatButton.setBounds(10, 50, 50, 50);
        chatButton.setEnabled(false);
        chat = new JTextArea();
        chat.setBounds(65, 50, 200, 75);
        chat.setEditable(false);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setBackground(Color.WHITE);
        chat.setForeground(Color.BLACK);
        chat.setFont(new Font("Arial", Font.PLAIN, 12));
        chat.setText("Welcome to the game chat!\n");

        JTextField chatField = new JTextField();
        chatField.setBounds(65, 130, 200, 30);
        layeredPane.add(chat, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatField, JLayeredPane.PALETTE_LAYER);

        //team score labels
        JLabel redScoreLabel = new JLabel("Red: " + redScore);
        redScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        redScoreLabel.setBounds(10, 700, 100, 20);
        layeredPane.add(redScoreLabel, JLayeredPane.PALETTE_LAYER);


        // weapon showcases
        ImageIcon weapon1Image = new ImageIcon(getClass().getResource(character.resourcePath + "weapon1.png"));
        Image scaledWeapon1Image = weapon1Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon1Icon = new ImageIcon(scaledWeapon1Image);
        JLabel weapon1Label = new JLabel(scaledWeapon1Icon);
        weapon1Label.setBounds(100, 700, 75, 100);
        layeredPane.add(weapon1Label, JLayeredPane.PALETTE_LAYER);

        ImageIcon weapon2Image = new ImageIcon(getClass().getResource(character.resourcePath + "weapon2.png"));
        Image scaledWeapon2Image = weapon2Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon2Icon = new ImageIcon(scaledWeapon2Image);
        JLabel weapon2Label = new JLabel(scaledWeapon2Icon);
        weapon2Label.setBounds(25, 700, 75, 100);
        layeredPane.add(weapon2Label, JLayeredPane.PALETTE_LAYER);

        // Special Ability Button
        specialAbilityButton = new JButton("\uD83C\uDF1F");
        specialAbilityButton.setBounds((int) bounds.getWidth() - 130, 700, 75, 75);
        if (!isAbilityActive) {
            specialAbilityButton.setEnabled(false);
        }
        specialAbilityButton.setEnabled(true);
        layeredPane.add(specialAbilityButton, JLayeredPane.PALETTE_LAYER);

        ammoLabel = new JLabel(ammoInMagazine + "/" + magazineSize);
        ammoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ammoLabel.setBounds((int) bounds.getWidth() - 200, (int) bounds.getHeight() - 75, 175, 20);
        layeredPane.add(ammoLabel, JLayeredPane.PALETTE_LAYER);

        JLabel returnLabel = new JLabel("Press ESC to return to the main menu");
        returnLabel.setFont(new Font("Arial", Font.BOLD, 15));
        returnLabel.setForeground(Color.PINK);
        returnLabel.setBounds(10, (int) bounds.getHeight() - 55, 300, 20);
        layeredPane.add(returnLabel, JLayeredPane.PALETTE_LAYER);

        // shortcut for returning to game selection menu
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                showPopUp(new FpsPauseFrame(FpsInGame.this));
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        KeyStroke slashKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, false);
        Action slashAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                chatField.setEditable(true);
                chatField.requestFocus();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(slashKeyStroke, "SLASH");
        getRootPane().getActionMap().put("SLASH", slashAction);

        KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        Action enterAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (chatField.isEditable()) {
                    chat.append("You: " + chatField.getText() + "\n");
                    chat.setCaretPosition(chat.getDocument().getLength());
                    Server.chat(chatField.getText());
                    chatField.setText("");
                    chatField.setEditable(false);
                    chatField.getParent().requestFocus();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKeyStroke, "ENTER");
        getRootPane().getActionMap().put("ENTER", enterAction);

        // Start a timer that will disable the button after a delay
        Timer timer = new Timer(60000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialAbilityButton.setEnabled(true);
                isAbilityUsed = false;
            }
        });
        timer.setRepeats(false);

        // key listener for the x key
        KeyStroke xKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false);
        Action xAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("X key pressed. isAbilityActive: " + isAbilityActive + ", isAbilityUsed: " + isAbilityUsed);

                if (isAbilityActive && !isAbilityUsed) {
                    specialAbilityButton.requestFocus();
                    specialAbilityButton.setEnabled(false);
                    isAbilityUsed = true;
                    timer.start();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(xKeyStroke, "X");
        getRootPane().getActionMap().put("X", xAction);

        // Initialize the leaderboard panel in your FpsInGame constructor
        InGameLeaderboard leaderboardPanel = new InGameLeaderboard(this);
        leaderboardPanel.setVisible(false);
        layeredPane.add(leaderboardPanel, JLayeredPane.PALETTE_LAYER);
        // Calculate the center point of the FpsInGame frame
        int centerX = (int) (bounds.getWidth() / 2);
        int centerY = (int) (bounds.getHeight() / 2);

// Calculate the top-left point of the InGameLeaderboard panel
        int panelX = centerX - (leaderboardPanel.getWidth() / 2);
        int panelY = centerY - (leaderboardPanel.getHeight() / 2);

// Set the location and size of the InGameLeaderboard panel
        leaderboardPanel.setBounds(panelX, panelY, leaderboardPanel.getWidth(), leaderboardPanel.getHeight());

// Modify your tAction to show/hide the leaderboard panel
        KeyStroke tKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, false);
        Action tAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("T action triggered");
                if (leaderboardPanel.isVisible()) {
                    // Hide the leaderboard
                    leaderboardPanel.setVisible(false);
                } else {
                    // Show the leaderboard
                    leaderboardPanel.setVisible(true);
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(tKeyStroke, "T");
        getRootPane().getActionMap().put("T", tAction);

        if (id == -1) {
            return;
        }

        debugLabel = new JLabel();
        debugLabel.setBounds(360, 100, 900, 30);
        layeredPane.add(debugLabel, JLayeredPane.PALETTE_LAYER);

        renderer = new OpenGLComponent();
        renderer.setBounds(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        layeredPane.add(renderer, JLayeredPane.DEFAULT_LAYER);

        try {
            new Game(id, this);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


        add(layeredPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        renderer.startRender();
    }

    public void updatePanels() {
        playerHealthBar.setValue(playerHealth);
        playerArmourBar.setValue(playerArmour);
        if (player.isAbilityActive()) {
            specialAbilityButton.setEnabled(false);
            specialAbilityButton.setText("ability active!");
            specialAbilityButton.setForeground(Color.GREEN);
        } else {
            if (player.isAbleToUseAbility()) {
                specialAbilityButton.setEnabled(true);
                specialAbilityButton.setText("ability can be used!");
                specialAbilityButton.setForeground(Color.YELLOW);
            } else {
                specialAbilityButton.setEnabled(false);
                specialAbilityButton.setText("ability on cooldown");
                specialAbilityButton.setForeground(Color.RED);
            }

        }

        while (player.getWeapon().isReloading()) {
            ammoLabel.setText("Reloading...");
            ammoLabel.setForeground(Color.RED);
        }

        ammoLabel.setText(ammoInMagazine + "/" + magazineSize);
        ammoLabel.setForeground(Color.BLACK);
        kdaLabel.setText(kills + "/" + deaths);
    }

    public JLabel getDebugLabel() {
        return debugLabel;
    }

    public OpenGLComponent getRenderer() {
        return renderer;
    }
    public void showPopUp(JFrame frame) {
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    public JTextArea getChat() {
        return chat;
    }
}
