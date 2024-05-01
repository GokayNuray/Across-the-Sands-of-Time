package com.halenteck.CombatGame.characters;

import com.halenteck.CombatGame.Location;
import com.halenteck.CombatGame.Character;
import com.halenteck.CombatGame.locations.HistoryMuseum;
import com.halenteck.CombatGame.locations.Lab;
import com.halenteck.CombatGame.locations.Tent;
import com.halenteck.CombatGame.locations.Tunnel;

public class Panzehir extends Character {

    public Panzehir() {
        super((byte) 1, "Panzehir", "/characters/panzehir.png", 65, 2,
                new Location[]{new Tent(), new Tunnel(), new Lab(), new HistoryMuseum()});
    }
}
