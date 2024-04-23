package com.halenteck.CombatGame;

public class Market extends BattLocation {

    public Market(Player player) {
        super(player, "Market", new Cashier(player), "Energy Drink");

    }

}
