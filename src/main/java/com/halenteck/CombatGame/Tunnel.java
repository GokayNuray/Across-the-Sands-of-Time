package com.halenteck.CombatGame;

public class Tunnel extends BattLocation {

    public Tunnel(Player player) {
        super(player, "Tunnel", new WolfMan(player), "Medicine2");
    }
}