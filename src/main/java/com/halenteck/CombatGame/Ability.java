package com.halenteck.CombatGame;

public class Ability extends ToolStore {

    protected int useage;
    protected int price;

    public Ability(Player aPerson) {
        super(aPerson);
        setAbility();
    }

    public void setAbility() {
        if (player.characterID == 1) {//uçmak
            useage = 3;
            price = 6;
        } else if (player.characterID == 2) {//eğilmek
            useage = 3;
            price = 9;
        } else if (player.characterID == 3) {//görünmezlik
            useage = 2;
            price = 13;
        } else if (player.characterID == 4) {//yeri sarsmak
            useage = 2;
            price = 17;
        } else if (player.characterID == 5) {//afallatmak
            useage = 2;
            price = 21;
        }
    }

    public void used() {
        useage--;
        if (useage == 0) {
            player.playingChar.abilityExists = false;
            //düğme inaktifleşmeli
        }
    }

    public void makePayment() {
        player.money -= this.price;
    }
}
