package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Merman;

public class River extends Location {

    public River() {
        super(2, "River", new Merman(), "Water");
    }
}
