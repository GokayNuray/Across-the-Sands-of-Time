package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.Glacier;
import com.halenteck.CombatGame.locations.GreenHouse;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Volcano;

public class GlobalWarming extends Character {

    public GlobalWarming() {
        super((byte) 3, "Lyra", "/characters/globalwarming/", 100, 4,
                new Location[]{new GreenHouse(), new Volcano(), new Glacier(), new HistoryMuseum()});

    }
}
