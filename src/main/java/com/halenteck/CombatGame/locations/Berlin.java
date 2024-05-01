package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Tank;

public class Berlin extends BattLocation {

    public Berlin() {
        super(1, "Berlin", new Tank(), "Grenade");
    }
}