package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Vampire;

public class Forest extends Location {

    public Forest() {
        super(0, "Forest", new Vampire(), "Fire");
    }
}