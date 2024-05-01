package com.halenteck.CombatGame;

import com.halenteck.CombatGame.characters.*;
import com.halenteck.server.CharacterData;
import com.halenteck.server.Server;

import java.util.Map;

public abstract class Character {

    static Map<Byte, Character> characters = Map.of(
            (byte) 0, new CaveMan(),
            (byte) 1, new Panzehir(),
            (byte) 2, new Nazi(),
            (byte) 3, new GlobalWarming(),
            (byte) 4, new Boss()
    );

    byte characterID;
    String name;
    String resourcePath;

    protected Ability ability;
    protected int health;
    protected int attackPower;
    protected boolean[] items = new boolean[4];
    protected Weapons[] weapons = new Weapons[2];//index 0'da short range, index 1'de long range var
    protected BattLocation[] maps;
    protected int shortRangeDamage;
    protected int longRangeDamage;


    public Character(byte characterId, String name, String resourcePath, int health, int attackPower, BattLocation[] maps) {

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
        weapons[0] = new Weapons(characterId + 1);
        weapons[1] = new Weapons(characterId + 2);
        shortRangeDamage = weapons[0].performance;
        longRangeDamage = weapons[1].performance;
    }

    public void collectItem(int i) {//when all the enemies at a location are killed, the item's collected
        items[i] = true;
        increaseProgress();
    }

    public void increaseProgress() {
        CharacterData characterData = Server.getUserData().getCharacters()[characterID];
        byte characterProgress = characterData.getProgress();
        if (items[3]) {
            characterProgress += 40;
        } else if (items[2]) {
            characterProgress += 25;
        } else if (items[1]) {
            characterProgress += 20;
        } else if (items[0]) {
            characterProgress += 15;
        }
        characterData.setProgress(characterProgress);
    }
}
