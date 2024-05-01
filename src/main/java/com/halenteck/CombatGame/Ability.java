package com.halenteck.CombatGame;


public class Ability {

    protected int usageLeft;
    protected int price;

    public Ability(byte characterID) {
        setAbility(characterID);
    }

    public void setAbility(byte characterID) {
        if (characterID == 1) {//uçmak
            usageLeft = 3;
            price = 6;
        } else if (characterID == 2) {//eğilmek
            usageLeft = 3;
            price = 9;
        } else if (characterID == 3) {//görünmezlik
            usageLeft = 2;
            price = 13;
        } else if (characterID == 4) {//yeri sarsmak
            usageLeft = 2;
            price = 17;
        } else if (characterID == 5) {//afallatmak
            usageLeft = 2;
            price = 21;
        }
    }

    public boolean use() {
        if (usageLeft == 0) {
            return false;
            //düğme inaktifleşmeli
        }
        usageLeft--;
        return true;
    }
}
