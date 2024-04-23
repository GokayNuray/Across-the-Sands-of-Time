package com.halenteck.CombatGame;

public abstract class Location {
    protected Player player;
    protected int locationId;
    protected String name;

    /*public Player getPlayer(){
        return player;
    }*/
    public Location(Player player) {
        this.player = player;
    }

    public abstract boolean getLocation();
}