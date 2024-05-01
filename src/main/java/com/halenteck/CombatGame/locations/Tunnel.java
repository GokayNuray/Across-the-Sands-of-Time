package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.WolfMan;

public class Tunnel extends BattLocation {

    public Tunnel() {
        super(1, "Tunnel", new WolfMan(), "Medicine2");
    }
}