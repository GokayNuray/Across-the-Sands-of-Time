package com.halenteck.CombatGame;

public class Camp extends BattLocation {

    public Camp(Player player) {
        super(player, "Camp", new Guardians(player), "Food");

    }

}
