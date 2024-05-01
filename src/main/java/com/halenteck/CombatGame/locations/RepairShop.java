package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.BattLocation;

import com.halenteck.CombatGame.enemies.SevenHeadedSnake;

public class RepairShop extends BattLocation {

    public RepairShop() {
        super(1, "Repair Shop", new SevenHeadedSnake(), "Tool Kit");

    }
}
