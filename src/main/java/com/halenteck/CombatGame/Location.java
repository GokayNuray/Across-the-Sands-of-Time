package com.halenteck.CombatGame;

import com.halenteck.combatUI.InGameFrame;
import com.halenteck.server.Server;
import com.halenteck.server.UserCharacterData;
import com.halenteck.server.UserData;

import java.util.Random;

public class Location {

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameOver = false;
    protected int locationId;
    protected String name;
    protected Enemy enemies;
    protected String award;

    Character player;
    byte characterID;
    int playerHealth;
    int playerAttackPower;
    int playerDefense;
    int playerShortRangeDamage;
    int playerLongRangeDamage;
    protected int playerX;
    Ability ability;
    protected boolean abilityActive;
    protected int enemyCount;
    protected int enemyX;
    int enemyHealth;
    int enemyAttackPower;
    InGameFrame inGameFrame;
    public Location(int locationId, String name, Enemy enemies, String award) {
        this.locationId = locationId;
        this.name = name;
        this.enemies = enemies;
        this.award = award;
        enemyCount = enemies.enemyCount();
    }

    public void startGame(Character player, InGameFrame inGameFrame) {
        this.player = player;
        this.inGameFrame = inGameFrame;
        characterID = player.characterID;
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[characterID];
        playerHealth = player.health;
        playerAttackPower = player.attackPower + characterData.getAbilityLevels()[0];
        playerDefense = new Armour(userData.getArmorLevel()).defence + characterData.getAbilityLevels()[1];
        playerShortRangeDamage = player.shortRangeDamage;
        playerLongRangeDamage = player.longRangeDamage;
        playerX = 40;
        enemyX = 440;
        ability = new Ability(characterID);
        abilityActive = false;
        enemyHealth = enemies.health;
        enemyAttackPower = enemies.damage;
    }

    public boolean goForward() {

        if (enemyX > playerX + 160) {
            playerX += 80;
            continueTurn();//in this case enemy will move after the character and will be able to move if the player made a valid move
            //the same goes for the enemy
            return true;
        }
        return false;
    }

    public boolean goBackward() {

        if (playerX - 80 > 0) {//to check limits
            playerX -= 80;
            continueTurn();
            return true;
        }
        return false;
    }


    public void shortRange() {

        int extraDamageForShortRange;
        int distance = Math.abs(playerX - enemyX);

        if (distance == 400) {
            extraDamageForShortRange = 0;
        } else if (distance == 320) {
            extraDamageForShortRange = 1;
        } else if (distance == 240) {
            extraDamageForShortRange = 2;
        } else if (distance == 160) {
            extraDamageForShortRange = 3;
        } else if (distance == 480) {
            extraDamageForShortRange = -1;
        } else if (distance == 560) {
            extraDamageForShortRange = -2;
        } else {
            extraDamageForShortRange = -3;
        }
        enemyHealth -= playerShortRangeDamage + extraDamageForShortRange + playerAttackPower;

        continueTurn();
    }

    public void longRange() {

        int addDamageForLongRange;
        int distance = Math.abs(playerX - enemyX);
        if (Math.abs(distance) == 640) {
            addDamageForLongRange = 3;
        } else if (Math.abs(distance) == 560) {
            addDamageForLongRange = 2;
        } else if (Math.abs(distance) == 480) {
            addDamageForLongRange = 1;
        } else if (Math.abs(distance) == 400) {
            addDamageForLongRange = 0;
        } else if (Math.abs(distance) == 320) {
            addDamageForLongRange = -1;
        } else if (Math.abs(distance) == 240) {
            addDamageForLongRange = -2;
        } else {
            addDamageForLongRange = -3;
        }

        enemyHealth -= playerLongRangeDamage + addDamageForLongRange + playerAttackPower;

        continueTurn();
    }

    public boolean useAbility() {
        if (!ability.use()) {
            return false;
        }
        abilityActive = true;
        continueTurn();
        return true;
    }

    public void enemyMove() {

        boolean playedTurn = false;
        Random rand = new Random();

        while (playedTurn == false) {

            int enemyProcess = rand.nextInt(3);

            if (abilityActive && player.characterID == 3) {

                enemyProcess = rand.nextInt(2);
            }

            if (abilityActive && player.characterID == 4) {

                enemyProcess = 2;
            }

            if (abilityActive && player.characterID == 5) {

                playedTurn = true;
            }

            if (enemyProcess == 0 && !playedTurn) { // to go forward
                if (enemyX - 160 != playerX) {
                    enemyX -= 80;
                    playedTurn = true;
                }
            }

            if (enemyProcess == 1 && !playedTurn) {// to go backward

                if (enemyX + 80 <= 800) {
                    enemyX += 80;
                    playedTurn = true;
                }
            }

            if (enemyProcess == 2 && !playedTurn) {//to attack

                int extraDamage;
                int distance = Math.abs(playerX - enemyX);
                if (distance == 160) {
                    extraDamage = 4;
                } else if (distance == 240) {
                    extraDamage = 3;
                } else if (distance == 320) {
                    extraDamage = 2;
                } else if (distance == 400) {
                    extraDamage = 1;
                } else {
                    extraDamage = 0;
                }

                if (abilityActive && (characterID == 1 || characterID == 2)) {
                    extraDamage /= 2;
                }

                playerHealth -= enemyAttackPower + extraDamage - playerDefense;

                playedTurn = true;
            }
        }
    }

    public void continueTurn() {

        if (enemyHealth <= 0 && enemyCount <= 0) {
            player.collectItem(locationId);
            int money = Server.getUserData().getMoney() + enemies.reward;
            Server.getUserData().setMoney(money);
            Server.updateUserData();
            isGameOver = true;
            return;
        }

        if (enemyHealth <= 0) {
            enemyCount--;
            enemyHealth = enemies.health;
            return;
        }

        enemyMove();

        if (playerHealth <= 0) {
            isGameOver = true;
        }

        abilityActive = false;
        inGameFrame.updatePanels();
    }

    public int getLocationId() {
        return locationId;
    }

    public Enemy getEnemies() {
        return enemies;
    }

    public String getAward() {
        return award;
    }

    public Character getPlayer() {
        return player;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public Ability getAbility() {
        return ability;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }
}