package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Bear;

public class Cave extends BattLocation {

    public Cave() {
        super(1, "Cave", new Bear(), "Wood");

    }
}
