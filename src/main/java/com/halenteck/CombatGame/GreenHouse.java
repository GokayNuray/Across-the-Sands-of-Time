package com.halenteck.CombatGame;

public class GreenHouse extends BattLocation {

    public GreenHouse(Player player) {
        super(player, "GreenHouse", new CarnivorousPlant(player), "Seed");

    }

}
