package com.halenteck.combatUI;

import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.ToolStore;
import com.halenteck.server.Server;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class UpgradeShopFrame extends JFrame {

    private JPanel characterDisplayPanel = new JPanel();
    private JPanel shopPanel = new JPanel(new BorderLayout());
    private static UpgradeShopFrame instance;

    // constructor
    public UpgradeShopFrame() {
        // set up frame
        setTitle("Upgrade Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // shortcut for returning to original shop menu
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    UpgradeShopFrame.getInstance().setVisible(false);
                    ShopFrame.getInstance().setVisible(true);
                }
            }
        });

        AtomicInteger characterSelection = new AtomicInteger(0);

        // character-based display panels
        JPanel[] characterDisplayPanels = new JPanel[Server.getUserData().getUnlockedCharacterCount()];
        for (int i = 0; i < characterDisplayPanels.length; i++) {
            characterDisplayPanels[i] = ShopFrame.createCharacterDisplayPanel(i);
        }

        JPanel[] abilityPanels = new JPanel[Server.getUserData().getUnlockedCharacterCount()];
        for (int i = 0; i < abilityPanels.length; i++) {
            abilityPanels[i] = createAbilityPanel(i);
        }

        // shop panel
        JPanel shopPanel = new JPanel(new BorderLayout());
        JPanel shopTitlePanel = new JPanel(new GridLayout(1, 2));
        JLabel currencyLabel = new JLabel("Currency: $" + Server.getUserData().getMoney(), SwingConstants.LEFT);
        JLabel shopTitle = new JLabel("Upgrade Abilities", SwingConstants.CENTER);
        shopTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currencyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        shopTitlePanel.add(currencyLabel);
        shopTitlePanel.add(shopTitle);
        shopPanel.add(shopTitlePanel, BorderLayout.NORTH);

        // horizontal slider for switching between character-based panels
        JSlider characterSlider = new JSlider(JSlider.HORIZONTAL, 0, characterDisplayPanels.length - 1, 0);
        shopPanel.add(characterSlider, BorderLayout.SOUTH);
        characterSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int newValue = characterSlider.getValue();
                int adjustedValue = Math.min(newValue, abilityPanels.length - 1); // ensuring value is within bounds
                characterSelection.set(adjustedValue);
                updatePanels(characterSelection.get());
            }
        });
        characterSlider.setMinorTickSpacing(1);

        // initially, first character info (weapon menu) is shown
        shopPanel.add(abilityPanels[0], BorderLayout.CENTER);
        characterDisplayPanel.add(characterDisplayPanels[0]);
        add(characterDisplayPanel, BorderLayout.WEST);
        add(shopPanel, BorderLayout.CENTER);

        // bottom buttons
        JPanel bottomButtonPanel = new JPanel(new GridLayout(2, 5));
        for (int i = 0; i < 5; i++) {
            bottomButtonPanel.add(new JLabel());
        }
        JButton returnButton = new JButton("Return to Shop");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        returnButton.addActionListener(e -> {
            UpgradeShopFrame.getInstance().setVisible(false);
            ShopFrame.getInstance().setVisible(true);
        });
        bottomButtonPanel.add(returnButton);
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
        add(bottomButtonPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }

    private void updatePanels(int characterId) {

        // Update character shop panel based on slider value
        shopPanel.removeAll();
        shopPanel.revalidate();
        JPanel[] panels = new JPanel[]{createAbilityPanel(characterId)};
        shopPanel.add(panels[characterId]);
        shopPanel.revalidate();  // Inform the panel layout needs to be updated

        // Update character display panel based on slider value
        ShopFrame.characterDisplayPanel.removeAll();
        ShopFrame.characterDisplayPanel.revalidate();
        ShopFrame.characterDisplayPanel.add(ShopFrame.createCharacterDisplayPanel(characterId));
        ShopFrame.characterDisplayPanel.revalidate();  // Inform the frame layout needs to be updated
        revalidate();
    }

    private JPanel createAbilityPanel(int characterId) {
        JPanel abilityPanel = new JPanel(new BorderLayout());
        Character character = Character.characters.get(characterId);
        JPanel abilityInfoPanel = new JPanel(new BorderLayout());
        JPanel barPanel = new JPanel(new GridLayout(6, 1));
        int attackPower = 3;
        JLabel attackLabel = new JLabel("Attack Power: " + attackPower, SwingConstants.LEFT);
        JProgressBar attackBar = new JProgressBar(0, 4);
        attackBar.setValue(attackPower);
        attackBar.setStringPainted(true);
        attackBar.setBackground(Color.RED);
        barPanel.add(attackLabel);
        barPanel.add(attackBar);
        int defencePower = Server.getUserData().getCharacters()[characterId].getAbilityLevels()[1];
        JLabel defenceLabel = new JLabel("Defence Power: " + defencePower, SwingConstants.LEFT);
        JProgressBar defenceBar = new JProgressBar(0, 4);
        defenceBar.setValue(defencePower);
        defenceBar.setStringPainted(true);
        defenceBar.setBackground(Color.GREEN);
        barPanel.add(defenceLabel);
        barPanel.add(defenceBar);
        int mobility = Server.getUserData().getCharacters()[characterId].getAbilityLevels()[2];
        JLabel mobilityLabel = new JLabel("Mobility: " + mobility, SwingConstants.LEFT);
        JProgressBar mobilityBar = new JProgressBar(0, 4);
        mobilityBar.setValue(mobility);
        mobilityBar.setStringPainted(true);
        mobilityBar.setBackground(Color.BLUE);
        barPanel.add(mobilityLabel);
        barPanel.add(mobilityBar);
        abilityInfoPanel.add(barPanel, BorderLayout.CENTER);
        JPanel pricePanel = new JPanel(new GridLayout(6, 1));
        JLabel attackPriceLabel = new JLabel("$ " + (1 + Server.getUserData().getCharacters()[characterId].getAbilityLevels()[0]) * 4, SwingConstants.LEFT);
        JButton attackUpgradeButton = new JButton("+");
        attackUpgradeButton.addActionListener(e -> {
            if (ToolStore.upgradeAttack((byte) characterId)) {
                updatePanels(characterId);
            }
        });
        pricePanel.add(attackPriceLabel);
        pricePanel.add(attackUpgradeButton);
        JLabel defencePriceLabel = new JLabel("$ " + (1 + Server.getUserData().getCharacters()[characterId].getAbilityLevels()[1]) * 4, SwingConstants.LEFT);
        JButton defenceUpgradeButton = new JButton("+");
        defenceUpgradeButton.addActionListener(e -> {
            if (ToolStore.upgradeDefence((byte) characterId)) {
                updatePanels(characterId);
            }
        });
        pricePanel.add(defencePriceLabel);
        pricePanel.add(defenceUpgradeButton);
        JLabel mobilityPriceLabel = new JLabel("$ " + (1 + Server.getUserData().getCharacters()[characterId].getAbilityLevels()[2]) * 4, SwingConstants.LEFT);
        JButton mobilityUpgradeButton = new JButton("+");
        mobilityUpgradeButton.addActionListener(e -> {
            if (ToolStore.upgradeMobility((byte) characterId)) {
                updatePanels(characterId);
            }
        });
        pricePanel.add(mobilityPriceLabel);
        pricePanel.add(mobilityUpgradeButton);
        abilityInfoPanel.add(pricePanel, BorderLayout.EAST);
        abilityInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        abilityPanel.add(abilityInfoPanel, BorderLayout.CENTER);

        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel itemLabel = new JLabel("Items", SwingConstants.CENTER);
        itemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        itemPanel.add(itemLabel, BorderLayout.NORTH);
        JPanel itemInfoPanel = new JPanel(new GridLayout(2, 2));
        for (int i = 0; i < 4; i++) {
            JCheckBox itemCheckBox = new JCheckBox("Item " + i);
            if (Server.getUserData().getCharacters()[characterId].getProgress() > 60) {
                itemCheckBox.setSelected(true);
            } else if (Server.getUserData().getCharacters()[characterId].getProgress() > 35 && i < 3) {
                itemCheckBox.setSelected(true);
            } else if (Server.getUserData().getCharacters()[characterId].getProgress() > 15 && i < 2) {
                itemCheckBox.setSelected(true);
            } else if (Server.getUserData().getCharacters()[characterId].getProgress() > 0 && i < 1) {
                itemCheckBox.setSelected(true);
            }
            itemCheckBox.setEnabled(false);
            itemInfoPanel.add(itemCheckBox);
        }
        itemPanel.add(itemInfoPanel, BorderLayout.CENTER);
        abilityPanel.add(itemPanel, BorderLayout.EAST);
        return abilityPanel;
    }

    public static UpgradeShopFrame getInstance() {
        if (instance == null) {
            instance = new UpgradeShopFrame();
        }
        return instance;
    }
}