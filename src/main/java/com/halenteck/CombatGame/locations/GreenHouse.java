package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.CarnivorousPlant;

public class GreenHouse extends BattLocation {

    public GreenHouse() {
        super(0, "GreenHouse", new CarnivorousPlant(), "Seed");

    }

}
