package com.halenteck.CombatGame;

import com.halenteck.fpsGame.Player;

import java.util.Random;

public class Enemy {


    protected int health;//initalized at the subclasses
    //when there is more than one enemy
    protected int damage;//the amount of damage the opponent will originally inflict
    protected Player player;
    protected int number;//initalized at subclasses
    protected int maxNumber;
    protected String name;//error'u gidermek için koydum
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

    //karakter oynadıktan sonra enemy oynacak

}
