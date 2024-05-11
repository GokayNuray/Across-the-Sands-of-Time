package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Lab;
import com.halenteck.CombatGame.locations.Tent;
import com.halenteck.CombatGame.locations.Tunnel;

public class Antidote extends Character {

    public Antidote() {
        super((byte) 1, "Panzehir", "/characters/antidote/", 65, 2,
                new Location[]{new Tent(), new Tunnel(), new Lab(), new HistoryMuseum()});
    }
}