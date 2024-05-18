package com.halenteck.fpsUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.fpsGame.Game;
import com.halenteck.fpsGame.Player;
import com.halenteck.render.OpenGLComponent;
import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FpsInGame extends JFrame {

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
    public String weaponName;
    public int kills = 0;
    public int deaths = 0;

    public int redScore;
    public int blueScore;
    JLabel redScoreLabel;
    JLabel blueScoreLabel;
    JLabel timeLabel;

    JProgressBar playerHealthBar;
    JProgressBar playerArmourBar;
    Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
    JLabel specialAbilityLabel;
    JLabel weaponNameLabel;
    JLabel kdaLabel;
    JLayeredPane layeredPane;
    JLabel debugLabel;
    JLabel ammoLabel;
    OpenGLComponent renderer;
    JTextArea chat;
    JLabel joinLabel;

    /**
     * Constructor for the FpsInGame class
     *
     * @param id the id of the game
     */
    public FpsInGame(int id) {
        Character character = Character.characters.get(Server.getUserData().getLastSelectedCharacter());
        setTitle("FPS Match");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(false);
        JLabel background = new JLabel();
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setBackground(new Color(0, 0, 0, 0)); // Set the background color to transparent

        joinLabel = new JLabel("", SwingConstants.CENTER);
        joinLabel.setFont(new Font("Arial", Font.BOLD, 80));
        joinLabel.setBounds(300, 250, 900, 300);
        joinLabel.setOpaque(false);
        layeredPane.add(joinLabel, JLayeredPane.PALETTE_LAYER);

        Timer joinTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinLabel.setVisible(false);
            }
        });

        // starting when the user joins
        joinTimer.start();

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
        chat.setBounds(65, 50, 220, 200);
        chat.setEditable(false);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setBackground(Color.WHITE);
        chat.setForeground(Color.BLACK);
        chat.setFont(new Font("Arial", Font.PLAIN, 12));
        chat.setText("Welcome to the game chat!\n");

        JTextField chatField = new JTextField();
        chatField.setBounds(65, 255, 220, 30);
        layeredPane.add(chat, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(chatField, JLayeredPane.PALETTE_LAYER);

        //team score labels
        redScoreLabel = new JLabel("Red: " + redScore, SwingConstants.CENTER);
        redScoreLabel.setForeground(Color.RED);
        redScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        redScoreLabel.setBounds((int) (bounds.getWidth() / 2 - 200), 20, 100, 20);
        layeredPane.add(redScoreLabel, JLayeredPane.PALETTE_LAYER);
        blueScoreLabel = new JLabel("Blue: " + blueScore, SwingConstants.CENTER);
        blueScoreLabel.setForeground(Color.BLUE);
        blueScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        blueScoreLabel.setBounds((int) (bounds.getWidth() / 2 - 200) + 200, 20, 100, 20);
        layeredPane.add(blueScoreLabel, JLayeredPane.PALETTE_LAYER);


        // weapon showcases
        ImageIcon weapon1Image = new ImageIcon(getClass().getResource("weapon1.jpg"));
        Image scaledWeapon1Image = weapon1Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon1Icon = new ImageIcon(scaledWeapon1Image);
        JLabel weapon1Label = new JLabel(scaledWeapon1Icon);
        weapon1Label.setBounds(100, 700, 75, 100);
        layeredPane.add(weapon1Label, JLayeredPane.PALETTE_LAYER);

        ImageIcon weapon2Image = new ImageIcon(getClass().getResource("weapon2.jpg"));
        Image scaledWeapon2Image = weapon2Image.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledWeapon2Icon = new ImageIcon(scaledWeapon2Image);
        JLabel weapon2Label = new JLabel(scaledWeapon2Icon);
        weapon2Label.setBounds(25, 700, 75, 100);
        layeredPane.add(weapon2Label, JLayeredPane.PALETTE_LAYER);

        // Special Ability Button
        specialAbilityLabel = new JLabel("\uD83C\uDF1F");
        specialAbilityLabel.setBounds((int) bounds.getWidth() - 180, 700, 105, 75);
        specialAbilityLabel.setEnabled(true);
        layeredPane.add(specialAbilityLabel, JLayeredPane.PALETTE_LAYER);

        weaponNameLabel = new JLabel(weaponName);
        weaponNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        weaponNameLabel.setBounds((int) bounds.getWidth() - 200, (int) bounds.getHeight() - 75, 175, 20);
        layeredPane.add(weaponNameLabel, JLayeredPane.PALETTE_LAYER);

        // Ammo label
        ammoLabel = new JLabel(ammoInMagazine + "/" + magazineSize);
        ammoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ammoLabel.setBounds((int) bounds.getWidth() - 200, (int) bounds.getHeight() - 50, 175, 20);
        layeredPane.add(ammoLabel, JLayeredPane.PALETTE_LAYER);

        // return to menu info label
        JLabel returnLabel = new JLabel("Press ESC to return to the main menu");
        returnLabel.setFont(new Font("Arial", Font.BOLD, 15));
        returnLabel.setForeground(Color.PINK);
        returnLabel.setBounds(10, (int) bounds.getHeight() - 55, 300, 20);
        layeredPane.add(returnLabel, JLayeredPane.PALETTE_LAYER);

        // crosshair
        ImageIcon crosshair = new ImageIcon(getClass().getClassLoader().getResource("crosshair.png"));
        Image scaledCrosshair = crosshair.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon finalCrosshair = new ImageIcon(scaledCrosshair);
        JLabel crosshairLabel = new JLabel(finalCrosshair);
        crosshairLabel.setOpaque(false);
        crosshairLabel.setBounds((int) (bounds.getWidth() / 2) - 10, (int) (bounds.getHeight() / 2) - 10, 20, 20);
        layeredPane.add(crosshairLabel);

        // transparent cursor
        Image cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);


        // shortcuts for the game
        // shortcut for pausing the game, showing pause frame
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                showPopUp(new FpsPauseFrame(FpsInGame.this));
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        // shortcut for opening the chat
        KeyStroke tStroke = KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, false);
        Action tAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                chatField.setEditable(true);
                chatField.requestFocus();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(tStroke, "T");
        getRootPane().getActionMap().put("T", tAction);

        // shortcut for sending a message in the chat, disabling the editablity of the field
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

        // adding the 3D game in the default layer
        if (id == -1) {
            return;
        }

        renderer = new OpenGLComponent();
        renderer.setBounds(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        layeredPane.add(renderer, JLayeredPane.DEFAULT_LAYER);

        try {
            game = new Game(id, this);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

        timeLabel = new JLabel("0:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setBounds((int) (bounds.getWidth() / 2 - 200) + 100, 20, 100, 20);
        layeredPane.add(timeLabel, JLayeredPane.PALETTE_LAYER);

        new Thread(() -> {
            long startTime = game.getStartTime();
            long endTime = startTime + 5 * 60 * 1000;
            while (true) {
                long currentTime = System.currentTimeMillis();
                long timeLeft = endTime - currentTime;
                if (timeLeft <= 0) {
                    break;
                }
                long minutes = timeLeft / 60000;
                long seconds = (timeLeft % 60000) / 1000;
                timeLabel.setText(minutes + ":" + seconds);
            }
        }).start();

        // leaderboard panel
        InGameLeaderboard leaderboardPanel = new InGameLeaderboard(game.getPlayers());
        leaderboardPanel.setVisible(false);
        layeredPane.add(leaderboardPanel, JLayeredPane.PALETTE_LAYER);
        // calculating the center point of the FpsInGame frame
        int centerX = (int) (bounds.getWidth() / 2);
        int centerY = (int) (bounds.getHeight() / 2);
        int panelX = centerX - (leaderboardPanel.getWidth() / 2);
        int panelY = centerY - (leaderboardPanel.getHeight() / 2);
        leaderboardPanel.setBounds(panelX, panelY, leaderboardPanel.getWidth(), leaderboardPanel.getHeight());

        // shortcut for showing the leaderboard
        KeyStroke lKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, false);
        Action lAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("L action triggered");
                if (leaderboardPanel.isVisible()) {
                    // Hide the leaderboard
                    leaderboardPanel.setVisible(false);
                } else {
                    // Show the leaderboard
                    leaderboardPanel.setVisible(true);
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(lKeyStroke, "L");
        getRootPane().getActionMap().put("L", lAction);


        add(layeredPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        renderer.startRender();
    }

    /**
     * Updates the panels in the game
     */
    public void updatePanels() {
        if (player.isRedTeam()) {
            joinLabel.setForeground(Color.RED);
            joinLabel.setText("You are on RED team");
        } else {
            joinLabel.setForeground(Color.BLUE);
            joinLabel.setText("You are on BLUE team");
        }
        playerHealthBar.setValue(playerHealth);
        playerArmourBar.setValue(playerArmour);
        if (player.isAbilityActive()) {
            specialAbilityLabel.setForeground(Color.ORANGE);
            specialAbilityLabel.setText("     Ability Active");
        } else {
            if (player.isAbleToUseAbility()) {
                specialAbilityLabel.setForeground(Color.GREEN);
                specialAbilityLabel.setText("   Ability Available");
            } else {

                specialAbilityLabel.setForeground(Color.RED);
                specialAbilityLabel.setText("   On Cooldown...");
            }

        }

        redScoreLabel.setText("Red: " + redScore);
        blueScoreLabel.setText("Blue: " + blueScore);

        if (player.getWeapon().isReloading()) {
            ammoLabel.setText("Reloading...");
            ammoLabel.setForeground(Color.RED);
        } else {
            ammoLabel.setText(ammoInMagazine + "/" + magazineSize);
            ammoLabel.setForeground(Color.BLACK);
        }

        kdaLabel.setText(kills + "/" + deaths);

    }

    /**
     * Returns the renderer
     *
     * @return the renderer
     */
    public OpenGLComponent getRenderer() {
        return renderer;
    }

    /**
     * Shows a pop up frame
     *
     * @param frame this frame
     */
    public void showPopUp(JFrame frame) {
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    /**
     * Returns the chat
     *
     * @return the chat
     */
    public JTextArea getChat() {
        return chat;
    }

    public void deathPopup() {
        // on death menu
        showPopUp(new FpsDeathFrame(FpsInGame.this, kills, deaths));
    }
}
