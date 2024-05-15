package com.halenteck.CombatGame;
//import javax.swing.JButton;

public class Weapons {
    protected int price;
    protected int performance;//the damage weapon will inflict originally
    protected int weaponID;
    protected boolean isActive;

    public Weapons(int weaponID) {
        this.weaponID = weaponID;
        setPriceAndDamage();
    }

    public void setPriceAndDamage() {//sets the price for the advanced weapon

        if (weaponID == 0) {
            performance = 2;
            isActive = true;
        } else if (weaponID == 1) {
            price = 5;
            performance = 3;
            isActive = false;
        } else if (weaponID == 2) {
            performance = 3;
            isActive = true;
        } else if (weaponID == 3) {
            price = 9;
            performance = 5;
            isActive = false;
        } else if (weaponID == 4) {
            performance = 4;
            isActive = true;
        } else if (weaponID == 5) {
            price = 17;
            performance = 7;
            isActive = false;
        } else if (weaponID == 6) {
            performance = 5;
            isActive = true;
        } else if (weaponID == 7) {
            price = 21;
            performance = 8;
            isActive = false;
        } else if (weaponID == 8) {
            performance = 6;
            isActive = true;
        } else if (weaponID == 9) {
            price = 29;
            performance = 9;
            isActive = false;
        }
    }
}