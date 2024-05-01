package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Vampire;

public class Forest extends BattLocation {

    public Forest() {
        super(0, "Forest", new Vampire(), "Fire");
    }
}