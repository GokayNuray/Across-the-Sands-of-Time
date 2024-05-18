package com.halenteck.CombatGame;

import com.halenteck.combatUI.InGameFrame;
import com.halenteck.server.Server;
import com.halenteck.server.UserCharacterData;
import com.halenteck.server.UserData;

import java.util.Random;

public class Location {

    public boolean isGameOver = false;
    public boolean isGameWon = false;
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
    int playerFrameX;
    int enemyFrameX;

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
        playerFrameX = inGameFrame.playerX;
        enemyFrameX = inGameFrame.enemyX;
        ability = new Ability(characterID);
        abilityActive = false;
        enemyHealth = enemies.health;
        enemyAttackPower = enemies.damage;
        inGameFrame.enemyHealth = enemyHealth;
        inGameFrame.enemyCount = enemyCount;
        inGameFrame.playerHealth = playerHealth;
    }

    public boolean goForward() {

        if (enemyX > playerX + 160) {
            playerX += 80;
            playerFrameX += 50;
            continueTurn();//in this case enemy will move after the character and will be able to move if the player made a valid move
            //the same goes for the enemy
            return true;
        }
        return false;
    }

    public boolean goBackward() {

        if (playerX - 80 > 0) {//to check limits
            playerX -= 80;
            playerFrameX -= 50;
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
                    enemyFrameX -= 50;
                    playedTurn = true;
                }
            }

            if (enemyProcess == 1 && !playedTurn) {// to go backward

                if (enemyX + 80 <= 800) {
                    enemyX += 80;
                    enemyFrameX += 50;
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
            UserData userData = Server.getUserData();
            player.collectItem(locationId);
            int money = userData.getMoney() + enemies.reward;
            userData.setMoney(money);
            short xp = userData.getXp();
            xp += (short) switch (locationId) {
                case 0 -> 15;
                case 1 -> 20;
                case 2 -> 25;
                case 3 -> 40;
                default -> throw new IllegalStateException("Unexpected value: " + locationId);
            };
            xp += (short) (characterID * 10);
            if (xp >= 100) {
                xp -= 100;
                byte level = userData.getLevel();
                level++;
                userData.setLevel(level);
            }
            userData.setXp(xp);
            byte combatLevelReached = (byte) (userData.getCombatLevelReached() + 1);
            userData.setCombatLevelReached(combatLevelReached);
            Server.updateUserData();
            isGameOver = true;
            isGameWon = true;
            updatePanels();
            return;
        }

        if (enemyHealth <= 0) {
            enemyCount--;
            enemyHealth = enemies.health;
            updatePanels();
            return;
        }

        enemyMove();

        if (playerHealth <= 0) {
            isGameOver = true;
            isGameWon = false;
        }

        abilityActive = false;
        updatePanels();
    }

    public void updatePanels() {
        inGameFrame.enemyHealth = enemyHealth;
        inGameFrame.enemyCount = enemyCount;
        inGameFrame.playerHealth = playerHealth;
        inGameFrame.playerX = playerFrameX;
        inGameFrame.enemyX = enemyFrameX;
        inGameFrame.isAbilityActive = abilityActive;

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

    public int getReward() {
        return enemies.reward;
    }
}