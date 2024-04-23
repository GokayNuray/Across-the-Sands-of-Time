package com.halenteck.CombatGame;

public class Tent extends BattLocation {

    public Tent(Player player) {
        super(player, "Tent", new Zombie(player), "Medicine1");

    }

}