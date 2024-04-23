package com.halenteck.CombatGame;

public class Lab extends BattLocation {

    public Lab(Player player) {
        super(player, "Lab", new Security(player), "Medicine4");

    }
}