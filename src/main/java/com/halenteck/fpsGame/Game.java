package com.halenteck.fpsGame;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Team redTeam;
    private Team blueTeam;
    private boolean isRunning;

    public Game() {
        this.players = new ArrayList<>();
        this.redTeam = new Team();
        this.blueTeam = new Team();
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

            if(redTeam.getTeamSize() == blueTeam.getTeamSize() && redTeam.getTeamSize() < 5)
            {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if (redTeam.getTeamSize() < blueTeam.getTeamSize())
            {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if(blueTeam.getTeamSize() < redTeam.getTeamSize())
            {
                blueTeam.addPlayer(player);
                player.setTeam(blueTeam);
            }
            System.out.println(player.getName() + " has joined the game.");
        }
    }

    public void announceDeath(Player killed, Player killer) {
        System.out.println(killed.getName() + " has been killed by " + killer.getName());
        killer.incrementKills();
        killed.incrementDeaths();

        if (killer.getTeam() == blueTeam) {
            blueTeam.incrementScore();
        }
        else
        {
            redTeam.incrementScore();
        }
    }
}
