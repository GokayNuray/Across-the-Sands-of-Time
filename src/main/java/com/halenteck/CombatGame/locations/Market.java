package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.Cashier;

public class Market extends BattLocation {

    public Market() {
        super(2, "Market", new Cashier(), "Energy Drink");

    }

}
