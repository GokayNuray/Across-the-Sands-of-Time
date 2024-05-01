package com.halenteck.CombatGame.locations;

import com.halenteck.CombatGame.Location;

import com.halenteck.CombatGame.enemies.SevenHeadedSnake;

public class RepairShop extends Location {

    public RepairShop() {
        super(1, "Repair Shop", new SevenHeadedSnake(), "Tool Kit");

    }
}
