package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.WolfMan;

public class Tunnel extends Location {

    public Tunnel() {
        super(1, "Tunnel", new WolfMan(), "Medicine2");
    }
}