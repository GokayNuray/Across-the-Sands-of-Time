package com.halenteck.CombatGame;

import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.Server;
import com.halenteck.server.UserData;

public class Game {

    Character player;
    Enemy enemy;

    int playerHealth;
    int playerAttack;
    int enemyHealth;
    int enemyAttack;


    public void start() {
        UserData userData = Server.getUserData();
        byte characterId = userData.getUnlockedCharacterCount();
        switch (characterId) {
            case 1 -> player = new CaveMan(null);
            case 2 -> player = new Panzehir(null);
            case 3 -> player = new Nazi(null);
            case 4 -> player = new GlobalWarming(null);
            case 5 -> player = new Boss(null);
            default -> throw new IllegalStateException("Unexpected value: " + characterId);
        }
        int lastLocation = userData.getCombatLevelReached();
        BattLocation location = player.maps[lastLocation % 4];

        location.startGame(player);
    }


}
