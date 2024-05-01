package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.CarnivorousPlant;

public class GreenHouse extends Location {

    public GreenHouse() {
        super(0, "GreenHouse", new CarnivorousPlant(), "Seed");

    }

}
