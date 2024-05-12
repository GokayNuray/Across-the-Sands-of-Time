package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Library;
import com.halenteck.CombatGame.locations.Office;
import com.halenteck.CombatGame.locations.RepairShop;

public class Boss extends Character {

    public Boss() {

        super((byte) 4, "Esther", "/characters/boss/", 150, 5,
                new Location[]{new Office(), new RepairShop(), new Library(), new HistoryMuseum()});

    }
}
