package com.halenteck.CombatGame;


import com.halenteck.server.Server;
import com.halenteck.server.UserCharacterData;
import com.halenteck.server.UserData;

public class ToolStore {

    public static boolean buyArmour(int type) {
        UserData userData = Server.getUserData();
        if (userData.getArmorLevel() >= type) {
            return false;
        }
        Armour armour = new Armour(type);
        if (userData.getMoney() >= armour.price) {
            userData.setMoney(userData.getMoney() - armour.price);
            userData.setArmorLevel((byte) type);
            Server.updateUserData();
            return true;
        }
        return false;
    }

    public static boolean buyWeapon(byte CharacterID) {
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[CharacterID];
        Character character = Character.characters.get(CharacterID);
        if (characterData.getUnlockedWeapons()[1]) {
            return false;
        }
        if (userData.getMoney() >= character.weapons[1].price) {
            userData.setMoney(userData.getMoney() - character.weapons[1].price);
            characterData.getUnlockedWeapons()[1] = true;
            Server.updateUserData();
            return true;
        }
        return false;
    }

    public static boolean buyAbility(byte CharacterID) {
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[CharacterID];
        Ability ability = new Ability(CharacterID);
        if (characterData.isSpecialAbilityUnlocked()) {
            return false;
        }
        if (userData.getMoney() >= ability.price) {
            userData.setMoney(userData.getMoney() - ability.price);
            characterData.setSpecialAbilityUnlocked(true);
            Server.updateUserData();
            return true;
        }
        return false;
    }

    public static boolean upgradeAttack(byte CharacterID) {//bu bir button'a bağlı olacak
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[CharacterID];
        byte attackLevel = characterData.getAbilityLevels()[0];
        if (attackLevel >= 3) {
            return false;
        }
        if (userData.getMoney() >= 4 * (attackLevel + 1)) {
            characterData.getAbilityLevels()[0] = (byte) (attackLevel + 1);
            userData.setMoney(userData.getMoney() - 4 * (attackLevel + 1));
            Server.updateUserData();
            return true;
        }
        return false;
    }

    public static boolean upgradeDefence(byte CharacterID) {//bu bir button'a baplı olacak
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[CharacterID];
        byte attackLevel = characterData.getAbilityLevels()[1];
        if (attackLevel >= 3) {
            return false;
        }
        if (userData.getMoney() >= 4 * (attackLevel + 1)) {
            characterData.getAbilityLevels()[1] = (byte) (attackLevel + 1);
            userData.setMoney(userData.getMoney() - 4 * (attackLevel + 1));
            Server.updateUserData();
            return true;
        }
        return false;
    }

    public static boolean upgradeMobility(byte CharacterID) {//bu bir button'a bağlı olacak
        UserData userData = Server.getUserData();
        UserCharacterData characterData = userData.getCharacters()[CharacterID];
        byte attackLevel = characterData.getAbilityLevels()[2];
        if (attackLevel >= 3) {
            return false;
        }
        if (userData.getMoney() >= 4 * (attackLevel + 1)) {
            characterData.getAbilityLevels()[2] = (byte) (attackLevel + 1);
            userData.setMoney(userData.getMoney() - 4 * (attackLevel + 1));
            Server.updateUserData();
            return true;
        }
        return false;
    }

}