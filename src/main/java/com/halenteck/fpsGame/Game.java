package com.halenteck.fpsGame;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Player> team1;
    private ArrayList<Player> team2;
    private int team1Kills;
    private int team2Kills;
    private boolean isRunning;

    public Game() {
        this.players = new ArrayList<>();
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
        team1Kills = 0;
        team2Kills = 0;
        this.isRunning = true;
    }

    public void loop() {
        while (isRunning)
        {
            //TODO: Create a game loop.
        }
    }

    public void addPlayer(Player player) {
        if (players.size() < 10) {
            players.add(player);

            if(team1.size() == team2.size() && team1.size() < 5)
            {
                team1.add(player);
            }
            if (team1.size() < team2.size())
            {
                team1.add(player);
            }
            if(team2.size() < team1.size())
            {
                team2.add(player);
            }
            System.out.println(player.getName() + " has joined the game.");
        }
    }

    public void announceDeath(Player killed, Player killer) {
        System.out.println(killed.getName() + " has been killed by " + killer.getName());
    }
}
