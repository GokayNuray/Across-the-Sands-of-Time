package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.Berlin;
import com.halenteck.CombatGame.locations.Camp;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Market;

public class Nazi extends Character {

    public Nazi() {
        super((byte) 2, "Nazi", "/characters/nazi.png", 80, 3,
                new Location[]{new Camp(), new Berlin(), new Market(), new HistoryMuseum()});

    }
}
