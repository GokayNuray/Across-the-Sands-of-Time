package com.halenteck.CombatGame;
//import javax.swing.JButton;

public class Weapons extends ToolStore { //extend edilmeyecek
    //her karakter için bir weapon objesi yaratılıyor ve onun üstünede oynama yapılıyor
    protected int price;
    protected double performance;//the damage weapon will inflict originally
    protected static int noOfWeapon = 1;
    protected int weaponID;
    //protected JButton buy = new JButton("buy");
    protected boolean isActive;

    public Weapons(Player aForWeapons) {

        super(aForWeapons);

        weaponID = noOfWeapon;
        noOfWeapon++;

        if (noOfWeapon > 10) {
            noOfWeapon = 1;
        }
        setPriceandDamage();
    }

    public void setPriceandDamage() {//sets the price for the advanced weapon

        if (weaponID == 1) {
            performance = 2;
            isActive = true;
        } else if (weaponID == 2) {
            price = 5;
            performance = 3;
            isActive = false;
        } else if (weaponID == 3) {
            performance = 3;
            isActive = true;
        } else if (weaponID == 4) {
            price = 9;
            performance = 5;
            isActive = false;
        } else if (weaponID == 5) {
            performance = 4;
            isActive = true;
        } else if (weaponID == 6) {
            price = 17;
            performance = 7;
            isActive = false;
        } else if (weaponID == 7) {
            performance = 5;
            isActive = true;
        } else if (weaponID == 8) {
            price = 21;
            performance = 8;
            isActive = false;
        } else if (weaponID == 9) {
            performance = 6;
            isActive = true;
        } else if (weaponID == 10) {
            price = 29;
            performance = 9;
            isActive = false;
        }
    }
}