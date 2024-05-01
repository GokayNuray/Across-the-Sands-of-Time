package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.BattLocation;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.Glacier;
import com.halenteck.CombatGame.locations.GreenHouse;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Volcano;

public class GlobalWarming extends Character {

    public GlobalWarming() {
        super((byte) 3, "Global Warming", "/characters/globalwarming.png", 100, 4,
                new BattLocation[]{new GreenHouse(), new Volcano(), new Glacier(), new HistoryMuseum()});

    }
}
