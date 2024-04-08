package com.halenteck;

import javax.swing.*;
import java.awt.*;

public class UserCard extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;
    private String userName;

    UserCard(String userName) {

        userName = this.userName;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("User Card");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // User Card Panel

        // Return Panel
        JPanel returnPanel = new JPanel(new GridLayout(1,3));
        JButton returnButton = new JButton("Return to Game Selection Menu");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        String finalUserName = userName;
        returnButton.addActionListener(e -> {
            new GameSelectionMenu(finalUserName);
            dispose();
        });
        returnPanel.add(new JPanel());
        returnPanel.add(new JPanel());
        returnPanel.add(returnButton);

        add(returnPanel, BorderLayout.SOUTH);


    }
}
