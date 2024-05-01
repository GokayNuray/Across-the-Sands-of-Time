package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Guardians;

public class Camp extends Location {

    public Camp() {
        super(0, "Camp", new Guardians(), "Food");

    }

}
