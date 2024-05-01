package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;
import com.halenteck.CombatGame.enemies.Zombie;


public class Tent extends BattLocation {

    public Tent() {
        super(0, "Tent", new Zombie(), "Medicine1");

    }

}