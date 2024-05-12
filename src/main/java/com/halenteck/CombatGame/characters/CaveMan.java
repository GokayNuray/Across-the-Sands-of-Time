package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.Cave;
import com.halenteck.CombatGame.locations.Forest;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.River;

public class CaveMan extends Character {

    public CaveMan() {
        super((byte) 0, "Grok", "/characters/caveman/", 50, 1,
                new Location[]{new Forest(), new Cave(), new River(), new HistoryMuseum()});
    }
}
