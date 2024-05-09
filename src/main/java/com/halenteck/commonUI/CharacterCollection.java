package com.halenteck.commonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.halenteck.server.Server;
import com.halenteck.server.UserCharacterData;
import com.halenteck.server.UserData;
import com.halenteck.CombatGame.Character;

public class CharacterCollection extends JFrame{

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;
    private UserCharacterData[] characters = Server.getUserData().getCharacters();

    public CharacterCollection() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("User Card");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // shortcut for returning to user card
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    new UserCard();
                    dispose();
                }
            }
        });

        // main panel
        JPanel collectionPanel = new JPanel(new GridLayout(3,2));

        for (int i = 0; i < 5; i++) {
            if (i < characters.length) {
                collectionPanel.add(createCharacterPanel(i));
            }
            else{
                collectionPanel.add(new JPanel());
            }
        }

        JButton returnButton = new JButton("Return to Stats Page");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        String finalUserName = Server.getUserData().getPlayerName();
        returnButton.addActionListener(e -> {
            new UserCard();
            dispose();
        });

        collectionPanel.add(returnButton);

        add(collectionPanel, BorderLayout.CENTER); // game in the background to be added to other sections later


        setVisible(true);
    }

    public JPanel createCharacterPanel(int characterID) {

        // character-based panel
        JPanel characterPanel = new JPanel(new GridLayout(1,3));
        UserCharacterData characterInfo = characters[characterID];
        Character character = Character.characters.get((byte) characterID);

        JPanel mainInfoPanel = new JPanel(new BorderLayout());
        // character image
        ImageIcon characterIcon = new ImageIcon(getClass().getResource(character.resourcePath + "skin.jpg"));
        Image scaledCharacter = characterIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
        ImageIcon scaledCharacterIcon = new ImageIcon(scaledCharacter); // Create a new ImageIcon from the scaled image
        JLabel iconLabel = new JLabel(scaledCharacterIcon);
        mainInfoPanel.add(iconLabel, BorderLayout.CENTER);

        ImageIcon abilityIcon = new ImageIcon(getClass().getResource(character.resourcePath + "specialability.png"));
        Image scaledAbility = abilityIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
        ImageIcon scaledAbilityIcon = new ImageIcon(scaledAbility); // Create a new ImageIcon from the scaled image
        JLabel specialAbility = new JLabel(scaledAbilityIcon);
        mainInfoPanel.add(specialAbility, BorderLayout.SOUTH);

//        // tool tip for character description
//        JTextArea characterInfo = new JTextArea(character.getDescription());
//        characterInfo.setLineWrap(true); // Enable line wrapping
//        characterInfo.setWrapStyleWord(true); // Wrap text at word boundaries (optional)
//        characterInfo.setEditable(false);
//        characterInfo.setFont(new Font("Sans Serif", Font.PLAIN, 15));
//        imageLabel.setToolTipText(character.getDescription()); // info when mouse is hovered on character image
        characterPanel.add(mainInfoPanel);

        // character name and items
        JPanel infoPanel = new JPanel(new GridLayout(2,1));
        JLabel characterLabel = new JLabel("Items", SwingConstants.CENTER);
        characterLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        infoPanel.add(characterLabel);

        JPanel itemPanel = new JPanel(new GridLayout(2,3));
        for (int i = 0; i < 5; i++) {

            if (i == 3) { // fourth item is centered, empty panel needed before it can be placed
                itemPanel.add(new JPanel()); // empty panel
            }

            else {
                JCheckBox itemBox = new JCheckBox();
                if (i <= 2 ? character.items[i] : character.items[3]) {
                    itemBox.setSelected(true);
                }
                else {
                    itemBox.setEnabled(false); // cannot be checked
                }
                itemPanel.add(itemBox);
            }
        }

        itemPanel.add(new JPanel()); // empty panel
        infoPanel.add(itemPanel);
        characterPanel.add(infoPanel);

        // weapon panel
        JPanel weaponPanel = new JPanel(new GridLayout(2,1));

        for (int i = 0; i < 2; i++) {
            JPanel weaponInfoPanel = new JPanel(new GridLayout(2,1));
            // weapon image
            ImageIcon weaponIcon = new ImageIcon(getClass().getResource(character.resourcePath + "weapon" + (i + 1) + ".png"));
            Image scaled = weaponIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
            ImageIcon scaledWeaponIcon = new ImageIcon(scaled); // Create a new ImageIcon from the scaled image
            JLabel weaponIconLabel = new JLabel(scaledWeaponIcon);
            weaponInfoPanel.add(weaponIconLabel);
            if (characterInfo.getUnlockedWeapons()[i]) {
                weaponInfoPanel.add(new JLabel("Unlocked"));
            }
            else {
                weaponInfoPanel.add(new JLabel("Locked"));
            }
            weaponInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            weaponPanel.add(weaponInfoPanel);
        }

        characterPanel.add(weaponPanel);
        characterPanel.setBorder(BorderFactory.createTitledBorder(character.name));

        return characterPanel;
    }

}
