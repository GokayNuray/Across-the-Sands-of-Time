package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Bear;

public class Cave extends Location {

    public Cave() {
        super(1, "Cave", new Bear(), "Wood");

    }
}
