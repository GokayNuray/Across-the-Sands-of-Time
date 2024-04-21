package com.halenteck.CombatGame;

public class Library extends BattLocation {

    public Library(Player player) {
        super(player, "Library", new Librarian(player), "Formula book");

    }
}