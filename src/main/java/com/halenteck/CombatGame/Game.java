package com.halenteck.CombatGame;

import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.Server;
import com.halenteck.server.UserData;

public class Game {

    Location location;

    public void start() {
        UserData userData = Server.getUserData();
        byte characterId = userData.getUnlockedCharacterCount();
        Character player;
        switch (characterId) {
            case 1 -> player = new CaveMan();
            case 2 -> player = new Antidote();
            case 3 -> player = new Nazi();
            case 4 -> player = new GlobalWarming();
            case 5 -> player = new Boss();
            default -> throw new IllegalStateException("Unexpected value: " + characterId);
        }
        int lastLocation = userData.getCombatLevelReached();
        location = player.maps[lastLocation % 4];

        location.startGame(player);
    }

    public void goForward() {
        location.goForward();
    }

    public void goBackward() {
        location.goBackward();
    }

    public void shortRangeAttack() {
        location.shortRange();
    }

    public void longRangeAttack() {
        location.longRange();
    }

    public void useAbility() {
        location.useAbility();
    }
}
