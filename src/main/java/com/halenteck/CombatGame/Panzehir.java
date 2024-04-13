package com.halenteck.CombatGame;

public class Panzehir extends Character {

    public Panzehir(Player player) {
        super(player);
        items[0] = false;
        items[1] = false;
        items[2] = false;
        items[3] = false;
        maps[0] = new Tent(player);
        maps[1] = new Tunnel(player);
        maps[2] = new Lab(player);
        maps[3] = new HistoryMuseum(player);
    }
}
