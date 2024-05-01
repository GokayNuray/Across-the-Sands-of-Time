package com.halenteck.server;

public class UserCharacterData {

    byte characterId;
    byte progress;
    boolean[] unlockedWeapons;
    byte lastSelectedWeapon;
    byte[] abilityLevels;
    boolean isSpecialAbilityUnlocked;

    public UserCharacterData(byte characterId, byte progress, boolean[] unlockedWeapons, byte lastSelectedWeapon, byte[] abilityLevels, boolean isSpecialAbilityUnlocked) {
        this.characterId = characterId;
        this.progress = progress;
        this.unlockedWeapons = unlockedWeapons;
        this.lastSelectedWeapon = lastSelectedWeapon;
        this.abilityLevels = abilityLevels;
        this.isSpecialAbilityUnlocked = isSpecialAbilityUnlocked;
    }


    public byte getCharacterId() {
        return characterId;
    }

    public void setCharacterId(byte characterId) {
        this.characterId = characterId;
    }

    public byte getProgress() {
        return progress;
    }

    public void setProgress(byte progress) {
        this.progress = progress;
    }

    public boolean[] getUnlockedWeapons() {
        return unlockedWeapons;
    }

    public void setUnlockedWeapons(boolean[] unlockedWeapons) {
        this.unlockedWeapons = unlockedWeapons;
    }

    public byte getLastSelectedWeapon() {
        return lastSelectedWeapon;
    }

    public void setLastSelectedWeapon(byte lastSelectedWeapon) {
        this.lastSelectedWeapon = lastSelectedWeapon;
    }

    public byte[] getAbilityLevels() {
        return abilityLevels;
    }

    public void setAbilityLevels(byte[] abilityLevels) {
        this.abilityLevels = abilityLevels;
    }

    public boolean isSpecialAbilityUnlocked() {
        return isSpecialAbilityUnlocked;
    }

    public void setSpecialAbilityUnlocked(boolean specialAbilityUnlocked) {
        isSpecialAbilityUnlocked = specialAbilityUnlocked;
    }
}
