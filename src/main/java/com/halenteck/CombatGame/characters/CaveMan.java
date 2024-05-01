package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.BattLocation;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.Cave;
import com.halenteck.CombatGame.locations.Forest;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.River;
import com.halenteck.fpsGame.Player;

public class CaveMan extends Character {

    public CaveMan() {
        super((byte) 0, "CaveMan", "/characters/caveman.png", 50, 1,
                new BattLocation[]{new Forest(), new Cave(), new River(), new HistoryMuseum()});
        maps[0] = new Forest();
        maps[1] = new Cave();
        maps[2] = new River();
        maps[3] = new HistoryMuseum();
    }
}
