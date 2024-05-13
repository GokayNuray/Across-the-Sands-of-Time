package com.halenteck.CombatGame;

import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.Server;
import com.halenteck.server.UserCharacterData;
import com.halenteck.server.UserData;

import java.util.Map;

public abstract class Character {

    public static Map<Byte, Character> characters = Map.of(
            (byte) 0, new CaveMan(),
            (byte) 1, new Antidote(),
            (byte) 2, new Nazi(),
            (byte) 3, new GlobalWarming(),
            (byte) 4, new Boss()
    );

    public byte characterID;
    public String name;
    public String resourcePath;

    public Ability ability;
    public int health;
    protected int attackPower;
    public boolean[] items = new boolean[4];
    protected Weapons[] weapons = new Weapons[2];//index 0 - short range, index 1 - long range
    public Location[] maps;
    protected int shortRangeDamage;
    protected int longRangeDamage;


    public Character(byte characterId, String name, String resourcePath, int health, int attackPower, Location[] maps) {

        this.characterID = characterId;
        this.ability = new Ability(characterId);
        this.name = name;
        this.resourcePath = resourcePath;
        this.health = health;
        this.attackPower = attackPower;
        this.maps = maps;

        createWeapons(characterId);
    }

    public void createWeapons(int characterId) {
        weapons[0] = new Weapons(2 * characterId);
        weapons[1] = new Weapons(2 * characterId + 1);
        shortRangeDamage = weapons[0].performance;
        longRangeDamage = weapons[1].performance;
    }

    public void collectItem(int i) {//when all the enemies at a location are killed, the item's collected
        items[i] = true;
        setProgress();
    }

    public void setProgress() {
        UserCharacterData characterData = Server.getUserData().getCharacters()[characterID];
        byte characterProgress = characterData.getProgress();
        byte newCharacterProgress;
        if (items[3]) {
            newCharacterProgress = 100;
            if (characterID < 4) {
                unlockNextCharacter();
            }
        } else if (items[2]) {
            newCharacterProgress = 60;
        } else if (items[1]) {
            newCharacterProgress = 35;
        } else if (items[0]) {
            newCharacterProgress = 15;
        } else {
            newCharacterProgress = 0;
        }
        characterData.setProgress((byte) Math.max(characterProgress, newCharacterProgress));
    }

    private void unlockNextCharacter() {
        UserData userData = Server.getUserData();
        UserCharacterData[] characters = userData.getCharacters();
        UserCharacterData[] newCharacters = new UserCharacterData[characters.length + 1];
        System.arraycopy(characters, 0, newCharacters, 0, characters.length);
        UserCharacterData newCharacter = new UserCharacterData((byte) (characterID + 1), (byte) 0, new boolean[]{true, false}, (byte) 0, new byte[3], false);
        newCharacters[characters.length] = newCharacter;
        userData.setCharacters(newCharacters);
        userData.setUnlockedCharacterCount((byte) newCharacters.length);
    }
}
