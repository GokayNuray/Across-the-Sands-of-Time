package com.halenteck.CombatGame;

public class Armour extends ToolStore {

    protected int type; //"1-> low, 2-> middle, 3-> strong"
    protected int price;
    protected double defence;

    public Armour(Player aGamer, int aType) {//JTextField or a button
        super(aGamer);
        type = aType;
        setPrice();
    }

    public void setPrice() {
        if (type == 1) {
            price = 3;
            defence = 0.5;
        } else if (type == 2) {
            price = 5;
            defence = 1;
        } else if (type == 3) {
            price = 7;
            defence = 1.5;
        }
    }

    public void makePayment() {
        player.money -= price;
        player.armourExists = true;
    }
}
