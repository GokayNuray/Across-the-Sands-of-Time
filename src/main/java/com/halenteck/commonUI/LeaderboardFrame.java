package com.halenteck.commonUI;

import com.halenteck.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LeaderboardFrame extends JFrame {

    /**
     * Constructor for the LeaderboardFrame class
     */
    LeaderboardFrame() {
        setTitle("Global Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();

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

        JLabel leaderboardLabel = new JLabel("Global Leaderboard", SwingConstants.LEFT);
        leaderboardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        leaderboardLabel.setBackground(new Color(198,152,116));
        leaderboardLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        add(leaderboardLabel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new GridLayout(1, 4));
        titlePanel.setBackground(new Color(198,152,116));
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        emptyLabel.setBackground(new Color(198,152,116));
        titlePanel.add(emptyLabel);
        JLabel levelLabel = new JLabel("Level", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        titlePanel.add(levelLabel);
        JLabel pointsLabel = new JLabel("Points", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        titlePanel.add(pointsLabel);
        titlePanel.add(new JLabel("", SwingConstants.CENTER));
        leaderboardPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Change layout manager to BoxLayout
        Object[] leaderboardInfo = Server.getLeaderboard();
        String[] userNames = (String[]) leaderboardInfo[0];
        byte[] userLevels = (byte[]) leaderboardInfo[1];
        int[] userPoints = (int[]) leaderboardInfo[2];

        for (int i = 0; i < userNames.length; i++) {
            JPanel userPanel = new JPanel(new GridLayout(1, 4));
            userPanel.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) (bounds.getHeight() / 10)));
            JPanel userNamePanel = new JPanel(new BorderLayout());
            userNamePanel.add(new JLabel(String.valueOf((i + 1)), SwingConstants.CENTER), BorderLayout.WEST);
            userNamePanel.add(new JLabel(userNames[i], SwingConstants.CENTER), BorderLayout.CENTER);
            userPanel.add(userNamePanel);
            JLabel levelNumberLabel = new JLabel("" + userLevels[i], SwingConstants.CENTER);
            userPanel.add(levelNumberLabel);
            JLabel pointsNumberLabel = new JLabel("" + userPoints[i], SwingConstants.CENTER);
            userPanel.add(pointsNumberLabel);
            userPanel.setBackground(new Color(213,176,124));
            userNamePanel.setBackground(new Color(213,176,124));
            JButton viewProfileButton = new JButton("View Profile");
            viewProfileButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
            viewProfileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            viewProfileButton.setBackground(new Color(198,152,116));
            int finalI = i;
            viewProfileButton.addActionListener(e -> {
                new UserCard(Server.getUserData(userNames[finalI]));
                dispose();
            });
            userPanel.add(viewProfileButton);
            infoPanel.add(userPanel);
        }
        leaderboardPanel.add(infoPanel, BorderLayout.CENTER);
        // scroller
        JScrollPane scroller = new JScrollPane(leaderboardPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leaderboardPanel.setBackground(new Color(213,176,124));
        scroller.getViewport().setBackground(new Color(198,152,116));
        scroller.getVerticalScrollBar().setBackground(new Color(198,152,116));
        scroller.setBackground(new Color(198,152,116));
        scroller.revalidate();
        scroller.repaint();
        add(scroller, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }

}
