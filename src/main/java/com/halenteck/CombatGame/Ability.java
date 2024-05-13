package com.halenteck.CombatGame;


public class Ability {

    public String name;
    public int usageLeft;
    protected int price;

    public Ability(byte characterID) {
        setAbility(characterID);
    }

    public void setAbility(byte characterID) {
        if (characterID == 0) {
            name = "Flying";
            usageLeft = 3;
            price = 6;
        } else if (characterID == 1) {
            name = "Crouching";
            usageLeft = 3;
            price = 9;
        } else if (characterID == 2) {
            name = "Invisibility";
            usageLeft = 2;
            price = 13;
        } else if (characterID == 3) {
            name = "Earthquake";
            usageLeft = 2;
            price = 17;
        } else if (characterID == 4) {
            name = "Stun";
            usageLeft = 2;
            price = 21;
        }
    }

    public boolean use() {
        if (usageLeft == 0) {
            return false;
        }
        usageLeft--;
        return true;
    }
}
