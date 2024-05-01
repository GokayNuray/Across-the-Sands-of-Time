package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.BattLocation;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Library;
import com.halenteck.CombatGame.locations.Office;
import com.halenteck.CombatGame.locations.RepairShop;

public class Boss extends Character {

    public Boss() {

        super((byte) 4, "Boss", "/characters/boss.png", 150, 5,
                new BattLocation[]{new Office(), new RepairShop(), new Library(), new HistoryMuseum()});

    }
}
