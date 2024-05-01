package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.enemies.Zombie;


public class Tent extends Location {

    public Tent() {
        super(0, "Tent", new Zombie(), "Medicine1");

    }

}