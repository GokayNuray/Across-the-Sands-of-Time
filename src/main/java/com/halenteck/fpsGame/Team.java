package com.halenteck.fpsGame;

import java.util.ArrayList;

public class Team {
    private String name;
    private int totalKills;
    private ArrayList<Player> members;

    public Team() {
        members = new ArrayList<>();
        totalKills = 0;
    }

    public void incrementScore() {
        totalKills++;
    }

    public void addPlayer(Player player) {
        members.add(player);
    }

    public int getTeamSize() {
        return members.size();
    }
}
