package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Guardians;

public class Camp extends BattLocation {

    public Camp() {
        super(0, "Camp", new Guardians(), "Food");

    }

}
