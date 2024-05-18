package com.halenteck.fpsUI;

import com.halenteck.commonUI.GameSelectionMenu;
import com.halenteck.server.Server;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LobbyFrame extends JFrame {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen size

    public static void main(String[] args) {
        try {
            Server.connect();
            Server.login("Babapiiro31", "Gokaynu2!");
            new LobbyFrame(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Constructor for the LobbyFrame class
     * @param rankedGame
     */
    public LobbyFrame(boolean rankedGame) {

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
        getContentPane().setBackground(new Color(198,152,116));
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
        JPanel titlePanel = new JPanel(new GridLayout(1, 4));
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
            int id = Server.createLobby(name);
            new FpsInGame(id);
            dispose();
        });
        titlePanel.add(createLobby);
        titlePanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 50));
        menuPanel.add(titlePanel, BorderLayout.NORTH);

        // server list panel
        JPanel serverListPanel = new JPanel(new GridLayout(10, 1));
        serverListPanel.setPreferredSize(new Dimension((int) (screenSize.getWidth() - 25), (int) (screenSize.getHeight() * 2)));

        for (String lobby : Server.getLobbyList()) { // lobby id, name, player count, creation time

            String[] lobbyData = lobby.split(","); // lobby id, name, player count, creation time

            int id = Integer.parseInt(lobbyData[0]);

            JPanel serverInfoPanel = new JPanel();
            serverInfoPanel.setLayout(new GridLayout(1, 4));
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
                new FpsInGame(id);
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }
}
