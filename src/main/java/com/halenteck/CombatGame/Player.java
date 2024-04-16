package com.halenteck.CombatGame;

import java.util.ArrayList;

public class Player {

    protected ArrayList<Weapons> boughtWeapons;//to collect the short range weapons that were bought
    protected int XP;
    protected int x;//the coordinates of the player
    protected int y;//will stay constant
    protected String characterName;
    protected int money;
    protected int XPlimit;
    protected int level;
    protected int characterID = 1;//to determine character IDs
    protected Character playingChar;
    protected Armour[] armours = new Armour[3];
    protected int height;
    protected String nameOfPlayer;
    protected boolean armourExists;// to see if he has an armour
    protected Armour armour;
    protected double realHealth;

    public Player(String name) {//
        nameOfPlayer = name;
        boughtWeapons = new ArrayList<Weapons>();
        money = 0;//both money and xp are 0 at the beginning
        XP = 0;
        XPlimit = 50;//the amount of XP needed to finish the first level
        level = 1;//level starts from 1 not 0.
        armourExists = false;
        x = 40;//the x coordinate the player will appear on
        y = 80;//the y coordinate the player will appear on
        height = 80;
        createArmour();
    }

    public void createArmour() {
        for (int i = 0; i < 3; i++) {
            armours[i] = new Armour(this, i + 1);
        }
    }

    public void checkItemList() {//to get to the next character
        if (playingChar.items[0] && playingChar.items[1] && playingChar.items[2] && playingChar.items[3]) { //called in the 4th location
            characterID++;
        }
    }

    public void checkLevel() {//to level up if necessary
        if (XP >= XPlimit) {
            XP = XP - XPlimit;
            level++;
            XPlimit = XPlimit + 50;//level points are incremented with 50 for every next level.
        }
    }

    public void checkXP() {//to decrease XP when the player gives up
        int pointLoss = 10 + characterID * 5;
        if (XP >= (pointLoss)) {
            XP -= pointLoss;
        } else {
            level--;
            int newPointLoss = pointLoss - XP;
            XPlimit = XPlimit - 50; // to go back to previous level's limit
            XP = XPlimit;
            XP -= newPointLoss;
        }
    }

    public void createCharacter() {
        if (characterID == 1) {
            this.playingChar = new CaveMan(this);
            initializePlayer("Caveman", 50);
        } else if (characterID == 2) {
            this.playingChar = new Panzehir(this);
            initializePlayer("Panzehir", 65);
        } else if (characterID == 3) {
            this.playingChar = new Nazi(this);
            initializePlayer("Nazi", 80);
        } else if (characterID == 4) {
            this.playingChar = new GlobalWarming(this);
            initializePlayer("Global Warming", 95);
        } else if (characterID == 5) {
            this.playingChar = new Boss(this);
            initializePlayer("Boss", 110);
        }
    }

    public void initializePlayer(String characterName, double health) {

        this.characterName = characterName;
        this.playingChar.health = health;
        this.x = 40;
        this.realHealth = health;
    }
}
