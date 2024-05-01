package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Fishman;

public class River extends BattLocation {

    public River() {
        super(2, "River", new Fishman(), "Water");
    }
}
