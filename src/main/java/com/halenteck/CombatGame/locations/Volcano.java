package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Dragon;

public class Volcano extends BattLocation {

    public Volcano() {
        super(1, "Volcano", new Dragon(), "Soil");

    }

}
