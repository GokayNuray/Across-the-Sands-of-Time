package com.halenteck.combatUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.ToolStore;
import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.server.Server;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class ShopFrame extends JFrame {
    static JPanel characterDisplayPanel = new JPanel();
    private JLabel currencyLabel = new JLabel("Currency: $" + Server.getUserData().getMoney(), SwingConstants.CENTER);
    private static ShopFrame instance;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout);
    private JSlider characterSlider;

    /**
     * Constructor for the ShopFrame class.
     */
    public ShopFrame() {

        instance = this; // set the instance to this object for static methods to be used in other classes
        setTitle("Tool Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        // character-based display panels
        JPanel[] characterDisplayPanels = new JPanel[Server.getUserData().getUnlockedCharacterCount()];
        for (int i = 0; i < characterDisplayPanels.length; i++) {
            characterDisplayPanels[i] = createCharacterDisplayPanel(i);
        }

        // shop panel
        JPanel shopPanel = new JPanel(new BorderLayout());
        // main shop panel, goes inside the larger shop panel
        JPanel mainShopPanel = new JPanel(new BorderLayout());

        // id for character and shop menu selection
        AtomicInteger menuSelection = new AtomicInteger(0);
        AtomicInteger characterSelection = new AtomicInteger(0);

        // character item display panels (creates panels for each character)
        JPanel[][] itemPanels = new JPanel[characterDisplayPanels.length][3];
        for (int i = 0; i < characterDisplayPanels.length; i++) {
            itemPanels[i] = createItemPanels(i);
        }

        // initially, first character info (weapon menu) is shown
        cards.add(itemPanels[0][0]);
        mainShopPanel.add(cards, BorderLayout.CENTER);
        characterDisplayPanel.add(characterDisplayPanels[0]);
        add(characterDisplayPanel, BorderLayout.WEST);

        // constant button panel
        JPanel shopButtonPanel = new JPanel(new GridLayout(4, 1));
        currencyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currencyLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        currencyLabel.setBackground(Color.YELLOW);
        shopButtonPanel.add(currencyLabel);
        JButton weaponButton = new JButton("Weapon");
        weaponButton.setBackground(Color.GREEN);
        weaponButton.setFont(new Font("Sans Serif", Font.BOLD, 16)); // Adjust font size as needed
        weaponButton.setBorder(BorderFactory.createRaisedBevelBorder()); // Adjust border style
        JButton armourButton = new JButton("Armour");
        armourButton.setBackground(Color.WHITE);
        armourButton.setFont(new Font("Sans Serif", Font.BOLD, 16)); // Adjust font size as needed
        armourButton.setBorder(BorderFactory.createRaisedBevelBorder()); // Adjust border style
        JButton abilityButton = new JButton("Special Ability");
        abilityButton.setBackground(Color.WHITE);
        abilityButton.setFont(new Font("Sans Serif", Font.BOLD, 16)); // Adjust font size as needed
        abilityButton.setBorder(BorderFactory.createRaisedBevelBorder()); // Adjust border style

        weaponButton.addActionListener(e -> {
            weaponButton.setBackground(Color.GREEN);
            armourButton.setBackground(Color.WHITE);
            abilityButton.setBackground(Color.WHITE);
            menuSelection.set(0);
            updatePanels(characterSelection.get(), menuSelection.get());
        });
        shopButtonPanel.add(weaponButton);

        armourButton.addActionListener(e -> {
            armourButton.setBackground(Color.GREEN);
            weaponButton.setBackground(Color.WHITE);
            abilityButton.setBackground(Color.WHITE);
            menuSelection.set(1);
            updatePanels(characterSelection.get(), menuSelection.get());
        });
        shopButtonPanel.add(armourButton);

        abilityButton.addActionListener(e -> {
            abilityButton.setBackground(Color.GREEN);
            armourButton.setBackground(Color.WHITE);
            weaponButton.setBackground(Color.WHITE);
            menuSelection.set(2);
            updatePanels(characterSelection.get(), menuSelection.get());
        });
        shopButtonPanel.add(abilityButton);
        mainShopPanel.add(shopButtonPanel, BorderLayout.WEST);


        // horizontal slider for switching between character-based panels
        characterSlider = new JSlider(JSlider.HORIZONTAL, 0, characterDisplayPanels.length - 1, 0);
        mainShopPanel.add(characterSlider, BorderLayout.SOUTH);
        characterSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newValue = characterSlider.getValue();
                int adjustedValue = Math.min(newValue, itemPanels.length - 1); // ensuring value is within bounds
                characterSelection.set(adjustedValue);
                updatePanels(characterSelection.get(), menuSelection.get());
            }
        });
        characterSlider.setMinorTickSpacing(1);

        // bottom buttons
        JPanel bottomButtonPanel = new JPanel(new GridLayout(2, 5));
        for (int i = 0; i < 5; i++) {
            bottomButtonPanel.add(new JLabel());
        }
        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        upgradeButton.addActionListener(e -> {
            ShopFrame.getInstance().setVisible(false);
            UpgradeShopFrame.getInstance().setVisible(true);
        });
        bottomButtonPanel.add(upgradeButton);
        bottomButtonPanel.add(new JLabel());
        bottomButtonPanel.add(new JLabel());
        bottomButtonPanel.add(new JLabel());
        JButton joinBattleButton = new JButton("Join Battle");
        joinBattleButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        joinBattleButton.addActionListener(e -> {
            try {
                new InGameFrame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        bottomButtonPanel.add(joinBattleButton);

        shopPanel.add(mainShopPanel, BorderLayout.CENTER);
        shopPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        add(shopPanel, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // setting the window to full screen
        setVisible(true);
    }

    /**
     * Updates the panels based on the character and shop menu selection.
     *
     * @param characterId the character id
     * @param shopId      the shop id
     */
    private void updatePanels(int characterId, int shopId) {
        characterSlider.setMaximum(Server.getUserData().getUnlockedCharacterCount() - 1);

        // update currency label
        currencyLabel.setText("Currency: $" + Server.getUserData().getMoney());

        // Update character shop panel based on slider value
        JPanel[] panels = createItemPanels(characterId);
        cards.removeAll(); // remove all components from cards
        cards.add(panels[shopId], String.valueOf(shopId)); // Add the selected panel to the cards
        cardLayout.show(cards, String.valueOf(shopId)); // Show the selected panel

        // Update character display panel based on slider value
        characterDisplayPanel.removeAll(); // remove all components from characterDisplayPanel
        characterDisplayPanel.revalidate();
        characterDisplayPanel.add(createCharacterDisplayPanel(characterId));
        characterDisplayPanel.revalidate();  // Inform the frame layout needs to be updated
        revalidate();
    }

    /** Creates the item panels for the shop.
     *
     * @param characterIndex the character index
     * @return the item panels
     */
    protected static JPanel[] createItemPanels(int characterIndex) {
        JPanel[] panels = new JPanel[3];
        Character character = Character.characters.get((byte) characterIndex);

        JPanel weaponPanel = new JPanel(new BorderLayout());
        // price panel
        JPanel weaponPricePanel = new JPanel(new GridLayout(1, 7));
        weaponPricePanel.add(new JLabel());
        JLabel weaponPriceLabel1 = new JLabel("$ 6", SwingConstants.CENTER);
        weaponPriceLabel1.setFont(new Font("Sans Serif", Font.BOLD, 14));
        weaponPricePanel.add(weaponPriceLabel1);
        JButton buyGunButton1 = new JButton("BOUGHT");
        buyGunButton1.setEnabled(false);
        weaponPricePanel.add(buyGunButton1);
        weaponPricePanel.add(new JLabel());
        JLabel weaponPriceLabel2 = new JLabel("$ 9", SwingConstants.CENTER);
        weaponPriceLabel2.setFont(new Font("Sans Serif", Font.BOLD, 14));
        weaponPricePanel.add(weaponPriceLabel2);
        JButton buyGunButton2 = new JButton("BUY");
        buyGunButton2.addActionListener(e -> {
            if (ToolStore.buyWeapon((byte) (characterIndex))) {
                buyGunButton2.setText("BOUGHT");
                buyGunButton2.setEnabled(false);
                instance.updatePanels(characterIndex, 0);
            }
        });
        if (Server.getUserData().getCharacters()[characterIndex].getUnlockedWeapons()[1]) {
            buyGunButton2.setText("BOUGHT");
            buyGunButton2.setEnabled(false);
        }
        weaponPricePanel.add(buyGunButton2);
        weaponPricePanel.add(new JLabel());
        weaponPanel.add(weaponPricePanel, BorderLayout.NORTH);
        // weapon display panel
        JPanel weaponDisplayPanel = new JPanel(new GridLayout(2, 2));
        // weapon image
        for (int i = 0; i < 2; i++) {
            ImageIcon weaponImageIcon = new ImageIcon(ShopFrame.class.getResource(character.resourcePath + "weapon" + (i + 1) + ".png"));
            Image scaledWeaponImage = weaponImageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
            ImageIcon scaledWeaponImageIcon = new ImageIcon(scaledWeaponImage); // Create a new ImageIcon from the scaled image
            JLabel weaponImageLabel = new JLabel(scaledWeaponImageIcon);
            weaponDisplayPanel.add(weaponImageLabel);
            weaponImageLabel.setToolTipText("Attack Damage: " + (i + 1) * 10);
        }
        for (int i = 0; i < 2; i++) {
            JLabel weaponNameLabel = new JLabel();
            switch (i) {
                case 0:
                    weaponNameLabel = new JLabel("Short-Range Weapon", SwingConstants.CENTER);
                    weaponNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                    break;
                case 1:
                    weaponNameLabel = new JLabel("Long-Range Weapon", SwingConstants.CENTER);
                    weaponNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                    break;
            }
            weaponDisplayPanel.add(weaponNameLabel);
        }
        weaponPanel.add(weaponDisplayPanel, BorderLayout.CENTER);
        weaponPanel.add(new JPanel(), BorderLayout.SOUTH);
        panels[0] = weaponPanel;

        // armour panel
        JPanel armourPanel = new JPanel(new BorderLayout());
        // armour prices
        JPanel armourPricePanel = new JPanel(new GridLayout(1, 6));
        JLabel armour1PriceLabel = new JLabel("$ " + 11, SwingConstants.CENTER);
        armour1PriceLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        armourPricePanel.add(armour1PriceLabel);
        JButton buyArmour1Button = new JButton("BUY");
        buyArmour1Button.addActionListener(e -> {
            if (ToolStore.buyArmour((byte) 1)) {
                instance.updatePanels(characterIndex, 1);
                buyArmour1Button.setText("BOUGHT");
                buyArmour1Button.setEnabled(false);
            }
        });
        if (Server.getUserData().getArmorLevel() >= 1) {
            buyArmour1Button.setText("BOUGHT");
            buyArmour1Button.setEnabled(false);
        }
        armourPricePanel.add(buyArmour1Button);
        JLabel armour2PriceLabel = new JLabel("$ " + 14, SwingConstants.CENTER);
        armour2PriceLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        armourPricePanel.add(armour2PriceLabel);
        JButton buyArmour2Button = new JButton("BUY");
        buyArmour2Button.addActionListener(e -> {
            if (ToolStore.buyArmour((byte) 2)) {
                instance.updatePanels(characterIndex, 1);
                buyArmour2Button.setText("BOUGHT");
                buyArmour2Button.setEnabled(false);
            }
        });
        if (Server.getUserData().getArmorLevel() >= 2) {
            buyArmour2Button.setText("BOUGHT");
            buyArmour2Button.setEnabled(false);
        }
        armourPricePanel.add(buyArmour2Button);
        JLabel armour3PriceLabel = new JLabel("$ " + 18, SwingConstants.CENTER);
        armour3PriceLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        armourPricePanel.add(armour3PriceLabel);
        JButton buyArmour3Button = new JButton("BUY");
        buyArmour3Button.addActionListener(e -> {
            if (ToolStore.buyArmour((byte) 3)) {
                instance.updatePanels(characterIndex, 1);
                buyArmour3Button.setText("BOUGHT");
                buyArmour3Button.setEnabled(false);
            }
        });
        if (Server.getUserData().getArmorLevel() >= 3) {
            buyArmour3Button.setText("BOUGHT");
            buyArmour3Button.setEnabled(false);
        }
        armourPricePanel.add(buyArmour3Button);
        armourPanel.add(armourPricePanel, BorderLayout.NORTH);
        // armour display panel
        JPanel armourDisplayPanel = new JPanel(new GridLayout(2, 3));
        // armour image
        for (int i = 0; i < 3; i++) {
            ImageIcon armourImageIcon = new ImageIcon(ShopFrame.class.getResource("/armour" + (i + 1) + ".png"));
            Image scaledArmourImage = armourImageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
            ImageIcon scaledArmourImageIcon = new ImageIcon(scaledArmourImage); // Create a new ImageIcon from the scaled image
            JLabel armourImageLabel = new JLabel(scaledArmourImageIcon);
            armourDisplayPanel.add(armourImageLabel);
        }
        for (int i = 0; i < 3; i++) {
            JLabel armourNameLabel = new JLabel();
            switch (i) {
                case 0:
                    armourNameLabel = new JLabel("Leather Armour", SwingConstants.CENTER);
                    armourNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                    break;
                case 1:
                    armourNameLabel = new JLabel("Iron Armour", SwingConstants.CENTER);
                    armourNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                    break;
                case 2:
                    armourNameLabel = new JLabel("Gold Armour", SwingConstants.CENTER);
                    armourNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                    break;
            }
            armourDisplayPanel.add(armourNameLabel);
        }
        armourPanel.add(armourDisplayPanel, BorderLayout.CENTER);
        // armour equip panel
        JPanel armourEquipPanel = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < 3; i++) {
            if (Server.getUserData().getArmorLevel() - 1 == i) { // armour is the strongest one available
                JLabel equippedArmourLabel = new JLabel("Equipped!", SwingConstants.CENTER);
                equippedArmourLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
                equippedArmourLabel.setForeground(Color.GREEN);
                armourEquipPanel.add(equippedArmourLabel);
            } else {
                armourEquipPanel.add(new JLabel());
            }
        }
        armourPanel.add(armourEquipPanel, BorderLayout.SOUTH);
        panels[1] = armourPanel;

        JPanel abilityPanel = new JPanel(new BorderLayout());
        // ability price panel
        JPanel abilityPricePanel = new JPanel(new GridLayout(1, 6));
        abilityPricePanel.add(new JLabel());
        abilityPricePanel.add(new JLabel());
        JLabel abilityPriceLabel = new JLabel("$ 11", SwingConstants.CENTER);
        abilityPricePanel.add(abilityPriceLabel);
        JButton buyAbilityButton = new JButton("BUY");
        buyAbilityButton.addActionListener(e -> {
            if (ToolStore.buyAbility((byte) (characterIndex))) {
                instance.updatePanels(characterIndex, 2);
                buyAbilityButton.setText("BOUGHT");
                buyAbilityButton.setEnabled(false);
            }
        });
        if (Server.getUserData().getCharacters()[characterIndex].isSpecialAbilityUnlocked()) {
            buyAbilityButton.setText("BOUGHT");
            buyAbilityButton.setEnabled(false);
        }
        abilityPricePanel.add(buyAbilityButton);
        abilityPricePanel.add(new JLabel());
        abilityPricePanel.add(new JLabel());
        abilityPanel.add(abilityPricePanel, BorderLayout.NORTH);
        // ability display panel
        // ability image
        JPanel abilityDisplayPanel = new JPanel(new BorderLayout());
        ImageIcon abilityImageIcon = new ImageIcon(ShopFrame.class.getResource(character.resourcePath + "specialability.png"));
        Image scaledAbilityImage = abilityImageIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledAbilityImageIcon = new ImageIcon(scaledAbilityImage);
        JLabel abilityImageLabel = new JLabel(scaledAbilityImageIcon);
        abilityDisplayPanel.add(abilityImageLabel, BorderLayout.CENTER);
        JLabel abilityNameLabel = new JLabel(character.ability.name, SwingConstants.CENTER);
        abilityNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        abilityDisplayPanel.add(abilityNameLabel, BorderLayout.SOUTH);
        abilityPanel.add(abilityDisplayPanel, BorderLayout.CENTER);
        // ability info panel
        JPanel abilityInfoPanel = new JPanel();
        JLabel infoLabel = new JLabel("You can use this ability " + character.ability.usageLeft + " times per game.", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        abilityInfoPanel.add(infoLabel);
        abilityPanel.add(abilityInfoPanel, BorderLayout.SOUTH);
        panels[2] = abilityPanel;

        return panels;
    }

    /**
     * Creates the character display panel.
     *
     * @param characterIndex the character index
     * @return the character display panel
     */
    protected static JPanel createCharacterDisplayPanel(int characterIndex) {
        Character character = Character.characters.get((byte) characterIndex);
        String characterName = character.name;
        String characterResourcePath = character.resourcePath;

        JPanel characterDisplayPanel = new JPanel(new BorderLayout());
        // level panel
        JPanel levelPanel = new JPanel(new GridLayout(2, 1));
        JLabel levelLabel = new JLabel("Level " + Server.getUserData().getLevel(), SwingConstants.RIGHT);
        JProgressBar xpBar = new JProgressBar(0, 100);
        xpBar.setValue(Server.getUserData().getXp());
        levelPanel.add(levelLabel);
        levelPanel.add(xpBar);
        characterDisplayPanel.add(levelPanel, BorderLayout.NORTH);

        // character panel
        JPanel characterPanel = new JPanel(new BorderLayout());
        // character image & name panel
        JPanel characterImagePanel = new JPanel(new BorderLayout());
        // character image
        System.out.println("Character Resource Path: " + characterResourcePath);
        ImageIcon imageIcon = new ImageIcon(ShopFrame.class.getResource(characterResourcePath + "skin.png"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 400, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled image
        JLabel imageLabel = new JLabel(scaledImageIcon);
        characterImagePanel.add(imageLabel, BorderLayout.CENTER);
        JLabel characterNameLabel = new JLabel(characterName, SwingConstants.CENTER);
        characterImagePanel.add(characterNameLabel, BorderLayout.SOUTH);
        characterPanel.add(characterImagePanel, BorderLayout.CENTER);
        // character progress panel
        JPanel characterProgressPanel = new JPanel(new BorderLayout());
        JProgressBar characterProgressBar = new JProgressBar(0, 100);
        System.out.println("Progress: " + Server.getUserData().getCharacters()[characterIndex].getProgress());
        characterProgressBar.setValue(Server.getUserData().getCharacters()[characterIndex].getProgress());
        characterProgressPanel.add(characterProgressBar, BorderLayout.CENTER);
        JLabel progressLabel = new JLabel(Server.getUserData().getCharacters()[characterIndex].getProgress() + "%", SwingConstants.CENTER);
        characterProgressPanel.add(progressLabel, BorderLayout.EAST);
        characterPanel.add(characterProgressPanel, BorderLayout.SOUTH);
        characterDisplayPanel.add(characterPanel, BorderLayout.CENTER);

        // character stats panel
        JPanel characterStatsPanel = new JPanel(new GridLayout(8, 1));
        JLabel attackLabel = new JLabel("Attack Power");
        JProgressBar attackBar = new JProgressBar(0, 4);
        attackBar.setValue(Server.getUserData().getCharacters()[characterIndex].getAbilityLevels()[0]);
        attackBar.setStringPainted(true);
        attackBar.setBackground(Color.RED);
        characterStatsPanel.add(attackLabel);
        characterStatsPanel.add(attackBar);

        JLabel defenceLabel = new JLabel("Defence Power");
        JProgressBar defenceBar = new JProgressBar(0, 4);
        defenceBar.setValue(Server.getUserData().getCharacters()[characterIndex].getAbilityLevels()[1]);
        defenceBar.setStringPainted(true);
        defenceBar.setBackground(Color.GREEN);
        characterStatsPanel.add(defenceLabel);
        characterStatsPanel.add(defenceBar);

        JLabel mobilityLabel = new JLabel("Mobility");
        JProgressBar mobilityBar = new JProgressBar(0, 4);
        mobilityBar.setValue(Server.getUserData().getCharacters()[characterIndex].getAbilityLevels()[2]);
        mobilityBar.setStringPainted(true);
        mobilityBar.setBackground(Color.BLUE);
        characterStatsPanel.add(mobilityLabel);
        characterStatsPanel.add(mobilityBar);
        characterStatsPanel.add(new JLabel(""));

        JButton selectButton = new JButton("Select Character");
        selectButton.setToolTipText("Select this character to play with in the FPS mode.");
        selectButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
        selectButton.addActionListener(e -> {
            selectButton.setEnabled(false);
            instance.updatePanels(characterIndex, 0);
        });
        characterStatsPanel.add(selectButton);

        characterDisplayPanel.add(characterStatsPanel, BorderLayout.SOUTH);
        return characterDisplayPanel;
    }

    /**
     * Gets the instance of the ShopFrame class.
     *
     * @return the instance of the ShopFrame class
     */
    public static ShopFrame getInstance() {
        if (instance == null) {
            instance = new ShopFrame();
        }
        instance.updatePanels(0, 0);
        return instance;
    }
}
