package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Librarian;

public class Library extends BattLocation {

    public Library() {
        super(2, "Library", new Librarian(), "Formula book");

    }
}