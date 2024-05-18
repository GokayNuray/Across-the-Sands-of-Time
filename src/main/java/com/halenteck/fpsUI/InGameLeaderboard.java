package com.halenteck.fpsUI;

import com.halenteck.fpsGame.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class InGameLeaderboard extends JPanel {
    public Map<Byte, Player> players;
    /**
     * Constructor for the InGameLeaderboard class
     * @param players the players in the current game
     */
    public InGameLeaderboard(Map<Byte, Player> players) {
        setSize(800, 600);
        setLayout(new BorderLayout());

        this.players = players;

        Collection<Player> playerList = players.values();
        Player[] playerArray = playerList.toArray(new Player[0]);

        JPanel titlePanel = new JPanel(new GridLayout(1, 3));
        JLabel leaderboardTitle = new JLabel("Leaderboard");
        leaderboardTitle.setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        titlePanel.add(leaderboardTitle);
        JLabel killsTitle = new JLabel("Kills");
        killsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        killsTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        titlePanel.add(killsTitle);
        JLabel deathsTitle = new JLabel("Deaths");
        deathsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        deathsTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        titlePanel.add(deathsTitle);
        add(titlePanel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new GridLayout(1, 3));

        JPanel firstRowPanel = new JPanel(new GridLayout(10, 1));
        for (int i = 0; i < playerArray.length; i++) { // add the users as in the current lobby
            JPanel namePanel = new JPanel(new BorderLayout());
            JLabel number = new JLabel((i + 1) + ". ");
            number.setFont(new Font("Arial", Font.PLAIN, 12));
            namePanel.add(number, BorderLayout.WEST);
            JLabel name = new JLabel(playerArray[i].getName(), SwingConstants.CENTER);
            name.setFont(new Font("Arial", Font.PLAIN, 12));
            namePanel.add(name, BorderLayout.CENTER);
            firstRowPanel.add(namePanel);
        }
        leaderboardPanel.add(firstRowPanel);

        JPanel killsPanel = new JPanel(new GridLayout(10, 1));
        for (int i = 0; i < playerArray.length; i++) { // add the kills of the users in the current lobby
            JLabel kills = new JLabel("" + playerArray[i].getKills(), SwingConstants.CENTER);
            kills.setFont(new Font("Arial", Font.PLAIN, 12));
            killsPanel.add(kills);
        }
        leaderboardPanel.add(killsPanel);

        JPanel deathsPanel = new JPanel(new GridLayout(10, 1));
        for (int i = 0; i < playerArray.length; i++) { // add the deaths of the users in the current lobby
            JLabel deaths = new JLabel("" + playerArray[i].getDeaths(), SwingConstants.CENTER);
            deaths.setFont(new Font("Arial", Font.PLAIN, 12));
            deathsPanel.add(deaths);
        }
        leaderboardPanel.add(deathsPanel);
        leaderboardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        add(leaderboardPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
