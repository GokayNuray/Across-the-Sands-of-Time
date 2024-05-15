package com.halenteck.CombatGame;


public class Armour {

    protected int type; //"1-> low, 2-> middle, 3-> strong"
    protected int price;
    protected int defence;

    public Armour(int aType) {
        type = aType;
        setPrice();
    }

    public void setPrice() {
        if (type == 1) {
            price = 3;
            defence = 1;
        } else if (type == 2) {
            price = 5;
            defence = 2;
        } else if (type == 3) {
            price = 7;
            defence = 3;
        }
    }
}
