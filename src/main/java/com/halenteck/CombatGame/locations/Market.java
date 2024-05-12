package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.Cashier;

public class Market extends Location {

    public Market() {
        super(2, "Market", new Cashier(), "Receipt");

    }

}
