package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Librarian;

public class Library extends Location {

    public Library() {
        super(2, "Library", new Librarian(), "Book");

    }
}