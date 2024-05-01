package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Security;

public class Lab extends BattLocation {

    public Lab() {
        super(2, "Lab", new Security(), "Medicine4");

    }
}