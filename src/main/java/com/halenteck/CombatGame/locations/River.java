package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Fishman;

public class River extends Location {

    public River() {
        super(2, "River", new Fishman(), "Water");
    }
}
