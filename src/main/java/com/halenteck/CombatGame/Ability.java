package com.halenteck.CombatGame;


public class Ability {

    protected int useage;
    protected int price;

    public Ability(byte characterID) {
        setAbility(characterID);
    }

    public void setAbility(byte characterID) {
        if (characterID == 1) {//uçmak
            useage = 3;
            price = 6;
        } else if (characterID == 2) {//eğilmek
            useage = 3;
            price = 9;
        } else if (characterID == 3) {//görünmezlik
            useage = 2;
            price = 13;
        } else if (characterID == 4) {//yeri sarsmak
            useage = 2;
            price = 17;
        } else if (characterID == 5) {//afallatmak
            useage = 2;
            price = 21;
        }
    }

    public boolean use() {
        if (useage == 0) {
            return false;
            //düğme inaktifleşmeli
        }
        useage--;
        return true;
    }
}
