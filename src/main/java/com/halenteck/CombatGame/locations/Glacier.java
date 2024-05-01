package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.PolarBear;

public class Glacier extends BattLocation {

    public Glacier() {
        super(2, "Glacier", new PolarBear(), "Ice");

    }

}
