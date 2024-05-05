//package com.halenteck.commonUI;
//
//import javax.swing.*;
//import java.awt.*;
//
//import com.halenteck.server.UserCharacterData;
//import com.halenteck.server.UserData;
//
//public class CharacterCollection extends JFrame{
//
//    private static final int FRAME_WIDTH = 800;
//    private static final int FRAME_HEIGHT = 500;
//    // private characters = Character.getCharacters();
//
//    public CharacterCollection() {
//
//        setSize(FRAME_WIDTH, FRAME_HEIGHT);
//        setTitle("User Card");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // main panel
//        JPanel collectionPanel = new JPanel(new GridLayout(3,2));
//
//        for (int i = 0; i < 5; i++) {
//            collectionPanel.add(createCharacterPanel(i));
////            if (!UserData.getUnlockedCharacterCount() < UserCharacterData.getCharacterId()) { // character is not unlocked
////                // show blurry image and lock icon
////            }
//        }
//
//        JButton returnButton = new JButton("Return to Stats Page");
//        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
//        String finalUserName = LogInFrame.userName;
//        returnButton.addActionListener(e -> {
//            new UserCard(finalUserName);
//            dispose();
//        });
//
//        collectionPanel.add(returnButton);
//
//        add(collectionPanel, BorderLayout.CENTER); // game in the background to be added to other sections later
//
//
//        setVisible(true);
//    }
//
//    public JPanel createCharacterPanel(int characterID) {
//
//        // chaaracter-based panel
//        JPanel characterPanel = new JPanel(new GridLayout(3,1));
//        Character character = Player.characters[characterID];
//
//        // character image
//        ImageIcon characterIcon = character.getImageIcon();
//        Image scaledImage = characterIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
//        ImageIcon scaledCharacterIcon = new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled image
//        JLabel imageLabel = new JLabel(scaledCharacterIcon);
//
//        // tool tip for character description
//        JTextArea characterInfo = new JTextArea(character.getDescription());
//        characterInfo.setLineWrap(true); // Enable line wrapping
//        characterInfo.setWrapStyleWord(true); // Wrap text at word boundaries (optional)
//        characterInfo.setEditable(false);
//        characterInfo.setFont(new Font("Sans Serif", Font.PLAIN, 15));
//        imageLabel.setToolTipText(character.getDescription()); // info when mouse is hovered on character image
//        characterPanel.add(imageLabel);
//
//        // character name and items
//        JPanel infoPanel = new JPanel(new GridLayout(2,1));
//        JLabel characterLabel = new JLabel(character.getName(), SwingConstants.CENTER);
//        characterLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
//        infoPanel.add(characterLabel);
//
//        JPanel itemPanel = new JPanel(new GridLayout(2,3));
//        for (int i = 0; i < 4; i++) {
//
//            if (i == 3) { // fourth item is centered, empty panel needed before it can be placed
//                itemPanel.add(new JPanel()); // empty panel
//            }
//
//            ImageIcon itemIcon = character.getItemImages()[i];
//            Image scaled = characterIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
//            ImageIcon scaledItemIcon = new ImageIcon(scaled); // Create a new ImageIcon from the scaled image
//            JLabel itemIconLabel = new JLabel(scaledItemIcon);
//            // tooltip for item description
//            JLabel itemLabel = new JLabel(character.getItemNames()[i]);
//            itemIconLabel.setToolTipText(itemLabel.getText());
//
//            if (character.getItems()[i]) { // true, item is earned
//                // draw checkmark on it
//            }
//
//            itemPanel.add(itemIconLabel);
//        }
//
//        infoPanel.add(itemPanel);
//
//        characterPanel.add(infoPanel);
//
//        // weapon panel
//        JPanel weaponPanel = new JPanel(new GridLayout(2,1));
//
//        for (int i = 0; i < 2; i++) {
//            JPanel weaponInfoPanel = new JPanel(new GridLayout(2,1));
//            // weapon image
//            ImageIcon weaponIcon = character.getWeapons[i].getImage();
//            Image scaled = weaponIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scales to 150 width, 100 height while maintaining aspect ratio
//            ImageIcon scaledWeaponIcon = new ImageIcon(scaled); // Create a new ImageIcon from the scaled image
//            JLabel weaponIconLabel = new JLabel(scaledWeaponIcon);
//            // weapon name
//            JLabel weaponLabel = new JLabel(character.getWeapons[i].getName());
//            // tool tip text for the image when hovered
//            weaponIconLabel.setToolTipText(weaponLabel.getText());
//            weaponInfoPanel.add(weaponIconLabel);
//            if (getWeapons[i].isBought()) {
//                // draw checkmark on it
//            }
//
//            if (getWeapons[i].isActive()) {
//                weaponInfoPanel.add(new JLabel("Active"));
//            }
//            else {
//                weaponInfoPanel.add(new JPanel()); // empty panel
//            }
//
//            weaponPanel.add(weaponInfoPanel);
//        }
//
//        characterPanel.add(weaponPanel);
//
//        return characterPanel;
//    }
//
//}
