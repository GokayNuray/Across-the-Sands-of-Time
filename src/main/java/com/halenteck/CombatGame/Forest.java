package com.halenteck.CombatGame;

public class Forest extends BattLocation {

    public Forest(Player player) {
        super(player, "Forest", new Vampire(player), "Fire");
    }
}