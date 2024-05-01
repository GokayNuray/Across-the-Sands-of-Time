package com.halenteck.CombatGame;

import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.Server;
import com.halenteck.server.UserData;

public class Game {

    Character player;

    public void start() {
        UserData userData = Server.getUserData();
        byte characterId = userData.getUnlockedCharacterCount();
        switch (characterId) {
            case 1 -> player = new CaveMan();
            case 2 -> player = new Panzehir();
            case 3 -> player = new Nazi();
            case 4 -> player = new GlobalWarming();
            case 5 -> player = new Boss();
            default -> throw new IllegalStateException("Unexpected value: " + characterId);
        }
        int lastLocation = userData.getCombatLevelReached();
        Location location = player.maps[lastLocation % 4];

        location.startGame(player);
    }


}
