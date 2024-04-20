package com.halenteck.CombatGame;

public class HistoryMuseum extends BattLocation {

    public HistoryMuseum(Player player) {
        super(player, "History Museum", new BossEnemy(player), "Clock");

    }
}
