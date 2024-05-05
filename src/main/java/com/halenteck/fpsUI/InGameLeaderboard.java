package com.halenteck.fpsUI;

import javax.swing.*;
import java.awt.*;

public class InGameLeaderboard extends JPanel {

    public InGameLeaderboard() {
        setSize(600, 400);
        setLayout(new BorderLayout());;

        JPanel titlePanel = new JPanel(new GridLayout(1,5));
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
        JLabel weaponTitle = new JLabel("Weapon");
        weaponTitle.setFont(new Font("Arial", Font.BOLD, 20));
        weaponTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        titlePanel.add(weaponTitle);
        JLabel specialSkillTitle = new JLabel("Special Skill");
        specialSkillTitle.setFont(new Font("Arial", Font.BOLD, 20));
        specialSkillTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        titlePanel.add(specialSkillTitle);
        add(titlePanel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new GridLayout(1,5));

        JPanel firstRowPanel = new JPanel(new GridLayout(10,1));
        for (int i = 0; i < 10; i++) { // add the users as in the current lobby
            JPanel namePanel = new JPanel(new BorderLayout());
            JLabel number = new JLabel("" + (i + 1));
            number.setFont(new Font("Arial", Font.PLAIN, 12));
            namePanel.add(number, BorderLayout.WEST);
            JLabel name = new JLabel("Player " + (i + 1));
            name.setFont(new Font("Arial", Font.PLAIN, 12));
            namePanel.add(name, BorderLayout.CENTER);
            firstRowPanel.add(namePanel);
        }
        leaderboardPanel.add(firstRowPanel);

        JPanel killsPanel = new JPanel(new GridLayout(10,1));
        for (int i = 0; i < 10; i++) { // add the kills of the users in the current lobby
            JLabel kills = new JLabel("" + i);
            kills.setFont(new Font("Arial", Font.PLAIN, 12));
            killsPanel.add(kills);
        }
        leaderboardPanel.add(killsPanel);

        JPanel deathsPanel = new JPanel(new GridLayout(10,1));
        for (int i = 0; i < 10; i++) { // add the deaths of the users in the current lobby
            JLabel deaths = new JLabel("" + i);
            deaths.setFont(new Font("Arial", Font.PLAIN, 12));
            deathsPanel.add(deaths);
        }
        leaderboardPanel.add(deathsPanel);

        JPanel weaponPanel = new JPanel(new GridLayout(10,1));
        for (int i = 0; i < 10; i++) { // add the weapon of the users in the current lobby
            JLabel weapon = new JLabel("Weapon " + i);
            weapon.setFont(new Font("Arial", Font.PLAIN, 12));
            weaponPanel.add(weapon);
        }
        leaderboardPanel.add(weaponPanel);

        JPanel specialSkillPanel = new JPanel(new GridLayout(10,1));
        for (int i = 0; i < 10; i++) { // add the special skill of the users in the current lobby
            JLabel specialSkill = new JLabel("Special Skill " + i);
            specialSkill.setFont(new Font("Arial", Font.PLAIN, 12));
            specialSkillPanel.add(specialSkill);
        }
        leaderboardPanel.add(specialSkillPanel);
        leaderboardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        add(leaderboardPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
