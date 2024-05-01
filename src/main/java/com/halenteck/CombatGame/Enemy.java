package com.halenteck.CombatGame;

import java.util.Random;

public class Enemy {


    // instance variables
    protected int health;
    protected int damage; //the amount of damage the opponent will originally inflict
    protected int number;
    protected int maxNumber;
    protected String name;
    protected int reward;

    public Enemy(String name, int damage, int reward, int health, int maxNumber) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxNumber = maxNumber;
        this.reward = reward;

    }

    public int enemyCount() {
        Random rand = new Random();
        number = (rand.nextInt(maxNumber) + 1);
        return number;
    }

}
