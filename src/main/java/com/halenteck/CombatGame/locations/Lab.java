package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Security;

public class Lab extends Location {

    public Lab() {
        super(2, "Lab", new Security(), "Medicine4");

    }
}