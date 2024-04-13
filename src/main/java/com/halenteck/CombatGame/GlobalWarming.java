package com.halenteck.CombatGame;

public class GlobalWarming extends Character {

    public GlobalWarming(Player player) {
        super(player);
        items[0] = false;
        items[1] = false;
        items[2] = false;
        items[3] = false;
        maps[0] = new GreenHouse(player);
        maps[1] = new Volcano(player);
        maps[2] = new Glacier(player);
        maps[3] = new HistoryMuseum(player);

    }
}
