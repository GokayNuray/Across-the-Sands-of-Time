package com.halenteck.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UserData {

    private String playerName;
    private boolean isFirstTime;
    private byte level;
    private short xp;
    private int rankPoints;
    private int money;
    private byte unlockedCharacterCount;
    private CharacterDataa[] characters;
    private byte unlockedWeaponCount;
    private byte[] unlockedWeapons;
    private byte lastSelectedCharacter;
    private byte combatLevelReached;

    public UserData(String playerName,
                    boolean isFirstTime,
                    byte level, short xp,
                    int rankPoints,
                    int money,
                    byte unlockedCharacterCount, CharacterDataa[] characters,
                    byte unlockedWeaponCount, byte[] unlockedWeapons,
                    byte lastSelectedCharacter,
                    byte combatLevelReached
    ) {
        this.playerName = playerName;
        this.isFirstTime = isFirstTime;
        this.level = level;
        this.xp = xp;
        this.rankPoints = rankPoints;
        this.money = money;
        this.unlockedCharacterCount = unlockedCharacterCount;
        this.characters = characters;
        this.unlockedWeaponCount = unlockedWeaponCount;
        this.unlockedWeapons = unlockedWeapons;
        this.lastSelectedCharacter = lastSelectedCharacter;
        this.combatLevelReached = combatLevelReached;
    }

    public static UserData readUserData(DataInputStream in) throws IOException {
        String playerName = in.readUTF();
        boolean isFirstTime = in.readBoolean();
        byte level = in.readByte();
        short xp = in.readShort();
        int rankPoints = in.readInt();
        int money = in.readInt();
        byte unlockedCharacterCount = in.readByte();
        CharacterDataa[] characters = new CharacterDataa[unlockedCharacterCount];
        for (int i = 0; i < unlockedCharacterCount; i++) {
            byte characterId = in.readByte();
            byte characterProgress = in.readByte();
            boolean[] unlockedWeapons = new boolean[in.readByte()];
            for (int j = 0; j < unlockedWeapons.length; j++) {
                unlockedWeapons[j] = in.readBoolean();
            }
            byte lastSelectedWeapon = in.readByte();
            byte armorLevel = in.readByte();
            byte[] abilityLevels = new byte[in.readByte()];
            for (int j = 0; j < abilityLevels.length; j++) {
                abilityLevels[j] = in.readByte();
            }
            boolean isSpecialAbilityUnlocked = in.readBoolean();
            characters[i] = new CharacterDataa(characterId, characterProgress, unlockedWeapons, lastSelectedWeapon, armorLevel, abilityLevels, isSpecialAbilityUnlocked);
        }
        byte unlockedWeaponCount = in.readByte();
        byte[] unlockedWeapons = new byte[unlockedWeaponCount];
        for (int i = 0; i < unlockedWeaponCount; i++) {
            unlockedWeapons[i] = in.readByte();
        }
        byte lastSelectedCharacter = in.readByte();
        byte combatLevelReached = in.readByte();
        return new UserData(playerName, isFirstTime, level, xp, rankPoints, money, unlockedCharacterCount, characters, unlockedWeaponCount, unlockedWeapons, lastSelectedCharacter, combatLevelReached);
    }

    public static void writeUserData(DataOutputStream out, UserData userData) throws IOException {
        out.writeUTF(userData.playerName);
        out.writeBoolean(userData.isFirstTime);
        out.writeByte(userData.level);
        out.writeShort(userData.xp);
        out.writeInt(userData.rankPoints);
        out.writeInt(userData.money);
        out.writeByte(userData.unlockedCharacterCount);
        for (CharacterDataa character : userData.characters) {
            out.writeByte(character.characterId);
            out.writeByte(character.progress);
            out.writeByte(character.unlockedWeapons.length);
            for (boolean unlockedWeapon : character.unlockedWeapons) {
                out.writeBoolean(unlockedWeapon);
            }
            out.writeByte(character.lastSelectedWeapon);
            out.writeByte(character.armorLevel);
            out.writeByte(character.abilityLevels.length);
            for (byte abilityLevel : character.abilityLevels) {
                out.writeByte(abilityLevel);
            }
            out.writeBoolean(character.isSpecialAbilityUnlocked);
        }
        out.writeByte(userData.unlockedWeaponCount);
        for (byte unlockedWeapon : userData.unlockedWeapons) {
            out.writeByte(unlockedWeapon);
        }
        out.writeByte(userData.lastSelectedCharacter);
        out.writeByte(userData.combatLevelReached);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public short getXp() {
        return xp;
    }

    public void setXp(short xp) {
        this.xp = xp;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public byte getUnlockedCharacterCount() {
        return unlockedCharacterCount;
    }

    public void setUnlockedCharacterCount(byte unlockedCharacterCount) {
        this.unlockedCharacterCount = unlockedCharacterCount;
    }

    public CharacterDataa[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterDataa[] characters) {
        this.characters = characters;
    }

    public byte getUnlockedWeaponCount() {
        return unlockedWeaponCount;
    }

    public void setUnlockedWeaponCount(byte unlockedWeaponCount) {
        this.unlockedWeaponCount = unlockedWeaponCount;
    }

    public byte[] getUnlockedWeapons() {
        return unlockedWeapons;
    }

    public void setUnlockedWeapons(byte[] unlockedWeapons) {
        this.unlockedWeapons = unlockedWeapons;
    }

    public byte getLastSelectedCharacter() {
        return lastSelectedCharacter;
    }

    public void setLastSelectedCharacter(byte lastSelectedCharacter) {
        this.lastSelectedCharacter = lastSelectedCharacter;
    }

    public byte getCombatLevelReached() {
        return combatLevelReached;
    }

    public void setCombatLevelReached(byte combatLevelReached) {
        this.combatLevelReached = combatLevelReached;
    }

    public int getArmorLevel() {
        return 0;
    }

    public void setArmorLevel(byte type) {
    }
}

