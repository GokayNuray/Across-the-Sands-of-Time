package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Tank;

public class Berlin extends Location {

    public Berlin() {
        super(1, "Berlin", new Tank(), "Grenade");
    }
}