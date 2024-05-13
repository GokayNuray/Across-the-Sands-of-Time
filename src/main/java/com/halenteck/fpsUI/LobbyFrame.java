package com.halenteck.fpsUI;

import com.halenteck.fpsGame.Game;
import com.halenteck.server.Server;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LobbyFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

    public LobbyFrame(boolean rankedGame) {

//        do {// if the server does not have 2 people, keep in loading screen
//            new LoadingScreen();
//        } while (playerCount < 2);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Lobby Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Server.leaveLobby(); // Call close connection method
                System.exit(0); // Explicitly exit the application
            }
        });


        setLayout(new BorderLayout());

        // menu panel
        JPanel menuPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createTitledBorder("Select a Server");
        menuPanel.setBorder(border);

        Border border2 = BorderFactory.createLineBorder(Color.BLACK);

        // title panel
        JPanel titlePanel = new JPanel(new GridLayout(1,4));
        JLabel serverNameLabel = new JLabel("Server Name", SwingConstants.CENTER);
        serverNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        serverNameLabel.setBorder(border2);
        titlePanel.add(serverNameLabel);
        JLabel serverPlayersLabel = new JLabel("Current Players / Capacity", SwingConstants.CENTER);
        serverPlayersLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        serverPlayersLabel.setBorder(border2);
        titlePanel.add(serverPlayersLabel);
        JLabel serverTimeLeftLabel = new JLabel("Time Left", SwingConstants.CENTER);
        serverTimeLeftLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        serverTimeLeftLabel.setBorder(border2);
        titlePanel.add(serverTimeLeftLabel);
        JButton createLobby = new JButton("Create Server");
        createLobby.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter server name:");
            Server.createLobby(Server.getUserData().getPlayerName(), name);
        });
        titlePanel.add(createLobby);
        titlePanel.setPreferredSize(new Dimension(FRAME_WIDTH, 50));
        menuPanel.add(titlePanel, BorderLayout.NORTH);

        // server list panel
        JPanel serverListPanel = new JPanel(new GridLayout(10, 1));
        serverListPanel.setPreferredSize(new Dimension(FRAME_WIDTH - 25, FRAME_HEIGHT * 2));

        for (String lobby : Server.getLobbyList()) {

            String[] lobbyData = lobby.split(","); // lobby id, name, player count, creation time

            JPanel serverInfoPanel = new JPanel();
            serverInfoPanel.setLayout(new GridLayout(1,4));
            JLabel serverName = new JLabel(lobbyData[1], SwingConstants.CENTER); // lobby name
            serverName.setBorder(border2);
            serverInfoPanel.add(serverName);
            JLabel serverPlayers = new JLabel(lobbyData[2] + " / 10", SwingConstants.CENTER);
            serverPlayers.setBorder(border2);
            serverInfoPanel.add(serverPlayers);
            long serverCreationTime = Long.parseLong(lobbyData[3]);
            long timeLeft = 300 - (System.currentTimeMillis() - serverCreationTime) / 1000;
            int minLeft = (int) (timeLeft / 60);
            int secLeft = (int) (timeLeft % 60);
            JLabel serverTimeLeft = new JLabel(minLeft + ":" + secLeft, SwingConstants.CENTER);
            serverTimeLeft.setBorder(border2);
            serverInfoPanel.add(serverTimeLeft);
            JButton joinButton = new JButton("Join");
            joinButton.addActionListener(e -> {
                Server.joinLobby(Server.getUserData().getPlayerName(), Integer.parseInt(lobbyData[0]));
                Server.addServerListener(new Game(null, null, null));
                new FpsInGame();
                dispose();
            });
            serverInfoPanel.add(joinButton);
            serverListPanel.add(serverInfoPanel);
        }

        menuPanel.add(serverListPanel, BorderLayout.CENTER);

        // scroller
        JScrollPane scroller = new JScrollPane(serverListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // Add scroller after positioning

        menuPanel.add(scroller, BorderLayout.CENTER);

        add(menuPanel, BorderLayout.CENTER);
        setVisible(true);

    }
}
