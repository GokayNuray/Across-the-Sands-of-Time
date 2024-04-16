package com.halenteck.server;

import javax.swing.*;

public class ServerTester {

    public static void main(String[] args) {
        FPSServer.connect();

        JFrame frame = new JFrame("FPS Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton joinLobbyButton = new JButton("Join Lobby");
        joinLobbyButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter player name:");
            FPSServer.joinLobby(name);
        });

        panel.add(joinLobbyButton);

        frame.setVisible(true);
    }
}
